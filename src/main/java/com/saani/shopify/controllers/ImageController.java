package com.saani.shopify.controllers;

import com.saani.shopify.dto.ImageDTO;
import com.saani.shopify.exceptions.ResourceNotFoundException;
import com.saani.shopify.models.Images;
import com.saani.shopify.response.ApiResponse;
import com.saani.shopify.services.image.ImageServiceInterface;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final ImageServiceInterface imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files,@RequestParam Long productId){
        try {
            List<ImageDTO> saveImageDTOs = imageService.saveImage(files,productId);
            return ResponseEntity.ok(new ApiResponse("Image Upload Successful",saveImageDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Image Upload Failed",e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId){
        Images images = imageService.getImageById(imageId);

        try {
            Blob blob = images.getImage();
            long length = blob.length();
            ByteArrayResource resource = new ByteArrayResource(blob.getBytes(1, (int) length));

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(images.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"" + images.getFileName() + "\"")
                    .body(resource);
        } catch (SQLException e) {
            return  ResponseEntity.status(NOT_FOUND).body(null);

        }
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@RequestBody MultipartFile file, @PathVariable Long imageId){
        Images image = imageService.getImageById(imageId);

        try {
            if(image != null){
                imageService.updateImage(file,imageId);
                return ResponseEntity.ok(new ApiResponse("Update success",null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update Failed",INTERNAL_SERVER_ERROR));

    }

    @DeleteMapping("images/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
        Images image = imageService.getImageById(imageId);

        try {
            if(image != null){
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete success",null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Delete Failed",e.getMessage()));
        }

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete Failed",INTERNAL_SERVER_ERROR));
    }
}
