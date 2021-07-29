package com.shopping_cart.web.rest_controllers;

import com.shopping_cart.models.binding_models.UserLoginBindingModel;
import com.shopping_cart.models.binding_models.UserRegisterBindingModel;
import com.shopping_cart.models.service_models.UserServiceModel;
import com.shopping_cart.models.view_models.UserLoginViewModel;
import com.shopping_cart.services.AuthService;
import com.shopping_cart.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.shopping_cart.constants.GlobalConstants.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(AuthService authService, UserService userService, ModelMapper modelMapper) {
        this.authService = authService;
        this.userService = userService;
        this.modelMapper = modelMapper;
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(allErrors);
            }
            /* Save to DB */
            UserServiceModel userServiceModel = this.userService.registerUser(userRegisterBindingModel);

            /* If username already exist */
            if (userServiceModel == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(USERNAME_ALREADY_IN_USE);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(REGISTER_SUCCESS);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(allErrors);
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
            UserLoginViewModel userLoginViewModel = this.modelMapper.map(userServiceModel, UserLoginViewModel.class);
            userLoginViewModel.setToken(newToken);

            return ResponseEntity.status(HttpStatus.OK).body(userLoginViewModel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            Principal principal,
            @RequestHeader(name = "Authorization") String token) {

        try {
            String userId = principal.getName();
            String nakedToken = token.replace(AUTHORIZATION_PREFIX, "");

            /* Invalidate the current token , saving it to the blackList */
            this.authService.createBlackToken(userId, nakedToken);

            return ResponseEntity.status(HttpStatus.OK).body(LOGOUT_SUCCESS);

        } catch (Exception e) {
            /* If the user logout from other device with old token, the old token won't be added to DB second time */
            return ResponseEntity.status(HttpStatus.OK).body(LOGOUT_SUCCESS);
        }
    }
}
