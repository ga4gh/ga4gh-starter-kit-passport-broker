package org.ga4gh.starterkit.passport.broker.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.HashSet;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.ga4gh.starterkit.common.exception.BadRequestException;
import org.ga4gh.starterkit.passport.broker.config.BrokerProps;
import org.ga4gh.starterkit.passport.broker.model.MintRequestBody;
import org.ga4gh.starterkit.passport.broker.model.PassportUser;
import org.ga4gh.starterkit.passport.broker.model.PassportVisa;
import org.ga4gh.starterkit.passport.broker.model.PassportVisaAssertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/ga4gh/passport/v1/mint")
public class Mint {
    /*
    @Autowired
    private PassportBrokerHibernateUtil hibernateUtil;

    @Autowired
    private BrokerProps brokerProps;

    @PostMapping
    public String mintPassport(@RequestBody MintRequestBody mintRequestBody) {
        // Validation Phase
        // Validation step: check the user id exists
        PassportUser researcher =  hibernateUtil.readEntityObject(PassportUser.class, mintRequestBody.getResearcherId(), true);
        if (researcher == null) {
            throw new BadRequestException("No user/researcher found by that id");
        }

        // Validation step: check the user has the requested visas authorized
        HashSet<String> researcherActiveVisaIds = new HashSet<>();
        HashMap<String, PassportVisa> researcherVisaMap = new HashMap<>();
        HashMap<String, PassportVisaAssertion> researcherVisaAssertionMap = new HashMap<>();
        for (PassportVisaAssertion visaAssertion : researcher.getPassportVisaAssertions()) {
            PassportVisa visa = visaAssertion.getPassportVisa();
            String visaId = visa.getId();
            String status = visaAssertion.getStatus();
            researcherVisaMap.put(visaId, visa);
            researcherVisaAssertionMap.put(visaId, visaAssertion);
            if (status.equals("active")) {
                researcherActiveVisaIds.add(visaId);
            }
        }

        for (String requestedVisa : mintRequestBody.getRequestedVisas()) {
            if (researcherActiveVisaIds.contains(requestedVisa) == false) {
                throw new BadRequestException("A visa was requested that the researcher does not have authorized access to");
            }
        }

        // Construct JWT Phase
        String passportIss = brokerProps.getPassportIssuer();
        String sub = mintRequestBody.getResearcherEmail();
        long iat = LocalDateTime.now(ZoneOffset.UTC).toEpochSecond(ZoneOffset.UTC);
        long exp = iat + 3600;
        String scope = "openid";

        // Construct JWT for each requested visa
        int nVisas = mintRequestBody.getRequestedVisas().size();
        String[] visaJwts = new String[nVisas];
        String[] containedVisas = new String[nVisas];
        int i = 0;
        for (String requestedVisaId : mintRequestBody.getRequestedVisas()) {
            PassportVisa visaObj = researcherVisaMap.get(requestedVisaId);
            PassportVisaAssertion assertionObj = researcherVisaAssertionMap.get(requestedVisaId);

            String visaName = visaObj.getVisaName();
            String visaIss = visaObj.getVisaIssuer();
            long visaAsserted = assertionObj.getAssertedAt();
            String visaJwt = JWT.create()
                .withClaim("iss", visaIss)
                .withClaim("sub", sub)
                .withClaim("iat", iat)
                .withClaim("exp", exp)
                .withClaim("ga4gh_visa_v1", new HashMap<String, Object>() {{
                    put("type", "ControlledAccessGrants"); // hardcoded to ControlledAccessGrants for now
                    put("asserted", visaAsserted); // get from assertion timestamp
                    put("value", "https://doi.org/10.1038/s41431-018-0219-y"); // hardcoded DOI to passport spec for now
                    put("source", visaIss); // same as visa issuer
                    put("by", "dac"); // hardcoded to DAC for now
                    put("visa_name", visaName);
                    put("visa_issuer", visaIss);
                }})
                .sign(Algorithm.HMAC256(visaObj.getVisaSecret()));
            
            visaJwts[i] = visaJwt;
            containedVisas[i] = visaName + "@" + visaIss;
            i++;
        }

        // Construct Final JWT
        return JWT.create()
            // JWT header - nothing needed as we only include 'typ' and 'alg'
            // JWT payload
            .withClaim("iss", passportIss)
            .withClaim("sub", sub)
            .withClaim("iat", iat)
            .withClaim("exp", exp)
            .withClaim("scope", scope)
            .withArrayClaim("ga4gh_passport_v1", visaJwts)
            .withArrayClaim("contained_visas", containedVisas)
            // JWT signature
            .sign(Algorithm.HMAC256(brokerProps.getBrokerSecret()));
    }
    */
}
