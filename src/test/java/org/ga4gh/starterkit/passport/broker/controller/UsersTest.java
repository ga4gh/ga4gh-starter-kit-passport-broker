package org.ga4gh.starterkit.passport.broker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.ga4gh.starterkit.passport.broker.PassportBroker;
import org.ga4gh.starterkit.passport.broker.app.PassportBrokerSpringConfig;
import org.ga4gh.starterkit.passport.broker.model.PassportUser;
import org.ga4gh.starterkit.passport.broker.model.PassportVisaAssertion;
// import org.ga4gh.starterkit.passport.broker.utils.hibernate.PassportBrokerHibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.*;
import testutils.ResourceLoader;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ContextConfiguration(classes = {PassportBroker.class, PassportBrokerSpringConfig.class, Users.class})
@WebAppConfiguration
public class UsersTest extends AbstractTestNGSpringContextTests {
    /*
    private static final String API_PREFIX = "/admin/ga4gh/passport/v1/users";

    private static final String RESPONSE_DIR = "/responses/users/";

    private static final String PAYLOAD_DIR = "/payloads/users/";

    @Autowired
    private PassportBrokerHibernateUtil hibernateUtil;

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @BeforeMethod
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @DataProvider(name = "indexUsersCases")
    public Object[][] indexUsersCases() {
        return new Object[][] {
            {
                status().isOk(),
                true,
                "00.json",
                null
            }
        };
    }

    @DataProvider(name = "showUserCases")
    public Object[][] showUserCases() {
        return new Object[][] {
            {
                "85ff5a54-48b9-4294-a91d-2be50bd2a77d",
                status().isOk(),
                true,
                "00.json",
                null
            },
            {
                "00000000-0000-0000-0000-000000000000",
                status().isNotFound(),
                false,
                null,
                "No PassportUser exists at id 00000000-0000-0000-0000-000000000000"
            }
        };
    }

    @DataProvider(name = "createUserCases")
    public Object[][] createUserCases() {
        return new Object[][] {
            {
                "00.json",
                status().isOk(),
                true,
                "00.json",
                null
            },
            {
                "00.json",
                status().isConflict(),
                false,
                null,
                "A(n) PassportUser already exists at id aaec9d73-d7d6-490e-a49f-98ac02db1cca"
            }
        };
    }

    @DataProvider(name = "updateUserCases")
    public Object[][] updateUserCases() {
        return new Object[][] {
            {
                "aaec9d73-d7d6-490e-a49f-98ac02db1cca",
                "00.json",
                status().isOk(),
                true,
                "00.json",
                null
            },
            {
                "85ff5a54-48b9-4294-a91d-2be50bd2a77d",
                "00.json",
                status().isBadRequest(),
                false,
                null,
                "Update requested at id 85ff5a54-48b9-4294-a91d-2be50bd2a77d, but new PassportUser has an id of aaec9d73-d7d6-490e-a49f-98ac02db1cca"
            },
            {
                "00000000-0000-0000-0000-000000000000",
                "01.json",
                status().isConflict(),
                false,
                null,
                "No PassportUser at id 00000000-0000-0000-0000-000000000000"
            }
        };
    }

    @DataProvider(name = "deleteUserCases")
    public Object[][] deleteUserCases() {
        return new Object[][] {
            {
                "aaec9d73-d7d6-490e-a49f-98ac02db1cca",
                status().isOk(),
                true,
                null
            },
            {
                "aaec9d73-d7d6-490e-a49f-98ac02db1cca",
                status().isConflict(),
                false,
                "No PassportUser at id aaec9d73-d7d6-490e-a49f-98ac02db1cca"
            }
        };
    }

    private void createTestEntities() throws Exception {
        String[] payloadFiles = {
            "/payloads/users/create/00.json"
        };
        for (String payloadFile: payloadFiles) {
            String payloadBody = ResourceLoader.load(payloadFile);
            ObjectMapper objectMapper = new ObjectMapper();
            PassportUser passportUser = objectMapper.readValue(payloadBody, PassportUser.class);
            hibernateUtil.createEntityObject(PassportUser.class, passportUser);
        }
    }

    private void deleteTestEntities() throws Exception {
        hibernateUtil.deleteEntityObject(PassportUser.class, "aaec9d73-d7d6-490e-a49f-98ac02db1cca");
    }

    @AfterGroups("createUser")
    public void cleanupCreate() throws Exception {
        deleteTestEntities();
    }

    @BeforeGroups("updateUser")
    public void setupUpdate() throws Exception {
        createTestEntities();
    }

    @AfterGroups("updateUser")
    public void cleanupUpdate() throws Exception {
        deleteTestEntities();
    }

    @BeforeGroups("deleteUser")
    public void setupDelete() throws Exception {
        createTestEntities();
    }

    private void genericAdminApiRequestTest(MvcResult result, String responseBody, boolean expSuccess, String expSubdir, String expFilename, String expMessage) throws Exception {
        if (expSuccess) {
            String expFile = RESPONSE_DIR + expSubdir + "/" + expFilename;
            String expResponseBody = ResourceLoader.load(expFile);
            Assert.assertEquals(responseBody, expResponseBody);
        } else {
            String message = result.getResolvedException().getMessage();
            Assert.assertEquals(message, expMessage);
        }
    }

    @Test(dataProvider = "indexUsersCases", groups = "indexUser")
    public void testGetPassportUsers(ResultMatcher expStatus, boolean expSuccess, String expFilename, String expMessage) throws Exception {
        MvcResult result = mockMvc.perform(get(API_PREFIX))
            .andExpect(expStatus)
            .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        genericAdminApiRequestTest(result, responseBody, expSuccess, "index", expFilename, expMessage);
    }

    @Test(dataProvider = "showUserCases", groups = "showUser")
    public void testShowPassportUser(String id, ResultMatcher expStatus, boolean expSuccess, String expFilename, String expMessage) throws Exception {
        MvcResult result = mockMvc.perform(get(API_PREFIX + "/" + id))
            .andExpect(expStatus)
            .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        genericAdminApiRequestTest(result, responseBody, expSuccess, "show", expFilename, expMessage);
    }

    @Test(dataProvider = "createUserCases", groups = "createUser")
    public void testCreatePassportUser(String payloadFilename, ResultMatcher expStatus, boolean expSuccess, String expFilename, String expMessage) throws Exception {
        String expSubdir = "create";
        String payloadFile = PAYLOAD_DIR + expSubdir + "/" + payloadFilename;
        String payloadBody = ResourceLoader.load(payloadFile);

        MvcResult result = mockMvc.perform(
            post(API_PREFIX)
            .content(payloadBody)
            .header("Content-Type", "application/json")
        )
            .andExpect(expStatus)
            .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        genericAdminApiRequestTest(result, responseBody, expSuccess, expSubdir, expFilename, expMessage);
    }


    @Test(dataProvider = "updateUserCases", groups = "updateUser", enabled = false)
    public void testUpdatePassportUser(String id, String payloadFilename, ResultMatcher expStatus, boolean expSuccess, String expFilename, String expMessage) throws Exception {
        String expSubdir = "update";
        String payloadFile = PAYLOAD_DIR + expSubdir + "/" + payloadFilename;
        String payloadBody = ResourceLoader.load(payloadFile);

        MvcResult result = mockMvc.perform(
            put(API_PREFIX + "/" + id)
            .content(payloadBody)
            .header("Content-Type", "application/json")
        )
            .andExpect(expStatus)
            .andReturn();
        
        // assertion timestamp changes with time. We make sure that "assertedAt"
        // is not null, then modify it to a constant so the overall JSON can be
        // compared against expected
        String responseBody = result.getResponse().getContentAsString();
        if (expSuccess) {
            ObjectMapper mapper = new ObjectMapper();
            PassportUser user = mapper.readValue(responseBody, PassportUser.class);
            for (PassportVisaAssertion assertion : user.getPassportVisaAssertions()) {
                Assert.assertNotNull(assertion.getAssertedAt());
                assertion.setAssertedAt(Long.valueOf(1652187600));
            }
            responseBody = mapper.writeValueAsString(user);
        }
        
        genericAdminApiRequestTest(result, responseBody, expSuccess, expSubdir, expFilename, expMessage);
    }

    @Test(dataProvider = "deleteUserCases", groups = "deleteUser")
    public void testDeletePassportUser(String id, ResultMatcher expStatus, boolean expSuccess, String expMessage) throws Exception {
        MvcResult result = mockMvc.perform(
            delete(API_PREFIX + "/" + id)
        )
            .andExpect(expStatus)
            .andReturn();
        if (!expSuccess) {
            String message = result.getResolvedException().getMessage();
            Assert.assertEquals(message, expMessage);
        }
    }
    */
}
