package com.eircode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class SwaggerDocController {
	
	@RequestMapping("/docs")
    public String getSwaggerApiDocsPage() {
        return "redirect:swagger-ui.html";
    }

}
