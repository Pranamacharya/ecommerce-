package com.example.final_UI_dev.service;

import com.example.final_UI_dev.entity.Review;
import com.example.final_UI_dev.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    public Review save(Review review) {
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
}


