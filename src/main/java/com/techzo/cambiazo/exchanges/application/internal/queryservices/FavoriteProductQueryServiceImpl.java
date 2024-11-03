package com.techzo.cambiazo.exchanges.application.internal.queryservices;

import com.techzo.cambiazo.exchanges.domain.model.dtos.FavoriteProductDto;
import com.techzo.cambiazo.exchanges.domain.model.dtos.Location;
import com.techzo.cambiazo.exchanges.domain.model.dtos.ProductDto;
import com.techzo.cambiazo.exchanges.domain.model.entities.*;
import com.techzo.cambiazo.exchanges.domain.model.queries.GetAllFavoriteProductsByUserIdQuery;
import com.techzo.cambiazo.exchanges.domain.services.IFavoriteProductQueryService;
import com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa.*;
import com.techzo.cambiazo.iam.domain.model.aggregates.User;
import com.techzo.cambiazo.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.techzo.cambiazo.iam.interfaces.rest.transform.UserResource2FromEntityAssembler;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class FavoriteProductQueryServiceImpl implements IFavoriteProductQueryService {

    private final IFavoriteProductRepository favoriteProductRepository;
    private final IProductRepository productRepository;
    private final UserRepository userRepository;
    private final IProductCategoryRepository productCategoryRepository;
    private final ICountryRepository countryRepository;
    private final IDepartmentRepository departmentRepository;
    private final IDistrictRepository districtRepository;

    private Map<Long, ProductCategory> productCategoryCache;
    private Map<Long, Country> countryCache;
    private Map<Long, Department> departmentCache;
    private Map<Long, District> districtCache;

    public FavoriteProductQueryServiceImpl(
            IFavoriteProductRepository favoriteProductRepository,
            UserRepository userRepository,
            IProductRepository productRepository,
            ICountryRepository countryRepository,
            IDepartmentRepository departmentRepository,
            IDistrictRepository districtRepository,
            IProductCategoryRepository productCategoryRepository
    ) {
        this.favoriteProductRepository = favoriteProductRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.countryRepository = countryRepository;
        this.departmentRepository = departmentRepository;
        this.districtRepository = districtRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @PostConstruct
    public void loadStaticDataIntoCache() {
        this.productCategoryCache = productCategoryRepository.findAll().stream()
                .collect(Collectors.toMap(ProductCategory::getId, category -> category));
        this.countryCache = countryRepository.findAll().stream()
                .collect(Collectors.toMap(Country::getId, country -> country));
        this.departmentCache = departmentRepository.findAll().stream()
                .collect(Collectors.toMap(Department::getId, department -> department));
        this.districtCache = districtRepository.findAll().stream()
                .collect(Collectors.toMap(District::getId, district -> district));
    }

    @Override
    public List<FavoriteProductDto> handle(GetAllFavoriteProductsByUserIdQuery query) {
        User user = this.userRepository.findById(query.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<FavoriteProduct> favoritesProducts = this.favoriteProductRepository.findFavoriteProductsByUserId(user);

        if (favoritesProducts.isEmpty()) {
            throw new IllegalArgumentException("No favorite products found for user with id " + query.userId());
        }

        return favoritesProducts.stream().map(favoriteProduct -> {
            Product product = this.productRepository.findById(favoriteProduct.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            User user2 = this.userRepository.findById(favoriteProduct.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            var userResource2 = UserResource2FromEntityAssembler.toResourceFromEntity(user2);

            ProductCategory productCategory = this.productCategoryCache.get(product.getProductCategoryId());
            if (productCategory == null) {
                throw new IllegalArgumentException("Product Category not found");
            }

            District district = this.districtCache.get(product.getDistrictId());
            if (district == null) {
                throw new IllegalArgumentException("District not found");
            }

            Department department = this.departmentCache.get(district.getDepartmentId());
            if (department == null) {
                throw new IllegalArgumentException("Department not found");
            }

            Country country = this.countryCache.get(department.getCountryId());
            if (country == null) {
                throw new IllegalArgumentException("Country not found");
            }

            Location location = new Location(
                    country.getId(), country.getName(),
                    department.getId(), department.getName(),
                    district.getId(), district.getName()
            );
            ProductDto productDto = new ProductDto(product, userResource2, productCategory, location);

            return new FavoriteProductDto(favoriteProduct.getId(), productDto, favoriteProduct.getUserId());
        }).toList();
    }
}
