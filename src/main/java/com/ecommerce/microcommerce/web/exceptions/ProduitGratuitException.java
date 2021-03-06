package com.ecommerce.microcommerce.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
public class ProduitGratuitException extends RuntimeException{
    public ProduitGratuitException(String s) {
        super("Impossible d'ajouter un produit ayant un prix de 0");
    }
}
