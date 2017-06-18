/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryptprogram;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;

/**
 *
 * @author Arno
 */
public class EncryptProgram {
    private PrivateKey privateKey;
    private byte[] input;
    
    public EncryptProgram(String author){
        try{
            retrievePrivateKey();
            getInputFile();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void retrievePrivateKey()throws Exception{
        byte[] keyBytes = Files.readAllBytes(new File("../privateKey").toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        privateKey = kf.generatePrivate(spec);
    }
    
    public void getInputFile()throws Exception{
        input = Files.readAllBytes(new File("../input.txt").toPath());
    }
    
    public void EncryptWithPrivate(String author)throws Exception{
        File output = new File("../INPUT(SignedBy" + author + ").EXT");
        Cipher cipher = Cipher.getInstance("RSA");;
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        writeToFile(output, cipher.doFinal(input));
    }
    
    public void writeToFile(File output, byte[] toWrite)throws Exception{
        FileOutputStream fos = new FileOutputStream(output);
        fos.write(toWrite);
        fos.flush();
        fos.close();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Enter the name of who is encrytping the file:");
        String encryptedBy = "Bert";
                //System.console().readLine();
        new EncryptProgram(encryptedBy);
    }
    
}
