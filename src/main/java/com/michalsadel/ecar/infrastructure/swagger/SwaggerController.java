package com.michalsadel.ecar.infrastructure.swagger;

import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.*;

import java.net.*;

@Controller
@RequestMapping("/")
class SwaggerController {
    @GetMapping
    ResponseEntity<?> swagger() {
        final URI redirectUri = UriComponentsBuilder.fromPath("swagger-ui.html").build().toUri();
        return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();
    }
}
