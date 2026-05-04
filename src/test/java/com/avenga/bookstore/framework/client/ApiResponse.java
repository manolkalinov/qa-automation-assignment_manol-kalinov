package com.avenga.bookstore.framework.client;

import java.util.Map;

public record ApiResponse<T>(int statusCode, T body, Map<String, String> headers) {}
