package com.shopping_cart.services.impl;

import com.shopping_cart.models.binding_models.UserRegisterBindingModel;
import com.shopping_cart.models.entities.User;
import com.shopping_cart.models.service_models.UserServiceModel;
import com.shopping_cart.repositories.UserRepository;
import com.shopping_cart.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.shopping_cart.constants.UserRolesConstants.ROLE_USER;
import static com.shopping_cart.constants.UserRolesConstants.ROLE_ADMIN;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserServiceModel registerUser(UserRegisterBindingModel userRegisterBindingModel) {

        /* Check if such username already exist */
        UserServiceModel userServiceModel = this.userRepository.findUserByUsername(userRegisterBindingModel.getUsername())
                .map(o -> this.modelMapper.map(o, UserServiceModel.class))
                .orElse(null);
        if (userServiceModel != null) {
            return null;
        }
        /* Construct the new user */
        long repoCount = this.userRepository.count();
        UserServiceModel userServiceModelNew = new UserServiceModel(
                userRegisterBindingModel.getUsername(),
                userRegisterBindingModel.getEmail());

        userServiceModelNew.setPassword(this.bCryptPasswordEncoder.encode(userRegisterBindingModel.getPassword()));
        userServiceModelNew.setRole(repoCount > 0 ? ROLE_USER : ROLE_ADMIN);
        userServiceModelNew.setRegistrationDate(LocalDateTime.now());

        /* Save the user to DB */
        User userUnsaved = this.modelMapper.map(userServiceModelNew, User.class);
        User userSaved = this.userRepository.saveAndFlush(userUnsaved);

        return this.modelMapper.map(userSaved, UserServiceModel.class);
    }

    @Override
    public UserServiceModel findUserByUsernameAndPassword(String username, String password) {

        List<UserServiceModel> userServiceModels = this.userRepository.findAllByUsername(username)
                .stream()
                .map(o -> o.get())
                .map(o -> this.modelMapper.map(o, UserServiceModel.class))
                .collect(Collectors.toList());

        for (UserServiceModel userServiceModel : userServiceModels) {
            boolean passwordMatch = this.bCryptPasswordEncoder.matches(password, userServiceModel.getPassword());
            if (passwordMatch) {
                return userServiceModel;
            }
        }
        /* If no such user return null */
        return null;
    }

    @Override
    public void updateUserToken(String newNakedToken, String userId) {
        this.userRepository.updateUserToken(newNakedToken, userId);
    }

    @Override
    public UserServiceModel findUserById(String userId) {
        return this.userRepository.findById(userId)
                .map(o -> this.modelMapper.map(o, UserServiceModel.class))
                .orElse(null);
    }
}
