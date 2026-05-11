package com.avenga.bookstore.tests.api.authors;

import com.avenga.bookstore.framework.client.AuthorsClient;
import com.avenga.bookstore.tests.api.BaseApiTest;
import com.google.inject.Inject;

public abstract class AuthorsBaseApiTest extends BaseApiTest {

  @Inject
  protected AuthorsClient authorsClient;
}
