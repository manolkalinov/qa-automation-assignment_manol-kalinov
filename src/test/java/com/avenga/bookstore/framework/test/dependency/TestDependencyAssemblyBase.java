package com.avenga.bookstore.framework.test.dependency;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE;

public class TestDependencyAssemblyBase implements Module {

  @Override
  public void configure(Binder binder) {
  }

  @Provides
  @Singleton
  public ObjectMapper provideObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.setPropertyNamingStrategy(SNAKE_CASE);
    return objectMapper;
  }

  @Provides
  @Singleton
  public OkHttpClient provideOkHttpClient() {
    OkHttpClient httpClient = new Builder().build();
    return httpClient;
  }

  @Provides
  @Singleton
  @Named("api.env")
  public String provideApiEnv() {
    String propertyApiEnv = System.getProperty("api.env", "dev");
    return propertyApiEnv;
  }
}
