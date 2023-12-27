package com.example.individualproject.controller;

import com.example.individualproject.models.ReviewModel;
import com.example.individualproject.models.UserModel;
import com.example.individualproject.repo.ReviewRepository;
import com.example.individualproject.repo.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class reviewController {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public reviewController(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/allReviews")
    public String showAllReviews(Model model) {
        Iterable<ReviewModel> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);
        return "user/reviews/allReviews";
    }


    @GetMapping("/newReview")
    public String showNewReview(Model model) {
        model.addAttribute("reviews", new ReviewModel());
        return "user/reviews/addReview";
    }

    @GetMapping("/editReview/{id}")
    public String showUpdateReview(@PathVariable("id") long id, Model model) {
        ReviewModel review = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("review", review);
        return "user/reviews/updateReview";
    }

    @GetMapping("/deleteReview/{id}")
    public String deleteReview(@PathVariable("id") long id, Model model) {
        ReviewModel review = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        reviewRepository.delete(review);
        model.addAttribute("review", reviewRepository.findAll());
        return "redirect:/allReviews";
    }


    @PostMapping("/addReview")
    public String addReview(@Valid ReviewModel review, BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            return "user/reviews/addReview";
        }
        String userEmail = authentication.getName();
        UserModel user = userRepository.findByUsernameContaining(userEmail);
        if (user != null) {
            review.setUser(user);
            reviewRepository.save(review);
        }
        model.addAttribute("reviews", reviewRepository.findAll());

        return "user/reviews/allReviews";
    }

    @PostMapping("/updateReview/{id}")
    public String updateRevies(@PathVariable("id") long id, @Valid ReviewModel review, BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            review.setId(id);
            return "user/reviews/updateReview";
        }
        String userEmail = authentication.getName();
        UserModel user = userRepository.findByUsernameContaining(userEmail);
        if (user != null) {
            review.setUser(user);
            reviewRepository.save(review);
        }
        reviewRepository.save(review);
        model.addAttribute("reviews", reviewRepository.findAll());
        return "user/reviews/allReviews";
    }
}
