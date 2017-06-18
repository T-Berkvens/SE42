/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decryptfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Bert
 */
public class DecryptFile {

    private Cipher cipher;
    private PublicKey publicKey;
    private String pathPublicKey = "";
    private int length = 0;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        DecryptFile df = new DecryptFile();
    }
    
    public DecryptFile() throws Exception
    {
        try {
            cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DecryptFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(DecryptFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();
        
        File fPK = new File("../" + pathPublicKey);
        File f = new File("../" + path);
        if (f.exists()) {
            publicKey = readPublicKey(fPK);
            byte[] bytes = getFileInBytes(f);
            String signature = checkSignature(bytes);
            if (signature == path.substring(path.indexOf("SignedBy") + 8)) {
                System.out.println("verified");
                byte[] bytesText = new byte[bytes.length - length];
                for (int i = length ; i < bytes.length - length ; i++)
                {
                    bytesText[i - length] = bytes[i];
                }
                writeToFile(f, bytesText);
            }
            else  {
                System.out.println("wrong signature");
            }
        }
    }
    
    
    
    private String checkSignature(byte[] bytes)
    {
        length = (int) bytes[0];
        byte[] signature = new byte[length];
        String signatureDecrypted = "";
        for (int i = 1 ; i < length ; i ++)
        {
            signature[i-1] = bytes[i];
        }
        try {
            signatureDecrypted = decryptText(signature, publicKey);
        } catch (Exception ex) {
            Logger.getLogger(DecryptFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return signatureDecrypted;
    }
    
    public String decryptText(byte[] msg, PublicKey key) throws InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(msg), "UTF-8");
    }
    
    private PublicKey readPublicKey(File f) throws Exception
    {
        byte[] keyBytes = Files.readAllBytes(f.toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
    
    public byte[] getFileInBytes(File f) throws IOException {
        FileInputStream fis = new FileInputStream(f);
        byte[] fbytes = new byte[(int) f.length()];
        fis.read(fbytes);
        fis.close();
        return fbytes;
    }
    
    
    private void writeToFile(File output, byte[] toWrite) throws IllegalBlockSizeException, BadPaddingException, IOException {
        FileOutputStream fos = new FileOutputStream(output);
        fos.write(toWrite);
        fos.flush();
        fos.close();
    }
}
