package com.vigimod.api.runner;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.vigimod.api.entity.Product;
import com.vigimod.api.entity.Seller;
import com.vigimod.api.repository.ProductDaoRepo;
import com.vigimod.api.repository.SellerDaoRepo;

@Component
public class ProductRunner implements ApplicationRunner {
    @Autowired
    SellerDaoRepo seller;
    @Autowired
    ProductDaoRepo repo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (repo.findAll().isEmpty() && !seller.findAll().isEmpty()) {
            setSeller();
        }
    }

    public void setSeller() {
        List<Seller> sellerList = seller.findAll();
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
        for (int i = 0; i < 10; i++) {
            String titles = example[rand.nextInt(example.length)];
            String brand = titles.split(" ")[0];
            Product p = new Product();
            p.setSeller(sellerList.get(rand.nextInt(sellerList.size())));
            p.setTitle(titles);
            p.setBrand(brand);
            p.setCategory(categories[rand.nextInt(categories.length)]);
            p.setDescription(faker.lorem().fixedString(200));
            p.setPrice(rand.nextInt(1000));
            p.setThumbnail(faker.internet().image());
            p.setStock(rand.nextInt(110));
            p.setDiscount(roundToTwoDecimals(rand.nextDouble(60)));
            p.setRating((roundToTwoDecimals(rand.nextDouble(3))) + 2);

            // System.out.println(p);
            if (repo.findByTitleAndDescriptionAndBrandAndCategoryAndPrice(p.getTitle(), p.getDescription(),
                    p.getBrand(), p.getCategory(), p.getPrice()).isEmpty()) {

                repo.save(p);
            }
        }
    }

    private Double roundToTwoDecimals(double number) {
        return Math.round(number * 100.0) / 100.0;
    }
    // private static final String[] TITLES_ELECTRONICS = {
    // "Apple iPhone 12", "Samsung Galaxy S21", "Sony PlayStation 5", "Canon EOS
    // R5",
    // "Samsung 65-inch 4K Smart TV", "Amazon Echo Dot", "Google Pixel 5",
    // "Microsoft Xbox Series X", "LG CX OLED TV"
    // };

    // private static final String[] TITLES_APPLIANCES = {
    // "Instant Pot Duo", "Dyson V11 Vacuum Cleaner", "Nespresso VertuoPlus Coffee
    // Machine",
    // "KitchenAid Stand Mixer", "Cuisinart Air Fryer", "Roomba i7+ Robot Vacuum",
    // "Breville Smart Oven"
    // };

    // private static final String[] TITLES_FASHION = {
    // "Nike Air Max 270", "Adidas Ultraboost", "Levi's 501 Jeans", "Ray-Ban
    // Wayfarer Sunglasses", "Hermes Birkin Bag",
    // "Rolex Submariner Watch", "Zara Trench Coat"
    // };

    // private static final String[] TITLES_HOME = {
    // "LG OLED CX Series", "Roomba i7+ Robot Vacuum", "Vitamix 5200 Blender",
    // "Sonos One Speaker", "Philips Hue Smart Bulbs", "Nest Learning Thermostat",
    // "Dyson Supersonic Hair Dryer"
    // };

    // private static final String[] TITLES_COMPUTERS = {
    // "Microsoft Surface Laptop 4", "Apple MacBook Pro", "Dell XPS 15", "HP Spectre
    // x360", "Lenovo ThinkPad X1 Carbon",
    // "ASUS ROG Zephyrus G14", "Acer Predator Helios 300"
    // };

    // private static final String[] TITLES_GAMING = {
    // "Sony PlayStation 5", "Microsoft Xbox Series X", "Nintendo Switch", "PC
    // Gaming Desktop", "Razer Gaming Mouse",
    // "SteelSeries Arctis 7 Headset", "Logitech G502 Gaming Keyboard"
    // };

    // private static final String[] TITLES_CAMERAS = {
    // "Canon EOS R5", "Sony Alpha A7 III", "Nikon Z6", "Fujifilm X-T4", "GoPro
    // Hero9 Black",
    // "DJI Mavic Air 2", "Olympus OM-D E-M1 Mark III"
    // };

    // private static final String[] TITLES_SHOES = {
    // "Nike Air Max 270", "Adidas Ultraboost", "Vans Old Skool", "Converse Chuck
    // Taylor All Star", "Puma Suede Classic",
    // "New Balance 574", "Reebok Classic Leather"
    // };

    // private static final String[] TITLES_SPORTS = {
    // "Nike Air Max 270", "Adidas Ultraboost", "Yoga Mat", "Dumbbell Set",
    // "Treadmill",
    // "Cycling Bike", "Boxing Gloves"
    // };

    // private static final String[] TITLES_HEALTH = {
    // "Fitbit Versa 3", "Apple Watch Series 6", "Garmin Forerunner 245", "Withings
    // Body+ Smart Scale", "Theragun PRO",
    // "Resistance Bands", "Yoga Block"
    // };
}
