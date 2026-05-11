package com.avenga.bookstore.tests.api.books;

import com.avenga.bookstore.framework.client.BooksClient;
import com.avenga.bookstore.tests.api.BaseApiTest;
import com.google.inject.Inject;

public abstract class BooksBaseApiTest extends BaseApiTest {

  @Inject
  protected BooksClient booksClient;
}
