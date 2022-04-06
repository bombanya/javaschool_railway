package com.bombanya.javaschool_railway.security.services;

import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.security.dao.UserRoleDAO;
import com.bombanya.javaschool_railway.security.entities.UserRoleEntity;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleDAO dao;

    @Transactional(readOnly = true)
    public ServiceAnswer<UserRoleEntity> getByName(String name){
        return dao.findByName(name)
                .map(ServiceAnswerHelper::ok)
                .orElseGet(() -> ServiceAnswer.<UserRoleEntity>builder()
                        .success(false)
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .errorMessage("No such role")
                        .build());
    }
}
