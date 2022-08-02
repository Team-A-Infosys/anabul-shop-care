package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.entity.Category;
import team.kucing.anabulshopcare.entity.Product;

public interface CategoryService {
    ResponseEntity<Object> createCategory(Category category);
}
