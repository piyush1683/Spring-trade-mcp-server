package com.sample.trade.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Base64;
import java.util.HashMap;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JWTTokenGenService {

    @Autowired
    private ResourceLoader resourceLoader;

    private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    public String generateToken(String username) throws Exception {
        // Generate a JWT token for the given username
        Map<String, Object> claims = new HashMap<>();
        claims.put("issuer", "MYLocalCA");
        claims.put("audience", "MYAudience");

        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(30, ChronoUnit.DAYS);

        return Jwts.builder().claims().add(claims)
                .subject(username)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
                // .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .and().signWith(readPrivatekey())
                .compact();
    }

    private RSAPrivateKey readPrivatekey() throws Exception {
        System.out.println("Reading private key from file");
        Resource resource = resourceLoader.getResource("classpath:keys/privatekey.pem");
        InputStream inputStream = resource.getInputStream();
        byte[] key = FileCopyUtils.copyToByteArray(inputStream);
        String privateKeyPEM = new String(key).replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

        // byte[] key = Files.readAllBytes(Paths.get("/keys/privatekey.pem"));
        // System.out.println("Private key read successfully" + privateKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        PrivateKey finalKey = keyFactory.generatePrivate(keySpec);
        System.out.println("Private key retrieved successfully");
        return (RSAPrivateKey) finalKey;
    }

    private RSAPublicKey readPublicKey() throws Exception {
        System.out.println("Reading public key from file");
        File file = ResourceUtils.getFile("classpath:keys/publickey.pem");

        FileInputStream inputStream = new FileInputStream(file);
        byte[] key = inputStream.readAllBytes();
        inputStream.close();
        String pemContent = new String(key, StandardCharsets.UTF_8);

        String pubKeyPEM = pemContent.replaceAll(System.lineSeparator(), "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "").replaceAll("\\s", "");
        ;
        System.out.println("Public key PEM content: " + pubKeyPEM);
        byte[] decodedKeyBytes = Base64.getDecoder().decode(pubKeyPEM);

        // 4. Create a X509EncodedKeySpec
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKeyBytes);

        // 5. Generate the PublicKey object
        KeyFactory keyFactory = KeyFactory.getInstance("RSA"); // Or "EC" for ECC keys
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public boolean validateToken(String jwtToken) {

        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'validateToken'");

        // return true;
        try {

            System.out.println("Validating JWT Token: " + jwtToken);
            if (jwtToken == null || jwtToken.isEmpty()) {
                System.out.println("JWT Token is null or empty");
                return false;
            }
            PublicKey publicKey = readPublicKey();
            Jwts.parser().verifyWith(publicKey)
                    .build()
                    .parseClaimsJws(jwtToken);
            System.out.println("JWT Token is valid");
            return true;
        } catch (Exception e) {
            System.out.println("JWT Token is not valid: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Authentication getAuthentication(String jwtToken) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAuthentication'");
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(token).build().parseSignedClaims(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration.before((new Date(System.currentTimeMillis())));
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
