package com.onlinebook.onlineBookStore.DTO.auth;

import com.onlinebook.onlineBookStore.Enum.Role;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDto {

    private String name;

    private String email;

    private String password;

    private Role role;

    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must be 10 digits"
    )
    private String phoneNumber;



}
