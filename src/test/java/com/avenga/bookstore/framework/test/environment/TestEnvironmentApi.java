package com.avenga.bookstore.framework.test.environment;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class TestEnvironmentApi extends TestEnvironmentBase {

  @Inject
  public TestEnvironmentApi(@Named("api.env") String envName) {
    super(envName);
  }

  public String getBaseUrl() {
    return properties.getProperty("api.baseUrl");
  }

  public String getApiKey() {
    return properties.getProperty("api.key");
  }
}
