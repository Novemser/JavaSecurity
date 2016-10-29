import utils.Color.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;

import static utils.Color.*;
import static utils.Color.print;

/**
 * Project: LearnSecurity
 * Package: PACKAGE_NAME
 * Author:  Novemser
 * 2016/10/28
 */
public class PrivateEnDecrypt {
    public static void main(String...args) throws Exception {
        String message = "我高数挂了，别告诉我妈";
        byte[] plainText = message.getBytes();
        // 1. generate a key
        System.out.println("Start generating AES key...");
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        Key key = keyGen.generateKey();
        System.out.println("Finished generating key!");

        // 2. get a Cipher instance
        // ECB是加密方式，PKCS5Padding是填充方法
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        // 3. use private key to encrypt
        System.out.println("Start encryption...");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // finished generation
        byte[] cipherText = cipher.doFinal(plainText);
        System.out.println("Finished encryption!");
        print(ANSI_PURPLE, "Origin message changed to:");
        print(ANSI_PURPLE, new String(cipherText, "UTF8"));

        // 4. use private key to decrypt
        print(ANSI_RED, "Start decrypting...");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedText = cipher.doFinal(cipherText);
        print(ANSI_GREEN, "Finished decrypting!");

        System.out.println(new String(decryptedText, "UTF8"));
    }
}
