package com.example.tacoemaillistener.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmailTaco {

    public EmailTaco(String name) {
        this.name = name;
    }

    private String name;
    private List<String> ingredients = new ArrayList<>();
}
