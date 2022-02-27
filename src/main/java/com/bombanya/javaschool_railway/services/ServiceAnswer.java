package com.bombanya.javaschool_railway.services;

import com.bombanya.javaschool_railway.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
@Builder
public class ServiceAnswer<T> {

    @JsonView(JacksonView.UserInfo.class)
    private final T serviceResult;
    private final boolean success;
    private final HttpStatus httpStatus;
    @JsonView(JacksonView.UserInfo.class)
    private final String errorMessage;
}