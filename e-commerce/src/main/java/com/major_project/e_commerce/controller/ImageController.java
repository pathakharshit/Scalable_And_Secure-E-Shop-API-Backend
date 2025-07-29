package com.major_project.e_commerce.controller;

import com.major_project.e_commerce.dto.response.ImageResponse;
import com.major_project.e_commerce.exception.ResourceNotFoundException;
import com.major_project.e_commerce.response.ApiResponse;
import com.major_project.e_commerce.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
@CrossOrigin
public class ImageController {
    private final ImageService imageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> saveImages(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("productId") Long productId) {
        if (files == null || files.isEmpty())
            throw new IllegalArgumentException("File list cannot be empty!");

        List<ImageResponse> images = imageService.saveImage(files,productId);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("Image saved successfully!!")
                        .httpStatus(HttpStatus.OK.value())
                        .data(images)
                        .build());
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) {
        ImageResponse imageResponse = imageService.getImageById(imageId);
        if (imageResponse.getImage() == null) {
            throw new ResourceNotFoundException("Image data not found!!");
        }
        try {
            ByteArrayResource resource = new ByteArrayResource(
                    imageResponse.getImage().getBytes(1,(int)imageResponse.getImage().length()));
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(imageResponse.getFiletype()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""+imageResponse.getFileName()+ "\"")
                    .body(resource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/update/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId,
                                                   @RequestPart("file") MultipartFile file) {
        ImageResponse imageResponse = imageService.getImageById(imageId);
        if (file == null || file.isEmpty())
            throw new IllegalArgumentException("File list cannot be empty!");
        System.out.println("Hello");
        imageService.updateImage(file,imageId);
        return ResponseEntity.ok(ApiResponse.builder().message("Image updated successfully!!")
                .httpStatus(HttpStatus.OK.value()).build());
    }

    @DeleteMapping(value = "/delete/{imageId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        imageService.deleteImageById(imageId);
        return ResponseEntity.ok(ApiResponse.builder().message("Image deleted successfully!!")
                .httpStatus(HttpStatus.OK.value()).build());
    }
}
