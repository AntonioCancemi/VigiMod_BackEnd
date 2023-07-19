package com.vigimod.api.runner;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.vigimod.api.entity.Image;
import com.vigimod.api.entity.Product;
import com.vigimod.api.entity.Seller;
import com.vigimod.api.repository.ImageDaoRepo;
import com.vigimod.api.repository.ProductDaoRepo;
import com.vigimod.api.repository.SellerDaoRepo;

@Component
public class ProductRunner implements ApplicationRunner {
    @Autowired
    SellerDaoRepo seller;
    @Autowired
    ProductDaoRepo repo;
    @Autowired
    ImageDaoRepo image;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (repo.findAll().isEmpty()) {
            setProduct();
        }
    }

    public void setProduct() {
        List<Seller> sellerList = seller.findAll();
        final String[] imagesPath = {
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3pUwjmkQ-rlNLeyTIdIyHqu1VLrqTfYRGVw&usqp=CAU",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTwoIf9ZklBa1ieaSmMsqePXX9QfbPoCzOM7Q&usqp=CAU",
                "https://rabona.store/wp-content/uploads/2022/09/pc-specs-header02.png" };
        final String[] example = {
                "Apple iPhone 12", "Samsung Galaxy S21", "Sony PlayStation 5", "Canon EOS R5",
                "Samsung 65-inch 4K Smart TV", "Amazon Echo Dot", "Google Pixel 5", "Microsoft Xbox Series X",
                "LG CX OLED TV", "Apple MacBook Pro", "Samsung Galaxy Watch", "Sony WH-1000XM4 Headphones",
                "DJI Mavic Air 2 Drone", "GoPro HERO9 Black", "Bose QuietComfort 35 II Headphones",
                "Nintendo Switch", "Fitbit Versa 3", "Sony A7 III", "LG 55-inch OLED 4K Smart TV",
                "Apple iPad Pro", "Samsung Galaxy Tab S7", "Sony WF-1000XM4 Earbuds", "Microsoft Surface Pro 7",
                "Canon EOS 5D Mark IV", "LG Soundbar", "Apple AirPods Pro", "Samsung 32-inch Curved Monitor",
                "Sony BRAVIA 65-inch 4K Smart TV", "Amazon Kindle Paperwhite", "Google Nest Hub"
        };
        final String[] categories = {
                "Consumer Electronics",
                "Appliances",
                "Clothing and Accessories",
                "Jewelry and Watches",
                "Home Goods",
                "Personal Care Products",
                "Tools and Equipment",
                "Sports and Outdoors",
                "Books and Media",
                "Office Products"
        };

        Faker faker = new Faker();
        Random rand = new Random();
        while (repo.findAll().size() < 15) {
            String titles = example[rand.nextInt(example.length)];
            String brand = titles.split(" ")[0];
            Product p = Product.builder()
                    .seller(sellerList.get(rand.nextInt(sellerList.size())))
                    .title(titles).brand(brand)
                    .category(categories[rand.nextInt(categories.length)])
                    .description(faker.lorem().fixedString(200))
                    .price(rand.nextInt(1000))
                    .stock(rand.nextInt(110))
                    .build();
            repo.save(p);

            image.save(Image.builder().imagePath(imagesPath[rand.nextInt(imagesPath.length)]).productId(p.getId())
                    .build());
            image.save(Image.builder().imagePath(imagesPath[rand.nextInt(imagesPath.length)]).productId(p.getId())
                    .build());
            image.save(Image.builder().imagePath(imagesPath[rand.nextInt(imagesPath.length)]).productId(p.getId())
                    .build());
            image.save(Image.builder().imagePath(imagesPath[rand.nextInt(imagesPath.length)]).productId(p.getId())
                    .build());
            // }

        }
    }

    private Double roundToTwoDecimals(double number) {
        return Math.round(number * 100.0) / 100.0;
    }

}
