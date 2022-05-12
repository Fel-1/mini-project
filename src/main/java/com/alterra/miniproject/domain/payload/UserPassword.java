package com.alterra.miniproject.domain.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserPassword {
    @Schema(type = "string", example = "username", description = "Username untuk login")
    private String username;

    @Schema(type = "string", example = "password", description = "Password untuk login")
    private String password;
}
