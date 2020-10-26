package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitGratuitException;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api( description="API pour les opérations CRUD sur les produits.")
@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;


    //Récupérer la liste des produits

    @ApiOperation(value = "Affiche les produits")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "ok"),
                    @ApiResponse(code = 401, message = "non autorisé"),
                    @ApiResponse(code = 403, message = "forbidden"),
                    @ApiResponse(code = 404, message = "not found")
            }
    )
    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    public MappingJacksonValue listeProduits() {

        Iterable<Product> produits = productDao.findAll();

        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");

        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);

        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);

        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
    }


    //Récupérer un produit par son Id
    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock!")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "ok"),
                    @ApiResponse(code = 401, message = "non autorisé"),
                    @ApiResponse(code = 403, message = "forbidden"),
                    @ApiResponse(code = 404, message = "not found")
            }
    )
    @GetMapping(value = "/Produits/{id}")

    public Product afficherUnProduit(@PathVariable int id) {

        Product produit = productDao.findById(id);

        if(produit==null) throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");

        return produit;
    }




    //ajouter un produit
    @ApiOperation(value = "Permet d'ajouter un produit à la collection à condition que son prix soit non nul")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "ok"),
                    @ApiResponse(code = 401, message = "non autorisé"),
                    @ApiResponse(code = 403, message = "forbidden"),
                    @ApiResponse(code = 404, message = "not found")
            }
    )
    @PostMapping(value = "/Produits")

    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {

        Product productAdded =  productDao.save(product);
        if (product.getPrix() == 0)
            throw new ProduitGratuitException("xx");
        if (productAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Permet de supprimer un produit de la collection via son ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "ok"),
                    @ApiResponse(code = 401, message = "non autorisé"),
                    @ApiResponse(code = 403, message = "forbidden"),
                    @ApiResponse(code = 404, message = "not found")
            }
    )
    @DeleteMapping (value = "/Produits/{id}")
    public void supprimerProduit(@PathVariable int id) {

        productDao.delete(id);
    }

    @ApiOperation(value = "Permet de mettre à jour un produit")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "ok"),
                    @ApiResponse(code = 401, message = "non autorisé"),
                    @ApiResponse(code = 403, message = "forbidden"),
                    @ApiResponse(code = 404, message = "not found")
            }
    )
    @PutMapping (value = "/Produits")
    public void updateProduit(@RequestBody Product product) {

        productDao.save(product);
    }


    //Pour les tests
    @GetMapping(value = "test/produits/{prix}")
    public List<Product>  testeDeRequetes(@PathVariable int prix) {

        return productDao.chercherUnProduitCher(400);
    }

    //Renvoie les produits tries par ordre alphabetique
    @ApiOperation(value = "Affiche les produits classés par odre alphabétique")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "ok"),
                    @ApiResponse(code = 401, message = "non autorisé"),
                    @ApiResponse(code = 403, message = "forbidden"),
                    @ApiResponse(code = 404, message = "not found")
            }
    )
    @GetMapping (value = "/OrderProducts")
    public List<Product> trierProduitsParOrdreAlphabetique(){
        return productDao.orderAscend();
    }

    @ApiOperation(value = "Renvoie la marge de chacun des produits")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "ok"),
                    @ApiResponse(code = 401, message = "non autorisé"),
                    @ApiResponse(code = 403, message = "forbidden"),
                    @ApiResponse(code = 404, message = "not found")
            }
    )
    @GetMapping (value = "/AdminProduits")
    public List<Product> calculerMargeProduit(){
        return productDao.calcMarge();
    }

}
