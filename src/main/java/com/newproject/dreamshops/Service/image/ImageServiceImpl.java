package com.newproject.dreamshops.Service.image;

import com.newproject.dreamshops.DTO.ImageDTO;
import com.newproject.dreamshops.Entity.Image;
import com.newproject.dreamshops.Entity.Product;
import com.newproject.dreamshops.Repository.ImageRepository;
import com.newproject.dreamshops.Service.product.IProductService;
import com.newproject.dreamshops.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements IImageService {

  private final ImageRepository imageRepository;
    private final IProductService productService;


    @Override
    public Image GetImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No such image found with id " + id));
    }

    @Override
    public List<ImageDTO> SaveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDTO> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try{
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/" ;
                String downloadUrl = buildDownloadUrl +image.getId();
                image.setDownloadUrl(downloadUrl);
               Image savedImage = imageRepository.save(image);

               savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
               imageRepository.save(savedImage);

               ImageDTO imageDTO = new ImageDTO();
               imageDTO.setImageId(savedImage.getId());
               imageDTO.setImageName(savedImage.getFileName());
               imageDTO.setDownloadUrl(savedImage.getDownloadUrl());
               savedImageDto.add(imageDTO);
            } catch (IOException  | SQLException e){
                throw  new RuntimeException(e.getMessage());

            }
        }

        return savedImageDto ;
    }


    @Override
    public void DeleteImage(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, ()->{
            throw new ResourceNotFoundException("No image found with id ! " + id);
                });
    }

    @Override
    public void UpdateImage(MultipartFile file, Long ImageId) {

        Image image = GetImageById(ImageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException  | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
