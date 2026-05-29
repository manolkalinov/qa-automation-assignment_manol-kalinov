package com.avenga.bookstore.framework.client;

import com.avenga.bookstore.framework.model.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

import java.util.List;

public class BooksClient extends BaseHttpClient {

  public BooksClient(OkHttpClient httpClient, ObjectMapper objectMapper, ApiConfig apiConfig) {
    super(httpClient, objectMapper, apiConfig.baseUrl());
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

  public ApiResponse<Void> createBook(String rawJson) {
    ApiResponse<Void> response = executePost("/api/v1/Books", rawJson);
    return response;
  }

  public ApiResponse<Book> updateBook(int id, Book book) {
    ApiResponse<Book> response = executePut("/api/v1/Books/" + id, book, Book.class);
    return response;
  }

  public ApiResponse<Void> updateBook(int id, String rawJson) {
    ApiResponse<Void> response = executePut("/api/v1/Books/" + id, rawJson);
    return response;
  }

  public ApiResponse<Void> deleteBook(int id) {
    ApiResponse<Void> response = executeDelete("/api/v1/Books/" + id);
    return response;
  }

  public ApiResponse<Void> deleteBook(String id) {
    ApiResponse<Void> response = executeDelete("/api/v1/Books/" + id);
    return response;
  }
}
