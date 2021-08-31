package com.kwan.saq.request;

public class PostPaymentRequest {
//    "service_id": 2,
//    "customer_id": 2,
//    "amount": 76,
//    "name_on_card": "Le Minh Quan",
//    "card_number": "4242424242424242",
//    "card_exp_month": 12,
//    "card_exp_year": 2022,
//    "card_cvc": 444
    private int service_id;
    private int customer_id;
    private float amount;
    private String name_on_card;
    private String card_number;
    private int card_exp_month;
    private int card_exp_year;
    private int card_cvc;

    public PostPaymentRequest(int service_id, int customer_id, float amount, String name_on_card, String card_number, int card_exp_month, int card_exp_year, int card_cvc) {
        this.service_id = service_id;
        this.customer_id = customer_id;
        this.amount = amount;
        this.name_on_card = name_on_card;
        this.card_number = card_number;
        this.card_exp_month = card_exp_month;
        this.card_exp_year = card_exp_year;
        this.card_cvc = card_cvc;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getName_on_card() {
        return name_on_card;
    }

    public void setName_on_card(String name_on_card) {
        this.name_on_card = name_on_card;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public int getCard_exp_month() {
        return card_exp_month;
    }

    public void setCard_exp_month(int card_exp_month) {
        this.card_exp_month = card_exp_month;
    }

    public int getCard_exp_year() {
        return card_exp_year;
    }

    public void setCard_exp_year(int card_exp_year) {
        this.card_exp_year = card_exp_year;
    }

    public int getCard_cvc() {
        return card_cvc;
    }

    public void setCard_cvc(int card_cvc) {
        this.card_cvc = card_cvc;
    }
}
