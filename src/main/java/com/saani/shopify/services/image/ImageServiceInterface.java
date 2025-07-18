package com.saani.shopify.services.image;

import com.saani.shopify.dto.ImageDTO;
import com.saani.shopify.models.Images;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImageServiceInterface {
    Images getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDTO> saveImage(List<MultipartFile> file, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
