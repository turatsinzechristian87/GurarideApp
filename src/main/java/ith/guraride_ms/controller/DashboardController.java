package ith.guraride_ms.controller;
import ith.guraride_ms.security.EndpointSessionAuthorize;
import ith.guraride_ms.service.UserService;
import ith.guraride_ms.dto.UserDto;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DashboardController {
    private final UserService userService;
    EndpointSessionAuthorize sessionAuthorize = new EndpointSessionAuthorize();
    @Autowired
    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-dashboard")
    public String userDashboard(HttpSession session, Model model) {
        if(sessionAuthorize.isRenter(session)){
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return "dashboard/user-dashboard";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }

    }

    @GetMapping("/admin-dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        if(sessionAuthorize.isAdmin(session)){
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return "dashboard/admin-dashboard";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }

    @GetMapping("/dashboard")
    public String Dashboard(HttpSession session,
                            Model model) {
        if(sessionAuthorize.isAdmin(session)){
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return "dashboard/index";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @GetMapping("/user-info")
    public String getAllPagesUserInfo(
            HttpSession session,
            Model model) {
        if(sessionAuthorize.isAdmin(session)){
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return getOnePageUserInfo(model, 1);
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }

    @GetMapping("/user-info/page/{pageNumber}")
    public String getOnePageUserInfo(
            Model model,
            @PathVariable("pageNumber") int currentPage){
        Page<UserDto> page = userService.findPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<UserDto> users = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("users", users);
        return "dashboard/user-info";
    }


    @GetMapping("/renter-info")
    public String renterInfo(HttpSession session, Model model) {
        if(sessionAuthorize.isAdmin(session)){
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            List<UserDto> users = userService.findRentersByStatus("renter");
            model.addAttribute("users", users);
            return "dashboard/renter-info";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }

    }
    @GetMapping("/worker-info")
    public String workerInfo(HttpSession session, Model model) {
        if(sessionAuthorize.isAdmin(session)){
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            List<UserDto> users = userService.findWorkersByStatus("worker");
            model.addAttribute("users", users);
            return "dashboard/worker-info";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @GetMapping("/renters-payment-info")
    public String rentPaymentInfo(
            HttpSession session,
            Model model) {
        if(sessionAuthorize.isAdmin(session)){
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return "dashboard/renters-payment-info";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }

    }

}