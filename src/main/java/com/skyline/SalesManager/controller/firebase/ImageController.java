package com.skyline.SalesManager.controller.firebase;

import com.skyline.SalesManager.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/upload")
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile multipartFile) {
        return imageService.upload(multipartFile);
    }
}

