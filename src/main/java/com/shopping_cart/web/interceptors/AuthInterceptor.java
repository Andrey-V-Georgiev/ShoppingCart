package com.shopping_cart.web.interceptors;

import com.shopping_cart.models.service_models.UserServiceModel;
import com.shopping_cart.services.AuthService;
import com.shopping_cart.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.shopping_cart.constants.AuthConstants.*;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;
    private final UserService userService;

    public AuthInterceptor(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean userIdIsValid = !userId.equals(ANONYMOUS_USER);
        boolean hasAuthorizationHeader = request.getHeader(AUTHORIZATION_HEADER) != null;

        if (userIdIsValid && hasAuthorizationHeader) {
            UserServiceModel userServiceModel = this.userService.findUserById(userId);
            if (userServiceModel == null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
            String nakedToken = request.getHeader(AUTHORIZATION_HEADER).replace(AUTHORIZATION_PREFIX, "");
            if (!userServiceModel.getToken().equals(nakedToken)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
            Boolean blackTokenExist = this.authService.blackTokenExist(nakedToken);
            if(blackTokenExist) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
            return true;
        }
        /* If no hasUserId or hasAuthorizationHeader (register and guest) */
        return true;
    }
}
