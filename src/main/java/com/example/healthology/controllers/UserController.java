package com.example.healthology.controllers;

import com.example.healthology.models.User;
import com.example.healthology.models.UserWithRoles;
import com.example.healthology.repositories.UsersRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {

    // These two next steps are often called dependency injection, where we create a Repository instance and initialize it in the controller class constructor.
    private final UsersRepository usersDao;
    private PasswordEncoder passwordEncoder;


//    @Value("${filestack.api.key}")
//    private String fsAPI;
//
//    @Value("${talkjs.api.key}")
//    private String tjAPI;

    public UserController(UsersRepository usersDao, PasswordEncoder passwordEncoder) {
        this.usersDao = usersDao;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("users", new User());
        return "users/register";
    }

    @GetMapping("/register?error_username")
    public String userErrorRegisterForm(Model model) {
        model.addAttribute("users", new User());
        return "users/register";
    }

    @GetMapping("/register?error_email")
    public String emailErrorRegisterForm(Model model) {
        model.addAttribute("users", new User());
        return "users/register";
    }

    @PostMapping("/register")

    public String saveUser(@ModelAttribute User user, @RequestParam(name = "client_token") String client_token) {
        if (usersDao.findByUsername(user.getUsername()) == null) {
            if (usersDao.findUserByEmail(user.getEmail()) == null) {

                if (client_token.equals("ABC")) {
                    user.setProfile_img("images/imagescontainer/profile.png");
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    usersDao.save(user);
                    authenticate(user);


                    return "redirect:/client_setup/"; // Redirect directly to whatever path your home page is
                } else {
                    return "redirect:/register";
                }
            } else {
                return "redirect:/register?error_email";
            }
        } else {

            return "redirect:/register?error_username";
        }
    }

    private void authenticate(User user) {
        // Notice how we're using an empty list for the roles
        UserDetails userDetails = new UserWithRoles(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(auth);
    }


}
