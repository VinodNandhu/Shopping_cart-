package com.newproject.dreamshops.Controllers;

import com.newproject.dreamshops.DTO.ImageDTO;
import com.newproject.dreamshops.Entity.Image;
import com.newproject.dreamshops.Response.ApiResponse;
import com.newproject.dreamshops.Service.image.IImageService;
import com.newproject.dreamshops.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload")
    private ResponseEntity<ApiResponse> saveImages(List<MultipartFile> files, @RequestParam Long productId) {
        try {
            List<ImageDTO> imageDTOS = imageService.SaveImages(files, productId);
            ApiResponse response = new ApiResponse("Image uploaded successfully", imageDTOS);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse("Image upload failed due to server error", e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.GetImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"").body(resource);
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
        try {
            Image image = imageService.GetImageById(imageId);
            if (image == null) {
                imageService.UpdateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Image Updated success ", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("image update failed", INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage (@PathVariable Long imageId) {
        try {
            Image image = imageService.GetImageById(imageId);
            if (image == null) {
                imageService.DeleteImage(imageId);
                return ResponseEntity.ok(new ApiResponse("Image delete success ", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("image delete failed", INTERNAL_SERVER_ERROR));
    }

}

