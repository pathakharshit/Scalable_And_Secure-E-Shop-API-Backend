package com.major_project.e_commerce.service.image;

import com.major_project.e_commerce.dto.response.ImageResponse;
import com.major_project.e_commerce.exception.ResourceNotFoundException;
import com.major_project.e_commerce.model.Image;
import com.major_project.e_commerce.model.Product;
import com.major_project.e_commerce.repository.ImageRepository;
import com.major_project.e_commerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    @Value("${api.prefix}")
    private String apiPrefix;

    @Override
    public ImageResponse getImageById(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found!!"));
        ImageResponse imageResponse = modelMapper.map(image,ImageResponse.class);
        imageResponse.setImage(image.getImage());
        return imageResponse;
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,
                () -> { throw new ResourceNotFoundException("Image not found!!"); });
    }

    @Override
    public List<ImageResponse> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setId(productId);

        List<ImageResponse> images = new ArrayList<>();

        for(MultipartFile file : files) {
            Image image = null;
            try {
                image = Image.builder()
                        .filename(file.getOriginalFilename())
                        .filetype(file.getContentType())
                        .image(new SerialBlob(file.getBytes()))
                        .product(product)
                        .build();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
            String downloadUrl = "/" + apiPrefix + "/images/image/download/" + image.getId();
            image.setDownloadUrl(downloadUrl);
            Image savedImage = imageRepository.save(image);
            savedImage.setDownloadUrl("/" + apiPrefix + "/images/image/download/" + savedImage.getId());
            imageRepository.save(savedImage);
            ImageResponse imageResponse = convertToResponseDto(savedImage);
            images.add(imageResponse);
        }
        return images;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found!!"));
        try {
            image.setFilename(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ImageResponse> getImageByProductId(Long id) {
        return convertToResponseDtoList(imageRepository.findAllByProductId(id));
    }

    @Override
    public List<ImageResponse> convertToResponseDtoList(List<Image> images) {
        return images.stream()
                .map(image -> modelMapper.map(image,ImageResponse.class))
                .toList();
    }

    @Override
    public ImageResponse convertToResponseDto(Image image) {
        return modelMapper.map(image,ImageResponse.class);
    }
}
