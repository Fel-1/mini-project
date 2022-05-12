package com.alterra.miniproject.service;

import com.alterra.miniproject.config.security.JwtTokenProvider;
import com.alterra.miniproject.domain.common.ApiResponse;
import com.alterra.miniproject.domain.common.ApiResponseStatus;
import com.alterra.miniproject.domain.dao.Doctor;
import com.alterra.miniproject.domain.dao.User;
import com.alterra.miniproject.domain.dto.UserDTO;
import com.alterra.miniproject.domain.payload.TokenResponse;
import com.alterra.miniproject.domain.payload.UserPassword;
import com.alterra.miniproject.repository.DoctorRepository;
import com.alterra.miniproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AuthService.class)
class AuthServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private AuthService authService;

    @Test
    void register_Success_Test() {

        User user = User.builder().id(1L).username("uname").build();

        UserDTO userDTO = UserDTO.builder().username("uname").build();

        UserPassword userPassword = new UserPassword();
        userPassword.setUsername("uname");
        userPassword.setPassword("passwd");

        when(userRepository.getDistinctTopByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_password_mock");
        when(userRepository.save(any())).thenReturn(user);
        when(modelMapper.map(any(),eq(UserDTO.class))).thenReturn(userDTO);

        ResponseEntity responseEntity = authService.register(userPassword, null);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("uname", ((UserDTO) apiResponse.getData()).getUsername());
    }

    @Test
    void register_Duplicate_Test() {
        User user = User.builder().id(1L).username("uname").build();

        UserDTO userDTO = UserDTO.builder().username("uname").build();

        UserPassword userPassword = new UserPassword();
        userPassword.setUsername("uname");
        userPassword.setPassword("passwd");

        when(userRepository.getDistinctTopByUsername(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_password_mock");
        when(userRepository.save(any())).thenReturn(user);
        when(modelMapper.map(any(),eq(UserDTO.class))).thenReturn(userDTO);

        ResponseEntity responseEntity = authService.register(userPassword, null);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        String code = ((ApiResponseStatus) apiResponse.getStatus()).getCode();

        assertEquals("BAD_CREDENTIALS", code);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void generateToken_Success_Test() {
        UserPassword userPassword = new UserPassword();
        userPassword.setUsername("uname");
        userPassword.setPassword("passwd");

        Authentication authentication = new UsernamePasswordAuthenticationToken("uname", "password");

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(any())).thenReturn("some_long_long_token");

        ResponseEntity<Object> responseEntity = authService.generateToken(userPassword);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("some_long_long_token", ((TokenResponse) apiResponse.getData()).getToken());
    }

    @Test
    void generateToken_BadCredentials_Test() {
        UserPassword userPassword = new UserPassword();
        userPassword.setUsername("uname");
        userPassword.setPassword("passwd");

        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);

        ResponseEntity responseEntity = authService.generateToken(userPassword);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        String code = ((ApiResponseStatus) apiResponse.getStatus()).getCode();

        assertEquals("BAD_CREDENTIALS", code);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void generateToken_Error_Test() {
        UserPassword userPassword = new UserPassword();
        userPassword.setUsername("uname");
        userPassword.setPassword("passwd");

        when(authenticationManager.authenticate(any())).thenThrow(NullPointerException.class);

        ResponseEntity responseEntity = authService.generateToken(userPassword);
        ApiResponse apiResponse = ((ApiResponse) responseEntity.getBody());
        String code = ((ApiResponseStatus) apiResponse.getStatus()).getCode();

        assertEquals("UNKNOWN_ERROR", code);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}