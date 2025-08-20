package com.onlinebook.onlineBookStore.Services.auth;


import com.onlinebook.onlineBookStore.DTO.auth.UserRegisterDto;
import com.onlinebook.onlineBookStore.Entity.UserInfo;
import com.onlinebook.onlineBookStore.ExceptionHandeling.CustomExceptionHandel;
import com.onlinebook.onlineBookStore.Mapper.UserMapper;
import com.onlinebook.onlineBookStore.Repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserInfoService implements UserDetailsService {

    private final EmailService emailService;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Optional<UserInfo> userInfo = userInfoRepository.findByEmail(email);
        return userInfo
                .map(UserInfoDetails::new)
                .orElseThrow(()->new CustomExceptionHandel("Email Not Found" + email,
                        HttpStatus.NOT_FOUND.value()));
    }

    public String register(UserRegisterDto dto){

        if(userInfoRepository.existsByEmail(dto.getEmail())){
            throw  new CustomExceptionHandel("Email already exists",
                    HttpStatus.CONFLICT.value());
        }
        if(userInfoRepository.existsByPhoneNumber(dto.getPhoneNumber())){
            throw  new CustomExceptionHandel("Email already exists",
                    HttpStatus.CONFLICT.value());
        }

        UserInfo userInfo = userMapper.toEntity(dto);
        userInfo.setPassword(passwordEncoder.encode(dto.getPassword()));

        userInfo.setEnabled(false);
        String subject = "Verify Your Email";
        String verificationUrl = "http://localhost:8080/api/auth/verify?token=" +
                userInfo.getVerificationToken();
        String message = "Welcome " + userInfo.getName() + "\n\n"
                + "Please click the link to verify your email: \n"
                + verificationUrl +"\n\n Thank You!!";

        userInfoRepository.save(userInfo);
        emailService.sendEmail(userInfo,subject,message);

        return "Registration Successful";
    }

    public void verifyUser(String token){
        UserInfo userInfo = userInfoRepository.findByVerificationToken(token)
                .orElseThrow(()->new CustomExceptionHandel("Invalid token", HttpStatus.BAD_REQUEST.value()));
        userInfo.setEnabled(true);
        userInfo.setVerificationToken(null);
    }

}
