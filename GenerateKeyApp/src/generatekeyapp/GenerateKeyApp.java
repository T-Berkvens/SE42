/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generatekeyapp;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arno
 */
public class GenerateKeyApp {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    
    public GenerateKeyApp(){
        GenerateKeys(1024);
    }
    
    private void GenerateKeys(int length){
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(GenerateKeyApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        keyGen.initialize(length);
        KeyPair keys = keyGen.genKeyPair();
        publicKey = keys.getPublic();
        privateKey = keys.getPrivate();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new GenerateKeyApp();
    }
}
