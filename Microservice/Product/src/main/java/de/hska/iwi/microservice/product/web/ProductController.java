package de.hska.iwi.microservice.product.web;

import de.hska.iwi.microservice.product.entity.Product;
import de.hska.iwi.microservice.product.service.IProductServiceFacade;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProductController implements IProductController {
    private static final Logger logger = Logger.getLogger(ProductController.class);

    private final IProductServiceFacade productServiceFacade;

    @Autowired
    public ProductController(IProductServiceFacade productServiceFacade) {
        super();
        logger.info("Erzeuge Produkt-Steuereinheit");
        this.productServiceFacade = productServiceFacade;
    }

    @Override
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Product createProduct(@RequestBody Product product) {
        logger.info("Erzeuge neues Produkt");
        return productServiceFacade.createProduct(product);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Product updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
        logger.info("Aktualisere vorhandenes Produkt");
        product.setId(id);
        return productServiceFacade.updateProduct(product);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean deleteProduct(@PathVariable("id") int id) {
        logger.info("Lösche vorhandenes Produkt");
        return productServiceFacade.deleteProduct(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Product> getProducts() {
        logger.info("Liefere alle Produkte zurück");
        return productServiceFacade.getProducts();
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable("id") int id) {
        logger.info("Liefere spezielles Produkt zurück");
        return productServiceFacade.getProduct(id);
    }

    @Override
    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    public List<Product> getProductsByCategorieId(@PathVariable("id") int id) {
        logger.info("Liefere alle Produkt mit der speziellen Kategorie zurück");
        return productServiceFacade.getProductsByCategoryId(id);
    }
}
