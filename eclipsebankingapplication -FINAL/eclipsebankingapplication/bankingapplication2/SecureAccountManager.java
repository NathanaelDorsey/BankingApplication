package bankingapplication2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;
import java.util.logging.Logger;

public class SecureAccountManager {
    private static final Logger LOGGER = Logger.getLogger(SecureAccountManager.class.getName());
    private static final String KEYSTORE_TYPE = "JCEKS";
    private static final String CIPHER_ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128; // bits
    private static final int IV_LENGTH = 12; // GCM recommended IV length in bytes

    private String keystoreFile;
    private String keystorePassword;
    private KeyStore keyStore;

    public SecureAccountManager() {
        loadConfiguration();
        initializeKeyStore();
    }

    private void loadConfiguration() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("resources/config.properties")) {
            props.load(input);
            keystoreFile = props.getProperty("keystoreFile");
            keystorePassword = props.getProperty("keystorePassword");
        } catch (Exception e) {
            LOGGER.severe("Unable to load configuration: " + e.getMessage());
            throw new RuntimeException("Configuration load failed", e);
        }
    }

    private void initializeKeyStore() {
        try {
            keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
            FileInputStream fis = new FileInputStream(keystoreFile);
            keyStore.load(fis, keystorePassword.toCharArray());
            fis.close();
            setupKey();
        } catch (Exception e) {
            LOGGER.severe("Keystore initialization failed: " + e.getMessage());
            throw new RuntimeException("Keystore initialization failed", e);
        }
    }

    private void setupKey() throws Exception {
        Key key = keyStore.getKey("aesGcmEncryptionKey", keystorePassword.toCharArray());
        if (key == null) {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256); // Key size
            SecretKey generatedKey = keyGen.generateKey();
            KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(generatedKey);
            KeyStore.ProtectionParameter protectionParam = new KeyStore.PasswordProtection(keystorePassword.toCharArray());
            keyStore.setEntry("aesGcmEncryptionKey", secretKeyEntry, protectionParam);
            FileOutputStream fos = new FileOutputStream(keystoreFile);
            keyStore.store(fos, keystorePassword.toCharArray());
            fos.close();
        }
    }

    public String encryptData(String data) throws Exception {
        SecretKey key = (SecretKey) keyStore.getKey("aesGcmEncryptionKey", keystorePassword.toCharArray());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        byte[] iv = new byte[IV_LENGTH];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(iv) + ":" + Base64.getEncoder().encodeToString(encryptedData);
    }

    public String decryptData(String encryptedData) throws Exception {
        String[] parts = encryptedData.split(":");
        byte[] iv = Base64.getDecoder().decode(parts[0]);
        byte[] cipherText = Base64.getDecoder().decode(parts[1]);
        SecretKey key = (SecretKey) keyStore.getKey("aesGcmEncryptionKey", keystorePassword.toCharArray());
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedData = cipher.doFinal(cipherText);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }
}
