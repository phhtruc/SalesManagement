package com.skyline.SalesManager.util;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseUtil {

    public ResponseEntity<Map<String, Object>> createSuccessResponse(Object data, String selfLink) {
        return createResponse(200, "Success", data, selfLink);
    }

    public ResponseEntity<Map<String, Object>> createNotFoundResponse() {
        return createResponse(404, "Not Found", null, null);
    }

    public ResponseEntity<Map<String, Object>> createErrorResponse(String message) {
        return createResponse(500, "Error", message, null);
    }

    private ResponseEntity<Map<String, Object>> createResponse(int status, String message, Object data, String selfLink) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("headers", Map.of(
                "Content-Type", "application/json",
                "Content-Length", "123"
        ));
        response.put("data", data);
        response.put("links", selfLink != null ? Map.of("self", selfLink) : null);
        response.put("metadata", Map.of(
                "version", "1.0",
                "timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        ));

        return ResponseEntity.status(status).body(response);
    }
}

