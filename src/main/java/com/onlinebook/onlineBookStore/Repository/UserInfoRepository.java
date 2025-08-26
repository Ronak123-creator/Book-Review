package com.onlinebook.onlineBookStore.Repository;

import com.onlinebook.onlineBookStore.Entity.UserInfo;
import com.onlinebook.onlineBookStore.Enum.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByEmail(String email);
    Optional<UserInfo> findByVerificationToken(String token);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByRole(Role role);
}
