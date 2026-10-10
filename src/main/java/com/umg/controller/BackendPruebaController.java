package com.umg.controller;


import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;

@Secured("ADMIN")
@Controller("/backend-prueba")
public class BackendPruebaController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}