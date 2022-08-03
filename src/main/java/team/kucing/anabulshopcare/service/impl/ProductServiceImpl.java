package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import team.kucing.anabulshopcare.dto.request.ProductRequest;
import team.kucing.anabulshopcare.entity.Category;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.repository.CategoryRepository;
import team.kucing.anabulshopcare.repository.ProductRepository;
import team.kucing.anabulshopcare.service.CategoryService;
import team.kucing.anabulshopcare.service.FileStorageService;
import team.kucing.anabulshopcare.service.ProductService;

import java.util.Optional;
import java.util.UUID;
import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private CategoryService categoryService;

    private final FileStorageService fileStorageService;

    @Override
    public ResponseEntity<Object> createProduct(ProductRequest productRequest, MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(fileName)
                .toUriString();

        Optional<Category> category = this.categoryRepository.findByCategoryName(productRequest.getCategory().getName());

        Product product = new Product();
        product.setName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());

        if (category.isEmpty()){
            Category newCategory = this.categoryService.createCategory(productRequest.getCategory());
            product.setCategory(newCategory);
            log.info("Creating new category " + productRequest.getCategory());
        } else {
            product.setCategory(category.get());
        }

        product.setLocation(productRequest.getLocation());
        product.setStock(productRequest.getStock());
        product.setPrice(product.getStock());

        product.setImageUrl(fileDownloadUri);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.productRepository.save(product));

    }

    @Override
    public ResponseEntity<Object> listProducts(Pageable pageable) {
        Page<Product> listProducts = this.productRepository.findByIsPublished(Boolean.TRUE, pageable);

        if (listProducts.getTotalPages() == 0){
            throw new ResourceNotFoundException("There are no product exist");
        }

        return ResponseEntity.ok().body(listProducts.toList());
    }

    @Override
    public ResponseEntity<Object> filterProductByName(String name, Pageable pageable ) {
        Page<Product> getProduct = this.productRepository.findByNameContaining(name, pageable);

        if (getProduct.getTotalPages() == 0) {
            throw new ResourceNotFoundException("Uppsss product not found by name : " + name);
        }

        return ResponseEntity.status(HttpStatus.OK).body(getProduct.toList());
    }
    @Override
        public ResponseEntity<Object> filterProductsByLocation(String location, Pageable pageable) {
        Page<Product> getProduct = this.productRepository.findByLocation(location, pageable);

        if (getProduct.getTotalPages() == 0){
            throw new ResourceNotFoundException("Sorry, There are no product in " + location + " area...");
        }

        return ResponseEntity.status(HttpStatus.OK).body(getProduct.toList());
    }

    @Override
    public ResponseEntity<Object> filterProductByPrice(double startPrice, double endPrice, Pageable pageable) {
        Page<Product> getProduct = this.productRepository.findByPriceBetween(startPrice, endPrice, pageable);

        if (getProduct.getTotalPages() == 0){
            throw new ResourceNotFoundException("Sorry, There are no product in that price range");
        }

        return ResponseEntity.ok().body(getProduct.toList());
    }

    @Override
    public ResponseEntity<Object> filterUnpublishedProduct(Pageable pageable){
        Page<Product> product = this.productRepository.findByIsPublished(Boolean.FALSE, pageable);

        if (product.getTotalPages() == 0){
            throw new ResourceNotFoundException("There are no product unpublished");
        }

        return ResponseEntity.ok().body(product.toList());
    }

    @Override
    public Product findById(UUID id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isEmpty()){
            throw new ResourceNotFoundException("Product with ID "+id+" Is Not Found");
        }

        return optionalProduct.get();
    }

    @Override
    public ResponseEntity<Object> updateProduct(Product product, MultipartFile file, UUID id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if(productOptional.isEmpty()){
            throw new ResourceNotFoundException("Product not exist with id"+id);
        }

        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(fileName)
                .toUriString();
        product.setImageUrl(fileDownloadUri);

        return ResponseEntity.ok().body(this.productRepository.save(product));
    }

    @Override
    public ResponseEntity<Object> deleteProduct(UUID id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isEmpty()){
            throw new ResourceNotFoundException("Product not exist with id : "+id);
        }

        Product product = productRepository.getReferenceById(id);
        this.productRepository.delete(product);

        return ResponseEntity.ok().body("Success Delete Product " + product);

    }

    @Override
    public ResponseEntity<Object> publishedStatus(UUID id) {
        Product getProduct = this.findById(id);
        if (!getProduct.getIsPublished()){
            getProduct.setIsPublished(Boolean.TRUE);
        }
        this.productRepository.save(getProduct);
        return ResponseEntity.ok().body("Success published " + getProduct.getName() +
                " hope there is buyer take your product");
    }

    @Override
    public ResponseEntity<Object> archivedStatus(UUID id) {
        Product getProduct = this.findById(id);
        if (getProduct.getIsPublished()){
            getProduct.setIsPublished(Boolean.FALSE);
        }
        this.productRepository.save(getProduct);
        return ResponseEntity.ok().body("Your Product archived " + getProduct.getName() +
                " get ready to put your product to the market");
    }

}