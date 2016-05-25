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

package de.hska.iwi.microservice.authentication.web;

import de.hska.iwi.microservice.authentication.domian.Customer;
import de.hska.iwi.microservice.authentication.service.IAuthenticationServiceFacade;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AuthenticationController implements IAuthenticationController {
    private final Logger logger = Logger.getLogger(AuthenticationController.class);
    private final IAuthenticationServiceFacade authenticationServiceFasade;

    @Autowired
    public AuthenticationController(IAuthenticationServiceFacade authenticationServiceFasade) {
        logger.info("Erzeuge Authentication-Steuereinheit");
        this.authenticationServiceFasade = authenticationServiceFasade;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public boolean createUser(@ModelAttribute Customer customer) {
        return authenticationServiceFasade.createCustomer(customer);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public String getUserInformation(@PathVariable("userId") long userId) {
        logger.info(String.format("Service-Aufruf: getCustomerInformation mit dem Wert: %d", userId));
        return authenticationServiceFasade.getCustomerInformation(userId).toString();
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public boolean updateUser(@PathVariable("userId") long userId) {
        //FIXME: Aktuell gibt es kein Anwendungsszenario, dennoch nett zu haben
        return false;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public boolean deleteUser(@PathVariable("userId") long userId) {
        //FIXME: Aktuell korrekt implemnentiert, jedoch kein Anwendungsszenario hierfür
        return authenticationServiceFasade.deleteCustomer(userId);
    }

    @RequestMapping(value = "/{userId}/login", method = RequestMethod.PUT)
    public boolean loginUser(@PathVariable("userId") long userId) {
        //TODO: Anmeldung liefert bei Erfolg den passenden Benutzer zurück
        //      Sassionhandling client-seitig?
        return false;
    }

    @RequestMapping(value = "/{userId}/logout", method = RequestMethod.PUT)
    public boolean logoutUser(@PathVariable("userId") long userId) {
        //TODO: Abmeldung liefert bei Erfolg den Benutzer zurück?
        //      Sassionhandling client-seitig?
        return false;
    }
}
