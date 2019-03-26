package com.michalsadel.ecar.infrastructure.swagger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/")
class SwaggerController {
    @GetMapping
    ResponseEntity<?> swagger() {
        final URI redirectUri = UriComponentsBuilder.fromPath("swagger-ui.html").build().toUri();
        return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();
    }
}
