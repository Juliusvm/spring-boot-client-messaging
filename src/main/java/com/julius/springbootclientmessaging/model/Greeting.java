package com.julius.springbootclientmessaging.model;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class Greeting {

    private String content;

    public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }

}
