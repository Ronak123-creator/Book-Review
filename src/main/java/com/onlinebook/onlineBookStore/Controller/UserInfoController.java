package com.onlinebook.onlineBookStore.Controller;


import com.onlinebook.onlineBookStore.Services.auth.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class UserInfoController {
    private final UserInfoService userInfoService;

    @GetMapping("/index")
    public String getWelcomePage(){
        return "...................Coming Soon...............................";
    }

}
