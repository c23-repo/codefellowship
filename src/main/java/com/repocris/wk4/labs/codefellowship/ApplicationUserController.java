package com.repocris.wk4.labs.codefellowship;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/signup")
    public String signupPage(){
        return "signup";
    }


    @PostMapping("/signup")
    public RedirectView createUser(@RequestParam String username, @RequestParam String password, @RequestParam String firstName,
                                   @RequestParam String lastName, @RequestParam String dateOfBirth, @RequestParam String bio) {
        ApplicationUser newUser = new ApplicationUser(username, bCryptPasswordEncoder.encode(password), firstName, lastName,
                dateOfBirth, bio);
        applicationUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("userprofile");
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/userprofile")
    public String getUserProfile(Principal p, Model m){
        ApplicationUser user = applicationUserRepository.findByUsername(p.getName());
        m.addAttribute("user", user);
        return "userprofile";
    }

    @GetMapping("/allusers")
    public String getAllUsers(Model model){
        Iterable<ApplicationUser> allUsers = applicationUserRepository.findAll();
        model.addAttribute("allusers", allUsers);
        return "allusers";
    }

    @GetMapping("/details/{id}")
    public String getUserDetails(@PathVariable Long id, Model model){
        ApplicationUser allUsers = applicationUserRepository.findById(id).get();
        model.addAttribute("allusers", allUsers);
        return "details";
    }



}
