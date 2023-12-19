package ith.guraride_ms.controller;
import ith.guraride_ms.dto.LoginDto;
import ith.guraride_ms.service.UserService;
import ith.guraride_ms.dto.UserDto;
import ith.guraride_ms.models.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    private final UserService userService;
    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;

    }
    @GetMapping("/")
    public String home() {
        return "index";
    }
    @GetMapping("/signin")
    public String Signin(Model model) {
        User users = new User();
        model.addAttribute("users", users);
        return "signin";
    }

    @PostMapping("/signin")
    public String login(@Valid @ModelAttribute("user") LoginDto loginDto,
                        @RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User existingUserEmail = userService.findByEmail(loginDto.getEmail());
        if(existingUserEmail == null || existingUserEmail.getEmail() == null || existingUserEmail.getEmail().isEmpty()) {
            return "redirect:/signin?fail= Not Registered, First Create an Account!";

        }
        User user = userService.findByEmailAndPassword(email, password);
        if (user != null) {
            if(user.getStatus().equals("admin")){
                session.setAttribute("email", email);
                session.setAttribute("status", user.getStatus());
                session.setAttribute("userID", user.getId());
                session.setAttribute("isAdmin", true);
                return "redirect:/dashboard";
            }
            else if(user.getStatus().equals("worker")){
                session.setAttribute("email", email);
                session.setAttribute("status", user.getStatus());
                session.setAttribute("userID", user.getId());
                session.setAttribute("isWorker", true);
                return "redirect:/worker-dashboard";
            }else{
                session.setAttribute("email", email);
                session.setAttribute("status", user.getStatus());
                session.setAttribute("userID", user.getId());
                session.setAttribute("isUser", true);
                return "redirect:/user-dashboard";
            }

//                    // Set the email in local storage
//                    localStorage.setItem('email', email);
//
//        // Retrieve the email from local storage
//        const storedEmail = localStorage.getItem('email');
//
//        // Remove the email from local storage
//                    localStorage.removeItem('email');

        } else {
            model.addAttribute("error", "Invalid email or password");
            return "redirect:/signin?fail= Invalid Password!!";
        }
    }
    @GetMapping("/signup")
    public String Signup(Model model)
    {
        User users = new User();
        model.addAttribute("users", users);
        return "signup";
    }

    @PostMapping("/signup")
    public String saveUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model){
        User existingUserEmail = userService.findByEmail(userDto.getEmail());
        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
            return "redirect:/signup?fail= User with that Email Already Exists!!!";

        }
        if(result.hasErrors()){
            model.addAttribute("users", userDto);
            return "redirect:/signup?fail= Validation Error!!";
        }
        userService.saveUser(userDto);
        return "redirect:/signin";
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/signin";
    }
}