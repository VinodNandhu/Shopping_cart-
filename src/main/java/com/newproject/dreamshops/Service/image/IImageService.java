package com.newproject.dreamshops.Service.image;

import com.newproject.dreamshops.DTO.ImageDTO;
import com.newproject.dreamshops.Entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image GetImageById(Long ImageId);

    List<ImageDTO> SaveImages(List<MultipartFile> files, Long productId);

    void DeleteImage(Long id);

    void UpdateImage(MultipartFile file, Long ImageId);


}
