package com.avenga.bookstore.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLoggingListener {

  private final Logger log = LoggerFactory.getLogger(getClass());

  void onTestStart(String className, String methodName) {
    log.debug("\n");
    logSeparationLine();
    log.debug("Test start: '{}.{}'", className, methodName);
    logSeparationLine();
  }

  void onTestSuccess() {
    log.debug("SUCCESS");
    logSeparationLine();
  }

  void onTestFailure() {
    log.debug("FAIL");
    logSeparationLine();
  }

  void onTestSkipped() {
    log.debug("SKIPPED");
    logSeparationLine();
  }

  private void logSeparationLine() {
    log.debug("===========================================");
  }
}
