package team.kucing.anabulshopcare.resources.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.service.ProductService;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Object> getAllProducts(Pageable pageable){
        var getAllProducts = this.productService.listProducts(pageable);
        log.info("success get data product " + getAllProducts);
        return getAllProducts;
    }
    @PostMapping(value = "/product/add",consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> addProduct(@RequestPart MultipartFile file, @RequestPart @Valid Product product){
        var createProduct = this.productService.createProduct(product,file);
        log.info("Success Create Product " + createProduct);
        return createProduct;
    }
    @PutMapping(value = "/product/update/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestPart MultipartFile file, @RequestPart @Valid Product product){
        Product product1 =productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Products Not Exist with product details : "+product) );

        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setCategory(product.getCategory());
        product1.setLocation(product.getLocation());
        product1.setStock(product.getStock());
        product1.setPrice(product.getPrice());
        product1.setImageUrl(product.getImageUrl());
        product1.setIsPublished(product.getIsPublished());
        product1.setCreatedBy(product.getCreatedBy());
        if(product1== null) {
            log.info("Failed to get product" + product1.getId());
        }
            else {
            log.info("Success Update Product " + product1);
        }
        return this.productService.updateProduct(product1, file, id);
    }
    
    @GetMapping("/products/search/name")
    public ResponseEntity<Object> findByProductName(@RequestParam(value = "productName") String name, Pageable pageable) {
        var getProduct = this.productService.getName(name,pageable);
        if (getProduct == null) {
            log.info("Failed to get Product " + name );
        }
        else {
            log.info("Success Search Product name" + getProduct);
        }
        return getProduct;
    }
    
    @GetMapping("/products/search/location")
    public ResponseEntity<Object> filterProductsByLocation(@RequestParam(value = "location", required = false) String location, Pageable pageable){
        var getProduct = this.productService.filterProductsByLocation(location, pageable);
        if (getProduct == null) {
            log.info("Failed to get Product with Location is " + location );
        }
        else {
            log.info("Success Search Product by location " + getProduct);
        }

        return getProduct;
    }

    @GetMapping("/products/search/price")
    public ResponseEntity<Object> filterProductsByPrice(@RequestParam(value = "start", required = false)BigDecimal startPrice, @RequestParam(value = "end", required = false)BigDecimal endPrice, Pageable pageable){
        var getProduct = this.productService.filterProductByPrice(startPrice, endPrice, pageable);
        if (getProduct == null) {
            log.info("Failed to get Product with price range " + startPrice + " to " + endPrice );
        }
        else {
            log.info("Success Search Product by price " + getProduct);
        }

        return getProduct;
    }

    @GetMapping("/products/search/unpublished")
    public ResponseEntity<Object> unpublished(Pageable pageable){
        var getProduct = this.productService.filterUnpublishedProduct(pageable);
        if (getProduct == null) {
            log.info("There are no products to be unpublished "   );
        }
        else {
            log.info("Success get Product " + getProduct);
        }
        return getProduct;
    }
    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable UUID id){
       Optional<Product> product = this.productService.findById(id);
        if (product.isEmpty()){
            log.info("Failed to delete product with id : " + id );
        } else {
            log.info("Success delete product with id : " + id);
        }
        return productService.deleteProduct(id);
    }

    @PutMapping(value = "/product/setPublished/{id}")
    public ResponseEntity<Object> changePublishStatus(@PathVariable(value = "id") UUID id){
        Product product1 =productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Products Not Exist"));
        if (!product1.getIsPublished()){
            log.info("Product is Published");
            product1.setIsPublished(true);
        } else {
            log.info("Product is Archived");
            product1.setIsPublished(false);
        }
        return this.productService.updatePublishedStatus(id, product1);
    }
}