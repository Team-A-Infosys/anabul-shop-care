package team.kucing.anabulshopcare.resources.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.request.CategoryRequest;
import team.kucing.anabulshopcare.entity.Category;
import team.kucing.anabulshopcare.service.CategoryService;

@RestController
@AllArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping(value = "/category/add")
    public ResponseEntity<Object> createCategory(@RequestBody CategoryRequest category){
        var newCategory = categoryService.createCategory(category);
        log.info("success create new category " + newCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @PutMapping("/category/update/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable(value = "id") Long id, @RequestBody CategoryRequest category){
        var updateCategory = categoryService.updateCategory(category, id);

        return updateCategory;
    }

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable(value = "id") Long id){
        var deleteCategory = categoryService.deleteCategory(id);

        return deleteCategory;
    }
}