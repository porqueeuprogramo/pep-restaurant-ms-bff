package com.pep.restaurant.ms.bff;

import com.pep.restaurant.ms.bff.domain.User;

public class ApplicationDataProvider {

    public User getUser(){
        return new User()
                .id("1")
                .username("joaobarbosa")
                .firstName("Joao")
                .lastName("Barbosa")
                .email("joaobarbosa@porqueeuprogramo.com")
                .password("1234");
    }

}
