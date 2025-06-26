package com.photoproject.project_photo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.photoproject.project_photo.model.Photo;
import com.photoproject.project_photo.model.photoDTO.PhotoDTO;
import com.photoproject.project_photo.repository.PhotoRepository;

@Service
public class PhotoService {
    private final PhotoRepository repo;

    public PhotoService(PhotoRepository repo){
        this.repo = repo;
    }

    public List<PhotoDTO> getAllPhotos(){

        return repo.findAll().stream().map(photo -> {
            PhotoDTO dto = new PhotoDTO();
            dto.setId(photo.getId());
            dto.setTitle(photo.getTitle());
            dto.setDescription(photo.getDescription());
            dto.setImagePath(photo.getImagePath());
            dto.setLikes(photo.getLikes());
            return dto;
        }).collect(Collectors.toList());
    }

    public Photo savePhoto(Photo photo){
        return repo.save(photo);
    }

    public void deletePhoto(Long id){
        repo.deleteById(id);
        
    }

    public Photo getPhoto(Long id){
        return repo.findById(id).orElse(null);
    }
    // //metodo para incrementar lo likes cada que damos click al boton
    // public void increaseLikes(Long id){
    //     //Usamos el objeto optional ya que nos permite realizar busquedas sin tener el error de NullPointerException ya que la consulta puede NO traer resultados
    //     Optional<Photo> optionalPhoto = repo.findById(id);
    //     //validamos que si haya encontrado una foto con el id
    //     if (optionalPhoto.isPresent()) {
    //         //obtenemos el objeto real dentro del Optional
    //         Photo photo = optionalPhoto.get();
    //         //Incrementamos el numero de likes
    //         photo.setLikes(photo.getLikes() + 1);
    //         //guardamos la foto en la BD
    //         repo.save(photo);
    //     }
    // }

    //metodo para incrementar likes integrando JS
    public int increaseLikesAndReturn(Long id){
        Optional<Photo> optionalPhoto = repo.findById(id);
        if (optionalPhoto.isPresent()) {
            Photo photo = optionalPhoto.get();
            photo.setLikes(photo.getLikes() + 1);
            repo.save(photo);
            return photo.getLikes();
        }
        return -1; //error controlado
    }

}
