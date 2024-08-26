package com.airtribe.NewAggregator;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class JwtSecretKeyGeneratorTest {

    @Test
    public void generateSecretKey() {
        SecretKey key = Jwts.SIG.HS256.key().build();
        String encKey = DatatypeConverter.printHexBinary(key.getEncoded());
        System.out.println("Key : " + encKey);
    }
}
