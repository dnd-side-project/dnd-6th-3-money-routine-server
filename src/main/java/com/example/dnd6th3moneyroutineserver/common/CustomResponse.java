package com.example.dnd6th3moneyroutineserver.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CustomResponse<T> {

    private int statusCode;
    private String responseMessage;
    private T data;

    public CustomResponse(final int statusCode, final String responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data = null;
    }

    public static<T> CustomResponse<T> response(final int statusCode, final String responseMessage) {
        return response(statusCode, responseMessage, null);
    }

    public static<T> CustomResponse<T> response(final int statusCode, final String responseMessage, final T data) {
        return CustomResponse.<T>builder()
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .data(data)
                .build();
    }
}
