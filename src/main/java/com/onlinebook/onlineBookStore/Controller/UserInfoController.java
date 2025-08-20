package com.onlinebook.onlineBookStore.Controller;


import com.onlinebook.onlineBookStore.DTO.ApiResponse;
import com.onlinebook.onlineBookStore.DTO.auth.LoginRequestDto;
import com.onlinebook.onlineBookStore.DTO.auth.UserRegisterDto;
import com.onlinebook.onlineBookStore.ExceptionHandeling.CustomExceptionHandel;
import com.onlinebook.onlineBookStore.Services.auth.JwtService;
import com.onlinebook.onlineBookStore.Services.auth.UserInfoService;
import com.onlinebook.onlineBookStore.Utils.ResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class UserInfoController {
    private final UserInfoService userInfoService;
    private final AuthenticationManager authenticationManager;
    private final ResponseUtils responseUtils;
    private final JwtService jwtService;

    @GetMapping("/index")
    public String getWelcomePage(){
        return "...................Coming Soon...............................";
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(
            @Valid @RequestBody UserRegisterDto registerDto
            ){
        String message = userInfoService.register(registerDto);
        return responseUtils.created(message);
    }

    @PostMapping("/login")
    public String login (@RequestBody LoginRequestDto requestDto){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(),
                        requestDto.getPassword())
        );
        if(auth.isAuthenticated()){
            return jwtService.generateToken(requestDto.getEmail());
        }
        else {
            throw new CustomExceptionHandel("Invalid Email Request",
                    HttpStatus.NO_CONTENT.value());
        }

    }

}
