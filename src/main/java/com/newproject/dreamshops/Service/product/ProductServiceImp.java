package com.newproject.dreamshops.Service.product;

import com.amazonaws.services.kms.model.AlreadyExistsException;
import com.newproject.dreamshops.DTO.ImageDTO;
import com.newproject.dreamshops.DTO.ProductDTO;
import com.newproject.dreamshops.Entity.Category;
import com.newproject.dreamshops.Entity.Image;
import com.newproject.dreamshops.Entity.Product;
import com.newproject.dreamshops.Repository.CategoryRepository;
import com.newproject.dreamshops.Repository.ImageRepository;
import com.newproject.dreamshops.Repository.ProductRepository;
import com.newproject.dreamshops.exceptions.ProductNotFoundException;
import com.newproject.dreamshops.request.AddProductRequest;

import com.newproject.dreamshops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        if (productExists(request.getName(),request.getBrand())) {
            throw new AlreadyExistsException(request.getBrand() +" "+ request.getName()+ " already exists you may update product instead !");
        }
        // Fetch category from the database by name or create a new one if it doesn't exist
        Category category = categoryRepository.findByName(request.getCategory());
        if (category == null) {
            category = new Category();
            category.setName(request.getCategory());
            category = categoryRepository.save(category); // Save new category
        }

        // Create the product with the fetched/created category
        Product product = createProduct(request, category);

        return productRepository.save(product);
    }

    private boolean productExists(String name, String brand) {
        return productRepository.existsByNameAndBrand(name, brand);

    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(request.getName(), request.getBrand(), request.getPrice(), request.getInventory(), request.getDescription(), category

        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product  not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> {
            throw new ProductNotFoundException("Product  not found!");
        });
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));

    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());
        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory((category));
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String Category, String Brand) {
        return productRepository.findByCategoryNameAndBrand(Category, Brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }


    @Override
    public List<Product> getProductsByBrandAndName(String name, String brand) {
        return productRepository.findByBrandAndName(name, brand);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String Name) {
        return productRepository.countByBrandAndName(brand, Name);
    }

    @Override
    public List<ProductDTO> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::covertToDto).toList();
    }

    @Override
    public ProductDTO covertToDto(Product product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDTO> imageDTOS = images.stream()
                .map(image -> modelMapper.map(image, ImageDTO.class))
                .toList();
        productDTO.setImages(imageDTOS);
        return productDTO;
    }
}
