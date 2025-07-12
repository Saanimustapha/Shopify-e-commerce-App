package com.saani.shopify.dto;

import lombok.Data;

@Data
public class ImageDTO {
    private Long imageId;
    private String imageName;
    private String downloadUrl;
}
