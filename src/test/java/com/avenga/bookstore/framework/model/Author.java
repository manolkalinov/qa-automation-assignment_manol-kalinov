package com.avenga.bookstore.framework.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Author(
    int id,
    @JsonProperty("idBook") int idBook,
    @JsonProperty("firstName") String firstName,
    @JsonProperty("lastName") String lastName
) {}
