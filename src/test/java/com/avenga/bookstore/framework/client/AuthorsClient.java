package com.avenga.bookstore.framework.client;

import com.avenga.bookstore.framework.model.Author;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

import java.util.List;

public class AuthorsClient extends BaseHttpClient {

  public AuthorsClient(OkHttpClient httpClient, ObjectMapper objectMapper, ApiConfig apiConfig) {
    super(httpClient, objectMapper, apiConfig.baseUrl());
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

  public ApiResponse<Void> createAuthor(String rawJson) {
    ApiResponse<Void> response = executePost("/api/v1/Authors", rawJson);
    return response;
  }

  public ApiResponse<Author> updateAuthor(int id, Author author) {
    ApiResponse<Author> response = executePut("/api/v1/Authors/" + id, author, Author.class);
    return response;
  }

  public ApiResponse<Void> updateAuthor(int id, String rawJson) {
    ApiResponse<Void> response = executePut("/api/v1/Authors/" + id, rawJson);
    return response;
  }

  public ApiResponse<Void> deleteAuthor(int id) {
    ApiResponse<Void> response = executeDelete("/api/v1/Authors/" + id);
    return response;
  }

  public ApiResponse<Void> deleteAuthor(String id) {
    ApiResponse<Void> response = executeDelete("/api/v1/Authors/" + id);
    return response;
  }
}
