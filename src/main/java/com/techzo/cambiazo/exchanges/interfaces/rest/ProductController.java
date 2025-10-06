package com.techzo.cambiazo.exchanges.interfaces.rest;


import com.techzo.cambiazo.exchanges.application.internal.outboundservices.IamAclOutboundService;
import com.techzo.cambiazo.exchanges.domain.model.commands.DeleteProductOfPendingExchangesCommand;
import com.techzo.cambiazo.exchanges.domain.model.dtos.ProductDto;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductsByProductCategoryIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductsByUserIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductsQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetProductByIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.IProductCommandService;
import com.techzo.cambiazo.exchanges.domain.services.IProductQueryService;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateProductResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.ErrorResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.ProductResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.UpdateProductResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.CreateProductCommandFromResourceAssembler;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.ProductResourceFromEntityAssembler;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.UpdateProductCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v2/products")
@Tag(name = "Products", description = "Products Management Endpoints")
public class ProductController {

    private final IProductCommandService productCommandService;

    private final IProductQueryService productQueryService;

    private final IamAclOutboundService iamAclOutboundService;


    public ProductController(IProductCommandService productCommandService, IamAclOutboundService iamAclOutboundService, IProductQueryService productQueryService) {
        this.productCommandService = productCommandService;
        this.iamAclOutboundService = iamAclOutboundService;
        this.productQueryService = productQueryService;
    }


    @Operation(summary = "Create a new Product", description = "Create a new Product with the input data.")
    @PostMapping
    public ResponseEntity<?>createProduct(@RequestBody CreateProductResource resource) {

        // Validate user is not banned
        if (iamAclOutboundService.isUserBanned(resource.userId())) {
            long remainingMinutes = iamAclOutboundService.getRemainingBanMinutes(resource.userId());
            ErrorResource error = new ErrorResource(
                    "Usuario baneado",
                    "No puedes crear productos porque tu cuenta está temporalmente suspendida",
                    remainingMinutes + 1,
                    "Tu cuenta ha sido suspendida por violar las políticas de contenido"
            );
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }
        try{
            var createProductCommand= CreateProductCommandFromResourceAssembler.toCommandFromResource(resource);
            var product= productCommandService.handle(createProductCommand);
            var productResource= ProductResourceFromEntityAssembler.toResourceFromEntity(product.get());
            return ResponseEntity.status(CREATED).body(productResource);
        }catch (IllegalArgumentException e){
            // Log el error para ver qué está pasando
            System.err.println("Error creating product: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // Captura cualquier otra excepción
            System.err.println("Unexpected error creating product: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno", "details", e.getMessage()));
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



    @Operation(summary = "Delete a Product", description = "Delete a Product with the input data.")
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        try {
            var deleteProductCommand = new DeleteProductOfPendingExchangesCommand(productId);
            productCommandService.handleDeleteProduct(deleteProductCommand);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
