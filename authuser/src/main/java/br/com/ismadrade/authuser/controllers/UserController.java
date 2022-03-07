package br.com.ismadrade.authuser.controllers;

import br.com.ismadrade.authuser.exceptions.CustomException;
import br.com.ismadrade.authuser.models.UserModel;
import br.com.ismadrade.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {


    final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId") UUID userId){
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent()){
            throw new CustomException(HttpStatus.NOT_FOUND, "Usuario não encontrado");
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(userModelOptional);
        }

    }
}
