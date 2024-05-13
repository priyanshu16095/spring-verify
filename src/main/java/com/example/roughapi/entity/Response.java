package com.example.roughapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@SuperBuilder
@JsonInclude(NON_NULL)
public class Response {
    protected String timestamp;
    protected HttpStatus httpStatus;
    protected int statusCode;
    protected String message;
    protected String path;
    protected Boolean enabled;
    protected Map<?, ?> data;
}
