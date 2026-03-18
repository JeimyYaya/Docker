package co.edu.escuelaing.framework.controller;

import co.edu.escuelaing.framework.annotations.GetMapping;
import co.edu.escuelaing.framework.annotations.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from your custom framework";
    }
}
