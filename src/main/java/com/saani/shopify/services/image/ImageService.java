package com.saani.shopify.services.image;

import com.saani.shopify.dto.ImageDTO;
import com.saani.shopify.exceptions.ResourceNotFoundException;
import com.saani.shopify.models.Images;
import com.saani.shopify.models.Products;
import com.saani.shopify.repository.ImageRepository;
import com.saani.shopify.services.product.ProductServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService implements ImageServiceInterface{
    private final ImageRepository imageRepository;
    private final ProductServiceInterface productService;

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(
                        imageRepository::delete,
                        () -> {throw new ResourceNotFoundException("Image does not exist");}
                );

    }

    @Override
    public List<ImageDTO> saveImage(List<MultipartFile> files, Long productId) {
        Products product = productService.getProductById(productId);

        List<ImageDTO> imageDTOs = new ArrayList<>();
        for (MultipartFile file:files){
            try{
                Images newImage = new Images();
                newImage.setFileName(file.getOriginalFilename());
                newImage.setFileType(file.getContentType());
                newImage.setImage(new SerialBlob(file.getBytes()));
                newImage.setProducts(product);

                String baseURL = "/api/v1/images/image/download/";
                String downloadURL = baseURL+newImage.getId();
                newImage.setDownloadUrl(downloadURL);
                Images savedImage = imageRepository.save(newImage);
                savedImage.setDownloadUrl(baseURL+savedImage.getId());
                imageRepository.save(savedImage);

                ImageDTO savedImageDTO = new ImageDTO();
                savedImageDTO.setImageId(savedImage.getId());
                savedImageDTO.setImageName(savedImage.getFileName());
                savedImageDTO.setDownloadUrl(savedImage.getDownloadUrl());

                imageDTOs.add(savedImageDTO);

            } catch(IOException | SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }

        return imageDTOs;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Images image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));

        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
