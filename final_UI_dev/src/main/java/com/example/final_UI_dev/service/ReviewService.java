package com.example.final_UI_dev.service;

import com.example.final_UI_dev.entity.Products;
import com.example.final_UI_dev.entity.Review;
import com.example.final_UI_dev.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    public Review save(Review review) {
        review.setReviewDate(new Date()); // set the review date to the current date and time
        return reviewRepository.save(review);
    }


    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }


    public Review getReviewById(Integer id) {
        return reviewRepository.findById(id).orElse(null);
    }


    public void deleteReview(Integer id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> getReviewsByProductId(Integer productId) {
        List<Review> allReviews = reviewRepository.findAll();
        List<Review> AllReviewByProductId = new ArrayList<>();
        for(Review r : allReviews){
            if(r.getProduct().getProductId()==productId){
                AllReviewByProductId.add(r);
            }
        }
        return AllReviewByProductId;
    }
    @Autowired
    private ProductsService productsService;
    public double getAverageRatingForProduct(int productId) {
        Products products = productsService.getProductById(productId).orElse(null);
        List<Review> reviews = reviewRepository.findByProduct(products);
        if (reviews.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Review review : reviews) {
            sum += review.getRating();
        }
        return sum / reviews.size();
    }
}


