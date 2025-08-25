package com.onlinebook.onlineBookStore.Services.auth;


import com.onlinebook.onlineBookStore.DTO.auth.UserRegisterDto;
import com.onlinebook.onlineBookStore.Entity.UserInfo;
import com.onlinebook.onlineBookStore.Enum.Role;
import com.onlinebook.onlineBookStore.ExceptionHandeling.CustomExceptionHandel;
import com.onlinebook.onlineBookStore.Mapper.UserMapper;
import com.onlinebook.onlineBookStore.Repository.UserInfoRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


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

    @PostConstruct
    public void createAdminIfNotExists(){
        if(!userInfoRepository.existsByRole(Role.ROLE_ADMIN)){
            UserInfo admin = new UserInfo();
            admin.setName("admin");
            admin.setEmail("admin@bookstore.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ROLE_ADMIN);
            admin.setPhoneNumber("1234567890");
            admin.setEnabled(true);
            userInfoRepository.save(admin);
            System.out.println("Admin Created");
        }
    }

    public String register(UserRegisterDto dto){

        if(userInfoRepository.existsByEmail(dto.getEmail())){
            throw  new CustomExceptionHandel("Email already exists",
                    HttpStatus.CONFLICT.value());
        }
        if(userInfoRepository.existsByPhoneNumber(dto.getPhoneNumber())){
            throw  new CustomExceptionHandel("Phone Number already exists",
                    HttpStatus.CONFLICT.value());
        }

        UserInfo userInfo = userMapper.toEntity(dto);
        userInfo.setPassword(passwordEncoder.encode(dto.getPassword()));

        userInfo.setEnabled(false);
        userInfo.setVerificationToken(UUID.randomUUID().toString());

        userInfoRepository.save(userInfo);

        emailService.sendVerificationEmail(userInfo);

        return "User Register Success.";

    }

    public void verifyUser(String token){
        UserInfo userInfo = userInfoRepository.findByVerificationToken(token)
                .orElseThrow(()->new CustomExceptionHandel("Invalid token", HttpStatus.BAD_REQUEST.value()));
        userInfo.setEnabled(true);
        userInfo.setVerificationToken(null);
        userInfoRepository.save(userInfo);
    }

}
