package com.izzisoft.open_vote.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class RsaKeyConfig {

    public RSAPrivateKey privateKey() {
        try {
            String key = new String(
                    new ClassPathResource("certs/private.pem")
                            .getInputStream()
                            .readAllBytes(),
                    StandardCharsets.UTF_8
            );

            String privateKeyPem = key
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");


            byte[] encoded = Base64.getDecoder().decode(privateKeyPem);

            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(encoded);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Could not load RSA private key", e
            );
        }
    }

    public RSAPublicKey publicKey() {
        try {
            String key = new String(
                    new ClassPathResource("certs/public.pem")
                            .getInputStream()
                            .readAllBytes(),
                    StandardCharsets.UTF_8
            );

            String publicKeyPem = key
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] encoded = Base64.getDecoder().decode(publicKeyPem);

            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(encoded);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Could not load RSA public key", e
            );
        }
    }
}
