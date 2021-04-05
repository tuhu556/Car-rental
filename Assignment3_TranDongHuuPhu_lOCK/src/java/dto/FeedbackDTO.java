/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author Admin
 */
public class FeedbackDTO {
    private String feedbackID;
    private String content;
    private int rating;
    private String email;
    private String carID;
    private String createDate;

    public FeedbackDTO() {
    }

    public FeedbackDTO(String feedbackID, String content, int rating, String email, String carID, String createDate) {
        this.feedbackID = feedbackID;
        this.content = content;
        this.rating = rating;
        this.email = email;
        this.carID = carID;
        this.createDate = createDate;
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    
    
}
