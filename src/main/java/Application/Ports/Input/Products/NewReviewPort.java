package Application.Ports.Input.Products;

import Domain.Entities.Products.Review;

public interface NewReviewPort {

    void newReview(Review review);
}
