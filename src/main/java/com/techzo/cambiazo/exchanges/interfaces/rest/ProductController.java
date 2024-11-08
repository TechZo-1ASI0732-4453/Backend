package com.techzo.cambiazo.exchanges.interfaces.rest;


import com.techzo.cambiazo.exchanges.domain.model.commands.DeleteProductOfPendingExchangesCommand;
import com.techzo.cambiazo.exchanges.domain.model.dtos.ProductDto;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductsByProductCategoryIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductsByUserIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductsQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetProductByIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.IProductCommandService;
import com.techzo.cambiazo.exchanges.domain.services.IProductQueryService;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateProductResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.ProductResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.UpdateProductResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.CreateProductCommandFromResourceAssembler;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.ProductResourceFromEntityAssembler;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.UpdateProductCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v2/products")
@Tag(name = "Products", description = "Products Management Endpoints")
public class ProductController {

    private final IProductCommandService productCommandService;

    private final IProductQueryService productQueryService;

    public ProductController(IProductCommandService productCommandService, IProductQueryService productQueryService) {
        this.productCommandService = productCommandService;
        this.productQueryService = productQueryService;
    }


    @Operation(summary = "Create a new Product", description = "Create a new Product with the input data.")
    @PostMapping
    public ResponseEntity<ProductResource>createProduct(@RequestBody CreateProductResource resource) {
        try{
            var createProductCommand= CreateProductCommandFromResourceAssembler.toCommandFromResource(resource);
            var product= productCommandService.handle(createProductCommand);
            var productResource= ProductResourceFromEntityAssembler.toResourceFromEntity(product.get());
            return ResponseEntity.status(CREATED).body(productResource);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto>getProductById(@PathVariable Long id){
        try{
            var getProductByIdQuery = new GetProductByIdQuery(id);
            var product = productQueryService.handle(getProductByIdQuery);
            return ResponseEntity.ok(product.get());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ProductDto>> getAllProductsByUserId(@PathVariable Long id){
        try{
            var getAllProductsByUserIdQuery = new GetAllProductsByUserIdQuery(id);
            var products = productQueryService.handle(getAllProductsByUserIdQuery);
            return ResponseEntity.ok(products);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/product-category/{id}")
    public ResponseEntity<List<ProductDto>> getAllProductsByProductCategoryId(@PathVariable Long id){
        try{
            var getAllProductsByProductCategoryIdQuery = new GetAllProductsByProductCategoryIdQuery(id);
            var products = productQueryService.handle(getAllProductsByProductCategoryIdQuery);
            return ResponseEntity.ok(products);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        try{
            var getAllProductsQuery = new GetAllProductsQuery();
            var products = productQueryService.handle(getAllProductsQuery);
            return ResponseEntity.ok(products);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


    @Operation(summary = "Update a Product", description = "Update a Product with the input data.")
    @PutMapping("/edit/{productId}")
    public ResponseEntity<ProductResource> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductResource resource) {
        try {
            var updateProductCommand = UpdateProductCommandFromResourceAssembler.toCommandFromResource(productId, resource);
            var product = productCommandService.handle(updateProductCommand);
            var productResource = ProductResourceFromEntityAssembler.toResourceFromEntity(product.get());
            return ResponseEntity.ok(productResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/delete-product/{productId}")
    public ResponseEntity<Void> updateProductOfPendingExchanges(@PathVariable Long productId) {
        try {
            var deleteProductOfPendingExchangesCommand = new DeleteProductOfPendingExchangesCommand(productId);
            productCommandService.deleteProductOfPendingExchanges(deleteProductOfPendingExchangesCommand);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @Operation(summary = "Delete a Product", description = "Delete a Product with the input data.")
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        try {
            productCommandService.handleDeleteProduct(productId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
