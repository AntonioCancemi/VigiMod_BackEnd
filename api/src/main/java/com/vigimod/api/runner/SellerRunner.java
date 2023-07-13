package com.vigimod.api.runner;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.vigimod.api.entity.Seller;
import com.vigimod.api.repository.SellerDaoRepo;
import com.vigimod.api.utils.SellerType;

@Component
public class SellerRunner implements ApplicationRunner {

    @Autowired
    SellerDaoRepo repo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (repo.findAll().isEmpty()) {
            setSeller();
        }
    }

    public void setSeller() {
        Faker faker = new Faker();
        SellerType[] t = SellerType.values();
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            Seller s = new Seller();
            s.setUsername(faker.name().username());
            s.setFullName(faker.name().fullName());
            s.setEmail(faker.internet().emailAddress());
            s.setPhoneNumber(faker.phoneNumber().cellPhone());
            s.setAccontActive(rand.nextBoolean());
            s.setImage(faker.internet().image());
            s.setSellerType(t[rand.nextInt(t.length)]);

            // System.out.println(s);
            if (!repo.existsByUsernameAndEmailAndPhoneNumber(s.getUsername(),
                    s.getEmail(), s.getPhoneNumber())) {

                repo.save(s);
            }
        }
    }
}
