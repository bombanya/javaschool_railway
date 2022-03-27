package com.bombanya.javaschool_railway.security.dao;

import com.bombanya.javaschool_railway.dao.DAO;
import com.bombanya.javaschool_railway.security.entities.UserAccount;

import java.util.Optional;

public interface UserAccountDAO extends DAO<UserAccount, Integer> {

    Optional<UserAccount> findByUsername(String username);
}
