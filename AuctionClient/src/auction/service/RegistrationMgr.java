package auction.service;

import java.util.*;
import web.User;


public class RegistrationMgr {

    public static User getUser(java.lang.String arg0) {
        web.RegistrationService service = new web.RegistrationService();
        web.Registration port = service.getRegistrationPort();
        return port.getUser(arg0);
    }

    public static User registerUser(java.lang.String arg0) {
        web.RegistrationService service = new web.RegistrationService();
        web.Registration port = service.getRegistrationPort();
        return port.registerUser(arg0);
    }

    
}
