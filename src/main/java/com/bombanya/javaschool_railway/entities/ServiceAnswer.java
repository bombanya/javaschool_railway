package com.bombanya.javaschool_railway.entities;

import com.bombanya.javaschool_railway.utils.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
@Builder
public class ServiceAnswer<T> {

    @JsonView(JacksonView.MinimalInfo.class)
    private final T serviceResult;
    private final boolean success;
    private final HttpStatus httpStatus;
    @JsonView(JacksonView.MinimalInfo.class)
    private final String errorMessage;


}
