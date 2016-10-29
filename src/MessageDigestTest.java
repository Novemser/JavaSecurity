import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Project: LearnSecurity
 * Package: PACKAGE_NAME
 * Author:  Novemser
 * 2016/10/28
 */
public class MessageDigestTest {
    public static void main(String... args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String code = "CODE:{123456}";

        // 1. get message digest instance
        MessageDigest digest = MessageDigest.getInstance("SHA1");
        // lets c information about the provider...
        System.out.println("ProviderInfo:\t" + digest.getProvider().getInfo());
        // 2. init the algorithm
        digest.update(code.getBytes());
        // 3. print the message digest
        System.out.println("MessageDigest:\t" + new String(digest.digest(), "UTF8") + "\t\t(not human language...)");
    }
}
