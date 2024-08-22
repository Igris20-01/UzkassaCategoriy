package uz.broker.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.broker.category.entity.Product;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findByDescription(String desc);

    List<Product> findByName(String name);

    List<Product> findByParentId(Long parentId);
}
