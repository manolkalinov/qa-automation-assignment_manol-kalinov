package com.avenga.bookstore.tests.api;

import com.avenga.bookstore.framework.TestLifecycleManager;
import com.avenga.bookstore.framework.WithLogging;
import com.avenga.bookstore.framework.assertions.AssertionContext;
import com.avenga.bookstore.framework.client.AuthorsClient;
import com.avenga.bookstore.framework.client.BooksClient;
import com.avenga.bookstore.framework.reporting.CustomHtmlReporter;
import com.avenga.bookstore.framework.test.dependency.TestDependencyAssemblyApi;
import com.avenga.bookstore.framework.test.dependency.TestDependencyAssemblyBase;
import com.google.inject.Inject;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Guice;
import org.testng.annotations.Listeners;

@Listeners({TestLifecycleManager.class, CustomHtmlReporter.class})
@Guice(modules = {TestDependencyAssemblyBase.class, TestDependencyAssemblyApi.class})
public abstract class BaseApiTest extends WithLogging {

  @Inject
  protected BooksClient booksClient;

  @Inject
  protected AuthorsClient authorsClient;

  protected SoftAssertions softly() {
    SoftAssertions softAssertions = AssertionContext.current();
    return softAssertions;
  }
}
