package com.avenga.bookstore.framework.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Book(
    int id,
    String title,
    String description,
    @JsonProperty("pageCount") int pageCount,
    String excerpt,
    @JsonProperty("publishDate") String publishDate
) {}
