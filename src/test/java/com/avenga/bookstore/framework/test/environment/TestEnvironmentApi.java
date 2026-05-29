package com.avenga.bookstore.framework.test.environment;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class TestEnvironmentApi extends TestEnvironmentBase {

  @Inject
  public TestEnvironmentApi(@Named("api.env") String envName) {
    super(envName);
  }

  public String getBaseUrl() {
    String baseUrl = properties.getProperty("api.baseUrl");
    return baseUrl;
  }

  public String getApiKey() {
    String apiKey = properties.getProperty("api.key");
    return apiKey;
  }
}
