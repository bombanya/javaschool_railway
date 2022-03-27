package com.bombanya.javaschool_railway.security;

import com.bombanya.javaschool_railway.entities.ServiceAnswer;
import com.bombanya.javaschool_railway.security.entities.UserAccount;
import com.bombanya.javaschool_railway.security.services.UserService;
import com.bombanya.javaschool_railway.services.ServiceAnswerHelper;
import com.bombanya.javaschool_railway.utils.JacksonView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public void login(){}

    @PostMapping("/user/new")
    @JsonView(JacksonView.UserInfo.class)
    public ResponseEntity<ServiceAnswer<Void>> saveNewUser(@RequestBody UserAccount account){
        System.out.println(account);
        return ServiceAnswerHelper.wrapIntoResponse(userService.saveNew(account));
    }
}
