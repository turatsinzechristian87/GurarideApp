package ith.guraride_ms.controller;
import ith.guraride_ms.dto.UserDto;
import ith.guraride_ms.models.User;
import ith.guraride_ms.security.EndpointSessionAuthorize;
import ith.guraride_ms.service.BikeService;
import ith.guraride_ms.dto.BikeDto;
import ith.guraride_ms.models.Bike;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BikeController {
    private final BikeService bikeService;
    EndpointSessionAuthorize sessionAuthorize = new EndpointSessionAuthorize();
    @Autowired
    public BikeController(BikeService bikeService) {

        this.bikeService = bikeService;
    }
    @GetMapping("register-bike")
    public String BikeRegisterView(HttpSession session, Model model){
        if(sessionAuthorize.isAdmin(session)){
            Bike bike = new Bike();
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            model.addAttribute("bike", bike);
            return "register-bike";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }

    }
    @PostMapping("register-bike")
    public String BikeRegister(@Valid @ModelAttribute("bikes") BikeDto bikeDto, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("bikes", bikeDto);
            return "redirect:/register-bike?fail= Validation Error";
        }
        bikeService.saveBike(bikeDto);
        return "redirect:/bike-info";
    }
    @GetMapping("/bike-info")
    public String getAllPagesBikeInfo(
            HttpSession session,
            Model model) {
        if(sessionAuthorize.isAdmin(session)){
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return getOnePageBikeInfo(model, 1);
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }

    @GetMapping("/bike-info/page/{pageNumber}")
    public String getOnePageBikeInfo(
            Model model,
            @PathVariable("pageNumber") int currentPage) {
        Page<BikeDto> page = bikeService.findPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<BikeDto> bikes = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("bikes", bikes);
            return "dashboard/bike-info";
    }

    @GetMapping("/bike-info/delete/{bikeId}")
    public String deleteBike(@PathVariable("bikeId")Long bikeId,
                             HttpSession session,
                             Model model) {
        if(sessionAuthorize.isAdmin(session)){
            bikeService.deleteBike(bikeId);
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return "redirect:/bike-info";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @GetMapping("/worker-bike-info/delete/{bikeId}")
    public String workerDeleteBike(@PathVariable("bikeId")Long bikeId,
                             HttpSession session,
                             Model model) {
        if(sessionAuthorize.isWorker(session)){
            bikeService.deleteBike(bikeId);
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return "redirect:/worker-bike-info";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @GetMapping("/bike-info/edit/{bikeId}")
    public String editBike(@PathVariable("bikeId") Long bikeId, Model model, HttpSession session) {
        if(sessionAuthorize.isAdmin(session)){
            BikeDto bike = bikeService.findBikeById(bikeId);
            model.addAttribute("bike", bike);
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return "bike-edit";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }

        }
    @GetMapping("/worker-bike-info/edit/{bikeId}")
    public String WorkerEditBike(@PathVariable("bikeId") Long bikeId, Model model, HttpSession session) {
        if(sessionAuthorize.isWorker(session)){
            BikeDto bike = bikeService.findBikeById(bikeId);
            model.addAttribute("bike", bike);
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return "dashboard/worker-bike-edit";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }

    }
    @PostMapping("/bike-info/edit/{bikeId}")
    public String updateBike(@PathVariable("bikeId") Long bikeId,
                             @Valid @ModelAttribute("bike") BikeDto bikes,
                             BindingResult result,
                             Model model,
                             HttpSession session) {
        if(sessionAuthorize.isAdmin(session)){
            if(result.hasErrors()) {
                model.addAttribute("bike", bikes);
                return "bike-edit?fail= Validation Error!!!";
            }
            bikes.setBikeId(bikeId);
            bikeService.updateBike(bikes);
            return "redirect:/bike-info";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @PostMapping("/worker-bike-info/edit/{bikeId}")
    public String workerUpdateBike(@PathVariable("bikeId") Long bikeId,
                             @Valid @ModelAttribute("bike") BikeDto bikes,
                             BindingResult result,
                             Model model,
                             HttpSession session) {
        if(sessionAuthorize.isWorker(session)){
            if(result.hasErrors()) {
                model.addAttribute("bike", bikes);
                return "dashboard/worker-bike-edit?fail= Validation Error!!!";
            }
            bikes.setBikeId(bikeId);
            bikeService.updateBike(bikes);
            return "redirect:/worker-bike-info";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }

    @GetMapping("worker-register-bike")
    public String workerBikeRegisterView(
            Model model,
            HttpSession session){
        if(sessionAuthorize.isWorker(session)){
            Bike bike = new Bike();
            model.addAttribute("bike", bike);
            String retrievedEmail = session.getAttribute("email").toString();
            model.addAttribute("retrievedEmail", retrievedEmail);
            return "dashboard/worker-register-bike";
        }
        else{
            return "redirect:/signin?fail= Unauthorized, Please First Signin!!";
        }
    }
    @PostMapping("worker-register-bike")
    public String workerBikeRegister(@Valid @ModelAttribute("bikes") BikeDto bikeDto, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("bikes", bikeDto);
            return "redirect:/worker-register-bike?fail = Validation Error";
        }
        bikeService.saveBike(bikeDto);
        return "redirect:/worker-bike-info";
    }

}