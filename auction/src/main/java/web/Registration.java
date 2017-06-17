/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import auction.domain.User;
import auction.service.RegistrationMgr;
import javax.jws.WebService;

/**
 *
 * @author Arno
 */
@WebService
public class Registration {
    private RegistrationMgr registrationMgr = new RegistrationMgr();
    
    public User registerUser(String email){
        return registrationMgr.registerUser(email);
    }
    
    public User getUser(String email){
        return registrationMgr.getUser(email);
    }
}
