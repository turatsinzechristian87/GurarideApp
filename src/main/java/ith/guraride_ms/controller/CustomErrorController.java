package ith.guraride_ms.controller;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class CustomErrorController implements ErrorController{

    @RequestMapping("/error")
    public String handleError() {
        return "Error/500";
    }

    @RequestMapping("/404")
    public String handleNotFound() {
        return "Error/404";
    }
}
