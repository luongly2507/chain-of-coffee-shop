package com.app.coffee.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageResponse{
    private String message;
    private int statusCode;
    private LocalDateTime timeStamp;
}
