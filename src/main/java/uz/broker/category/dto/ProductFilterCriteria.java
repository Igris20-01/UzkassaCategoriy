package uz.broker.category.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilterCriteria {

    private Long id;
    private Long parentId;
    private String name;
    private String description;
}
