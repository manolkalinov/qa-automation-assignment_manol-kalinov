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
    return executeGet("/api/v1/Authors", new TypeReference<List<Author>>() {});
  }

  public ApiResponse<Author> retrieveAuthorById(int id) {
    return executeGet("/api/v1/Authors/" + id, Author.class);
  }

  public ApiResponse<Author> retrieveAuthorById(String id) {
    return executeGet("/api/v1/Authors/" + id, Author.class);
  }

  public ApiResponse<List<Author>> listAuthorsByBookId(int idBook) {
    return executeGet("/api/v1/Authors/authors/books/" + idBook, new TypeReference<List<Author>>() {});
  }

  public ApiResponse<Author> createAuthor(Author author) {
    return executePost("/api/v1/Authors", author, Author.class);
  }

  public ApiResponse<Void> createAuthor(String rawJson) {
    return executePost("/api/v1/Authors", rawJson);
  }

  public ApiResponse<Author> updateAuthor(int id, Author author) {
    return executePut("/api/v1/Authors/" + id, author, Author.class);
  }

  public ApiResponse<Void> updateAuthor(int id, String rawJson) {
    return executePut("/api/v1/Authors/" + id, rawJson);
  }

  public ApiResponse<Void> deleteAuthor(int id) {
    return executeDelete("/api/v1/Authors/" + id);
  }

  public ApiResponse<Void> deleteAuthor(String id) {
    return executeDelete("/api/v1/Authors/" + id);
  }
}
