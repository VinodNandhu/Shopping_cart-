package com.newproject.dreamshops.Service.product;

import com.newproject.dreamshops.DTO.ProductDTO;
import com.newproject.dreamshops.Entity.Product;
import com.newproject.dreamshops.request.AddProductRequest;
import com.newproject.dreamshops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product ,Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String Category, String Brand);
    List<Product> getProductsByName(String name);
//    List<Product> getProductsByBrandName(String brand,String name);
    List<Product> getProductsByBrandAndName( String name, String brand);
    Long countProductsByBrandAndName(String brand, String Name);


    List<ProductDTO> getConvertedProducts(List<Product> products);

    ProductDTO covertToDto(Product product);
}
