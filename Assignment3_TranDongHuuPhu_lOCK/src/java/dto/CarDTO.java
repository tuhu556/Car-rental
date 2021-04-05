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
public class CarDTO {

    private String carID;
    private String carName;
    private String categoryID;
    private float price;
    private int quantity;
    private int year;
    private String color;
    private String image;
    private boolean status;
    private String startDate;
    private String endDate;
    private int totalDays;
    private String code;

    public CarDTO() {
    }

    public CarDTO(String carID, String carName, String categoryID, float price, int quantity, int year, String color, String image, boolean status, String startDate, String endDate, int totalDays, String code) {
        this.carID = carID;
        this.carName = carName;
        this.categoryID = categoryID;
        this.price = price;
        this.quantity = quantity;
        this.year = year;
        this.color = color;
        this.image = image;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalDays = totalDays;
        this.code = code;
    }

    public CarDTO(String carID, String carName, String categoryID, float price, int quantity, int year, String color, String image, boolean status) {
        this.carID = carID;
        this.carName = carName;
        this.categoryID = categoryID;
        this.price = price;
        this.quantity = quantity;
        this.year = year;
        this.color = color;
        this.image = image;
        this.status = status;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
