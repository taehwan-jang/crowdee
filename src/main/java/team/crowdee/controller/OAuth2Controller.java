package team.crowdee.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class OAuth2Controller {

    @GetMapping({"","/"})
    public String getAuthorizationMessage(){
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        log.info("로그인페이지");
        return "login";
    }

    @GetMapping({"/loginSuccess", "hello"})
    public String loginSuccess(){
        return "hello";
    }

    @GetMapping("/loginFailure")
    public String loginFailure(){
        return "loginFailure";
    }
}
