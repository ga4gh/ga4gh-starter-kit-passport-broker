package org.ga4gh.starterkit.passport.broker.config;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Component
@ConfigurationProperties(prefix = "ga4gh.service-info")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceInfoConfig {

    @Data
    public static class Organization {
        private String name;
        private String url;
    }

    @Data
    public static class ServiceType {
        private String group;
        private String artifact;
        private String version;
    }

    private String id;
    private String name;
    private String description;
    private String contactUrl;
    private String documentationUrl;
    private String createdAt;
    private String updatedAt;
    private String environment;
    private String version;
    private Organization organization;
    private ServiceType type;
}
