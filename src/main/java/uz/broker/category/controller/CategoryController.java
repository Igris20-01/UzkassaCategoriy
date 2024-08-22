package uz.broker.category.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.broker.category.dto.ProductCreateDto;
import uz.broker.category.dto.ProductDto;
import uz.broker.category.dto.ProductsDto;
import uz.broker.category.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductCreateDto productCreateDto) {
        ProductDto createdProduct = categoryService.create(productCreateDto);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<ProductDto>> getAllProducts(@PathVariable Long productId) {
        List<ProductDto> products = categoryService.getAllProducts(productId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/filter")
    public List<ProductsDto> filterCategories(
            @RequestParam(value = "parentId", required = false) Long parentId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "desc", required = false) String desc
    ) {
        return categoryService.filterCategories(parentId, name, desc);
    }
}





