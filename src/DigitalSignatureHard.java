import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;

import static utils.Color.*;

/**
 * Project: LearnSecurity
 * Package: PACKAGE_NAME
 * Author:  Novemser
 * 2016/10/29
 */
public class DigitalSignatureHard {


    public static void main(String... args) throws Exception {
        String message = "明天上午9点30分在济事楼集合";
        byte[] plainText = message.getBytes();

        print(ANSI_BLACK, "Sender:");
        // Using MD5 Algorithm
        // 1. get an MD5 message digest object and compute the plaintext digest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(plainText);
        byte[] md = messageDigest.digest();
        print(ANSI_BLACK, "\nDigest: ");
        print(ANSI_BLACK, new String(md, "UTF8"));

        // 2. generate an RSA key pair
        print(ANSI_YELLOW, "\nStart generating RSA key");
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair key = keyGen.generateKeyPair();
        print(ANSI_GREEN, "Finish generating RSA key");

        // 3. get an RSA cipher instance
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        // 4. encrypt the message digest with the RSA private key
        // to create the signature
        print(ANSI_YELLOW, "\nStart encryption");
        cipher.init(Cipher.ENCRYPT_MODE, key.getPrivate());
        byte[] cipheredMsgDigest = cipher.doFinal(md);
        print(ANSI_GREEN, "Finish encryption! Encrypted message digest:");
        print(ANSI_GREEN, new String(cipheredMsgDigest, "UTF8"));

        print(ANSI_CYAN, "---------------------------------------------------------------------------");
        print(ANSI_CYAN, "---------------------------------------------------------------------------");
        print(ANSI_BLACK, "Receiver:");

        // 5. to verify, start by decrypting the signature with the
        // RSA public key
        print(ANSI_YELLOW, "\nStart decryption");
        cipher.init(Cipher.DECRYPT_MODE, key.getPublic());

        byte[] decryptedMsgDigest = cipher.doFinal(cipheredMsgDigest);
        print(ANSI_BLACK, "Finish decryption of digest: ");
        print(ANSI_BLACK, new String(decryptedMsgDigest, "UTF8"));

        // 6. then, recreate the message digest from the plaintext
        // to simulate what a receiver must do
        print(ANSI_YELLOW, "\nStart signature verification");
        messageDigest.reset();
        messageDigest.update(plainText);
        byte[] incomingMsgDigest = messageDigest.digest();

        // 7. verify that the two message digests match
        int len = decryptedMsgDigest.length;
        if (len != incomingMsgDigest.length) {
            print(ANSI_RED, "Signature failed, length error.");
            System.exit(1);
        }
        for (int i = 0; i < len; ++i)
            if (incomingMsgDigest[i] != decryptedMsgDigest[i]) {
                print(ANSI_GREEN, "Signature failed, element error.");
                System.exit(1);
            }
        print(ANSI_GREEN, "Signature verified! The incoming message is not changed.");

    }
}
