package org.ga4gh.starterkit.passport.broker.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.ga4gh.starterkit.common.requesthandler.BasicCreateRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicDeleteRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicShowRequestHandler;
import org.ga4gh.starterkit.common.requesthandler.BasicUpdateRequestHandler;
import org.ga4gh.starterkit.passport.broker.model.PassportVisa;
import org.ga4gh.starterkit.passport.broker.utils.SerializeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/ga4gh/passport/v1/visas")
public class Visas {

    /*
    @Autowired
    private PassportBrokerHibernateUtil hibernateUtil;

    @Resource(name = "showVisaRequestHandler")
    private BasicShowRequestHandler<String, PassportVisa> showVisa;

    @Resource(name = "createVisaRequestHandler")
    private BasicCreateRequestHandler<String, PassportVisa> createVisa;

    @Resource(name = "updateVisaRequestHandler")
    private BasicUpdateRequestHandler<String, PassportVisa> updateVisa;

    @Resource(name = "deleteVisaRequestHandler")
    private BasicDeleteRequestHandler<String, PassportVisa> deleteVisa;

    @GetMapping
    @JsonView(SerializeView.Visa.class)
    public List<PassportVisa> getPassportVisas() {
        return hibernateUtil.getPassportVisas();
    }

    @GetMapping(path = "/{visaId:.+}")
    @JsonView(SerializeView.VisaRelational.class)
    public PassportVisa getPassportVisa(
        @PathVariable(name = "visaId") String visaId
    ) {
        return showVisa.prepare(visaId).handleRequest();
    }

    @PostMapping
    @JsonView(SerializeView.VisaRelational.class)
    public PassportVisa createPassportVisa(
        @RequestBody PassportVisa passportVisa
    ) {
        setBidirectionalRelationship(passportVisa);
        // set visa secret if it doesn't exist
        if (passportVisa.getVisaSecret() == null) {
            passportVisa.setVisaSecret(RandomStringUtils.random(64, true, true));
        }
        return createVisa.prepare(passportVisa).handleRequest();
    }

    @PutMapping(path = "/{visaId:.+}")
    @JsonView(SerializeView.VisaRelational.class)
    public PassportVisa updatePassportVisa(
        @PathVariable(name = "visaId") String visaId,
        @RequestBody PassportVisa passportVisa
    ) {
        setBidirectionalRelationship(passportVisa);
        // set visa secret if it doesn't exist
        if (passportVisa.getVisaSecret() == null) {
            passportVisa.setVisaSecret(RandomStringUtils.random(64, true, true));
        }
        return updateVisa.prepare(visaId, passportVisa).handleRequest();
    }

    @DeleteMapping(path = "/{visaId:.+}")
    @JsonView(SerializeView.VisaRelational.class)
    public PassportVisa deletePassportVisa(
        @PathVariable(name = "visaId") String visaId
    ) {
        return deleteVisa.prepare(visaId).handleRequest();
    }

    private void setBidirectionalRelationship(PassportVisa passportVisa) {
        if (passportVisa.getPassportVisaAssertions() != null) {
            passportVisa.getPassportVisaAssertions().forEach(assertion -> assertion.setPassportVisa(passportVisa));
        }
    }
    */
}
