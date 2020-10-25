package com.cb.controller;

import com.cb.Service.TheCircuitBreaker;
import com.ecommerce.microcommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CircuitBreakerController {
    @Autowired
    private TheCircuitBreaker circuitBreaker;

    @RequestMapping(value = "/Produits/{id}", method = RequestMethod.GET)
    public Product cGetProduit(@PathVariable int id){
        return circuitBreaker.cbAfficherUnProduit(id);
    }

    @RequestMapping(value = "/OrderProducts", method = RequestMethod.GET)
    public List<Product> cOrderAscend(){
        return circuitBreaker.cbOrderProducts();
    }
}
