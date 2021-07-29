package com.shopping_cart.services.impl;

import com.shopping_cart.enums.UserRole;
import com.shopping_cart.models.entities.BlackToken;
import com.shopping_cart.models.service_models.BlackTokenServiceModel;
import com.shopping_cart.repositories.BlackTokenRepository;
import com.shopping_cart.services.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.shopping_cart.constants.GlobalConstants.SECRET_KEY;
import static com.shopping_cart.constants.GlobalConstants.TOKEN_EXPIRY_TIME;

@Service
public class AuthServiceImpl implements AuthService {

    private final BlackTokenRepository blackTokenRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthServiceImpl(BlackTokenRepository blackTokenRepository, ModelMapper modelMapper) {
        this.blackTokenRepository = blackTokenRepository;
        this.modelMapper = modelMapper;
    }

    public String createJwtToken(String userId, UserRole userRole) {

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(userRole.toString());

        List<String> authorities = grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        /* Create new JWT token */
        String token = Jwts.builder()
                .setId(userId)
                .claim("authorities", authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRY_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes())
                .compact();

        return String.format("Bearer %s", token);
    }

    @Override
    public Boolean blackTokenExist(String token) {
        BlackToken blackToken = blackTokenRepository.findBlackTokenByToken(token).orElse(null);
        return blackToken != null;
    }

    @Override
    public BlackTokenServiceModel createBlackToken(String userId, String token) {

        /* Create new service model */
        BlackTokenServiceModel blackTokenServiceModel = new BlackTokenServiceModel(userId, token);

        /* Map the service model to entity and save it to DB */
        BlackToken blackToken = this.blackTokenRepository.saveAndFlush(
                this.modelMapper.map(blackTokenServiceModel, BlackToken.class));

        return this.modelMapper.map(blackToken, BlackTokenServiceModel.class);
    }
}
