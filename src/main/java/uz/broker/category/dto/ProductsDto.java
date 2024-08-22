package uz.broker.category.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ProductsDto {

    private Long id;
    private String name;
    private String description;
    private Long parentId;
    private List<ProductsDto> childList;
}
