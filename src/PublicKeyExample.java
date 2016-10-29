import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import static utils.Color.*;

/**
 * Project: LearnSecurity
 * Package: PACKAGE_NAME
 * Author:  Novemser
 * 2016/10/29
 */
public class PublicKeyExample {
    public static void main(String...args) throws Exception {
        String message = "Hey Bob, how about lunch at Taco Bell. I hear they have free refills!";
        byte[] plainText = message.getBytes();

        print(ANSI_BLACK, "Sender: Susan");

        // 1. generate an RSA key
        print(ANSI_YELLOW, "Start generating RSA key...");
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        KeyPair keyPair = generator.generateKeyPair();
        print(ANSI_GREEN, "Finished generating RSA key\n");

        // 2. Creates an RSA cipher object
        // (specifying the public key algorithm, mode, and padding).
        // RSA: This algorithm is the most popular public key cipher.
        //
        // ECB: (Electronic Code Book)
        //
        // PKCS1Padding: With PKCS1, a short block is padded with a repeating byte
        //               whose value represents the number of remaining bytes.
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        // 3. encrypt the plainText using the public key
        print(ANSI_YELLOW, "Start encrypting...");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        byte[] cipherText = cipher.doFinal(plainText);
        print(ANSI_GREEN, "Finished encryption!\n");
        print(ANSI_PURPLE, "Susan's origin message encrypted to:");
        print(ANSI_PURPLE, new String(cipherText));

        print(ANSI_CYAN, "---------------------------------------------------------------------------");
        print(ANSI_CYAN, "Susan is sending cipherText to Bob...");
        print(ANSI_CYAN, "---------------------------------------------------------------------------");
        print(ANSI_BLACK, "Receiver: Bob");

        // 4. decrypt the cipherText using private key
        print(ANSI_YELLOW, "\nStart decryption...");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        byte[] originMessage = cipher.doFinal(cipherText);
        print(ANSI_GREEN, "Finished decryption! Decrypted message:");
        print(ANSI_CYAN, new String(originMessage));

    }
}
