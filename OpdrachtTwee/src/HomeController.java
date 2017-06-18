/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * FXML Controller class
 *
 * @author Arno
 */
public class HomeController implements Initializable {

    private Cipher c;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.c = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private byte[] generateSalt()
    {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }
    
    private byte[] encrypt(String strToEncrypt) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        char[] password = new char[] {'a', 'a', 'a', 'a', 'a'};
        byte[] byteToEncrypt = strToEncrypt.getBytes("UTF-8");
        this.c.init(Cipher.ENCRYPT_MODE, (new String(password)).getBytes(), generateSalt());
        byte[] encryptedBytes = this.c.doFinal(byteToEncrypt);

        return encryptedBytes;

    }

    // Initialize the cipher in DECRYPT_MODE
    // Decrypt and store as byte[]
    // Convert to plainText and return

    private String decrypt(byte[] byteToDecrypt) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

        c.init(Cipher.DECRYPT_MODE, this.s_KEY, generateSalt());

        byte[] plainByte = this.c.doFinal(byteToDecrypt);

        String plainText = new String(plainByte);

        return plainText;

    }
    
}
