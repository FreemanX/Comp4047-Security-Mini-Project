package KeySystem;

import FileSystem.FileUtils;
import com.sun.istack.internal.Nullable;
import com.sun.org.apache.bcel.internal.generic.PUSH;

import javax.crypto.SecretKey;
import java.io.*;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

/**
 * Created by xupengfei on 6/4/2016.
 */
public class KeyManager {

    KeyStore keyStore;
    private String passpharse = "";


    public KeyManager() {

    }

    public boolean isSystemInit() {
        return false;
    }

    public boolean initKeyStore() {
        try {
            FileInputStream fileIn = new FileInputStream(KeyStore.KEY_STORE_PATH);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object readInKeyStore = objectIn.readObject();
            if (readInKeyStore instanceof KeyStore) {
                this.keyStore = (KeyStore) readInKeyStore;
                return true;
            } else
                return false;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }

    }

    public void createKeyStore(String keyDesc) {
        keyStore = new KeyStore();
        keyStore.createKeyStore(keyDesc);
    }

    public void buildKeyStore(File keyStoreFile) throws ExecutionException {
        try {
            if (keyStore != null) {
                // key obj to byte
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(keyStore);
                byte[] keyB = bos.toByteArray();
                oos.close();
                bos.close();

                // pbe
                SecureRandom rand = SecureRandom.getInstanceStrong();
                byte[] salt = new byte[8];
                rand.nextBytes(salt);
                SecretKey pbeKey = KeyUtils.generatePBESymKey(passpharse.toCharArray(), salt);
                CBCWrapper wrapper = KeyUtils.encryptPBE(pbeKey, keyB);

                // store this two into the file
                byte[] iv = wrapper.getIv();
                byte[] encKey = wrapper.getData();
                int ivLength = iv.length;
                System.out.println("File path: " + keyStoreFile.getAbsolutePath());
                FileOutputStream fos = new FileOutputStream(keyStoreFile);
                fos.write(ivLength);
                fos.write(iv);
                fos.write(ivLength);
                fos.flush();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkPassord(String passord) {

        return true;
    }

    public void setPassword(String password) {
        passpharse = password;
    }

    public String[] getPublicKeyDescriptions() {
        String[] debugStr = {"sff", "gee", "qqq"};
        return debugStr;
    }

    public String[] getPrivateKeyDescriptions() {
        String[] debugStr = {"bbb", "www", "lll"};
        return debugStr;
    }

    public void updatePublicKeyDescription(int index, String desc) {

    }

    public void updatePrivateKeyDescription(int index, String desc) {

    }

    public SecurityKey generatePrivateKeyPair(@Nullable SecurityKey PrivateKey) {
        if (PrivateKey == null) {
            //TODO create a new pair of key

        } else {
            if (PrivateKey.isPublicKey()) return null;
            PrivateKey privateKey = PrivateKey.getPrivateKey();
            //TODO generate public key from privateKey
        }

        return null; //TODO return a SecurityKey instance with is a private Key and public key is store in this instance
    }

    public void readInKeyStore(String filePath) {

    }

    public void exportPublicKey(String filePath) {

    }

    public void exportPrivateKey(String filePath) {

    }

    public void importPublicKey(String filePath) {

    }

    public void importPrivateKey(String filePath) {

    }

    public void removePublicKey() {

    }

    public void removePrivateKey() {

    }


    public void changePassword(String newPassword) {

    }

    public void changeKeyDescription() {

    }

}
