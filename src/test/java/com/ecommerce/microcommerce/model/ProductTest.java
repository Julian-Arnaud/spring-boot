package com.ecommerce.microcommerce.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {

    Product p = new Product(212332, "p1", 30, 10);

    @Test
    public void test1(){
        assertEquals(10, p.getPrixAchat());
    }

    @Test
    public void test2(){
        assertEquals(30, p.getPrix());
    }

    @Test
    public void test3(){
        assertEquals(212332, p.getId());
    }

    @Test
    public void test4(){
        assertEquals("p1", p.getNom());
    }

    @Test
    public void test5(){
        p.setPrixAchat(15);
        assertEquals(15, p.getPrixAchat());
    }

    @Test
    public void test6(){
        p.setPrix(15);
        assertEquals(15, p.getPrix());
    }

    @Test
    public void test7(){
        p.setPrix(30);
        p.setPrixAchat(10);
        assertEquals(20, p.getPrix() - p.getPrixAchat());
    }
}