package com.example.bakeryproject.review;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class ReviewController {
    private final ReviewService reviewService;
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    private boolean isLoggedIn(HttpSession session) {
        Object role = session.getAttribute("role");
        return "ADMIN".equals(role) || "CUSTOMER".equals(role);
    }
    private boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("role"));
    }
    private boolean isCustomer(HttpSession session) {
        return "CUSTOMER".equals(session.getAttribute("role"));
    }
    private void prepareReviewForm(Model model, Review review, String pageTitle) {
        model.addAttribute("review", review == null ? new Review() :
                review);
        model.addAttribute("pageTitle", pageTitle);
    }
    @GetMapping({
            "/reviews/add", "/review/add",
            "/reviews/new", "/review/new",
            "/reviews/add-review", "/review/add-review",
            "/reviews/write", "/review/write",
            "/reviews/write-review", "/review/write-review",
            "/write-review", "/write-a-review",
            "/add-review", "/addreview",
            "/feedback", "/feedback/add", "/feedback/new",
            "/feedback/review", "/feedback/write", "/feedback/writereview",
            "/feedbacks/add", "/add-feedback", "/write-feedback"
    })
    public String showAddReviewPage(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        prepareReviewForm(model, new Review(), "Write a Review");
        return "review/add-review";
    }@PostMapping({
            "/reviews/save", "/review/save",
            "/reviews/add", "/review/add",
            "/reviews/new", "/review/new",
            "/reviews/add-review", "/review/add-review",
            "/write-review", "/write-a-review",
            "/add-review", "/addreview",
            "/feedback/save", "/feedback/add", "/feedback/new",
            "/feedback/review", "/feedback/write", "/feedback/writereview",
            "/add-feedback", "/write-feedback"
    })
    public String saveReview(@ModelAttribute Review review, HttpSession
            session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        reviewService.addReview(review);
        if (isAdmin(session)) {
            return "redirect:/reviews/admin";
        }
        return "redirect:/customer-home";
    }
    @GetMapping({
            "/reviews/list", "/review/list", "/reviews", "/review",
            "/review-list", "/reviews/review-list",
            "/feedback/list", "/feedbacks",
            "/my-reviews", "/public-reviews", "/feedback-wall"
    })
    public String showReviewList(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        if (isCustomer(session)) {
            return "redirect:/reviews/add";
        }
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "review/review-list";
    }
    @GetMapping({
            "/customer-review-wall", "/review-wall",
            "/customer/reviews", "/customer/review-wall",
            "/customer/customer-review-wall"
    })
    public String removedCustomerReviewWall(HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        if (isAdmin(session)) {
            return "redirect:/reviews/admin";
        }
        return "redirect:/reviews/add";
    }
    @GetMapping({"/reviews/edit/{id}", "/review/edit/{id}",
            "/feedback/edit/{id}"})
    public String showEditReviewPage(@PathVariable String id, Model model,
                                     HttpSession session) {if (!isLoggedIn(session)) {
        return "redirect:/login";
    }
        Review review = reviewService.findReviewById(id);
        if (review == null) {
            return "redirect:/reviews/list";
        }
        prepareReviewForm(model, review, "Edit Review");
        return "review/add-review";
    }
    @PostMapping({"/reviews/update", "/review/update", "/feedback/update"})
    public String updateReview(@ModelAttribute Review review, HttpSession
            session) {
        if (!isLoggedIn(session)) {
            return "redirect:/login";
        }
        reviewService.updateReview(review);
        return "redirect:/reviews/list";
    }
    @GetMapping({"/reviews/delete/{id}", "/review/delete/{id}",
            "/feedback/delete/{id}"})
    public String deleteReview(@PathVariable String id, HttpSession
            session) {
        if (!isAdmin(session)) {
            return "redirect:/customer-home";
        }
        reviewService.deleteReview(id);
        return "redirect:/reviews/list";
    }
    @GetMapping({"/reviews/admin", "/review/admin", "/admin/reviews",
            "/feedback/admin"})
    public String adminReviewControl(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/customer-home";
        }
        model.addAttribute("reviews", reviewService.getAllReviews());
        return "review/admin-review-control";
    }
}