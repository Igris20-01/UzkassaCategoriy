package uz.broker.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.broker.category.dto.*;
import uz.broker.category.entity.Product;
import uz.broker.category.repository.ProductRepository;

import java.util.*;


@Service
@RequiredArgsConstructor
public class CategoryServiceI implements CategoryService {

    private final ProductRepository productRepository;


    @Override
    public ProductDto create(ProductCreateDto productCreateDto) {
        if (productCreateDto.getParentId() == null) {
            Product product = new Product();
            product.setName(productCreateDto.getName());
            product.setDescription(productCreateDto.getDescription());
            Product savedProduct = productRepository.save(product);
            return mapProductToDto(savedProduct);
        } else {
            Product parent = productRepository.findById(productCreateDto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent product not found"));
            Product child = new Product();
            child.setName(productCreateDto.getName());
            child.setDescription(productCreateDto.getDescription());
            child.setParentId(parent.getId());
            parent.getChildren().add(child);
            Product savedChild = productRepository.save(child);
            return mapProductToDto(savedChild);
        }
    }

    @Override
    public ProductDto mapProductToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setChildren(Collections.emptyList());
        return productDto;
    }

    private ProductDto mapProductToDtoRecursive(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        List<ProductDto> childrenDto = new ArrayList<>();
        if (product.getChildren() != null && !product.getChildren().isEmpty()) {
            for (Product child : product.getChildren()) {
                ProductDto childDto = mapProductToDtoRecursive(child);
                childrenDto.add(childDto);
            }
        }
        productDto.setChildren(childrenDto);
        return productDto;
    }

    @Override
    public List<ProductDto> getAllProducts(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product requestedProduct = optionalProduct.get();
            return Collections.singletonList(mapProductToDtoRecursive(requestedProduct));
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Product> getCategoriesByParentId(Long parentId) {
        return productRepository.findByParentId(parentId);
    }

    @Override
    public List<Product> getCategoriesByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getCategoriesByDescription(String desc) {
        return productRepository.findByDescription(desc);
    }

    @Override
    public List<Product> getAllCategories() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductsDto> filterCategories(Long parentId, String name, String desc) {
        List<Product> filteredCategories;
        Map<Long, Product> categoryMap = new HashMap<>();
        if (parentId != null) {
            filteredCategories = getCategoriesByParentId(parentId);
        } else if (name != null) {
            filteredCategories = getCategoriesByName(name);
        } else if (desc != null) {
            filteredCategories = getCategoriesByDescription(desc);
        } else {
            filteredCategories = getAllCategories();
        }
        return convertToDtoList(filteredCategories, categoryMap);

    }

    @Override
    public ProductsDto convertToDto(Product category, Map<Long, Product> categoryMap) {
        ProductsDto categoryDto = new ProductsDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        categoryDto.setParentId(category.getParentId());
        categoryDto.setChildList(new ArrayList<>());
        for (Product child : category.getChildren()) {
            categoryDto.getChildList().add(convertToDto(child, categoryMap));
        }
        return categoryDto;
    }

    @Override
    public List<ProductsDto> convertToDtoList(List<Product> categories, Map<Long, Product> categoryMap) {
        List<ProductsDto> categoryDtoList = new ArrayList<>();
        for (Product category : categories) {
            categoryDtoList.add(convertToDto(category, categoryMap));
        }
        return categoryDtoList;
    }


}
