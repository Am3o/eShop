package de.hska.iwi.microservice.product.web;

import de.hska.iwi.microservice.product.domain.Category;
import de.hska.iwi.microservice.product.domain.Product;
import de.hska.iwi.microservice.product.domain.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductController implements IProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        super();
        this.productRepository = productRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Product createProduct() {
        return new Product("leer", 0.1, "leer", new Category("dummy"));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Product updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Product deleteProduct(@PathVariable("id") int id) {
        Product product = productRepository.findById(id);
        productRepository.delete(product);
        return product;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable("id") int id) {
        return productRepository.findById(id);
    }
}
