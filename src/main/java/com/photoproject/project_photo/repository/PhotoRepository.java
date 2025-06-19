package com.photoproject.project_photo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.photoproject.project_photo.model.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo,Long>{
    
}
