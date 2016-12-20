package de.hska.iwi.microservice.catalog.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.hska.iwi.microservice.catalog.client.AuthenticationServiceClient;
import de.hska.iwi.microservice.catalog.client.CategoryServiceClient;
import de.hska.iwi.microservice.catalog.client.ProductServiceClient;
import de.hska.iwi.microservice.catalog.client.api.ProductService;
import de.hska.iwi.microservice.catalog.entity.Catalog;
import de.hska.iwi.microservice.catalog.entity.Category;
import de.hska.iwi.microservice.catalog.entity.Credential;
import de.hska.iwi.microservice.catalog.entity.Product;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

/**
 * Created by ameo on 13.11.16.
 */
@Service
public class CatalogServiceFacade implements ICatalogServiceFacade{
    private final Logger logger = Logger.getLogger(CatalogServiceFacade.class);

    private static final String CATEGORY_SERVICE = "category-Service";
    private static final String PRODUCT_SERVICE = "production-service";
    private static final String AUTHENTICATION_SERVICE = "AUTHENTICATION-SERVICE";

    private static final String CATEGORY_SERVICE_URL_DEFAULTT = "http://localhost:4211/";
    private static final String PRODUCT_SERVICE_URL_DEFAULT = "http://localhost:4205/";
    private static final String AUTHENTICATION_SERVICE_URL_DEFAULT = "http://localhost:4201/";

    private CategoryServiceClient categoryServiceClient;
    private ProductServiceClient productServiceClient;
    private AuthenticationServiceClient authenticationServiceClient;

    @Autowired
    public CatalogServiceFacade(DiscoveryClient discoveryClient) {
        try{
            if(discoveryClient.getServices().contains(CATEGORY_SERVICE)) {
                URI categoryServiceUri = discoveryClient.getInstances(CATEGORY_SERVICE).get(0).getUri();
                this.categoryServiceClient = new CategoryServiceClient(categoryServiceUri.toString());
            }
        }catch (Exception ex) {
            logger.error("Initialisierungsfehler: CategoryService", ex);
        } finally {
            if (!(this.categoryServiceClient instanceof CategoryServiceClient))
                this.categoryServiceClient = new CategoryServiceClient(CATEGORY_SERVICE_URL_DEFAULTT);
        }

        try {
            if(discoveryClient.getServices().contains(PRODUCT_SERVICE)){
                URI productServiceUri = discoveryClient.getInstances(PRODUCT_SERVICE).get(0).getUri();
                this.productServiceClient = new ProductServiceClient(productServiceUri.toString());
            }
        }catch (Exception ex) {
            logger.error("Initialisierungsfehler: ProductService", ex);
        }finally {
            if(!(this.productServiceClient instanceof ProductServiceClient))
                this.productServiceClient = new ProductServiceClient(PRODUCT_SERVICE_URL_DEFAULT);
        }

        try {
            if(discoveryClient.getServices().contains(AUTHENTICATION_SERVICE)){
                URI authenticationUrl = discoveryClient.getInstances(AUTHENTICATION_SERVICE).get(0).getUri();
                this.authenticationServiceClient = new AuthenticationServiceClient(authenticationUrl.toString());
            }
        }catch (Exception ex) {
            logger.error("Initialisierungsfehler: ProductService", ex);
        }finally {
            if(!(this.authenticationServiceClient instanceof AuthenticationServiceClient))
                this.authenticationServiceClient = new AuthenticationServiceClient(AUTHENTICATION_SERVICE_URL_DEFAULT);
        }
    }

    @Override
    @HystrixCommand
    public boolean checkPermission(Credential credential) {
        return authenticationServiceClient.existCustomer(credential.getUsername(), credential.getPassword(), true);
    }

    @Override
    @HystrixCommand
    public List<Category> getAllCategories() {
        return categoryServiceClient.getAllCategories();
    }

    @Override
    @HystrixCommand
    public Category createCategory(Category category) {
        return categoryServiceClient.createCategory(category);
    }

    @Override
    @HystrixCommand
    public Category updateCategory(int categoryId, Category category) {
        return categoryServiceClient.updateCategory(category);
    }

    @Override
    @HystrixCommand
    public Category getCategory(int categoryId) {
        return categoryServiceClient.getCategory(categoryId);
    }

    @Override
    @HystrixCommand
    public boolean deleteCategory(int categoryId) {
        return categoryServiceClient.deleteCategory(categoryId);
    }

    @Override
    @HystrixCommand
    public Product createProduct(Product product) {
        return productServiceClient.createProduct(product);
    }

    @Override
    @HystrixCommand
    public Product updateProduct(int productId, Product product) {
        return productServiceClient.updateProduct(productId, product);
    }

    @Override
    @HystrixCommand
    public boolean deleteProduct(int id) {
        return productServiceClient.deleteProduct(id);
    }

    @Override
    @HystrixCommand
    public List<Product> getProducts(int categoryId) {
        return productServiceClient.getProducts(categoryId);
    }

    @Override
    @HystrixCommand
    public Product getProduct(int id) {
        return productServiceClient.getProduct(id);
    }

    @Override
    @HystrixCommand
    public List<Catalog> getCatalog() {
        return null;
    }

    @Override
    @HystrixCommand
    public List<Catalog> getCatalogCategorie(int categoryId) {
        return null;
    }

    @Override
    @HystrixCommand
    public List<Product> searchCatalog(double minPrice, double maxPrice, String content) {
        return productServiceClient.search(content, minPrice, maxPrice);
    }
}
