package com.techzo.cambiazo.exchanges.application.internal.commandservices;


import com.techzo.cambiazo.exchanges.domain.model.commands.CreateProductCommand;
import com.techzo.cambiazo.exchanges.domain.model.commands.UpdateProductCommand;
import com.techzo.cambiazo.exchanges.domain.model.entities.District;
import com.techzo.cambiazo.exchanges.domain.model.entities.Product;
import com.techzo.cambiazo.exchanges.domain.model.entities.ProductCategory;
import com.techzo.cambiazo.exchanges.domain.services.IProductCommandService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IDistrictRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IProductCategoryRepository;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.IProductRepository;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductCommandServiceImpl implements IProductCommandService {

    private final IProductRepository productRepository;

    private final UserRepository userRepository;

    private final IProductCategoryRepository productCategoryRepository;

    private final IDistrictRepository districtRepository;

    public ProductCommandServiceImpl(IProductRepository productRepository, UserRepository userRepository, IProductCategoryRepository productCategoryRepository, IDistrictRepository districtRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.districtRepository = districtRepository;
    }

    @Override
    public Optional<Product>handle(CreateProductCommand command) {
        ProductCategory productCategory = productCategoryRepository.
                findById(command.productCategoryId()).orElseThrow(() -> new IllegalArgumentException("Product Category with id not found"));

        User user = userRepository.findById(command.userId()).orElseThrow(() -> new IllegalArgumentException("User with id not found"));

        District district= districtRepository.findById(command.districtId()).orElseThrow(() -> new IllegalArgumentException("District with id not found"));

        var product = new Product(command,productCategory,user,district);
        productRepository.save(product);
        return Optional.of(product);
    }

    @Override
    public Optional<Product>handle(UpdateProductCommand command){
        if(productRepository.existsByNameAndId(command.name(), command.id())){
            throw new IllegalArgumentException("Product with same name already exists");
        }

        var result=productRepository.findById(command.id());

        if(result.isEmpty()){
            throw new IllegalArgumentException("Product with id not found");
        }

        var productToUpdate=result.get();

        try {
            ProductCategory productCategory = productCategoryRepository.findById(command.productCategoryId()).
                    orElseThrow(() -> new IllegalArgumentException("Product Category with id not found"));

            User user = userRepository.findById(command.userId()).
                    orElseThrow(() -> new IllegalArgumentException("User with id not found"));

            District district = districtRepository.findById(command.districtId()).
                    orElseThrow(() -> new IllegalArgumentException("District with id not found"));

            var updateProduct=productRepository.save(
                    productToUpdate.updateInformation(command.name(), command.description(),
                            command.desiredObject(), command.price(), command.image(), command.boost(),
                            command.available(), productCategory, user, district)
            );
            return Optional.of(updateProduct);
        }catch (Exception e){
            throw new IllegalArgumentException("Error while updating product: " + e.getMessage());
        }

    }

    @Override
    public boolean handleDeleteProduct(Long id){
        Optional<Product> product=productRepository.findById(id);
        if(product.isPresent()){
            productRepository.delete(product.get());
            return true;
        }else{
            return false;
        }
    }

}
