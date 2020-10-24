package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ProductControllerTest {

    @MockBean
    public ProductDao dao;

    @Before
    public void setUp(){
        Product p1 = new Product(1,"Ordinateur portable",350,120);
        Product p2 = new Product(2, "Aspirateur robot", 500, 200);
        Product p3 = new Product(3, "Table ping pong", 750, 400);
        List<Product> list = new ArrayList<>();
        list.add(p2);list.add(p1);list.add(p3);
        Mockito.when(dao.findById(1)).thenReturn(p1);
        Mockito.when(dao.findById(2)).thenReturn(p2);
        Mockito.when(dao.findById(3)).thenReturn(p3);

        Mockito.when(dao.orderAscend()).thenReturn(list);

        Mockito.when(dao.calcMarge()).thenReturn(list);
    }

    @Test
    public void testGetProductById(){

        assertEquals("Ordinateur portable", dao.findById(1).getNom());
    }

    @Test
    public void testOrderAscend(){
        List<Product> res = dao.orderAscend();
        assertEquals("Aspirateur robot", res.get(0).getNom());
        assertEquals("Ordinateur portable", res.get(1).getNom());
        assertEquals("Table ping pong", res.get(2).getNom());
    }

    @Test
    public void testCalcMarge(){
        List<Product> res = dao.calcMarge();
        assertEquals(300, res.get(0).getPrix() - res.get(0).getPrixAchat());
        assertEquals(230, res.get(1).getPrix() - res.get(1).getPrixAchat());
        assertEquals(350, res.get(2).getPrix() - res.get(2).getPrixAchat());
    }
}