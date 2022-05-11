package com.alterra.miniproject.service;

import com.alterra.miniproject.config.security.JwtTokenProvider;
import com.alterra.miniproject.constant.AppConstant;
import com.alterra.miniproject.domain.dao.User;
import com.alterra.miniproject.domain.dto.UserDTO;
import com.alterra.miniproject.domain.payload.TokenResponse;
import com.alterra.miniproject.domain.payload.UserPassword;
import com.alterra.miniproject.repository.UserRepository;
import com.alterra.miniproject.util.ResponseUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> register(UserPassword req, String username) {
        log.info("Executing register new user");
        Optional<User> userOptional = userRepository.getDistinctTopByUsername(req.getUsername());

        if (userOptional.isPresent()) {
            log.info("User with username : [{}] already exist, aborting register", req.getUsername());
            return ResponseUtil.build(
                    AppConstant.ResponseCode.BAD_CREDENTIALS,
                    "Username already exist",
                    HttpStatus.BAD_REQUEST
            );
        }

        log.info("User doesnt exist yet, creating new user");
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setCreatedBy(username);
        userRepository.save(user);
        log.info("User doesnt exist yet, creating new user");
        return ResponseUtil.build(
                AppConstant.ResponseCode.SUCCESS,
                modelMapper.map(user, UserDTO.class),
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> generateToken(UserPassword req) {
        log.info("Generating JWT based on provided username and password");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getUsername(),
                            req.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setToken(jwt);

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, tokenResponse, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            log.error("Bad Credential", e);
            return ResponseUtil.build(
                    AppConstant.ResponseCode.BAD_CREDENTIALS,
                    "Username or Password is incorrect",
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseUtil.build(
                    AppConstant.ResponseCode.UNKNOWN_ERROR,
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}
