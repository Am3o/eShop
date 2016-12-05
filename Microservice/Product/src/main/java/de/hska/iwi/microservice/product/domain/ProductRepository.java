/*
 *    Copyright (c) 2016.   Joshua Braun
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package de.hska.iwi.microservice.product.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends CrudRepository<ProductDAO, Long> {
    ProductDAO save(ProductDAO product);

    List<ProductDAO> findAll();

    List<ProductDAO> findByCategoryId(int id);

    ProductDAO findById(int id);

    ProductDAO findByName(String name);

    ProductDAO findByPrice(double price);

    @Query("SELECT u FROM ProductDAO u WHERE u.details LIKE LOWER(CONCAT('%',:details, '%')) AND u.price BETWEEN :minPrice AND :maxPrice")
    List<ProductDAO> search(@Param("details") String details, @Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);
}
