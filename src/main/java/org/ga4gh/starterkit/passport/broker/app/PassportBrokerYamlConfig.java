package org.ga4gh.starterkit.passport.broker.app;

import lombok.Getter;
import lombok.Setter;
import org.ga4gh.starterkit.common.config.ServerProps;
import org.ga4gh.starterkit.passport.broker.config.BrokerProps;
import org.ga4gh.starterkit.passport.broker.config.PassportBrokerDBConfig;
import org.ga4gh.starterkit.passport.broker.model.PassportBrokerServiceInfo;

@Setter
@Getter
public class PassportBrokerYamlConfig {

    private ServerProps serverProps;
    private PassportBrokerDBConfig databaseProps;
    private PassportBrokerServiceInfo serviceInfo;
    private BrokerProps brokerProps;

    public PassportBrokerYamlConfig() {
        serverProps = new ServerProps();
        databaseProps = new PassportBrokerDBConfig();
        serviceInfo = new PassportBrokerServiceInfo();
        brokerProps = new BrokerProps();
    }
}
