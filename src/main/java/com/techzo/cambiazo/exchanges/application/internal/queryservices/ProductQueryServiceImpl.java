package com.techzo.cambiazo.exchanges.application.internal.queryservices;

import com.techzo.cambiazo.exchanges.domain.model.dtos.ProductDto;
import com.techzo.cambiazo.exchanges.domain.model.entities.*;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductsByProductCategoryIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductsByUserIdQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllProductsQuery;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetProductByIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.IProductQueryService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.*;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.techzo.cambiazo.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductQueryServiceImpl implements IProductQueryService {

    private final IProductRepository productRepository;

    private final UserRepository userRepository;

    private final IProductCategoryRepository productCategoryRepository;

    private final IDistrictRepository districtRepository;

    private final IDepartmentRepository departmentRepository;

    private final ICountryRepository countryRepository;

    private final List<District>districts;
    private final List<Department>departments;
    private final List<Country>countries;
    private final List<ProductCategory>categories;

    public ProductQueryServiceImpl(
            IProductRepository productRepository,
            UserRepository userRepository,
            IProductCategoryRepository productCategoryRepository,
            IDistrictRepository districtRepository,
            IDepartmentRepository departmentRepository,
            ICountryRepository countryRepository
    ){
        this.productRepository=productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.userRepository = userRepository;
        this.districtRepository = districtRepository;
        this.departmentRepository = departmentRepository;
        this.countryRepository = countryRepository;
        this.districts = districtRepository.findAll();
        this.departments = departmentRepository.findAll();
        this.countries = countryRepository.findAll();
        this.categories = productCategoryRepository.findAll();
    }


    @Override
    public Optional<ProductDto> handle(GetProductByIdQuery query) {
        User user = userRepository.findById(query.id())
                .orElseThrow(() -> new IllegalArgumentException("User with id "+query.id()+" not found"));
        Product product = productRepository.findById(query.id())
                .orElseThrow(() -> new IllegalArgumentException("Product with id "+query.id()+" not found"));
        District district = districts.stream().filter(d -> d.getId().equals(product.getDistrictId())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("District not found"));
        Department department = departments.stream().filter(d -> d.getId().equals(district.getDepartmentId())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        Country country = countries.stream().filter(c -> c.getId().equals(department.getCountryId())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Country not found"));
        ProductCategory productCategory = categories.stream().filter(c -> c.getId().equals(product.getProductCategoryId())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product Category not found"));
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user);
        return Optional.of(new ProductDto(product, userResource, productCategory, district, department, country));
    }

    @Override
    public List<ProductDto> handle(GetAllProductsQuery query) {
        List<User> users = userRepository.findAll();
        List<Product>products = productRepository.findAll();
        return products.stream().map(product -> {
            User user = users.stream().filter(u -> u.getId().equals(product.getUserId())).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            District district = districts.stream().filter(d -> d.getId().equals(product.getDistrictId())).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("District not found"));
            Department department = departments.stream().filter(d -> d.getId().equals(district.getDepartmentId())).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Department not found"));
            Country country = countries.stream().filter(c -> c.getId().equals(department.getCountryId())).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Country not found"));
            ProductCategory productCategory = categories.stream().filter(c -> c.getId().equals(product.getProductCategoryId())).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Product Category not found"));
            var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user);
            return new ProductDto(product, userResource, productCategory, district, department, country);
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> handle(GetAllProductsByUserIdQuery query) {
        User user = userRepository.findById(query.userId())
                .orElseThrow(() -> new IllegalArgumentException("User with id "+query.userId()+" not found"));
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user);
        List<Product> products = productRepository.findProductsByUserId(user);
        return products.stream().map(product -> {
            District district = districts.stream().filter(d -> d.getId().equals(product.getDistrictId())).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("District not found"));
            Department department = departments.stream().filter(d -> d.getId().equals(district.getDepartmentId())).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Department not found"));
            Country country = countries.stream().filter(c -> c.getId().equals(department.getCountryId())).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Country not found"));
            ProductCategory productCategory = categories.stream().filter(c -> c.getId().equals(product.getProductCategoryId())).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Product Category not found"));
            return new ProductDto(product, userResource,productCategory, district, department, country);
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> handle(GetAllProductsByProductCategoryIdQuery query) {
        ProductCategory productCategory = productCategoryRepository.findById(query.productCategoryId())
                .orElseThrow(()->new IllegalArgumentException("Product Category with id "+query.productCategoryId()+" not found"));

        List<Product> products = productRepository.findProductsByProductCategoryId(productCategory);
        List<User>users = userRepository.findAll();
        return products.stream().map(product -> {
            User user = users.stream().filter(u -> u.getId().equals(product.getUserId())).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            District district = districts.stream().filter(d -> d.getId().equals(product.getDistrictId())).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("District not found"));
            Department department = departments.stream().filter(d -> d.getId().equals(district.getDepartmentId())).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Department not found"));
            Country country = countries.stream().filter(c -> c.getId().equals(department.getCountryId())).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Country not found"));
            var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user);
            return new ProductDto(product, userResource, productCategory, district, department, country);
        }).collect(Collectors.toList());
    }
}
