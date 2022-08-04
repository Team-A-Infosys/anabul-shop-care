package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.CategoryRequest;
import team.kucing.anabulshopcare.entity.Category;
import team.kucing.anabulshopcare.entity.Product;

public interface CategoryService {
    Category createCategory(CategoryRequest category);

    ResponseEntity<Object> updateCategory(CategoryRequest category, Long id);

    ResponseEntity<Object> deleteCategory(Long id);
}