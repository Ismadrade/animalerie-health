package br.com.ismadrade.authuser.controllers;

import br.com.ismadrade.authuser.dtos.UserDto;
import br.com.ismadrade.authuser.exceptions.CustomException;
import br.com.ismadrade.authuser.models.UserModel;
import br.com.ismadrade.authuser.services.UserService;
import br.com.ismadrade.authuser.specifications.SpecificationTemplate;
import br.com.ismadrade.authuser.views.UserView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
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
        if(!userModelOptional.isPresent())
            throw new CustomException(HttpStatus.NOT_FOUND, "Usuario não encontrado");
        return ResponseEntity.status(HttpStatus.OK).body(userModelOptional);
    }

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec spec,
                                                       @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable){
        Page<UserModel> userModelPage = userService.findAll(spec, pageable);
        if(!userModelPage.isEmpty())
            userModelPage.forEach(user -> user.add(linkTo(methodOn(UserController.class).getOneUser(user.getUserId())).withSelfRel()));
        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserModel> updateUser(@PathVariable(value = "userId") UUID userId,
                                             @RequestBody
                                             @JsonView(UserView.UserPut.class)
                                             @Validated(UserView.UserPut.class) UserDto userDto){
        log.debug("PUT updateUser userDto received {}", userDto.toString());
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent())
            throw new CustomException(HttpStatus.NOT_FOUND, "Usuario não encontrado");
        var userModel = userModelOptional.get();
        userModel.setFullName(userDto.getFullName());
        userModel.setPhoneNumber(userDto.getPhoneNumber());
        userModel.setCpf(userDto.getCpf());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.updateUser(userModel);
        log.debug("PUT updateUser userId saved {}", userModel.getUserId());
        log.info("User updated successfully userId {}", userModel.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
                                                 @RequestBody
                                                 @JsonView(UserView.PasswordPut.class)
                                                 @Validated(UserView.PasswordPut.class) UserDto userDto){
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent())
            throw new CustomException(HttpStatus.NOT_FOUND, "Usuario não encontrado");
        if(!userModelOptional.get().getPassword().equals(userDto.getOldPassword()))
            throw new CustomException(HttpStatus.CONFLICT, "Senha antiga é incompatível!");
        var userModel = userModelOptional.get();
        userModel.setPassword(userDto.getPassword());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.updatePassword(userModel);
        log.debug("PUT updatePassword userId saved {}", userModel.getUserId());
        log.info("Password updated successfully userId {}", userModel.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully.");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId){
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(!userModelOptional.isPresent())
            throw new CustomException(HttpStatus.NOT_FOUND, "Usuario não encontrado");
        userService.delete(userModelOptional.get());
        log.debug("DELETE deleteUser userId deleted {}", userId);
        log.info("User deleted successfully userId {}",  userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
