package com.wico.systemlinkweb.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "gasadmin")
public class GasAdminProperties {
    private String adminDefaultPassword;
    private String usernameHeader;
    private final Jwt jwt = new Jwt();

    @Data
    public static class Jwt {
        private String header;
        private String secret;
        private long expireTime;
    }
}
