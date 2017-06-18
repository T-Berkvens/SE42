package opdrachttwee;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * FXML Controller class
 *
 * @author Arno
 */
public class HomeController implements Initializable {
    @FXML
    TextField txtPassword;
    @FXML
    TextArea txtContent;
    
    byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    IvParameterSpec ivspec = new IvParameterSpec(iv);
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML
    private void btnEncrypt(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, IOException, InvalidKeyException, InvalidAlgorithmParameterException{
        char[] password = txtPassword.getText().toCharArray();
        String text = txtContent.getText();
        txtPassword.setText("");
        txtContent.setText("");
        byte[] salt = generateSalt();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        Key key = (Key)getKey(password, salt);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
        byte[] ciphertext = cipher.doFinal(text.getBytes("UTF-8"));
        File f = new File("../text");
        writeToFile(f, ciphertext, salt);
        txtContent.setText(new String(ciphertext));
        resetPassword(password);
    }
    
    private void resetPassword(char[] password)
    {
        for (int i = 0; i < password.length; i++) {
            password[i] = '0';
        }
    }
    
    private SecretKey getKey(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, salt, 935, 128);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
        return secret;
    }
    
    @FXML
    private void btnDecrypt(ActionEvent event) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
        char[] password = txtPassword.getText().toCharArray();
        txtPassword.setText("");
        File f = new File("../text");
        byte[] totalText = getFileInBytes(f);
        byte[] salt = new byte[20];
        for (int i = 0; i < 20; i++)
        {
            salt[i] = totalText[i];
        }
        byte[] text = new byte[totalText.length - 20];
        for (int i = 20; i < totalText.length; i++)
        {
            text[i-20] = totalText[i];
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        Key key = (Key)getKey(password, salt);
        cipher.init(Cipher.DECRYPT_MODE, key, ivspec);
        byte[] ciphertext = cipher.doFinal(text);
        txtContent.setText(new String(ciphertext));
        resetPassword(password);
    }
    
    private byte[] generateSalt()
    {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }
    
    public byte[] getFileInBytes(File f) throws IOException {
        FileInputStream fis = new FileInputStream(f);
        byte[] fbytes = new byte[(int) f.length()];
        fis.read(fbytes);
        fis.close();
        return fbytes;
    }
    
    private void writeToFile(File output, byte[] toWrite, byte[] salt) throws IllegalBlockSizeException, BadPaddingException, IOException {
        FileOutputStream fos = new FileOutputStream(output);
        fos.write(salt);
        fos.write(toWrite);
        fos.flush();
        fos.close();
    }
}
