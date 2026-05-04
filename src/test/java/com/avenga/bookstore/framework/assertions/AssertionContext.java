package com.avenga.bookstore.framework.assertions;

import org.assertj.core.api.SoftAssertions;

public final class AssertionContext {

  private static final ThreadLocal<SoftAssertions> holder = new ThreadLocal<>();

  private AssertionContext() {}

  public static void init() {
    holder.set(new SoftAssertions());
  }

  public static SoftAssertions current() {
    SoftAssertions softAssertions = holder.get();
    if (softAssertions == null) {
      throw new IllegalStateException(
          "AssertionContext not initialised for this thread — was AssertionContext.init() called?");
    }
    return softAssertions;
  }

  public static void flush() {
    try {
      current().assertAll();
    } finally {
      holder.remove();
    }
  }
}
