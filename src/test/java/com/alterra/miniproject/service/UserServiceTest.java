package com.alterra.miniproject.service;

import com.alterra.miniproject.domain.common.ApiResponse;
import com.alterra.miniproject.domain.common.ApiResponseStatus;
import com.alterra.miniproject.domain.dao.User;
import com.alterra.miniproject.domain.payload.UserPassword;
import com.alterra.miniproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserService.class)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void loadUserByUsername_Success_Test() {
        User user = User.builder().id(1L).username("uname").build();
        when(userRepository.getDistinctTopByUsername(anyString())).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername("uname");

        assertEquals("uname", userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_NotFound_Test() {
        when(userRepository.getDistinctTopByUsername(anyString())).thenReturn(Optional.empty());


        assertThrows(UsernameNotFoundException.class, () -> {
            UserDetails userDetails = userService.loadUserByUsername("uname");
        });
    }

}