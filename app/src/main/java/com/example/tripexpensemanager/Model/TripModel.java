package com.example.tripexpensemanager.Model;

import java.util.List;

public class TripModel {
    public TripModel() {
    }
    int primaryId;
    String tripName,description,date,timeStampTrip;
    List<PersonModel> personModelList;
    int debitMoney = 0;
    int totleAmount = 0 ;

    public TripModel(int primaryId, String tripName, String description, String date, String timeStampTrip) {
        this.primaryId = primaryId;
        this.tripName = tripName;
        this.description = description;
        this.date = date;
        this.timeStampTrip = timeStampTrip;
    }

    public int getTotleAmount() {
        return totleAmount;
    }

    public void setTotleAmount(int totleAmount) {
        this.totleAmount = totleAmount;
    }

    public int getDebitMoney() {
        return debitMoney;
    }

    public void setDebitMoney(int debitMoney) {
        this.debitMoney = debitMoney;
    }

    public List<PersonModel> getPersonModelList() {
        return personModelList;
    }

    public void setPersonModelList(List<PersonModel> personModelList) {
        this.personModelList = personModelList;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public int getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(int primaryId) {
        this.primaryId = primaryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeStampTrip() {
        return timeStampTrip;
    }

    public void setTimeStampTrip(String timeStampTrip) {
        this.timeStampTrip = timeStampTrip;
    }
}