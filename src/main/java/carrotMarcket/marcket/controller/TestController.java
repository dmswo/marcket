package carrotMarcket.marcket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Controller
public class TestController {

    @GetMapping("/hello")
    public String hello(){
        return "dmswo";
    }
}
