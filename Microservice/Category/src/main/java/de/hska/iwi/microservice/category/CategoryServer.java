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

package de.hska.iwi.microservice.category;

import de.hska.iwi.microservice.category.config.CategoryServerConfig;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * Created by ameo on 27.05.16.
 */
@EnableDiscoveryClient
@SpringBootApplication
@Import(CategoryServerConfig.class)
public class CategoryServer {
    private static Logger logger = Logger.getLogger(CategoryServer.class);

    public static void main(String[] args) {
        logger.info("Erzeuge CategoryDAO-Service.");
        System.setProperty("spring.config.name", "Category-Server");
        SpringApplication.run(CategoryServer.class, args);
    }
}
