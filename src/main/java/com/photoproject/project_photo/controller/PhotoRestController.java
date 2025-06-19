package com.photoproject.project_photo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.photoproject.project_photo.model.photoDTO.PhotoDTO;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api")
public class PhotoRestController {

    @GetMapping("/photo_details")
    public  PhotoDTO clasePhoto(){
        PhotoDTO photo = new PhotoDTO();
        photo.setDescription("Imagen de un alcón volando");
        photo.setTitle("Imagen de un alcón");
        photo.setImagePath("/home");
        return photo;
    }

}
