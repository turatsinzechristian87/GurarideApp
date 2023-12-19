package ith.guraride_ms.controller;

import ith.guraride_ms.dto.BikeDto;
import ith.guraride_ms.dto.RentalDto;
import ith.guraride_ms.dto.UserDto;
import ith.guraride_ms.models.Bike;
import ith.guraride_ms.models.Rental;
import ith.guraride_ms.models.User;
import ith.guraride_ms.security.EndpointSessionAuthorize;
import ith.guraride_ms.service.BikeService;
import ith.guraride_ms.service.RentalService;
import ith.guraride_ms.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    private final UserService userService;
    private final BikeService bikeService;
    private final RentalService rentalService;
    EndpointSessionAuthorize sessionAuthorize = new EndpointSessionAuthorize();
    @Autowired
    public UserController(
            UserService userService,
            BikeService bikeService,
            RentalService rentalService) {
        this.userService = userService;
        this.bikeService = bikeService;
        this.rentalService = rentalService;
    }

    @GetMapping("/user-available")
    public String userAvailable(HttpSession session, Model model) {
        if(sessionAuthorize.isRenter(session)){
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            List<BikeDto> bike = bikeService.findAllBike();
            model.addAttribute("bikes", bike);
            return "dashboard/user-available";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @GetMapping("/user-reserved")
    public String userReserved(HttpSession session, Model model) {
        if(sessionAuthorize.isRenter(session)){
            Rental rental = new Rental();
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
//            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//            System.out.println(retrievedEmail);
//            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            List<RentalDto> reservedBike = rentalService.findUserRentedBikes(retrievedEmail, "RESERVE");
            model.addAttribute("reservedBikes", reservedBike);
            return "dashboard/user-reserved";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @GetMapping("/user-rented")
    public String userRented(HttpSession session, Model model) {
        if(sessionAuthorize.isRenter(session)){
            Rental rental = new Rental();
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
//            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//            System.out.println(retrievedEmail);
//            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            List<RentalDto> rentedBike = rentalService.findUserRentedBikes(retrievedEmail, "RENT");
            model.addAttribute("rentedBikes", rentedBike);
            return "dashboard/user-rented";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @GetMapping("/user-profile")
    public String userProfile(HttpSession session, Model model) {
        if(sessionAuthorize.isRenter(session)){
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return "dashboard/user-profile";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }



    @GetMapping("/user-reserve-rental")
    public String viewWorkerRegisterRental(HttpSession session, Model model){
        if(sessionAuthorize.isRenter(session)){
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            Bike bike = new Bike();
            model.addAttribute("bike", bike);
            return "register-bike";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @PostMapping("/user-reserve-rental")
    public String workerRegisterRental(
            HttpSession session,
            @Valid @ModelAttribute("rentals") RentalDto rentalDto,
            BindingResult result,
            Model model){

        if(result.hasErrors()){
            model.addAttribute("rentals", rentalDto);
            return "redirect:/user-reserve-rental?fail";
        }
        String retrievedEmail = session.getAttribute("email").toString();
        model.addAttribute("retrievedEmail", retrievedEmail);
        Long retrievedID = Long.parseLong(session.getAttribute("userID").toString());
        User user = new User();
        user.setId(retrievedID);
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//        System.out.println(retrievedID);
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        rentalDto.setIsReturned(false);
        rentalDto.setUser(user);

        rentalService.saveRental(rentalDto);
        return "redirect:/user-reserved";
    }

@GetMapping("user-info/delete/{id}")
    public String DeleteUser(
            Model model,
            @PathVariable("id") Long userId
            ){
        userService.deleteUser(userId);
        return"redirect:/user-info";

}
@GetMapping("/user-info/edit/{id}")
public String viewEditUserInfo(
        @PathVariable("id") Long userId,
        Model model
        ){
        UserDto user = userService.findUserById(userId);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
    System.out.println(user.getId());
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
        model.addAttribute("users", user);
        return "dashboard/user-info-edit";
}
@PostMapping("/user-info/edit/{id}")
public String editUserInfo(@PathVariable("id") Long userId,
                           @Valid @ModelAttribute("user") UserDto users,
                           BindingResult result,
                           Model model,
                           HttpSession session){
        if(sessionAuthorize.isAdmin(session)){
            if(result.hasErrors()){
                model.addAttribute("user", users);
                return "dashboard/user-info?fail = Validation Error!!";
            }
            users.setId(userId);
            userService.updateUser(users);
            return "redirect:/user-info";
        }else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
}
}
