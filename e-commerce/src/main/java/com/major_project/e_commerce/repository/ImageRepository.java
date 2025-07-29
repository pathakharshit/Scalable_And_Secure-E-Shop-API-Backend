package com.major_project.e_commerce.repository;

import com.major_project.e_commerce.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> findAllByProductId(Long id);
}
