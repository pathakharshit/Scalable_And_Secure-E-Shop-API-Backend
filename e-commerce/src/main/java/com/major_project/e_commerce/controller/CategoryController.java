package com.major_project.e_commerce.controller;

import com.major_project.e_commerce.dto.request.CategoryRequest;
import com.major_project.e_commerce.dto.response.CategoryResponse;
import com.major_project.e_commerce.response.ApiResponse;
import com.major_project.e_commerce.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<CategoryResponse> categoryResponses = categoryService.getAllCategories();
        return ResponseEntity.ok(ApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("All categories!!")
                .data(categoryResponses)
                .build());
    }

    @PostMapping("/category/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.addCategory(categoryRequest);
        return ResponseEntity.ok(ApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .data(categoryResponse)
                .message("Category added successfully!!")
                .build());
    }

    @GetMapping("/category-by-id/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .data(categoryResponse)
                .message("Got category having id : " + id)
                .build());
    }

    @GetMapping("/category-by-name/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        CategoryResponse categoryResponse = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(ApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .data(categoryResponse)
                .message("Got category having name : " + name)
                .build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("Category deleted successfully!!")
                .build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.updateCategory(categoryRequest,id);
        return ResponseEntity.ok(ApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .data(categoryResponse)
                .message("Category updated successfully!!")
                .build());
    }

}
