package com.example.tacoemaillistener.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmailOrder {

    public EmailOrder(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    private List<EmailTaco> tacos = new ArrayList<>();

    public void addTaco(EmailTaco taco) {
        this.tacos.add(taco);
    }

}
