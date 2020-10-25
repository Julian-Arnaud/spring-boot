package com.cb.Service;

import com.ecommerce.microcommerce.model.Product;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TheCircuitBreaker {
    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(){return new RestTemplate();}

    @HystrixCommand(fallbackMethod = "generalFallback")
    public Product cbAfficherUnProduit(int id){
        return restTemplate.exchange("http://localhost:8080/Produits/{id}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Product>() {
                },
                id
        ).getBody();
    }

    @HystrixCommand(fallbackMethod = "generalFallback")
    public String cbAjouterProduit(){
        return restTemplate.exchange("http://localhost:8080/Produits"
                , HttpMethod.POST
                , null
                , new ParameterizedTypeReference<String>() {
                }
                , "Erreur on POST").getBody();
    }

    @HystrixCommand(fallbackMethod = "generalFallback")
    public List<Product> cbOrderProducts(){
        return restTemplate.exchange("http://localhost:8080/OrderProducts"
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<List<Product>>() {
                }
                , "Erreur sur recuperation de liste ordonnee").getBody();
    }

    @HystrixCommand(fallbackMethod = "generalFallback")
    public String cbMargeProduit(){
        return restTemplate.exchange("http://localhost:8080/Produits"
                , HttpMethod.GET
                , null
                , new ParameterizedTypeReference<String>() {
                }
                , "Erreur recuperation de la marge des produits").getBody();
    }

    public String generalFallback(){return "Failure but no worry, uncle Tim is working on it.";}
}
