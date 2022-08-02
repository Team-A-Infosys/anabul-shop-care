package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.kucing.anabulshopcare.entity.Category;
import team.kucing.anabulshopcare.repository.CategoryRepository;
import team.kucing.anabulshopcare.service.CategoryService;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<Object> createCategory(Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.categoryRepository.save(category));
    }
}
