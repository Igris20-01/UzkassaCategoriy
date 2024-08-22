package uz.broker.category.service;

import uz.broker.category.dto.*;
import uz.broker.category.entity.Product;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    ProductDto create(ProductCreateDto product);

    ProductDto mapProductToDto(Product product);

    List<ProductDto> getAllProducts(Long productId);


    List<Product> getCategoriesByParentId(Long parentId);

    List<Product> getCategoriesByName(String name);

    List<Product> getCategoriesByDescription(String desc);

    List<Product> getAllCategories();

    List<ProductsDto> filterCategories(Long parentId, String name, String desc);

    ProductsDto convertToDto(Product category, Map<Long, Product> categoryMap);

    List<ProductsDto> convertToDtoList(List<Product> categories, Map<Long, Product> categoryMap);
}
