package com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Adapters;

import com.tecbom.e_commerce.Application.DTOs.Products.DTOSearchProduct;
import com.tecbom.e_commerce.Application.DTOs.Products.OrderBy;
import com.tecbom.e_commerce.Application.Ports.Output.ProductRepository;
import com.tecbom.e_commerce.Domain.Entities.Products.AvailabilityStatus;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.Domain.Entities.Products.Status;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.DatabaseFailedException;
import com.tecbom.e_commerce.Domain.ValueObjects.Natural;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Models.ProductEntity;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Repositories.MongoProductRepository;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Mappers.NoSQLMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Component
public class ProductRepositoryAdapter implements ProductRepository {

    private final MongoTemplate mongoTemplate;
    private final MongoProductRepository frameworkRepository;
    private final NoSQLMapper mapper;

    public ProductRepositoryAdapter(MongoTemplate mongoTemplate, MongoProductRepository frameworkRepository, NoSQLMapper mapper) {
        this.mongoTemplate = mongoTemplate;
        this.frameworkRepository = frameworkRepository;
        this.mapper = mapper;
    }

    @Override
    public void saveProduct(Product product) {
        try {
            ProductEntity productEntity = mapper.toEntity(product);
            frameworkRepository.save(productEntity);
        } catch (Exception ex) {
            throw new DatabaseFailedException();
        }

    }

    private Sort handleOrderBy(OrderBy orderBy) {
        return switch (orderBy) {
            case PRICE_ASC -> Sort.by(Sort.Direction.ASC, "models.price");
            case PRICE_DESC -> Sort.by(Sort.Direction.DESC, "models.price");
            case LATEST -> Sort.by(Sort.Direction.DESC, "createdAt");
            case POPULARITY -> Sort.by(Sort.Direction.DESC, "timesPurchased");
            case RELEVANCE -> Sort.by(Sort.Direction.DESC, "timesViewed");
        };
    }

    private Pageable configPageable(Natural pages, Natural size) {
        return PageRequest.of(pages.i(), size.i());
    }

    @Override
    public List<Product> adminSearchProducts(DTOSearchProduct dtoSearchProduct, Natural pages, Natural size) {
        try {
            Query query = new Query();

            Pageable pageable = configPageable(pages, size);

            query.with(pageable);

            if (dtoSearchProduct.searchTerm() != null && !dtoSearchProduct.searchTerm().isBlank()) {
                String term = dtoSearchProduct.searchTerm().trim();
                Criteria nameLike = Criteria.where("name").regex(term, "i");

                String uuidRegex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

                if (term.matches(uuidRegex)) {
                    query.addCriteria(new Criteria().orOperator(
                            Criteria.where("id").is(UUID.fromString(term)),
                            nameLike
                    ));
                } else {
                    query.addCriteria(nameLike);
                }
            }

            if (dtoSearchProduct.category() != null) {
                query.addCriteria(Criteria.where("category").is(dtoSearchProduct.category()));
            }

            if (dtoSearchProduct.price() != null) {
                query.addCriteria(Criteria.where("models.price").lte(dtoSearchProduct.price().price().doubleValue()));
            }

            if (dtoSearchProduct.orderBy() != null) {
                query.with(handleOrderBy(dtoSearchProduct.orderBy()));
            }

            List<ProductEntity> entities = mongoTemplate.find(query, ProductEntity.class);
            return entities.stream()
                    .map(mapper::toDomain)
                    .toList();

        } catch (Exception e) {
            throw new DatabaseFailedException();
        }
    }

    @Override
    public List<Product> searchProducts(DTOSearchProduct dtoSearchProduct, Natural pages, Natural size) {
        try {
            Query query = new Query();
            Pageable pageable = configPageable(pages, size);
            query.with(pageable);

            query.addCriteria(Criteria.where("status").is(Status.ON));
            query.addCriteria(Criteria.where("availabilityStatus").is(AvailabilityStatus.IN_STOCK));
            // --------------------------------

            if (dtoSearchProduct.searchTerm() != null && !dtoSearchProduct.searchTerm().isBlank()) {
                String term = dtoSearchProduct.searchTerm().trim();
                Criteria nameLike = Criteria.where("name").regex(term, "i");

                String uuidRegex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

                if (term.matches(uuidRegex)) {
                    query.addCriteria(new Criteria().orOperator(
                            Criteria.where("id").is(UUID.fromString(term)),
                            nameLike
                    ));
                } else {
                    query.addCriteria(nameLike);
                }
            }

            if (dtoSearchProduct.category() != null) {
                query.addCriteria(Criteria.where("category").is(dtoSearchProduct.category()));
            }

            if (dtoSearchProduct.price() != null) {
                query.addCriteria(Criteria.where("models.price").lte(dtoSearchProduct.price().price().doubleValue()));
            }

            if (dtoSearchProduct.orderBy() != null) {
                query.with(handleOrderBy(dtoSearchProduct.orderBy()));
            }

            List<ProductEntity> entities = mongoTemplate.find(query, ProductEntity.class);
            return entities.stream()
                    .map(mapper::toDomain)
                    .toList();

        } catch (Exception e) {
            throw new DatabaseFailedException();
        }
    }

    @Override
    public Optional<Product> checkProductById(UUID id) {
        try {
            return frameworkRepository.findById(id)
                    .map(mapper::toDomain);
        } catch (Exception e) {
            throw new DatabaseFailedException();
        }
    }
}
