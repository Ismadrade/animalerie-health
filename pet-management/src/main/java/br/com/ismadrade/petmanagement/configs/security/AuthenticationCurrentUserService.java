package br.com.ismadrade.petmanagement.configs.security;

import br.com.ismadrade.petmanagement.models.UserModel;
import br.com.ismadrade.petmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationCurrentUserService {

    @Autowired
    private UserService userService;

    public UserDetailsImpl getCurrentUserDetailImpl(){
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public UserModel getCurrentUser(){
        return  userService.findById(getCurrentUserDetailImpl().getUserId());
    }

    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}