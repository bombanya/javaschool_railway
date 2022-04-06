package com.bombanya.javaschool_railway.security.dao;

import com.bombanya.javaschool_railway.dao.DAO;
import com.bombanya.javaschool_railway.security.entities.UserRoleEntity;

import java.util.Optional;

public interface UserRoleDAO extends DAO<UserRoleEntity, Integer> {

    Optional<UserRoleEntity> findByName(String name);
}
