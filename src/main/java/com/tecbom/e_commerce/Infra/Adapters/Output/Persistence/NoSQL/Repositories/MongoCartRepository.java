package com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Repositories;

import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Models.CartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoCartRepository extends MongoRepository<CartEntity, String> {
}
