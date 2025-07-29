package com.major_project.e_commerce.service.category;

import com.major_project.e_commerce.dto.request.CategoryRequest;
import com.major_project.e_commerce.dto.response.CategoryResponse;
import com.major_project.e_commerce.exception.AlreadyExistException;
import com.major_project.e_commerce.exception.ResourceNotFoundException;
import com.major_project.e_commerce.model.Category;
import com.major_project.e_commerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        return convertToDto(category);
    }

    @Override
    public CategoryResponse getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        return convertToDto(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return converToDtoList(categoryRepository.findAll());
    }

    @Override
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        Category category = convertToModel(categoryRequest);
        return convertToDto(Optional.of(category)
                .filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> new AlreadyExistException("Already exist")));
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest categoryRequest,Long id) {
        Category oldCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        oldCategory.setName(categoryRequest.getName());
        Category updatedCategory = categoryRepository.save(oldCategory);
        return convertToDto(updatedCategory);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete,
                () -> {throw new ResourceNotFoundException("Category not found!!");});
    }

    @Override
    public List<CategoryResponse> converToDtoList(List<Category> categories) {
        return categories.stream()
                .map(category -> modelMapper.map(category,CategoryResponse.class)).toList();
    }

    @Override
    public CategoryResponse convertToDto(Category category) {
        return modelMapper.map(category,CategoryResponse.class);
    }

    @Override
    public Category convertToModel(CategoryRequest categoryRequest) {
        return modelMapper.map(categoryRequest,Category.class);
    }
}
