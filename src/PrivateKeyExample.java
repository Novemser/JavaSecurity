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
public class PrivateKeyExample {
    public static void main(String...args) throws Exception {
        String message = "我高数挂了，别告诉我妈";
        byte[] plainText = message.getBytes();
        print(ANSI_BLACK, "Sender:");

        // 1. generate an AES private key
        print(ANSI_YELLOW, "Start generating AES key...");
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        Key key = keyGen.generateKey();
        print(ANSI_GREEN, "Finished generating AES key!\n");

        // 2. Creates the Cipher object
        // (specifying the private key algorithm, mode, and padding algorithm).
        // AES: (Advanced Encryption Standard) replaces DES as the U.S. standard.
        //      It is a 128-bit block cipher with key lengths of 128, 192, or 256 bits.
        //
        // ECB: (Electronic Code Book)
        //
        // PKCS5Padding: With PKCS5, a short block is padded with a repeating byte
        //               whose value represents the number of remaining bytes.
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        // 3. use private key to encrypt
        print(ANSI_YELLOW, "Start message encryption...");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // finished generation
        byte[] cipherText = cipher.doFinal(plainText);
        print(ANSI_GREEN, "Finished encryption!\n");
        print(ANSI_PURPLE, "Origin message encrypted to:");
        print(new String(cipherText, "UTF8"), ANSI_PURPLE);

        print(ANSI_CYAN, "---------------------------------------------------------------------------");
        print(ANSI_CYAN, "Sending cipherText to receiver...");
        print(ANSI_CYAN, "---------------------------------------------------------------------------");
        print(ANSI_BLACK, "Receiver:");

        // 4. use private key to decrypt
        print(ANSI_RED, "\nStart decrypting...");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedText = cipher.doFinal(cipherText);
        print(ANSI_GREEN, "Finished decrypting! Decrypted message:");

        System.out.println(new String(decryptedText, "UTF8"));
    }
}
