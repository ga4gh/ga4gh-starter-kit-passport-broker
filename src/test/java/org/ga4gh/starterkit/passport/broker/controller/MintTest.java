package org.ga4gh.starterkit.passport.broker.controller;

import org.ga4gh.starterkit.passport.broker.PassportBroker;
import org.ga4gh.starterkit.passport.broker.app.PassportBrokerSpringConfig;
import org.ga4gh.starterkit.passport.broker.model.MintRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import testutils.TestJwtVerifier;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ContextConfiguration(classes = {PassportBroker.class, PassportBrokerSpringConfig.class, Mint.class})
@WebAppConfiguration
public class MintTest extends AbstractTestNGSpringContextTests {
    /*
    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @DataProvider(name = "mintCases")
    public Object[][] mintCases() {
        return new Object[][] {
            {
                "85ff5a54-48b9-4294-a91d-2be50bd2a77d",
                "jeremy.adams@ga4gh.org",
                "Jeremy",
                "Adams",
                new ArrayList<String>() {{
                    add("670cc2e7-9a9c-4273-9334-beb40d364e5c");
                    add("39e6e359-e8da-4193-92cc-2eed421fe729");
                }},
                status().isOk(),
                true
            },
            {
                "00000000-0000-0000-0000-000000000000",
                "nobody@ga4gh.org",
                "No",
                "Body",
                new ArrayList<String>() {{
                    add("670cc2e7-9a9c-4273-9334-beb40d364e5c");
                    add("39e6e359-e8da-4193-92cc-2eed421fe729");
                }},
                status().isBadRequest(),
                false
            },
            {
                "46d40e07-8d51-4e4d-a93a-e3438271172b",
                "emre.ferhatoglu@ga4gh.org",
                "Emre",
                "Ferhatoglu",
                new ArrayList<String>() {{
                    add("670cc2e7-9a9c-4273-9334-beb40d364e5c");
                    add("39e6e359-e8da-4193-92cc-2eed421fe729");
                }},
                status().isBadRequest(),
                false
            }
        };
    }

    @Test(dataProvider = "mintCases")
    public void testMintPassport(
        String researcherId, String researcherEmail, String researcherFirstName,
        String researcherLastName, List<String> requestedVisas,
        ResultMatcher expStatus, boolean expSuccess
    ) throws Exception {
        MintRequestBody requestBody = new MintRequestBody();
        requestBody.setResearcherId(researcherId);
        requestBody.setResearcherEmail(researcherEmail);
        requestBody.setResearcherFirstName(researcherFirstName);
        requestBody.setResearcherLastName(researcherLastName);
        requestBody.setRequestedVisas(requestedVisas);

        ObjectMapper mapper = new ObjectMapper();

        MvcResult result = mockMvc.perform(
            post("/admin/ga4gh/passport/v1/mint")
            .content(mapper.writeValueAsString(requestBody))
            .header("Content-Type", "application/json")
        )
            .andExpect(expStatus)
            .andReturn();
        
        if (expSuccess) {
            // verify the JWT as the passport level
            String rawPassportJwt = result.getResponse().getContentAsString();
            TestJwtVerifier.verify(rawPassportJwt);
        }
    }
    */
}
