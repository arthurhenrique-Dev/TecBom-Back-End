package com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Repositories;

import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Models.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface MongoProductRepository extends MongoRepository<ProductEntity, UUID> {
}
