package com.major_project.e_commerce.service.image;

import com.major_project.e_commerce.dto.response.ImageResponse;
import com.major_project.e_commerce.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    ImageResponse getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageResponse> saveImage(List<MultipartFile> file, Long productId);
    void updateImage(MultipartFile file,Long imageId);
    List<ImageResponse> getImageByProductId(Long id);

    List<ImageResponse> convertToResponseDtoList(List<Image> images);
    ImageResponse convertToResponseDto(Image image);
}
