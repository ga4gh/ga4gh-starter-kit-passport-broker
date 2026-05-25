package org.ga4gh.starterkit.passport.broker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ga4gh.starterkit.passport.broker.config.ServiceInfoConfig;

@RestController
@RequestMapping("/ga4gh/passport/v1/service-info")
public class ServiceInfo {

    @Autowired
    private ServiceInfoConfig serviceInfoConfig;

    @GetMapping
    public ServiceInfoConfig getServiceInfo() {
        return serviceInfoConfig;
    }
}
