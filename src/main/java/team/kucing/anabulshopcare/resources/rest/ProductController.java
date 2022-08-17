package team.kucing.anabulshopcare.resources.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.dto.request.ProductRequest;
import team.kucing.anabulshopcare.dto.request.UpdateProduct;
import team.kucing.anabulshopcare.service.ProductService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "02. Product Controller")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> getAllProducts(@ParameterObject Pageable pageable){
        return this.productService.listProducts(pageable);
    }

    @PostMapping(value = "/product/add",consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> addProduct(@RequestPart MultipartFile file, @RequestPart @Valid ProductRequest product){
        return this.productService.createProduct(product,file);
    }

    @PutMapping(value = "/product/{id}/update")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestPart MultipartFile file, @RequestPart @Valid UpdateProduct productRequest){
        return this.productService.updateProduct(productRequest, file, id);
    }
    
    @GetMapping("/products/search/name")
    @PreAuthorize("none")
    public ResponseEntity<Object> findByProductName(@RequestParam(value = "productName") String name, @ParameterObject Pageable pageable) {
        return this.productService.filterProductByName(name,pageable);
    }
    
    @GetMapping("/products/search/location")
    @PreAuthorize("none")
    public ResponseEntity<Object> filterProductsByLocation(@RequestParam(value = "location", required = false) String location, @ParameterObject  Pageable pageable){
        return this.productService.filterProductsByLocation(location, pageable);
    }

    @GetMapping("/products/search/price")
    @PreAuthorize("none")
    public ResponseEntity<Object> filterProductsByPrice(@RequestParam(value = "start", required = false)double startPrice, @RequestParam(value = "end", required = false)double endPrice, @ParameterObject  Pageable pageable){
        return this.productService.filterProductByPrice(startPrice, endPrice, pageable);
    }

    @GetMapping("/products/search/unpublished")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> unpublished(@ParameterObject Pageable pageable){
        return this.productService.filterUnpublishedProduct(pageable);
    }
    @DeleteMapping("/product/{id}/delete")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> deleteProduct(@PathVariable UUID id){
        return this.productService.deleteProduct(id);
    }

    @PutMapping(value = "/product/{id}/setPublished")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> setPublished(@PathVariable(value = "id") UUID id){
        return this.productService.publishedStatus(id);
    }

    @PutMapping(value = "/product/{id}/setArchived")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> setArchived(@PathVariable(value = "id") UUID id){
        return this.productService.archivedStatus(id);
    }
}