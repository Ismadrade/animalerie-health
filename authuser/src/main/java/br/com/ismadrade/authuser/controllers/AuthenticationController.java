package br.com.ismadrade.authuser.controllers;

import br.com.ismadrade.authuser.configs.security.JwtProvider;
import br.com.ismadrade.authuser.dtos.JwtDto;
import br.com.ismadrade.authuser.dtos.LoginDto;
import br.com.ismadrade.authuser.dtos.UserDto;
import br.com.ismadrade.authuser.enums.RoleType;
import br.com.ismadrade.authuser.enums.UserStatus;
import br.com.ismadrade.authuser.enums.UserType;
import br.com.ismadrade.authuser.exceptions.CustomException;
import br.com.ismadrade.authuser.models.RoleModel;
import br.com.ismadrade.authuser.models.UserModel;
import br.com.ismadrade.authuser.services.RoleService;
import br.com.ismadrade.authuser.services.UserService;
import br.com.ismadrade.authuser.views.UserView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Log4j2
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    final UserService userService;
    final RoleService roleService;
    final PasswordEncoder encoder;
    final JwtProvider jwtProvider;
    final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(UserService userService, RoleService roleService, PasswordEncoder encoder, JwtProvider jwtProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.roleService = roleService;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody
                                               @JsonView(UserView.RegistrationPost.class)
                                               @Validated(UserView.RegistrationPost.class) UserDto userDto){

        log.debug("POST registerUser userDto recebido {}", userDto.toString());

        if(userService.existsByUsername(userDto.getUsername())){
            log.warn("Username {} já cadastrado ", userDto.getUsername());
            throw new CustomException(HttpStatus.CONFLICT, "Usuario já cadastrado!");
        }

        if(userService.existsByEmail(userDto.getEmail())){
            log.warn("E-mail {} já cadastrado ", userDto.getEmail());
            throw new CustomException(HttpStatus.CONFLICT, "E-mail já cadastrado!");
        }

        if(userService.existsByCpf(userDto.getCpf())){
            log.warn("CPF {} já cadastrado ", userDto.getCpf());
            throw new CustomException(HttpStatus.CONFLICT, "CPF já cadastrado!");
        }

        RoleModel roleModel = roleService.findByRoleName(RoleType.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Erro: Role is Not Found"));

        userDto.setPassword(encoder.encode(userDto.getPassword()));

        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setUserType(UserType.USER);
        userModel.getRoles().add(roleModel);
        userService.saveUser(userModel);
        log.debug("POST registerUser userId {}", userModel.getUserId());
        log.info("Usuario salvo com sucesso! userId {}", userModel.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);

    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> authenticateUser(@Valid @RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwt(authentication);
        return ResponseEntity.ok(new JwtDto(jwt));
    }
}
