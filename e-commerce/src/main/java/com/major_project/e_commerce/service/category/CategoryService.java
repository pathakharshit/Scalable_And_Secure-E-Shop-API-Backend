package com.major_project.e_commerce.service.category;

import com.major_project.e_commerce.dto.request.CategoryRequest;
import com.major_project.e_commerce.dto.response.CategoryResponse;
import com.major_project.e_commerce.model.Category;


import java.util.List;

public interface CategoryService {
    CategoryResponse getCategoryById(Long id);
    CategoryResponse getCategoryByName(String name);
    List<CategoryResponse> getAllCategories();
    CategoryResponse addCategory(CategoryRequest categoryRequest);
    CategoryResponse updateCategory(CategoryRequest categoryRequest,Long id);
    void deleteCategoryById(Long id);

    List<CategoryResponse> converToDtoList(List<Category> categories);
    CategoryResponse convertToDto(Category category);
    Category convertToModel(CategoryRequest categoryRequest);
}
