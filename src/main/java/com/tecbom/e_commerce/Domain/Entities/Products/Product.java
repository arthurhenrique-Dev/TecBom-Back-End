package com.tecbom.e_commerce.Domain.Entities.Products;

import com.tecbom.e_commerce.Domain.ValueObjects.Photos;
import com.tecbom.e_commerce.Domain.ValueObjects.Price;
import com.tecbom.e_commerce.Domain.ValueObjects.Quantity;
import com.tecbom.e_commerce.Domain.ValueObjects.ValidText;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Product {

    private final UUID id;
    private ValidText name;
    private ValidText description;
    private Category category;
    private List<Model> models;
    private List<Review> reviews;
    private Quantity totalQuantity;
    private AvailabilityStatus availability;
    private Integer timesViewed;
    private Integer timesPurchased;
    private Integer timesViewedInMonth;
    private Integer timesPurchasedInMonth;
    private LocalDateTime createdAt;
    private Status status;

    private Quantity quantityTotal(List<Model> models) {
        Integer totalQuantity = 0;
        for (Model model : models) {
            totalQuantity += model.getQuantity().quantity();
        }
        return new Quantity(totalQuantity);
    }

    private Integer totalTimesViewed(List<Model> models) {
        Integer totalTimesViewed = 0;
        for (Model model : models) {
            totalTimesViewed += model.getTimesViewed();
        }
        return totalTimesViewed;
    }

    private Integer totalTimesPurchased(List<Model> models) {
        Integer totalTimesPurchased = 0;
        for (Model model : models) {
            totalTimesPurchased += model.getTimesPurchased();
        }
        return totalTimesPurchased;
    }

    private Integer totalTimesViewedInMonth(List<Model> models) {
        Integer totalTimesViewedInMonth = 0;
        for (Model model : models) {
            totalTimesViewedInMonth += model.getTimesViewedInMonth().stats();
        }
        return totalTimesViewedInMonth;
    }

    private Integer totalTimesPurchasedInMonth(List<Model> models) {
        Integer totalTimesPurchasedInMonth = 0;
        for (Model model : models) {
            totalTimesPurchasedInMonth += model.getTimesPurchasedInMonth().stats();
        }
        return totalTimesPurchasedInMonth;
    }

    private AvailabilityStatus checkAvailability() {
        return this.totalQuantity.quantity() > 0 ? AvailabilityStatus.IN_STOCK : AvailabilityStatus.OUT_OF_STOCK;
    }

    public void DeleteProduct() {
        this.status = Status.OFF;
    }

    public void UpdateProduct(Integer idxModel, ValidText name, ValidText modelName, Price price, Quantity quantity, Photos photos, BigDecimal discountPercentage) {
        if (name != null && !name.text().trim().isEmpty()) this.name = name;
        if (idxModel != null && idxModel > 0 && idxModel <= this.models.size())
            this.models.get(idxModel - 1).UpdateModel(modelName, price, quantity, photos, discountPercentage);
    }

    public void newModel(ValidText name, Price price, Quantity quantity, Photos photos, BigDecimal DiscountPercentage) {
        Model newModel = new Model(
                name,
                price,
                quantity,
                photos,
                DiscountPercentage);
        this.models.add(newModel);
    }

    public void DeleteModel(Integer idxModel) {
        if (idxModel != null && idxModel >= 0 && idxModel <= this.models.size()) this.models.remove(idxModel - 1);
    }

    public void AddReview(Review review) {
        this.reviews.add(review);
    }

    public void RegisterPurchase(Integer idxModel, Integer quantity) {

        Model model = this.models.get(idxModel - 1);
        model.PurchaseModel(quantity);
    }


    public Product(ValidText name, ValidText description, List<Model> models, Category category) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.models = models;
        this.reviews = new ArrayList<>();
        this.timesViewed = totalTimesViewed(models);
        this.timesPurchased = totalTimesPurchased(models);
        this.totalQuantity = quantityTotal(models);
        this.timesViewedInMonth = totalTimesViewedInMonth(models);
        this.timesPurchasedInMonth = totalTimesPurchasedInMonth(models);
        this.availability = checkAvailability();
        this.createdAt = LocalDateTime.now();
        this.category = category;
        this.status = Status.ON;
    }

    public Product(UUID id, ValidText name, ValidText description, Category category, List<Model> models, List<Review> reviews, Quantity totalQuantity, AvailabilityStatus availability, Integer timesViewed, Integer timesPurchased, Integer timesViewedInMonth, Integer timesPurchasedInMonth, LocalDateTime createdAt, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.models = models;
        this.reviews = reviews;
        this.totalQuantity = totalQuantity;
        this.availability = availability;
        this.timesViewed = timesViewed;
        this.timesPurchased = timesPurchased;
        this.timesViewedInMonth = timesViewedInMonth;
        this.timesPurchasedInMonth = timesPurchasedInMonth;
        this.createdAt = createdAt;
        this.status = status;
    }

    public void setName(ValidText name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public ValidText getName() {
        return name;
    }

    public ValidText getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public List<Model> getModels() {
        return models;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public Quantity getTotalQuantity() {
        return totalQuantity;
    }

    public AvailabilityStatus getAvailability() {
        return availability;
    }

    public Integer getTimesViewed() {
        return timesViewed;
    }

    public Integer getTimesPurchased() {
        return timesPurchased;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public Integer getTimesViewedInMonth() {
        return totalTimesViewedInMonth(models);
    }

    public Integer getTimesPurchasedInMonth() {
        return totalTimesPurchasedInMonth(models);
    }
}
