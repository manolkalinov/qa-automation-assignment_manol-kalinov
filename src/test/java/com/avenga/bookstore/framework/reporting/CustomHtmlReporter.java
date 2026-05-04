package com.avenga.bookstore.framework.reporting;

import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CustomHtmlReporter implements IReporter {

  @Override
  public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
    Path reportDir = Path.of(outputDirectory, "custom-report");
    Path reportFile = reportDir.resolve("report.html");

    try {
      Files.createDirectories(reportDir);
    } catch (IOException e) {
      throw new RuntimeException("Failed to create report directory: " + reportDir, e);
    }

    List<ITestResult> allResults = collectAllResults(suites);
    allResults.sort(Comparator.comparingLong(ITestResult::getStartMillis));

    try (BufferedWriter writer = Files.newBufferedWriter(reportFile)) {
      writeHtmlReport(writer, allResults);
    } catch (IOException e) {
      throw new RuntimeException("Failed to write custom HTML report: " + reportFile, e);
    }
  }

  private List<ITestResult> collectAllResults(List<ISuite> suites) {
    List<ITestResult> results = new ArrayList<>();

    for (ISuite suite : suites) {
      Map<String, ISuiteResult> suiteResults = suite.getResults();
      for (ISuiteResult suiteResult : suiteResults.values()) {
        ITestContext context = suiteResult.getTestContext();

        results.addAll(context.getPassedTests().getAllResults());
        results.addAll(context.getFailedTests().getAllResults());
        results.addAll(context.getSkippedTests().getAllResults());
      }
    }

    return results;
  }

  private void writeHtmlReport(BufferedWriter writer, List<ITestResult> results) throws IOException {
    writer.write("<!DOCTYPE html>");
    writer.newLine();
    writer.write("<html lang=\"en\">");
    writer.newLine();
    writer.write("<head>");
    writer.newLine();
    writer.write("<meta charset=\"UTF-8\"/>");
    writer.newLine();
    writer.write("<title>Avenga Assessment - Test Report</title>");
    writer.newLine();
    writer.write("<style>");
    writer.newLine();
    writer.write("body { font-family: Arial, sans-serif; margin: 20px; }");
    writer.newLine();
    writer.write("h1 { font-size: 20px; }");
    writer.newLine();
    writer.write("table { border-collapse: collapse; width: 100%; }");
    writer.newLine();
    writer.write("th, td { border: 1px solid #ddd; padding: 8px; font-size: 13px; }");
    writer.newLine();
    writer.write("th { background-color: #f2f2f2; text-align: left; }");
    writer.newLine();
    writer.write(".status-PASS { color: #2e7d32; font-weight: bold; }");
    writer.newLine();
    writer.write(".status-FAIL { color: #c62828; font-weight: bold; }");
    writer.newLine();
    writer.write(".status-SKIP { color: #6d6d6d; font-weight: bold; }");
    writer.newLine();
    writer.write(".small { font-size: 11px; color: #555; }");
    writer.newLine();
    writer.write("</style>");
    writer.newLine();
    writer.write("</head>");
    writer.newLine();
    writer.write("<body>");
    writer.newLine();
    writer.write("<h1>Avenga Senior QA Automation Assessment - Custom HTML Report</h1>");
    writer.newLine();

    writer.write("<table>");
    writer.newLine();
    writer.write("<tr>");
    writer.write("<th>#</th>");
    writer.write("<th>Test</th>");
    writer.write("<th>Class</th>");
    writer.write("<th>Groups</th>");
    writer.write("<th>Status</th>");
    writer.write("<th>Duration (ms)</th>");
    writer.write("<th>Error</th>");
    writer.write("</tr>");
    writer.newLine();

    int index = 1;
    for (ITestResult result : results) {
      String methodName = result.getMethod().getMethodName();
      String className = result.getTestClass().getName();
      String[] groups = result.getMethod().getGroups();
      String joinedGroups = String.join(", ", groups);

      String status = mapStatus(result.getStatus());
      String statusClass = "status-" + status;

      long duration = result.getEndMillis() - result.getStartMillis();
      String error = buildErrorMessage(result);

      writer.write("<tr>");
      writer.write("<td>" + index++ + "</td>");
      writer.write("<td>" + escape(methodName) + "</td>");
      writer.write("<td class=\"small\">" + escape(className) + "</td>");
      writer.write("<td class=\"small\">" + escape(joinedGroups) + "</td>");
      writer.write("<td class=\"" + statusClass + "\">" + status + "</td>");
      writer.write("<td>" + duration + "</td>");
      writer.write("<td class=\"small\">" + escape(error) + "</td>");
      writer.write("</tr>");
      writer.newLine();
    }

    writer.write("</table>");
    writer.newLine();
    writer.write("</body>");
    writer.newLine();
    writer.write("</html>");
    writer.newLine();
  }

  private String mapStatus(int status) {
    return switch (status) {
      case ITestResult.SUCCESS -> "PASS";
      case ITestResult.FAILURE -> "FAIL";
      case ITestResult.SKIP -> "SKIP";
      default -> "UNKNOWN";
    };
  }

  private String buildErrorMessage(ITestResult result) {
    Throwable throwable = result.getThrowable();
    if (throwable == null) {
      return "";
    }
    String message = throwable.getMessage();
    if (message == null || message.isBlank()) {
      return throwable.getClass().getSimpleName();
    }
    return message;
  }

  private String escape(String text) {
    if (text == null) {
      return "";
    }
    return text
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;");
  }
}
