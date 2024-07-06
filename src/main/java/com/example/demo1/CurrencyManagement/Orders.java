package com.example.demo1.CurrencyManagement;

public class Orders {
    private String username;
    private int id;
    private String date;
    private String time;
    private String situation;
    private String currency;
    private double amount;
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return this.username;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return this.time;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return this.date;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public void setSituation(String situation) {
        this.situation = situation;
    }
    public String getSituation() {
        return this.situation;
    }
    public boolean getSituationV() {
        return this.situation.equals("accepted");
    }
    public void setCurrency(String name) {
        this.currency = name;
    }
    public String getCurrency() {
        return this.currency;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public double getAmount() {
        return this.amount;
    }

}
