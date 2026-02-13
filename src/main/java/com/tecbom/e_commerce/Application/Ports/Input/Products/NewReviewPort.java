package com.tecbom.e_commerce.Application.Ports.Input.Products;

import com.tecbom.e_commerce.Domain.Entities.Products.Review;

public interface NewReviewPort {

    void newReview(Review review);
}
