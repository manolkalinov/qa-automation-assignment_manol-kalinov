package com.avenga.bookstore.framework;

import com.avenga.bookstore.framework.assertions.AssertionContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestLifecycleManager extends TestListenerAdapter {

  private final TestLoggingListener loggingListener = new TestLoggingListener();

  @Override
  public void onTestStart(ITestResult result) {
    super.onTestStart(result);
    AssertionContext.init();
    loggingListener.onTestStart(
        result.getTestClass().getRealClass().getSimpleName(),
        result.getMethod().getMethodName()
    );
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    super.onTestSuccess(result);
    loggingListener.onTestSuccess();
    AssertionContext.flush();
  }

  @Override
  public void onTestFailure(ITestResult result) {
    super.onTestFailure(result);
    loggingListener.onTestFailure();
    AssertionContext.flush();
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    super.onTestSkipped(result);
    loggingListener.onTestSkipped();
    AssertionContext.flush();
  }
}
