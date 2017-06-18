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
import java.security.Signature;
import java.security.SignatureException;
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

    private PublicKey publicKey;
    private String pathPublicKey = "publicKey";
    private int length = 0;
    Signature signature;
    private String name = "";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        new DecryptFile();
    }
    
    public DecryptFile() throws Exception
    {
        System.out.println("Enter the name of whose file you want to verrify:");
        Scanner scanner = new Scanner(System.in);
        name = scanner.nextLine();
        
        File fPK = new File("../" + pathPublicKey);
        File f = new File("../" + "INPUT(SignedBy" + name + ").EXT");
        if (f.exists()) {
            publicKey = readPublicKey(fPK);
            byte[] bytes = getFileInBytes(f);
            if (checkSignature(bytes)) {
                System.out.println("verified");
                byte[] bytesText = new byte[bytes.length - length];
                for (int i = length + 1 ; i < bytes.length ; i++)
                {
                    bytesText[i - length - 1] = bytes[i];
                }
                File writeF = new File("../" + "INPUT.EXT");
                writeToFile(writeF, bytesText);
            }
            else  {
                System.out.println("wrong signature");
            }
        }
    }
    
    
    
    private boolean checkSignature(byte[] bytes)
    {
        length = Math.abs((int) bytes[0]);
        byte[] signature = new byte[length];
        for (int i = 1 ; i <= length ; i ++)
        {
            signature[i-1] = bytes[i];
        }
        try {
            return decryptText(signature, publicKey);
        } catch (Exception ex) {
            Logger.getLogger(DecryptFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean decryptText(byte[] msg, PublicKey key) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        signature = Signature.getInstance("SHA1withRSA");
        signature.initVerify(key);
        signature.update(name.getBytes());
        return signature.verify(msg);
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
