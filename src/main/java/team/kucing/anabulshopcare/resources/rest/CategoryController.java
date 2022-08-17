package team.kucing.anabulshopcare.resources.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.request.CategoryRequest;
import team.kucing.anabulshopcare.service.CategoryService;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "03. Category Controller")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping(value = "/category/add")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> createCategory(@RequestBody CategoryRequest category){
        var newCategory = categoryService.createCategory(category);
        log.info("success create new category " + newCategory);

        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @PutMapping("/category/{id}/update")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> updateCategory(@PathVariable(value = "id") Long id, @RequestBody CategoryRequest category){
        var updateCategory = categoryService.updateCategory(category, id);
        if(updateCategory == null) {
         log.info("Category is not Updated " + category);
         log.info("Because Category with id  " + id + " is not Found");
        }
        else {
            log.info("Success Update Category " + category);
        }
        return updateCategory;
    }

    @DeleteMapping("/category/{id}/delete")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> deleteCategory(@PathVariable(value = "id") Long id){
        var deleteCategory = categoryService.deleteCategory(id);
        if(deleteCategory == null) {
            log.info("Category is not Deleted " + deleteCategory);
            log.info("Category with id " + id + " is not Found");
        } else {
            log.info("Success Delete Category " + deleteCategory);
        }
        return deleteCategory;
    }
}