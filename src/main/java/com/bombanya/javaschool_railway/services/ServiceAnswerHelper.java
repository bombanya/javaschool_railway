package com.bombanya.javaschool_railway.services;

import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ServiceAnswerHelper {

    public static <T> ServiceAnswer<T> ok(T serviceResult){
        return new ServiceAnswer<>(serviceResult, true, HttpStatus.OK, null);
    }

    public static <T> ResponseEntity<ServiceAnswer<T>> wrapIntoResponse(ServiceAnswer<T> answer){
        return ResponseEntity.status(answer.getHttpStatus()).body(answer);
    }
}
