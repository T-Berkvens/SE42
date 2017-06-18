
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
/**
 *
 * @author Arno
 */
public class EncryptProgram {
    private PrivateKey privateKey;
    private byte[] input;
    Signature signature;
    
    public EncryptProgram(String author){
        try{
            retrievePrivateKey();
            getInputFile();
            getSignature();
            encryptWithPrivate(author);
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
        input = Files.readAllBytes(new File("../INPUT.EXT").toPath());
    }
    
    public byte[] getSignature() throws Exception{
        signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(privateKey);
        signature.update(input);
        return signature.sign();
    }
    
    public void encryptWithPrivate(String author)throws Exception{
        File output = new File("../INPUT(SIGNEDBY" + author + ").EXT");
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
        // TODO code application logic here
    }
    
}
