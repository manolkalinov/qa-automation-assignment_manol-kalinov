package com.avenga.bookstore.framework.test.dependency;

import com.avenga.bookstore.framework.client.ApiConfig;
import com.avenga.bookstore.framework.client.AuthorsClient;
import com.avenga.bookstore.framework.client.BooksClient;
import com.avenga.bookstore.framework.test.environment.TestEnvironmentApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.Properties;

public class TestDependencyAssemblyApi implements Module {

  @Override
  public void configure(Binder binder) {
  }

  @Provides
  @Singleton
  @Named("bookstore")
  public ApiConfig provideBookstoreApiConfig(@Named("api.env") String envName) {
    var properties = new Properties();
    String fileName = String.format("test-environments/%s.properties", envName);
    try (var stream = getClass().getClassLoader().getResourceAsStream(fileName)) {
      if (stream == null) {
        throw new IllegalStateException("Cannot find environment file: " + fileName);
      }
      properties.load(stream);
      return new ApiConfig(properties.getProperty("bookstore.baseUrl"), "");
    } catch (IOException e) {
      throw new RuntimeException("Failed to load bookstore configuration for env: " + envName, e);
    }
  }

  @Provides
  @Singleton
  public BooksClient provideBooksClient(
      OkHttpClient httpClient,
      ObjectMapper objectMapper,
      @Named("bookstore") ApiConfig apiConfig) {
    return new BooksClient(httpClient, objectMapper, apiConfig);
  }

  @Provides
  @Singleton
  public AuthorsClient provideAuthorsClient(
      OkHttpClient httpClient,
      ObjectMapper objectMapper,
      @Named("bookstore") ApiConfig apiConfig) {
    return new AuthorsClient(httpClient, objectMapper, apiConfig);
  }

  @Provides
  @Singleton
  public TestEnvironmentApi provideTestEnvironmentApi(@Named("api.env") String envName) {
    return new TestEnvironmentApi(envName);
  }
}
