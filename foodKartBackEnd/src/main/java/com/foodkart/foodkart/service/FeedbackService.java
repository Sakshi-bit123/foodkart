package com.foodkart.foodkart.service;

import com.foodkart.foodkart.exception.DetailsNotFoundException;
import com.foodkart.foodkart.model.CustomerFeedbackRequest;
import com.foodkart.foodkart.model.Feedback;
import com.foodkart.foodkart.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Simpson Alfred
 */

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> getFeedbackByEmail(String email) {
        return feedbackRepository.findAllByEmail(email);
    }

    public Feedback createFeedback(CustomerFeedbackRequest customerFeedbackRequest) {

        Feedback feedback = new Feedback();
        feedback.setEmail(customerFeedbackRequest.getEmail());
        feedback.setMessage(customerFeedbackRequest.getMessage());
        feedback.setSubject(customerFeedbackRequest.getSubject());
        feedback.setTimestamp(LocalDate.now());
        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(String email) {
        List<Feedback> feedbackList = feedbackRepository.findAllByEmail(email);
        if (feedbackList == null){
            throw new DetailsNotFoundException("Sorry, feedback not found with email: " + email);
        }
        feedbackList.forEach(feedback -> {
            feedbackRepository.deleteById(feedback.getId());
        });
    }



}
