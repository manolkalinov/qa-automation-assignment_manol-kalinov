package com.avenga.bookstore.framework.client;

import com.avenga.bookstore.framework.WithLogging;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseHttpClient extends WithLogging {

  protected final OkHttpClient httpClient;
  protected final ObjectMapper objectMapper;
  protected final String baseUrl;

  protected BaseHttpClient(OkHttpClient httpClient, ObjectMapper objectMapper, String baseUrl) {
    this.httpClient = httpClient;
    this.objectMapper = objectMapper;
    this.baseUrl = normalizeBaseUrl(baseUrl);
  }

  protected String normalizeBaseUrl(String raw) {
    return raw.endsWith("/") ? raw.substring(0, raw.length() - 1) : raw;
  }

  protected <T> ApiResponse<T> executeGet(String path, Class<T> bodyType) {
    String fullUrl = baseUrl + path;
    log.debug("Request: GET {}", fullUrl);

    Request request = new Request.Builder().url(fullUrl).get().build();
    int statusCode;
    T body = null;
    Map<String, String> headers;

    try (Response response = httpClient.newCall(request).execute()) {
      statusCode = response.code();
      headers = extractHeaders(response);
      String responseContent = readBody(response);
      log.debug("Response: {} | body: {}", statusCode, responseContent);
      if (response.isSuccessful() && !responseContent.isBlank()) {
        body = objectMapper.readValue(responseContent, bodyType);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to execute GET " + path, e);
    }

    return new ApiResponse<>(statusCode, body, headers);
  }

  protected <T> ApiResponse<T> executeGet(String path, TypeReference<T> typeRef) {
    String fullUrl = baseUrl + path;
    log.debug("Request: GET {}", fullUrl);

    Request request = new Request.Builder().url(fullUrl).get().build();
    int statusCode;
    T body = null;
    Map<String, String> headers;

    try (Response response = httpClient.newCall(request).execute()) {
      statusCode = response.code();
      headers = extractHeaders(response);
      String responseContent = readBody(response);
      log.debug("Response: {} | body: {}", statusCode, responseContent);
      if (response.isSuccessful() && !responseContent.isBlank()) {
        body = objectMapper.readValue(responseContent, typeRef);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to execute GET " + path, e);
    }

    return new ApiResponse<>(statusCode, body, headers);
  }

  protected <T> ApiResponse<T> executePost(String path, Object payload, Class<T> bodyType) {
    String fullUrl = baseUrl + path;
    log.debug("Request: POST {}", fullUrl);

    Request request = new Request.Builder()
        .url(fullUrl)
        .post(buildJsonBody(payload, path))
        .build();

    int statusCode;
    T body = null;
    Map<String, String> headers;

    try (Response response = httpClient.newCall(request).execute()) {
      statusCode = response.code();
      headers = extractHeaders(response);
      String responseContent = readBody(response);
      log.debug("Response: {} | body: {}", statusCode, responseContent);
      if (response.isSuccessful() && !responseContent.isBlank()) {
        body = objectMapper.readValue(responseContent, bodyType);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to execute POST " + path, e);
    }

    return new ApiResponse<>(statusCode, body, headers);
  }

  protected ApiResponse<Void> executePost(String path, String rawJson) {
    String fullUrl = baseUrl + path;
    log.debug("Request: POST {}", fullUrl);
    RequestBody requestBody = RequestBody.create(rawJson, MediaType.get("application/json; charset=utf-8"));
    Request request = new Request.Builder().url(fullUrl).post(requestBody).build();
    int statusCode;
    Map<String, String> headers;
    try (Response response = httpClient.newCall(request).execute()) {
      statusCode = response.code();
      headers = extractHeaders(response);
      String responseContent = readBody(response);
      log.debug("Response: {} | body: {}", statusCode, responseContent);
    } catch (IOException e) {
      throw new RuntimeException("Failed to execute POST " + path, e);
    }
    return new ApiResponse<>(statusCode, null, headers);
  }

  protected <T> ApiResponse<T> executePut(String path, Object payload, Class<T> bodyType) {
    String fullUrl = baseUrl + path;
    log.debug("Request: PUT {}", fullUrl);

    Request request = new Request.Builder()
        .url(fullUrl)
        .put(buildJsonBody(payload, path))
        .build();

    int statusCode;
    T body = null;
    Map<String, String> headers;

    try (Response response = httpClient.newCall(request).execute()) {
      statusCode = response.code();
      headers = extractHeaders(response);
      String responseContent = readBody(response);
      log.debug("Response: {} | body: {}", statusCode, responseContent);
      if (response.isSuccessful() && !responseContent.isBlank()) {
        body = objectMapper.readValue(responseContent, bodyType);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to execute PUT " + path, e);
    }

    return new ApiResponse<>(statusCode, body, headers);
  }

  protected ApiResponse<Void> executePut(String path, String rawJson) {
    String fullUrl = baseUrl + path;
    log.debug("Request: PUT {}", fullUrl);
    RequestBody requestBody = RequestBody.create(rawJson, MediaType.get("application/json; charset=utf-8"));
    Request request = new Request.Builder().url(fullUrl).put(requestBody).build();
    int statusCode;
    Map<String, String> headers;
    try (Response response = httpClient.newCall(request).execute()) {
      statusCode = response.code();
      headers = extractHeaders(response);
      String responseContent = readBody(response);
      log.debug("Response: {} | body: {}", statusCode, responseContent);
    } catch (IOException e) {
      throw new RuntimeException("Failed to execute PUT " + path, e);
    }
    return new ApiResponse<>(statusCode, null, headers);
  }

  protected ApiResponse<Void> executeDelete(String path) {
    String fullUrl = baseUrl + path;
    log.debug("Request: DELETE {}", fullUrl);

    Request request = new Request.Builder().url(fullUrl).delete().build();
    int statusCode;
    Map<String, String> headers;

    try (Response response = httpClient.newCall(request).execute()) {
      statusCode = response.code();
      headers = extractHeaders(response);
      String responseContent = readBody(response);
      log.debug("Response: {} | body: {}", statusCode, responseContent);
    } catch (IOException e) {
      throw new RuntimeException("Failed to execute DELETE " + path, e);
    }

    return new ApiResponse<>(statusCode, null, headers);
  }

  protected Map<String, String> extractHeaders(Response response) {
    Map<String, String> headers = new LinkedHashMap<>();
    for (String name : response.headers().names()) {
      headers.put(name, response.header(name));
    }
    return headers;
  }

  protected String readBody(Response response) throws IOException {
    ResponseBody responseBody = response.body();
    return responseBody != null ? responseBody.string() : "";
  }

  protected RequestBody buildJsonBody(Object payload, String path) {
    try {
      String json = objectMapper.writeValueAsString(payload);
      return RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to serialize request payload for " + path, e);
    }
  }
}
