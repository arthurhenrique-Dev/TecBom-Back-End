package com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Models;

import com.tecbom.e_commerce.Domain.Entities.Products.AvailabilityStatus;
import com.tecbom.e_commerce.Domain.Entities.Products.Category;
import com.tecbom.e_commerce.Domain.Entities.Products.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "product_entity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

    @Id
    private UUID id;
    private String name;
    private String description;
    private Category category;
    private List<ModelEntity> models;
    private List<ReviewEntity> reviews;
    private Integer totalQuantity;
    private AvailabilityStatus availability;
    private Integer timesViewed;
    private Integer timesPurchased;
    private Integer timesViewedInMonth;
    private Integer timesPurchasedInMonth;
    private LocalDateTime createdAt;
    private Status status;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModelEntity {
        private String name;
        private Double price;
        private Integer quantity;
        private List<String> photos;
        private String availability;
        private Integer timesViewed;
        private Integer timesPurchased;
        private Double discountPercentage;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewEntity {
        private UUID id;
        private String message;
        private Integer rating;
        private Integer idxModel;
        private String reviewerName;
    }
}
