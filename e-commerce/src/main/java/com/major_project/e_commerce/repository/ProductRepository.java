package com.major_project.e_commerce.repository;

import com.major_project.e_commerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategory_Name(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByBrandAndCategory_Name(String brand, String category);

    List<Product> findByName(String name);

    List<Product> findByNameAndBrand(String name, String brand);

    Long countByBrandAndName(String brand, String name);

    boolean existsByNameAndBrand(String name, String brand);
}
