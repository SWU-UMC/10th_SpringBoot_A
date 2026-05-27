package com.example.umc10th.global.security.jwt;

import com.example.umc10th.domain.member.entity.Member;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 외부 라이브러리 없이 HMAC-SHA256 기반 JWT access token을 발급하고 검증합니다.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String TOKEN_TYPE = "Bearer";

    private final ObjectMapper objectMapper;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration-millis}")
    private long accessTokenExpirationMillis;

    /**
     * 인증 성공 또는 회원가입 직후 클라이언트가 Authorization 헤더에 담아 사용할 토큰을 만듭니다.
     */
    public TokenInfo generateAccessToken(Member member) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusMillis(accessTokenExpirationMillis);

        Map<String, Object> header = new LinkedHashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("sub", member.getEmail());
        payload.put("memberId", member.getId());
        payload.put("iat", now.getEpochSecond());
        payload.put("exp", expiresAt.getEpochSecond());

        String unsignedToken = base64UrlEncode(writeJson(header))
                + "."
                + base64UrlEncode(writeJson(payload));
        String signature = base64UrlEncode(sign(unsignedToken));

        return new TokenInfo(unsignedToken + "." + signature, TOKEN_TYPE, accessTokenExpirationMillis);
    }

    /**
     * 서명과 만료 시간을 함께 검증해 필터에서 신뢰 가능한 토큰만 SecurityContext에 올립니다.
     */
    public boolean validateToken(String token) {
        try {
            Map<String, Object> claims = parseAndVerify(token);
            Number expiresAt = (Number) claims.get("exp");
            return expiresAt != null && Instant.now().getEpochSecond() < expiresAt.longValue();
        } catch (RuntimeException exception) {
            return false;
        }
    }

    public String getSubject(String token) {
        Object subject = parseAndVerify(token).get("sub");
        if (subject == null) {
            throw new IllegalArgumentException("JWT subject is missing.");
        }
        return subject.toString();
    }

    private Map<String, Object> parseAndVerify(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("JWT must have 3 parts.");
        }

        String unsignedToken = parts[0] + "." + parts[1];
        byte[] expectedSignature = sign(unsignedToken);
        byte[] actualSignature = base64UrlDecode(parts[2]);
        if (!MessageDigest.isEqual(expectedSignature, actualSignature)) {
            throw new IllegalArgumentException("JWT signature is invalid.");
        }

        try {
            return objectMapper.readValue(
                    base64UrlDecode(parts[1]),
                    new TypeReference<Map<String, Object>>() {
                    }
            );
        } catch (Exception exception) {
            throw new IllegalArgumentException("JWT payload is invalid.", exception);
        }
    }

    private byte[] writeJson(Map<String, Object> value) {
        try {
            return objectMapper.writeValueAsBytes(value);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to write JWT json.", exception);
        }
    }

    private byte[] sign(String value) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM));
            return mac.doFinal(value.getBytes(StandardCharsets.UTF_8));
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to sign JWT.", exception);
        }
    }

    private String base64UrlEncode(byte[] value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value);
    }

    private byte[] base64UrlDecode(String value) {
        return Base64.getUrlDecoder().decode(value);
    }

    public record TokenInfo(
            String accessToken,
            String tokenType,
            Long expiresIn
    ) {
    }
}
