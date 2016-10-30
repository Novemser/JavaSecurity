import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;

/**
 * Project: LearnSecurity
 * Package: PACKAGE_NAME
 * Author:  Novemser
 * 2016/10/26
 */
public class GenSig {
    public static void main(String...args) throws Exception {
        int keyLen = 1024;

        // 1. create key pair generator
        KeyPairGenerator generator = KeyPairGenerator.getInstance("DSA");

        // 2. initialize the key pair generator
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        generator.initialize(keyLen, random);

        // 3. generate the pair of keys
        KeyPair keyPair = generator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // 4. get a signature object
        Signature signature = Signature.getInstance("SHA1withDSA");
        signature.initSign(privateKey);

        // 5. supply the data
        FileInputStream fileInputStream = new FileInputStream("C:\\Demo\\code");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        byte[] tmpBuf = new byte[bufferedInputStream.available()];
        int len;
        while ((len = bufferedInputStream.read(tmpBuf)) >= 0) {
            signature.update(tmpBuf, 0, len);
        }
        bufferedInputStream.close();

        // 6. generate digital signature
        byte[] sig = signature.sign();

        // 7. save the signature/public key in a file
        FileOutputStream outputStream = new FileOutputStream("C:\\Demo\\signature");
        outputStream.write(sig);
        outputStream.close();

        outputStream = new FileOutputStream("C:\\Demo\\publicKey");
        outputStream.write(publicKey.getEncoded());
        outputStream.close();

        System.out.println("Signature generated successfully.");
    }
}
