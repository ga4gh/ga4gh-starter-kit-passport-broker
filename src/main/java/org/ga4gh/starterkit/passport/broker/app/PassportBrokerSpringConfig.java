package org.ga4gh.starterkit.passport.broker.app;

import org.apache.catalina.connector.Connector;
import org.apache.commons.cli.Options;
import org.ga4gh.starterkit.common.config.DatabaseProps;
import org.ga4gh.starterkit.common.config.ServerProps;
import org.ga4gh.starterkit.common.requesthandler.BasicCreateRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicDeleteRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicShowRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicUpdateRequestHandler;
import org.ga4gh.starterkit.common.util.CliYamlConfigLoader;
import org.ga4gh.starterkit.common.util.DeepObjectMerger;
import org.ga4gh.starterkit.common.util.webserver.AdminEndpointsConnector;
import org.ga4gh.starterkit.common.util.webserver.AdminEndpointsFilter;
import org.ga4gh.starterkit.common.util.webserver.CorsFilterBuilder;
import org.ga4gh.starterkit.common.util.webserver.TomcatMultiConnectorServletWebServerFactoryCustomizer;
import org.ga4gh.starterkit.passport.broker.config.BrokerProps;
import org.ga4gh.starterkit.passport.broker.model.PassportUser;
import org.ga4gh.starterkit.passport.broker.model.PassportVisa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConfigurationProperties
public class PassportBrokerSpringConfig implements WebMvcConfigurer {

    /* ******************************
     * TOMCAT SERVER
     * ****************************** */

    @Value("${server.admin.port:4501}")
    private String serverAdminPort;

    @Bean
    public WebServerFactoryCustomizer servletContainer() {
        Connector[] additionalConnectors = AdminEndpointsConnector.additionalConnector(serverAdminPort);
        ServerProperties serverProperties = new ServerProperties();
        return new TomcatMultiConnectorServletWebServerFactoryCustomizer(serverProperties, additionalConnectors);
    }

    @Bean
    public FilterRegistrationBean<AdminEndpointsFilter> adminEndpointsFilter() {
        return new FilterRegistrationBean<AdminEndpointsFilter>(new AdminEndpointsFilter(Integer.valueOf(serverAdminPort)));
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(
        @Autowired ServerProps serverProps
    ) {
        return new CorsFilterBuilder(serverProps).buildFilter();
    }

    /* ******************************
     * YAML CONFIG
     * ****************************** */

    @Bean
    public Options getCommandLineOptions() {
        final Options options = new Options();
        options.addOption("c", "config", true, "Path to YAML config file");
        return options;
    }

    @Bean
    @Scope(PassportBrokerConstants.PROTOTYPE)
    @Qualifier(PassportBrokerConstants.EMPTY_CONFIG_CONTAINER)
    public PassportBrokerYamlConfigContainer emptyConfigContainer() {
        return new PassportBrokerYamlConfigContainer();
    }

    @Bean
    @Qualifier(PassportBrokerConstants.DEFAULT_CONFIG_CONTAINER)
    public PassportBrokerYamlConfigContainer defaultConfigContainer() {
        return new PassportBrokerYamlConfigContainer();
    }

    @Bean
    @Qualifier(PassportBrokerConstants.USER_CONFIG_CONTAINER)
    public PassportBrokerYamlConfigContainer userConfigContainer(
        @Autowired ApplicationArguments args,
        @Autowired() Options options,
        @Qualifier(PassportBrokerConstants.EMPTY_CONFIG_CONTAINER) PassportBrokerYamlConfigContainer configContainer
    ) {
        PassportBrokerYamlConfigContainer userConfigContainer = CliYamlConfigLoader.load(PassportBrokerYamlConfigContainer.class, args, options, "config");
        if (userConfigContainer != null) {
            return userConfigContainer;
        }
        return configContainer;
    }

    @Bean
    @Qualifier(PassportBrokerConstants.FINAL_CONFIG_CONTAINER)
    public PassportBrokerYamlConfigContainer finalConfigContainer(
        @Qualifier(PassportBrokerConstants.DEFAULT_CONFIG_CONTAINER) PassportBrokerYamlConfigContainer defaultContainer,
        @Qualifier(PassportBrokerConstants.USER_CONFIG_CONTAINER) PassportBrokerYamlConfigContainer userContainer
    ) {
        DeepObjectMerger merger = new DeepObjectMerger();
        merger.merge(userContainer, defaultContainer);
        return defaultContainer;
    }

    @Bean
    public ServerProps getServerProps(
        @Qualifier(PassportBrokerConstants.FINAL_CONFIG_CONTAINER) PassportBrokerYamlConfigContainer configContainer
    ) {
        return configContainer.getPassportBrokerConfig().getServerProps();
    }

    @Bean
    public DatabaseProps getDatabaseProps(
        @Qualifier(PassportBrokerConstants.FINAL_CONFIG_CONTAINER) PassportBrokerYamlConfigContainer configContainer
    ) {
        return configContainer.getPassportBrokerConfig().getDatabaseProps();
    }

    @Bean
    public BrokerProps getBrokerProps(
        @Qualifier(PassportBrokerConstants.FINAL_CONFIG_CONTAINER) PassportBrokerYamlConfigContainer configContainer
    ) {
        return configContainer.getPassportBrokerConfig().getBrokerProps();
    }
}