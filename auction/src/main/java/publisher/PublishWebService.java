/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publisher;

import javax.xml.ws.Endpoint;
import webService.WebAuction;

/**
 *
 * @author Arno
 */
public class PublishWebService {
    private static final String url = "http://localhost:8080/WebAuction";

    public static void main(String[] args) {
        Endpoint.publish(url, new WebAuction());
    }
}