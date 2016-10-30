import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 * Project: LearnSecurity
 * Package: PACKAGE_NAME
 * Author:  Novemser
 * 2016/10/27
 */
public class VerSig {

    @SuppressWarnings("all")
    public static void main(String...args) throws Exception {
        // 1. read the encoded public key
        FileInputStream pubKFile = new FileInputStream("C:\\Demo\\publicKey");
        byte[] encKey = new byte[pubKFile.available()];
        pubKFile.read(encKey);
        pubKFile.close();

        // 2. decode the public key
        X509EncodedKeySpec spec = new X509EncodedKeySpec(encKey);
        PublicKey publicKey = KeyFactory.getInstance("DSA").generatePublic(spec);

        // 3. read the signature
        FileInputStream signatureFile = new FileInputStream("C:\\Demo\\signature");
        byte[] sigByte = new byte[signatureFile.available()];
        signatureFile.read(sigByte);
        signatureFile.close();

        // 4. generate the signature
        Signature signature = Signature.getInstance("SHA1withDSA");
        signature.initVerify(publicKey);

        // 5. supply the data
        FileInputStream dataFile = new FileInputStream("C:\\Demo\\code");
        BufferedInputStream dataStream = new BufferedInputStream(dataFile);
        byte[] tmpBuf = new byte[dataStream.available()];
        int len;
        while ((len = dataStream.read(tmpBuf)) >= 0) {
            signature.update(tmpBuf, 0, len);
        }
        dataStream.close();

        // 6. verify
        boolean result = signature.verify(sigByte);
        System.out.println("Result:" + result);
    }
}
