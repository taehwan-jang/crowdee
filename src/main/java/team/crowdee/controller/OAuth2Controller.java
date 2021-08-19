package team.crowdee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OAuth2Controller {

    @GetMapping({"","/"})
    public String getAuthorizationMessage(){
        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping({"/loginSuccess", "/hello"})
    public String loginSuccess(){
        return "hello";
    }

    @GetMapping("/loginFailure")
    public String loginFailure(){
        return "loginFailure";
    }
}
