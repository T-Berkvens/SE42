/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webService;

import javax.jws.WebService;
import services.Auction;
import services.Registration;

/**
 *
 * @author Arno
 */
@WebService
public class WebAuction {
    private final Auction actuion = new Auction();
    private final Registration registration = new Registration();
    
    //#ToDo add all methods from the Registration and Auction classes
}
