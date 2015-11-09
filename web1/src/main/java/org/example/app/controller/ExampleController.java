package org.example.app.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class ExampleController {

    @RequestMapping("/")
    public String index() {
        return "Web1";
    }

}
