package com.major_project.e_commerce.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Blob;

@Data
public class ImageResponse {
    private Long id;
    private String fileName;
    private String filetype;
    @JsonIgnore
    private Blob image;
    private String downloadUrl;
}
