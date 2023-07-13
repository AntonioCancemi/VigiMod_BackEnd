package com.vigimod.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Console;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;
import com.vigimod.api.entity.Ad;
import com.vigimod.api.entity.Seller;
import com.vigimod.api.utils.SellerType;
//I test vanno runnati singolarmente o non funzionano tutti 
@SpringBootTest
@AutoConfigureMockMvc
public class SellerApiTest {

        private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        @Autowired
        private MockMvc mockMvc;

        static Faker faker = new Faker();
        static SellerType[] t = SellerType.values();
        static Random rand = new Random();

        @Test
        public void testGetAll() throws Exception {
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/seller"))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                                .andReturn();

                // Verifica che la risposta contenga la lista dei venditori
                String responseBody = result.getResponse().getContentAsString();
                List<Seller> sellers = objectMapper.readValue(responseBody, new TypeReference<List<Seller>>() {
                });

                // Verifica che la lista non sia vuota
                assertFalse(sellers.isEmpty());

                // Altre asserzioni per verificare il contenuto della risposta
        }

        @Test
        public void testGetById() throws Exception {
                Long sellerId = 2L;
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/seller/{id}", sellerId))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(sellerId))
                                .andReturn();

                String jsonResponse = result.getResponse().getContentAsString();
                Seller seller = objectMapper.readValue(jsonResponse, Seller.class);
                assertEquals(Seller.class, seller.getClass());

                // Altre asserzioni per verificare il contenuto della risposta
        }

        @Test
        public void testDelete() throws Exception {
                // Creazione di un venditore nel database per poi eliminarlo
                Seller createdSeller = createSellerInDatabase();
                Long sellerId = createdSeller.getId();

                // Effettua la chiamata DELETE per eliminare il venditore
                mockMvc.perform(MockMvcRequestBuilders.delete("/api/seller/{id}", sellerId))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                // Verifica che il venditore sia stato eliminato correttamente
                assertTrue(sellerExistsInDatabase(sellerId));
        }

        private Seller createSellerInDatabase() throws Exception {
                Seller sellerfake = new Seller();
                sellerfake.setUsername(faker.name().username());
                sellerfake.setFullName(faker.name().fullName());
                sellerfake.setEmail(faker.internet().emailAddress());
                sellerfake.setPhoneNumber(faker.phoneNumber().cellPhone());
                sellerfake.setAccontActive(rand.nextBoolean());
                sellerfake.setImage(faker.internet().image());
                sellerfake.setSellerType(t[rand.nextInt(t.length)]);
                // Conversione del venditore in JSON
                String jsonPayload = objectMapper.writeValueAsString(sellerfake);

                // Effettua la chiamata POST per creare il venditore nel database
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/seller")
                                .content(jsonPayload)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                                .andReturn();

                // Estrazione del venditore creato dalla risposta
                String jsonResponse = result.getResponse().getContentAsString();

                return objectMapper.readValue(jsonResponse, Seller.class);
        }

        private boolean sellerExistsInDatabase(Long sellerId) {
                // Effettua una chiamata GET per verificare se il venditore esiste nel database
                try {
                        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/seller/{id}", sellerId))
                                        .andExpect(MockMvcResultMatchers.status().isNotFound())
                                        .andReturn();
                        return true;
                } catch (Exception e) {
                        return false;
                }
        }

        @Test
        public void testCreate() throws Exception {
                Seller sellerfake = new Seller();
                sellerfake.setUsername(faker.name().username());
                sellerfake.setFullName(faker.name().fullName());
                sellerfake.setEmail(faker.internet().emailAddress());
                sellerfake.setPhoneNumber(faker.phoneNumber().cellPhone());
                sellerfake.setAccontActive(rand.nextBoolean());
                sellerfake.setImage(faker.internet().image());
                sellerfake.setSellerType(t[rand.nextInt(t.length)]);
                String jsonPayload = objectMapper.writeValueAsString(sellerfake);

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/seller")
                                .content(jsonPayload)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                                .andReturn();

                // Verifica che la risposta contenga la lista dei venditori
                String responseBody = result.getResponse().getContentAsString();
                Seller seller = objectMapper.readValue(responseBody, new TypeReference<Seller>() {
                });

                // Verifica che la lista non sia vuota
                assertEquals(sellerfake.getEmail(), seller.getEmail());
                assertEquals(sellerfake.getFullName(), seller.getFullName());
                assertEquals(sellerfake.getImage(), seller.getImage());
                assertEquals(sellerfake.getPhoneNumber(), seller.getPhoneNumber());
                assertEquals(sellerfake.getUsername(), seller.getUsername());
                assertEquals(sellerfake.getSellerType(), seller.getSellerType());

                // Verifica che il venditore appena creato sia presente nella lista
                mockMvc.perform(MockMvcRequestBuilders.delete("/api/seller/{id}", seller.getId()))
                                .andExpect(MockMvcResultMatchers.status().isOk());

        }

        @Test
        public void testUpdate() throws Exception {
                Long sellerId = 2L;
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/seller/{id}", sellerId))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(sellerId))
                                .andReturn();
                // verifico la reponse
                String jsonResponse = result.getResponse().getContentAsString();
                Seller seller = objectMapper.readValue(jsonResponse, Seller.class);
                assertEquals(Seller.class, seller.getClass());

                seller.setId(2L);
                seller.setUsername("updated_username");
                String jsonPayload = objectMapper.writeValueAsString(seller);

                mockMvc.perform(MockMvcRequestBuilders.put("/api/seller/{id}", sellerId)
                                .content(jsonPayload)
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
                // Altre asserzioni per verificare il contenuto della risposta
        }

}