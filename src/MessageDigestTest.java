import java.security.MessageDigest;

/**
 * Project: LearnSecurity
 * Package: PACKAGE_NAME
 * Author:  Novemser
 * 2016/10/28
 */
public class MessageDigestTest {
    public static void main(String... args) throws Exception {
        String message = "高数挂了，桑心";
        byte[] plainText = message.getBytes();

        // 1. Creates the message digest
        MessageDigest digest = MessageDigest.getInstance("SHA1");
        // 2. Calculates the message digest with a plaintext string.
        digest.update(plainText);
        // 3. Reads the message digest.
        System.out.println("MessageDigest:\t" + new String(digest.digest(), "UTF8"));
    }
}
