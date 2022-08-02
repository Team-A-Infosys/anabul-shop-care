package team.kucing.anabulshopcare.resources.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.kucing.anabulshopcare.entity.Category;
import team.kucing.anabulshopcare.service.CategoryService;

@RestController
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping(value = "/add/category")
    public ResponseEntity<Object> createCategory(@RequestBody Category category){
        var newCategory = categoryService.createCategory(category);
        return newCategory;
    }
}
