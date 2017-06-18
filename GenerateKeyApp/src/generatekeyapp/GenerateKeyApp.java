/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generatekeyapp;

import java.io.File;
import java.io.FileOutputStream;
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
        try{
            GenerateKeys(1024);
            writeToFile("publicKey", publicKey.getEncoded());
            writeToFile("privateKey", privateKey.getEncoded());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    private void GenerateKeys(int length) throws Exception{
        KeyPairGenerator keyGen = null;
        keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(length);
        KeyPair keys = keyGen.genKeyPair();
        publicKey = keys.getPublic();
        privateKey = keys.getPrivate();
    }
    
    public void writeToFile(String path, byte[] key) throws Exception {
        File f = new File("../" + path);

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(key);
        fos.flush();
        fos.close();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new GenerateKeyApp();
    }
}
