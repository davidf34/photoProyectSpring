package com.photoproject.project_photo.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.photoproject.project_photo.model.Photo;
import com.photoproject.project_photo.service.PhotoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
public class PhotoController {
    private final PhotoService service;
    
    @Value("${upload.dir}")
    private String uploadDir;
    //Este bloque se inyecta automaticamente para lograr usar la ID(inyeccion de dependencias) correctamente
    public PhotoController(PhotoService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("photos", service.getAllPhotos());
        return "index";
    }

    @GetMapping("/add")
    public String addPhotoForm(Model model) {
        model.addAttribute("photo", new Photo());//Acá se le pasa como parámetro el objeto vacio de Photo, por eso la forma de que accede en el HTML es
                                                               //th:field="*{title}": Thymeleaf usará el campo photo.title
                                                               //Esto va ligado con el PostMapping, porque es la forma en la que se enviaría la peticion y por eso se debe 
                                                               //colocar el @ModelAttribute
        return "add-photo";
    }

    // @GetMapping("/like/{id}")
    // public String likePhoto(@PathVariable Long id, Model model) {
    //     service.increaseLikes(id);

    //     return "redirect:/";
    // }
    
    //Integración con JS
    @GetMapping("/like/{id}")
    @ResponseBody //Esta anotacion hace que el metodo devuelva JSON en lugar de una vista
    public int likePhoto(@PathVariable Long id) {
        return service.increaseLikesAndReturn(id);
    }


    @PostMapping("/add")     //⬆️⬆️⬆️⬆️⬆️⬆️⬆️⬆️      //Busca el parámetro imageFile en el form, MultipartFile es el tipo de Spring para archivos subidos(Images,PDFs.etc)
    public String savePhoto(@ModelAttribute Photo photo, @RequestParam("imageFile") MultipartFile file) throws IOException{
        if (!file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename(); //UUID.randomUUID() genera un ID único como b1234af0-ff31-4a3f-b132-52a0a0002fa2
                                                                                    //file.getOriginalFilename() te da el nombre original del archivo (ej: foto.jpg)
            file.transferTo(new File(uploadDir + "/" + fileName)); //Crea un nuevo archivo en el path "uploadDir", usa el nombre generado en la linea anterior y 
                                                                   //transferTo... escribe el contenido del archivo subido fisicamente en el disco
            photo.setImagePath(fileName); //Guarda el nombre del archivo en el atributo imagePath del objeto Photo
        }

        service.savePhoto(photo);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deletePhoto(@PathVariable Long id) {
        service.deletePhoto(id);
        return "redirect:/";
    }


}
