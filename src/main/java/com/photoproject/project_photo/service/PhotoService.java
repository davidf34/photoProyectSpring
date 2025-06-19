package com.photoproject.project_photo.service;

import java.util.List;
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
}
