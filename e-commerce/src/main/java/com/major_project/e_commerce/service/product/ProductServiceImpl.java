package com.major_project.e_commerce.service.product;

import com.major_project.e_commerce.dto.request.ProductRequest;
import com.major_project.e_commerce.dto.response.ImageResponse;
import com.major_project.e_commerce.dto.response.ProductResponse;
import com.major_project.e_commerce.exception.AlreadyExistException;
import com.major_project.e_commerce.exception.ResourceNotFoundException;
import com.major_project.e_commerce.model.Category;
import com.major_project.e_commerce.model.Image;
import com.major_project.e_commerce.model.Product;
import com.major_project.e_commerce.repository.CategoryRepository;
import com.major_project.e_commerce.repository.ImageRepository;
import com.major_project.e_commerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductResponse addProduct(ProductRequest product) {
        if(productExist(product.getName(),product.getBrand())) {
            throw new AlreadyExistException(product.getBrand() + " "  + product.getName() + " already exist you may  update the product instead");
        }
        Category category = categoryRepository.findByName(product.getCategory().getName())
                .orElseGet(() -> {
                    Category newCategory = Category.builder()
                            .name(product.getCategory().getName())
                            .build();
                    return categoryRepository.save(newCategory);
                });
        product.setCategory(category);
        return convertToResponseDto(productRepository.save(createProduct(product)));
    }


    private boolean productExist(String name,String brand) {
        return productRepository.existsByNameAndBrand(name,brand);
    }

    private Product createProduct(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .brand(productRequest.getBrand())
                .price(productRequest.getPrice())
                .category(productRequest.getCategory())
                .inventory(productRequest.getInventory())
                .description(productRequest.getDescription())
                .build();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return convertToResponseDto(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found")));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,() -> {
                    throw new ResourceNotFoundException("Product not found");
                });
    }

    @Override
    public ProductResponse updateProduct(ProductRequest product, Long id) {
        return convertToResponseDto(productRepository.findById(id)
                .map(existingProduct -> updateExistingProduct(existingProduct,product))
                .map(productRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!!")));
    }

    private Product updateExistingProduct(Product existingProduct,ProductRequest productRequest) {
        existingProduct.setName(productRequest.getName());
        existingProduct.setCategory(productRequest.getCategory());
        existingProduct.setBrand(productRequest.getBrand());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setInventory(productRequest.getInventory());
        existingProduct.setDescription(productRequest.getDescription());

        Optional<Category> category = categoryRepository.findByName(productRequest.getCategory().getName());
        Category cat;
        if(category.isEmpty()) {
            Category newCategory = Category.builder().name(productRequest.getCategory().getName()).build();
            categoryRepository.save(newCategory);
            cat = newCategory;
        }
        else cat = category.get();
        existingProduct.setCategory(cat);
        return existingProduct;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return convertToResponseDtoList(productRepository.findAll());
    }

    @Override
    public List<ProductResponse> getProductByCategory(String category) {
        return convertToResponseDtoList(productRepository.findByCategory_Name(category));
    }

    @Override
    public List<ProductResponse> getProductByBrand(String brand) {
        return convertToResponseDtoList(productRepository.findByBrand(brand));
    }

    @Override
    public List<ProductResponse> getProductByBrandAndCategory(String brand, String category) {
        return convertToResponseDtoList(productRepository.findByBrandAndCategory_Name(brand,category));
    }

    @Override
    public List<ProductResponse> getProductByName(String name) {
        return convertToResponseDtoList(productRepository.findByName(name));
    }

    @Override
    public List<ProductResponse> getProductByNameAndBrand(String name, String brand) {
        return convertToResponseDtoList(productRepository.findByNameAndBrand(name,brand));
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }

    @Override
    public Product convertToModel(ProductResponse productResponse) {
        return  modelMapper.map(productResponse,Product.class);
    }

    @Override
    public List<ProductResponse> convertToResponseDtoList(List<Product> products) {
        return products.stream()
                .map(this::convertToResponseDto)
                .toList();
    }

    @Override
    public ProductResponse convertToResponseDto(Product product) {
        ProductResponse productResponse = modelMapper.map(product,ProductResponse.class);
        List<Image> images = imageRepository.findAllByProductId(product.getId());
        List<ImageResponse> imageResponses = images.stream()
                .map(image -> modelMapper.map(image,ImageResponse.class))
                .toList();
        productResponse.setImages(imageResponses);
        return productResponse;
    }
}
