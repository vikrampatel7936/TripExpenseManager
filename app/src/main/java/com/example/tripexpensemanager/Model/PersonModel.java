package com.example.tripexpensemanager.Model;

public class PersonModel {
    public PersonModel() {
    }

    int personId,tripId,amountDebit,amountCredit;
    String name,timeStampTrip,admin;

    Boolean isSelectedPersonForPurchese = false;

    public PersonModel(int personId, int tripId, int amountDebit, int amountCredit, String name, String timeStampTrip, String admin) {
        this.personId = personId;
        this.tripId = tripId;
        this.amountDebit = amountDebit;
        this.amountCredit = amountCredit;
        this.name = name;
        this.timeStampTrip = timeStampTrip;
        this.admin = admin;
    }

    public Boolean getSelectedPersonForPurchese() {
        return isSelectedPersonForPurchese;
    }

    public void setSelectedPersonForPurchese(Boolean selectedPersonForPurchese) {
        isSelectedPersonForPurchese = selectedPersonForPurchese;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getAmountDebit() {
        return amountDebit;
    }

    public void setAmountDebit(int amountDebit) {
        this.amountDebit = amountDebit;
    }

    public int getAmountCredit() {
        return amountCredit;
    }

    public void setAmountCredit(int amountCredit) {
        this.amountCredit = amountCredit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeStampTrip() {
        return timeStampTrip;
    }

    public void setTimeStampTrip(String timeStampTrip) {
        this.timeStampTrip = timeStampTrip;
    }
}

