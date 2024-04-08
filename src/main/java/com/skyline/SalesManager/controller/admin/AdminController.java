package com.skyline.SalesManager.controller.admin;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/home")
public class AdminController {

    @GetMapping()
    public String get(){
        return "GET:: admin controller";
    }
    @PostMapping()
    public String post(){
        return "POST:: admin controller";
    }
    @PutMapping()
    public String put(){
        return "PUT:: admin controller";
    }
    @DeleteMapping()
    public String delete(){
        return "Delete:: admin controller";
    }
}
