package com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Adapters;

import com.tecbom.e_commerce.Application.Ports.Output.CartRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.Cart;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.DatabaseFailedException;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Mappers.NoSQLMapper;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Models.CartEntity;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Repositories.MongoCartRepository;

import java.util.Optional;

public class CartRepositoryAdapter implements CartRepository {

    private final MongoCartRepository repository;
    private final NoSQLMapper mapper;

    public CartRepositoryAdapter(MongoCartRepository repository, NoSQLMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void saveCart(Cart cart) {
        try {
            CartEntity cartEntity = mapper.toEntity(cart);
            repository.save(cartEntity);
        } catch (Exception e) {
            throw new DatabaseFailedException();
        }

    }

    @Override
    public Optional<Cart> getCart(Cpf cpf) {
        try {
            return repository.findById(cpf.cpf())
                    .map(mapper::toDomain);
        } catch (Exception e) {
            throw new DatabaseFailedException();
        }
    }
}
