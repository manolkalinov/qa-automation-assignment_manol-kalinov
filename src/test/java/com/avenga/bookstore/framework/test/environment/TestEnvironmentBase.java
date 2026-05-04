package com.avenga.bookstore.framework.test.environment;

import java.io.IOException;
import java.util.Properties;

public abstract class TestEnvironmentBase {

  protected final Properties properties;

  protected TestEnvironmentBase(String environmentName) {
    this.properties = load(environmentName);
  }

  private Properties load(String environmentName) {
    String resourceName = "test-environments/" + environmentName + ".properties";

    Properties props = new Properties();
    try (
        var stream = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream(resourceName)
    ) {
      if (stream == null) {
        throw new IllegalStateException("Missing env file: " + resourceName);
      }
      props.load(stream);
      return props;
    } catch (IOException e) {
      throw new RuntimeException("Failed loading env file: " + resourceName, e);
    }
  }
}
