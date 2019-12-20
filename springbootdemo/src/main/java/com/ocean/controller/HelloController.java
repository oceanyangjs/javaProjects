package com.ocean.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.util.Date;

//@RestController
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello(Model m) throws Exception {
        m.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
        System.out.println("test");
        if(true){
            throw new Exception("some exception");
        }
        return "hello";
    }
}
