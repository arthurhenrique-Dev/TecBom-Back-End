package com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Mappers;

import com.tecbom.e_commerce.Domain.Entities.Products.Model;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.Domain.Entities.Products.Rating;
import com.tecbom.e_commerce.Domain.Entities.Products.Review;
import com.tecbom.e_commerce.Domain.Entities.Users.Cart;
import com.tecbom.e_commerce.Domain.ValueObjects.*;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Models.CartEntity;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Models.ProductEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoSQLMapper {

    public ProductEntity toEntity(Product product) {
        var modelEntities = product.getModels().stream()
                .map(model -> new ProductEntity.ModelEntity(
                        model.getName().text(),
                        model.getPrice().price().doubleValue(),
                        model.getQuantity().quantity(),
                        model.getPhotos().photos(),
                        model.getAvailability().name(),
                        model.getTimesViewed(),
                        model.getTimesPurchased(),
                        model.getDiscountPercentage() != null ? model.getDiscountPercentage().doubleValue() : null
                )).collect(Collectors.toList());

        var reviewEntities = product.getReviews().stream()
                .map(review -> new ProductEntity.ReviewEntity(
                        review.id(),
                        review.message().text(),
                        review.rating().ordinal(),
                        review.idxModel(),
                        review.reviewerName().text()
                )).collect(Collectors.toList());

        return new ProductEntity(
                product.getId(),
                product.getName().text(),
                product.getDescription().text(),
                product.getCategory(),
                modelEntities,
                reviewEntities,
                product.getTotalQuantity().quantity(),
                product.getAvailability(),
                product.getTimesViewed(),
                product.getTimesPurchased(),
                product.getTimesViewedInMonth(),
                product.getTimesPurchasedInMonth(),
                product.getCreatedAt(),
                product.getStatus()
        );
    }

    public Product toDomain(ProductEntity entity) {
        if (entity == null) return null;

        List<Model> models = entity.getModels().stream()
                .map(m -> new Model(
                        new ValidText(m.getName()),
                        new Price(BigDecimal.valueOf(m.getPrice())),
                        new Quantity(m.getQuantity()),
                        new Photos(m.getPhotos()),
                        m.getDiscountPercentage() != null ? BigDecimal.valueOf(m.getDiscountPercentage()) : null
                )).collect(Collectors.toList());

        List<Review> reviews = entity.getReviews().stream()
                .map(r -> new Review(
                        new ValidText(r.getMessage()),
                        Rating.values()[r.getRating()],
                        r.getId(),
                        r.getIdxModel(),
                        new ValidText(r.getReviewerName())
                )).collect(Collectors.toList());

        return new Product(
                entity.getId(),
                new ValidText(entity.getName()),
                new ValidText(entity.getDescription()),
                entity.getCategory(),
                models,
                reviews,
                new Quantity(entity.getTotalQuantity()),
                entity.getAvailability(),
                entity.getTimesViewed(),
                entity.getTimesPurchased(),
                entity.getTimesViewedInMonth(),
                entity.getTimesPurchasedInMonth(),
                entity.getCreatedAt(),
                entity.getStatus()
        );
    }

    public CartEntity toEntity(Cart cart) {
        if (cart == null) return null;

        var itemEntities = cart.getItems().stream()
                .map(item -> new CartEntity.CartItemEntity(
                        item.getId(),
                        item.getName().text(),
                        item.getQuantity().quantity(),
                        item.getPrice().price().doubleValue()
                )).toList();

        return new CartEntity(
                cart.getCpf().toString(),
                itemEntities,
                cart.getPrice().price().doubleValue()
        );
    }

    public Cart toDomain(CartEntity entity) {
        if (entity == null) return null;

        Cpf cpf = new Cpf(entity.getCpf());

        List<CartItem> items = entity.getItems().stream()
                .map(item -> new CartItem(
                        item.getId(),
                        new ValidText(item.getName()),
                        new Quantity(item.getQuantity()),
                        new Price(BigDecimal.valueOf(item.getPrice()))
                )).collect(Collectors.toList());

        return new Cart(
                cpf,
                items
        );
    }
}

