package uz.broker.category.mapper;

import org.mapstruct.Mapper;
import uz.broker.category.dto.ProductDto;
import uz.broker.category.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductsMapper {

    ProductDto toDto(Product entity);

    Product toEntity(ProductDto dto);
}
