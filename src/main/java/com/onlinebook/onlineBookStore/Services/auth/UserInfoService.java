package com.onlinebook.onlineBookStore.Services.auth;


import com.onlinebook.onlineBookStore.DTO.auth.UserRegisterDto;
import com.onlinebook.onlineBookStore.Entity.UserInfo;
import com.onlinebook.onlineBookStore.ExceptionHandeling.CustomExceptionHandel;
import com.onlinebook.onlineBookStore.Mapper.UserMapper;
import com.onlinebook.onlineBookStore.Repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserInfoService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;
    private final UserMapper userMapper;

    public UserInfoService(UserMapper userMapper, UserInfoRepository userInfoRepository) {
        this.userMapper = userMapper;
        this.userInfoRepository = userInfoRepository;
    }

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
        userInfoRepository.save(userInfo);

        return "Registration Successful";
    }

}
