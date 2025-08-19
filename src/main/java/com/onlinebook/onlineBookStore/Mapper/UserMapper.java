package com.onlinebook.onlineBookStore.Mapper;

import com.onlinebook.onlineBookStore.DTO.auth.UserRegisterDto;
import com.onlinebook.onlineBookStore.Entity.UserInfo;
import com.onlinebook.onlineBookStore.Enum.Role;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserInfo toEntity(UserRegisterDto dto){
        UserInfo userInfo = new UserInfo();
        userInfo.setName(dto.getName());
        userInfo.setEmail(dto.getEmail());
        userInfo.setPassword(dto.getPassword());
        userInfo.setRole(Role.ROLE_USER);
        userInfo.setPhoneNumber(dto.getPhoneNumber());

        return userInfo;
    }
}
