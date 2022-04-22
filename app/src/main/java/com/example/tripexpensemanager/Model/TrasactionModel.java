package com.example.tripexpensemanager.Model;

public class TrasactionModel {
    public TrasactionModel() {
    }

    int trasactionId,tripId,payAmount,perPersonPayment;
    String description,date,category;

    public TrasactionModel(int trasactionId, int tripId, int payAmount, int perPersonPayment, String description, String date, String category) {
        this.trasactionId = trasactionId;
        this.tripId = tripId;
        this.payAmount = payAmount;
        this.perPersonPayment = perPersonPayment;
        this.description = description;
        this.date = date;
        this.category = category;
    }

    public int getPerPersonPayment() {
        return perPersonPayment;
    }

    public void setPerPersonPayment(int perPersonPayment) {
        this.perPersonPayment = perPersonPayment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTrasactionId() {
        return trasactionId;
    }

    public void setTrasactionId(int trasactionId) {
        this.trasactionId = trasactionId;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
