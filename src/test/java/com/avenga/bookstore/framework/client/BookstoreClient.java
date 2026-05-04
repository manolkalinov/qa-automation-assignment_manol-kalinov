package com.avenga.bookstore.framework.client;

import com.avenga.bookstore.framework.WithLogging;
import com.avenga.bookstore.framework.model.Author;
import com.avenga.bookstore.framework.model.Book;
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
import java.util.List;
import java.util.Map;

public class BookstoreClient extends WithLogging {

  private final OkHttpClient httpClient;
  private final ObjectMapper objectMapper;
  private final String baseUrl;

  public BookstoreClient(OkHttpClient httpClient, ObjectMapper objectMapper, ApiConfig apiConfig) {
    this.httpClient = httpClient;
    this.objectMapper = objectMapper;
    this.baseUrl = normalizeBaseUrl(apiConfig.baseUrl());
  }

  private String normalizeBaseUrl(String raw) {
    String normalized = raw.endsWith("/") ? raw.substring(0, raw.length() - 1) : raw;
    return normalized;
  }

  public ApiResponse<List<Book>> listBooks() {
    ApiResponse<List<Book>> response = executeGet("/api/v1/Books", new TypeReference<List<Book>>() {});
    return response;
  }

  public ApiResponse<Book> retrieveBookById(int id) {
    ApiResponse<Book> response = executeGet("/api/v1/Books/" + id, Book.class);
    return response;
  }

  public ApiResponse<Book> retrieveBookById(String id) {
    ApiResponse<Book> response = executeGet("/api/v1/Books/" + id, Book.class);
    return response;
  }

  public ApiResponse<Book> createBook(Book book) {
    ApiResponse<Book> response = executePost("/api/v1/Books", book, Book.class);
    return response;
  }

  public ApiResponse<Book> createBook(String rawJson) {
    String path = "/api/v1/Books";
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
    ApiResponse<Book> result = new ApiResponse<>(statusCode, null, headers);
    return result;
  }

  public ApiResponse<Book> updateBook(int id, Book book) {
    ApiResponse<Book> response = executePut("/api/v1/Books/" + id, book, Book.class);
    return response;
  }

  public ApiResponse<Book> updateBook(int id, String rawJson) {
    String path = "/api/v1/Books/" + id;
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
    ApiResponse<Book> result = new ApiResponse<>(statusCode, null, headers);
    return result;
  }

  public ApiResponse<Void> deleteBook(int id) {
    ApiResponse<Void> response = executeDelete("/api/v1/Books/" + id);
    return response;
  }

  public ApiResponse<Void> deleteBook(String id) {
    ApiResponse<Void> response = executeDelete("/api/v1/Books/" + id);
    return response;
  }

  public ApiResponse<List<Author>> listAuthors() {
    ApiResponse<List<Author>> response = executeGet("/api/v1/Authors", new TypeReference<List<Author>>() {});
    return response;
  }

  public ApiResponse<Author> retrieveAuthorById(int id) {
    ApiResponse<Author> response = executeGet("/api/v1/Authors/" + id, Author.class);
    return response;
  }

  public ApiResponse<Author> retrieveAuthorById(String id) {
    ApiResponse<Author> response = executeGet("/api/v1/Authors/" + id, Author.class);
    return response;
  }

  public ApiResponse<List<Author>> listAuthorsByBookId(int idBook) {
    ApiResponse<List<Author>> response = executeGet("/api/v1/Authors/authors/books/" + idBook, new TypeReference<List<Author>>() {});
    return response;
  }

  public ApiResponse<Author> createAuthor(Author author) {
    ApiResponse<Author> response = executePost("/api/v1/Authors", author, Author.class);
    return response;
  }

  public ApiResponse<Author> createAuthor(String rawJson) {
    String path = "/api/v1/Authors";
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
    ApiResponse<Author> result = new ApiResponse<>(statusCode, null, headers);
    return result;
  }

  public ApiResponse<Author> updateAuthor(int id, Author author) {
    ApiResponse<Author> response = executePut("/api/v1/Authors/" + id, author, Author.class);
    return response;
  }

  public ApiResponse<Author> updateAuthor(int id, String rawJson) {
    String path = "/api/v1/Authors/" + id;
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
    ApiResponse<Author> result = new ApiResponse<>(statusCode, null, headers);
    return result;
  }

  public ApiResponse<Void> deleteAuthor(int id) {
    ApiResponse<Void> response = executeDelete("/api/v1/Authors/" + id);
    return response;
  }

  public ApiResponse<Void> deleteAuthor(String id) {
    ApiResponse<Void> response = executeDelete("/api/v1/Authors/" + id);
    return response;
  }

  private <T> ApiResponse<T> executeGet(String path, Class<T> bodyType) {
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

    ApiResponse<T> result = new ApiResponse<>(statusCode, body, headers);
    return result;
  }

  private <T> ApiResponse<T> executeGet(String path, TypeReference<T> typeRef) {
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

    ApiResponse<T> result = new ApiResponse<>(statusCode, body, headers);
    return result;
  }

  private <T> ApiResponse<T> executePost(String path, Object payload, Class<T> bodyType) {
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

    ApiResponse<T> result = new ApiResponse<>(statusCode, body, headers);
    return result;
  }

  private <T> ApiResponse<T> executePut(String path, Object payload, Class<T> bodyType) {
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

    ApiResponse<T> result = new ApiResponse<>(statusCode, body, headers);
    return result;
  }

  private ApiResponse<Void> executeDelete(String path) {
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

    ApiResponse<Void> result = new ApiResponse<>(statusCode, null, headers);
    return result;
  }

  private Map<String, String> extractHeaders(Response response) {
    Map<String, String> headers = new LinkedHashMap<>();
    for (String name : response.headers().names()) {
      headers.put(name, response.header(name));
    }
    return headers;
  }

  private String readBody(Response response) throws IOException {
    ResponseBody responseBody = response.body();
    String body = responseBody != null ? responseBody.string() : "";
    return body;
  }

  private RequestBody buildJsonBody(Object payload, String path) {
    RequestBody requestBody;
    try {
      String json = objectMapper.writeValueAsString(payload);
      requestBody = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to serialize request payload for " + path, e);
    }
    return requestBody;
  }
}
