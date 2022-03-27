package com.bombanya.javaschool_railway.security.services;

import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.security.dao.UserAccountDAO;
import com.bombanya.javaschool_railway.security.entities.UserAccount;
import com.bombanya.javaschool_railway.security.entities.UserAccountUserDetails;
import com.bombanya.javaschool_railway.security.entities.UserRoleEntity;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserAccountDAO dao;
    private final UserRoleService roleService;

    @Transactional
    public ServiceAnswer<Void> saveNew(UserAccount userAccount){
        try{
            List<UserRoleEntity> roleEntities = new ArrayList<>();
            for (UserRoleEntity role : userAccount.getRoles()) {
                ServiceAnswer<UserRoleEntity> roleEntityWrapper = roleService.getByName(role.getName().name());
                if (roleEntityWrapper.isSuccess()) roleEntities.add(roleEntityWrapper.getServiceResult());
                else return ServiceAnswerHelper.badRequest("bad user");
            }
            userAccount.setRoles(roleEntities);
            userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
            dao.save(userAccount);
            return ServiceAnswerHelper.ok(null);
        } catch (PersistenceException e){
            if (e.getCause() == null ||
                    !e.getCause().getClass().equals(ConstraintViolationException.class)) throw e;
            return ServiceAnswerHelper.badRequest("bad user");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return dao.findByUsername(username)
                .map(UserAccountUserDetails::fromUserAccount)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }
}
