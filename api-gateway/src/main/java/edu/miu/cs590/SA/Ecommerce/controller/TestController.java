package edu.miu.cs590.SA.Ecommerce.controller;

import edu.miu.cs590.SA.Ecommerce.constant.RestEndpoints;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class TestController {

    @GetMapping
    public String working(){
        return "Working";
    }

}
