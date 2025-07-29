package com.major_project.e_commerce.service.product;

import com.major_project.e_commerce.dto.request.ProductRequest;
import com.major_project.e_commerce.dto.response.ProductResponse;
import com.major_project.e_commerce.model.Product;

import java.util.List;

public interface ProductService {
    ProductResponse addProduct(ProductRequest product);
    ProductResponse getProductById(Long id);
    void deleteProductById(Long id);
    ProductResponse updateProduct(ProductRequest product,Long id);
    List<ProductResponse> getAllProducts();
    List<ProductResponse> getProductByCategory(String category);
    List<ProductResponse> getProductByBrand(String brand);
    List<ProductResponse> getProductByBrandAndCategory(String brand,String category);
    List<ProductResponse> getProductByName(String name);
    List<ProductResponse> getProductByNameAndBrand(String name,String brand);
    Long countProductsByBrandAndName(String brand,String name);

    Product convertToModel(ProductResponse productResponse);

    List<ProductResponse> convertToResponseDtoList(List<Product> products);

    ProductResponse convertToResponseDto(Product product);
}
