package ith.guraride_ms.controller;

import ith.guraride_ms.dto.BikeDto;
import ith.guraride_ms.dto.RentalDto;
import ith.guraride_ms.dto.UserDto;
import ith.guraride_ms.models.Bike;
import ith.guraride_ms.models.Rental;
import ith.guraride_ms.models.User;
import ith.guraride_ms.security.EndpointSessionAuthorize;
import ith.guraride_ms.service.RentalService;
import ith.guraride_ms.service.BikeService;
import ith.guraride_ms.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WorkerController {
    private final RentalService rentalService;
    private final UserService userService;
    private final BikeService bikeService;

    EndpointSessionAuthorize sessionAuthorize = new EndpointSessionAuthorize();
    @Autowired
    public WorkerController(RentalService rentalService, BikeService bikeService, UserService userService){
        this.rentalService = rentalService;
        this.userService = userService;
        this.bikeService = bikeService;
    }
    @GetMapping("worker-dashboard")
    public String viewWorkerDashboard(HttpSession session,
                                      Model model){
        if(sessionAuthorize.isWorker(session)){
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return "dashboard/worker-dashboard";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @GetMapping("worker-renter-info")
    public String viewRenterInfo(Model model,
                                 HttpSession session){
        if(sessionAuthorize.isWorker(session)){
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            List<UserDto> users = userService.findRentersByStatus("renter");
            model.addAttribute("users", users);
            return "dashboard/worker-renter-info";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @GetMapping("worker-bike-info")
    public String getAllPagesViewBikeInfo(
            Model model,
            HttpSession session){
        if(sessionAuthorize.isWorker(session)){
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return getOnePageViewBikeInfo(model, 1);
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @GetMapping("worker-bike-info/page/{pageNumber}")
    public String getOnePageViewBikeInfo(
            Model model,
           @PathVariable("pageNumber") int currentPage){
        Page<BikeDto> page = bikeService.findPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<BikeDto> bikes = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("bikes", bikes);
            return "dashboard/worker-bike-info";
    }

    @GetMapping("worker-all-rental")
    public String viewRentalOps(Model model,
                                HttpSession session){
        if(sessionAuthorize.isWorker(session)){
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            List<RentalDto> rentals = rentalService.findAllRental();
            model.addAttribute("rentalsInfo", rentals);
            return "dashboard/worker-all-rental";
        }
        else{ return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @GetMapping("/worker-update-returned-value")
    public  String viewWorkerUpdateReturned(
            HttpSession session,
            Model model){
        if(sessionAuthorize.isWorker(session)){
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return "redirect:/worker-all-rental";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @PostMapping("/worker-update-returned-value/{rental_id}")
    public  String workerUpdateReturned(
            @RequestParam Long rental_id,
            RentalDto re){
        RentalDto rentalDto = rentalService.findRentalById(rental_id);
        rentalDto.setIsReturned(true);
        rentalService.updateRental(rentalDto);
        return "redirect:/worker-all-rental";
    }
    @GetMapping("/worker-register-rental")
    public String viewWorkerRegisterRental(Model model,
                                           HttpSession session){
        if(sessionAuthorize.isWorker(session)){
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            Rental rental = new Rental();
            List<UserDto> userInfo = userService.findRentersByStatus("renter");
            List<Long> usersIds = userInfo.stream().map(UserDto::getId).collect(Collectors.toList());
            model.addAttribute("userIds", usersIds);

            List<BikeDto> bikeInfo = bikeService.findAllBike();
            List<Long> bikeIds = bikeInfo.stream().map(BikeDto::getBikeId).collect(Collectors.toList());
            model.addAttribute("bikeIds", bikeIds);

            model.addAttribute("rental", rental);
            return "dashboard/worker-register-rental";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @PostMapping("/worker-register-rental")
    public String workerRegisterRental(
            @Valid @ModelAttribute("rentals") RentalDto rentalDto,
            BindingResult result,
            Model model){
        if(result.hasErrors()){
            model.addAttribute("rentals", rentalDto);
            return "redirect:/worker-register-rental?fail= Validation Error!!";
        }
        rentalDto.setIsReturned(false);
        rentalService.saveRental(rentalDto);
        return "redirect:/worker-all-rental";
    }
}
