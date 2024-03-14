package com.wico.systemlinkweb.component;


import com.wico.systemlinkweb.property.GasAdminProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

/**
 * @author Neosgao
 * @date 17:51 2022/12/9
 **/
@Component
@ToString
public class JwtComponent {
    private final String header;
    private final long expireTime;
    private final SecretKey secretKey;

    public JwtComponent(GasAdminProperties properties) {
        this.header = properties.getJwt().getHeader();
        this.expireTime = properties.getJwt().getExpireTime();
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.getJwt().getSecret()));
    }

    public String getHeader() {
        return header;
    }

    public String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(this.secretKey)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime * 1000))
                .compact();
    }

    public JwtHelper helper(String token) {
        return new JwtHelper(token);
    }

    public class JwtHelper {
        private final String token;

        private final JwtParser parser;

        private JwtHelper(String token) {
            this.token = token;
            this.parser = Jwts.parserBuilder().setSigningKey(JwtComponent.this.secretKey).build();
        }

        public Object getValue(String key) {
            return parser.parseClaimsJws(token).getBody().get(key);
        }

        public boolean isExpired() {
            try {
                parser.parseClaimsJws(token);
                return false;
            } catch (io.jsonwebtoken.ExpiredJwtException ex) {
                return true;
            }
        }
    }
}
