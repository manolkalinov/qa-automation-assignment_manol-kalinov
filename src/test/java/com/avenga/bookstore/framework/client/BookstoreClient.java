package com.avenga.bookstore.framework.client;

import com.avenga.bookstore.framework.model.Author;
import com.avenga.bookstore.framework.model.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

import java.util.List;

public class BookstoreClient extends BaseHttpClient {

  public BookstoreClient(OkHttpClient httpClient, ObjectMapper objectMapper, ApiConfig apiConfig) {
    super(httpClient, objectMapper, apiConfig.baseUrl());
  }

  public ApiResponse<List<Book>> listBooks() {
    return executeGet("/api/v1/Books", new TypeReference<List<Book>>() {});
  }

  public ApiResponse<Book> retrieveBookById(int id) {
    return executeGet("/api/v1/Books/" + id, Book.class);
  }

  public ApiResponse<Book> retrieveBookById(String id) {
    return executeGet("/api/v1/Books/" + id, Book.class);
  }

  public ApiResponse<Book> createBook(Book book) {
    return executePost("/api/v1/Books", book, Book.class);
  }

  public ApiResponse<Void> createBook(String rawJson) {
    return executePost("/api/v1/Books", rawJson);
  }

  public ApiResponse<Book> updateBook(int id, Book book) {
    return executePut("/api/v1/Books/" + id, book, Book.class);
  }

  public ApiResponse<Void> updateBook(int id, String rawJson) {
    return executePut("/api/v1/Books/" + id, rawJson);
  }

  public ApiResponse<Void> deleteBook(int id) {
    return executeDelete("/api/v1/Books/" + id);
  }

  public ApiResponse<Void> deleteBook(String id) {
    return executeDelete("/api/v1/Books/" + id);
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
