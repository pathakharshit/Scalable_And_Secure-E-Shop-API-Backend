package com.major_project.e_commerce.controller;

import com.major_project.e_commerce.dto.request.ProductRequest;
import com.major_project.e_commerce.dto.response.ProductResponse;
import com.major_project.e_commerce.response.ApiResponse;
import com.major_project.e_commerce.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<ProductResponse> productResponses = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.builder()
                .message("All Products!!")
                .data(productResponses)
                .httpStatus(HttpStatus.OK.value())
                .build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/product/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.addProduct(productRequest);
        return ResponseEntity.ok(ApiResponse.builder()
                        .message("Product added successfully!!")
                        .data(productResponse)
                        .httpStatus(HttpStatus.OK.value())
                .build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductRequest productRequest, @PathVariable Long productId) {
        ProductResponse productResponse = productService.updateProduct(productRequest,productId);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Produxt updated successfully!!")
                .data(productResponse)
                .httpStatus(HttpStatus.OK.value())
                .build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Product deleted successfully!!")
                .httpStatus(HttpStatus.OK.value())
                .build());
    }

    @GetMapping("/product-by-name/{name}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        List<ProductResponse> productResponses = productService.getProductByName(name);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Got product having name : " + name)
                .data(productResponses)
                .httpStatus(HttpStatus.OK.value())
                .build());
    }

    @GetMapping("/product-by-id/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        ProductResponse productResponse = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Got product having id : " + id)
                .data(productResponse)
                .httpStatus(HttpStatus.OK.value())
                .build());
    }

    @GetMapping("/product-by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand) {
        List<ProductResponse> productResponses = productService.getProductByBrand(brand);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("All " + brand + " products!!")
                .data(productResponses)
                .httpStatus(HttpStatus.OK.value())
                .build());
    }

    @GetMapping("/product-by-name-and-brand")
    public ResponseEntity<ApiResponse> getProductByNameAndBrand(@RequestParam String name,@RequestParam String brand) {
        List<ProductResponse> productResponses = productService.getProductByNameAndBrand(name,brand);
        return ResponseEntity.ok(ApiResponse.builder()
                .message(brand + " " + name + "s")
                .data(productResponses)
                .httpStatus(HttpStatus.OK.value())
                .build());
    }

    @GetMapping("/product-by-category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        List<ProductResponse> productResponses = productService.getProductByBrandAndCategory(brand, category);
        return ResponseEntity.ok(ApiResponse.builder()
                .message(category + " category of " + brand)
                .data(productResponses)
                .httpStatus(HttpStatus.OK.value())
                .build());
    }

    @GetMapping("/product/category")
    public ResponseEntity<ApiResponse> getProductByCategory(@RequestParam String category) {
        List<ProductResponse> productResponses = productService.getProductByCategory(category);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("All " + category + " products : ")
                .data(productResponses)
                .httpStatus(HttpStatus.OK.value())
                .build());
    }

    @GetMapping("/product/count-by-name-and-brand")
    public ResponseEntity<ApiResponse> countProductByNameAndBrand(@RequestParam String name,@RequestParam String brand) {
        Long count = productService.countProductsByBrandAndName(brand,name);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Number of " + brand + " " + name)
                .data(count)
                .httpStatus(HttpStatus.OK.value())
                .build());
    }
}
