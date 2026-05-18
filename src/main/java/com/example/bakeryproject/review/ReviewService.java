package com.example.bakeryproject.review;

import com.example.bakeryproject.common.FileUtil;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class ReviewService {
    private final String FILE_PATH = "src/main/resources/data/reviews.txt";
    public void addReview(Review review) {
        if (review == null) {return;
        }
        if (review.getReviewId() == null ||
                review.getReviewId().trim().isEmpty()) {
            review.setReviewId("R" + System.currentTimeMillis());
        }
// If the same review ID already exists, update it instead of adding a duplicate;
        if (findReviewById(review.getReviewId()) != null) {
            updateReview(review);
        } else {
            FileUtil.saveLine(FILE_PATH, review.toFileString());
        }
    }
    public List<Review> getAllReviews() {
        List<String> lines = FileUtil.readLines(FILE_PATH);
        List<Review> reviews = new ArrayList<>();
        for (String line : lines) {
            if (line != null && !line.trim().isEmpty()) {
                Review review = Review.fromFileString(line);
                if (review.getReviewId() != null &&
                        !review.getReviewId().trim().isEmpty()) {
                    reviews.add(review);
                }
            }
        }
        return reviews;
    }
    public Review findReviewById(String id) {
        if (id == null) {
            return null;
        }
        for (Review review : getAllReviews()) {
            if (id.equals(review.getReviewId())) {
                return review;
            }
        }
        return null;
    }
    public void updateReview(Review updatedReview) {
        if (updatedReview == null || updatedReview.getReviewId() == null) {
            return;
        }
        List<Review> reviews = getAllReviews();
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;
        for (Review review : reviews) {
            if (updatedReview.getReviewId().equals(review.getReviewId())) {
                updatedLines.add(updatedReview.toFileString());
                found = true;
            } else {
                updatedLines.add(review.toFileString());}
        }
        if (!found) {
            updatedLines.add(updatedReview.toFileString());
        }
        FileUtil.overwriteFile(FILE_PATH, updatedLines);
    }
    public void deleteReview(String id) {
        List<Review> reviews = getAllReviews();
        List<String> updatedLines = new ArrayList<>();
        for (Review review : reviews) {
            if (!review.getReviewId().equals(id)) {
                updatedLines.add(review.toFileString());
            }
        }
        FileUtil.overwriteFile(FILE_PATH, updatedLines);
    }
}