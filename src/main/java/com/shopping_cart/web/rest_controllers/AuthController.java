package com.shopping_cart.web.rest_controllers;

import com.google.gson.Gson;
import com.shopping_cart.models.binding_models.UserLoginBindingModel;
import com.shopping_cart.models.binding_models.UserRegisterBindingModel;
import com.shopping_cart.models.service_models.UserServiceModel;
import com.shopping_cart.models.view_models.UserViewModel;
import com.shopping_cart.services.AuthService;
import com.shopping_cart.services.UserService;
import com.shopping_cart.utils.ValidationMsgUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.shopping_cart.constants.AuthConstants.*;
import static com.shopping_cart.constants.ResponseMsgConstants.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ValidationMsgUtil validationMsgUtil;

    @Autowired
    public AuthController(AuthService authService, UserService userService, ModelMapper modelMapper, ValidationMsgUtil validationMsgUtil) {
        this.authService = authService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.validationMsgUtil = validationMsgUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody UserRegisterBindingModel userRegisterBindingModel,
            BindingResult bindingResult) {

        /* Check does password and confirmedPassword match */
        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CONFIRMED_PASSWORD_NOT_MATCH);
        }
        try {
            /* Validate fields requirements */
            if (bindingResult.hasErrors()) {
                List<ObjectError> allErrors = bindingResult.getAllErrors();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.validationMsgUtil.parse(allErrors));
            }
            /* Save to DB */
            UserServiceModel userServiceModel = this.userService.registerUser(userRegisterBindingModel);

            /* If username already exist */
            if (userServiceModel == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(USERNAME_ALREADY_IN_USE);
            }
            Gson g = new Gson();
            return ResponseEntity.status(HttpStatus.CREATED).body(REGISTER_SUCCESS);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FRIENDLY_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody UserLoginBindingModel userLoginBindingModel,
            BindingResult bindingResult) {

        try {
            /* Validate fields requirements */
            if (bindingResult.hasErrors()) {
                List<ObjectError> allErrors = bindingResult.getAllErrors();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.validationMsgUtil.parse(allErrors));
            }
            /* Check username and password are valid */
            UserServiceModel userServiceModel = this.userService.findUserByUsernameAndPassword(
                    userLoginBindingModel.getUsername(),
                    userLoginBindingModel.getPassword());

            if (userServiceModel == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(WRONG_USERNAME_OR_PASSWORD);
            }

            /* Generate JWT token and update in DB */
            String newToken = this.authService.createJwtToken(userServiceModel.getId(), userServiceModel.getRole());
            String newNakedToken = newToken.replace(AUTHORIZATION_PREFIX, "");
            this.userService.updateUserToken(newNakedToken, userServiceModel.getId());


            /* Prepare UserLoginViewModel for FE */
            UserViewModel userViewModel = this.modelMapper.map(userServiceModel, UserViewModel.class);
            userViewModel.setToken(newToken);

            return ResponseEntity.ok().body(userViewModel);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FRIENDLY_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader(name = "Authorization") String token) {

        try {
            String userId = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            String nakedToken = token.replace(AUTHORIZATION_PREFIX, "");

            /* Invalidate the current token , saving it to the blackList */
            this.authService.createBlackToken(userId, nakedToken);

            return ResponseEntity.status(HttpStatus.OK).body(LOGOUT_SUCCESS);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FRIENDLY_INTERNAL_SERVER_ERROR);
        }
    }
}
