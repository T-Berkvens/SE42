package opdrachttwee;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.security.SecureRandom;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML
    private void btnEncrypt(ActionEvent event){
        
    }
    
    @FXML
    private void btnDecrypt(ActionEvent event){
        
    }
    
    private byte[] generateSalt()
    {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }
}
