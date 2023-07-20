package com.sparta.community.controller;

import com.sparta.community.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String goHome(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        model.addAttribute("username", userDetails.getUsername());
        return "home";
    }
}
