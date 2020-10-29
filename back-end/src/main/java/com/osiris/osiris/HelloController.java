package com.osiris.osiris;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/")
public class HelloController {

    @GetMapping
	public String hello() {
		return "Osiris App it's working! \\o/";
    }
}