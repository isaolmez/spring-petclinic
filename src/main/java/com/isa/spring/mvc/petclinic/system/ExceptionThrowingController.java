package com.isa.spring.mvc.petclinic.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionThrowingController {

    @GetMapping("/throwException")
    public String throwException() {
        throw new RuntimeException("Planned exception");
    }
}
