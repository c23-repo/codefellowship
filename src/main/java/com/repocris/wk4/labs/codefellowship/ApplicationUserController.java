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
import java.util.List;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/")
    public String getCodeFellowship() {
        return "codefellowship";
    }

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
    public String getAllUsers(Model model, Principal principal){
        ApplicationUser loggedInUser = applicationUserRepository.findByUsername(principal.getName());
        model.addAttribute("loggedInUser", loggedInUser);
        Iterable<ApplicationUser> friendable = applicationUserRepository.findAll();
        model.addAttribute("friendable", friendable);
        return "allusers";
    }

    @GetMapping("/details/{id}")
    public String getUserDetails(@PathVariable Long id, Model model, Principal principal){
        ApplicationUser allUsers = applicationUserRepository.findById(id).get();
        model.addAttribute("allusers", allUsers);
        ApplicationUser loggedInUser = applicationUserRepository.findByUsername(principal.getName());
        model.addAttribute("loggedInUser", loggedInUser);
        return "details";
    }

    @GetMapping("/createpost")
    public String createPost(Model model, Principal principal){
        ApplicationUser loggedInUser = applicationUserRepository.findByUsername(principal.getName());
        model.addAttribute("loggedInUser", loggedInUser);
        return "createpost";
    }

    @PostMapping("/createpost")
    public RedirectView displayPost(String body, Principal principal){
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        Post post = new Post(body, user);
        postRepository.save(post);
        return new RedirectView("/feed");
    }

    @PostMapping("/follow/{id}")
    public RedirectView createFriendship(@PathVariable Long id, Principal principal, Model model){
        ApplicationUser loggedInUser = applicationUserRepository.findByUsername(principal.getName());
        ApplicationUser newFriend = applicationUserRepository.findById(id).get();
        loggedInUser.friends.add(newFriend);
        applicationUserRepository.save(loggedInUser);
        return new RedirectView("/userprofile");
    }

    @GetMapping("/feed")
    public String seeFriendsPosts(Model model, Principal principal){
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "/feed";
    }

}
