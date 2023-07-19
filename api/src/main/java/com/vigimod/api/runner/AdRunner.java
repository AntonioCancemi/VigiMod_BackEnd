package com.vigimod.api.runner;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.vigimod.api.entity.Ad;
import com.vigimod.api.entity.Product;
import com.vigimod.api.repository.AdDaoRepo;
import com.vigimod.api.repository.ProductDaoRepo;
import com.vigimod.api.utils.AdStatus;
import com.vigimod.api.utils.ShippingType;

@Component
public class AdRunner implements ApplicationRunner {

    @Autowired
    AdDaoRepo repo;
    @Autowired
    ProductDaoRepo productRpo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (repo.findByAdStatus(AdStatus.PENDING).isEmpty()) {
            setAd();
        }
    }

    public void setAd() {
        List<Product> pList = productRpo.findAll();
        ShippingType[] sList = ShippingType.values();
        Random rand = new Random();
        String[] larr = {
                "Italia, Lombardia, Milano",
                "Spagna, Catalogna, Barcellona",
                "Francia, Île-de-France, Parigi",
                "Germania, Baviera, Monaco di Baviera",
                "Regno Unito, Inghilterra, Londra",
                "Svizzera, Zurigo, Zurigo",
                "Svezia, Stoccolma, Stoccolma",
                "Olanda, Olanda Settentrionale, Amsterdam",
                "Belgio, Bruxelles, Bruxelles",
                "Norvegia, Oslo, Oslo",
                "Danimarca, Zelanda, Copenaghen",
                "Portogallo, Lisbona, Lisbona",
                "Austria, Vienna, Vienna",
                "Grecia, Attica, Atene",
                "Repubblica Ceca, Praga, Praga",
                "Polonia, Masovia, Varsavia",
                "Ungheria, Budapest, Budapest",
                "Finlandia, Uusimaa, Helsinki",
                "Irlanda, Dublino, Dublino",
                "Croazia, Zagabria, Zagabria",
                "Romania, Bucarest, Bucarest",
                "Bulgaria, Sofia, Sofia",
                "Lituania, Vilnius, Vilnius",
                "Lettonia, Riga, Riga",
                "Estonia, Harju, Tallinn",
                "Slovenia, Lubiana, Lubiana",
                "Slovacchia, Bratislava, Bratislava",
                "Serbia, Belgrado, Belgrado",
                "Bosnia-Erzegovina, Sarajevo, Sarajevo",
                "Montenegro, Podgorica, Podgorica",
                "Macedonia del Nord, Skopje, Skopje",
                "Albania, Tirana, Tirana",
                "Kosovo, Pristina, Pristina",
                "Islanda, Regione della Capitale, Reykjavík",
                "Malta, La Valletta, La Valletta",
                "Cipro, Nicosia, Nicosia"
        };
        List<String> llist = new ArrayList<>(Arrays.asList(larr));
        for (int i = 0; i < 10; i++) {
            Ad a = new Ad();
            a.setAdStatus(AdStatus.PENDING);
            a.setShippingType(sList[rand.nextInt(sList.length)]);
            // Generate a random index only if the location array has elements
            if (!llist.isEmpty()) {
                a.setLocation(llist.get(rand.nextInt(llist.size())));
            }

            a.setProduct(pList.get(rand.nextInt(pList.size())));
            a.setPublicationDate(LocalDateTime.now());
            System.out.println(a);
            if (repo.findByProductAndLocationAndAndPublicationDate(a.getProduct(), a.getLocation(),
                    a.getPublicationDate()).isEmpty()) {
                repo.save(a);
            }

        }
    }
}
