package com.techzo.cambiazo.exchanges.interfaces.rest;


import com.techzo.cambiazo.exchanges.domain.model.entities.ProductCategory;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductCategoriesQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetProductCategoryByIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.IProductCategoryCommandService;
import com.techzo.cambiazo.exchanges.domain.services.IProductCategoryQueryService;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.CreateProductCategoryResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.ProductCategoryResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.CreateProductCategoryCommandFromResourceAssembler;
import com.techzo.cambiazo.exchanges.interfaces.rest.transform.ProductCategoryResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v2/product-categories")
@Tag(name = "Product Category", description = "Product Category Management Endpoints")
public class ProductCategoryController {

    private final IProductCategoryCommandService productCategoryCommandService;

    private final IProductCategoryQueryService productCategoryQueryService;

    public ProductCategoryController(IProductCategoryCommandService productCategoryCommandService, IProductCategoryQueryService productCategoryQueryService) {
        this.productCategoryCommandService = productCategoryCommandService;
        this.productCategoryQueryService = productCategoryQueryService;
    }

    @Operation(summary = "Create a new Product Category", description = "Create a new Product Category with the input data.")
    @PostMapping
    public ResponseEntity<ProductCategoryResource>createProductCategory(@RequestBody CreateProductCategoryResource resource) {
        Optional<ProductCategory>productCategory= productCategoryCommandService.handle(CreateProductCategoryCommandFromResourceAssembler.toCommandFromResource(resource));
        return productCategory.map(source -> new ResponseEntity<>(ProductCategoryResourceFromEntityAssembler.toResourceFromEntity(source),CREATED)).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryResource>getProductCategoryById(@PathVariable Long id){
        try{
            var getProductCategoryByIdQuery = new GetProductCategoryByIdQuery(id);
            var productCategory = productCategoryQueryService.handle(getProductCategoryByIdQuery);
            var productCategoryResource = ProductCategoryResourceFromEntityAssembler.toResourceFromEntity(productCategory.get());
            return ResponseEntity.ok(productCategoryResource);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductCategoryResource>>getAllProductCategories(){
        try{
            var getAllProductCategoriesQuery = new GetAllProductCategoriesQuery();
            var productCategories = productCategoryQueryService.handle(getAllProductCategoriesQuery);
            var productCategoryResources = productCategories.stream()
                    .map(ProductCategoryResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
            return ResponseEntity.ok(productCategoryResources);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
