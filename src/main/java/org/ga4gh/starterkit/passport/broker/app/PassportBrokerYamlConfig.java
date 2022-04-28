package org.ga4gh.starterkit.passport.broker.app;

import org.ga4gh.starterkit.common.config.DatabaseProps;
import org.ga4gh.starterkit.common.config.ServerProps;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PassportBrokerYamlConfig {

    private ServerProps serverProps;
    private DatabaseProps databaseProps;

    public PassportBrokerYamlConfig() {
        serverProps = new ServerProps();
        databaseProps = new DatabaseProps();
    }
}
