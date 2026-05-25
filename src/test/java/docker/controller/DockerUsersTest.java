package docker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ga4gh.starterkit.passport.broker.model.PassportUser;
import org.ga4gh.starterkit.passport.broker.model.PassportVisaAssertion;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testutils.HttpMethod;
import testutils.ResourceLoader;
import testutils.SimpleHttpRequestTester;

import java.net.http.HttpResponse;

public class DockerUsersTest {
    /*
    private static final String BASE_URL = "http://localhost:4501/admin/ga4gh/passport/v1/users";
    private static final String PAYLOAD_DIR = "/payloads/users/";
    private static final String RESPONSE_DIR = "/responses/users/";

    @DataProvider(name = "showUser")
    public Object[][] showUserCases() {
        return new Object[][] {
            {
                "85ff5a54-48b9-4294-a91d-2be50bd2a77d",
                200,
                true,
                "00.json",
            },
            {
                "00000000-0000-0000-0000-000000000000",
                404,
                false,
                null
            }
        };
    }

    @DataProvider(name = "createUser")
    public Object[][] createUserCases() {
        return new Object[][] {
            {
                "00.json",
                200,
                true,
                "00.json"
            },
            {
                "00.json",
                409,
                false,
                null
            }
        };
    }

    @DataProvider(name = "updateUser")
    public Object[][] updateUserCases() {
        return new Object[][] {
            {
                "aaec9d73-d7d6-490e-a49f-98ac02db1cca",
                "00.json",
                200,
                true,
                "00.json",
            },
            {
                "85ff5a54-48b9-4294-a91d-2be50bd2a77d",
                "00.json",
                400,
                false,
                null
            },
            {
                "00000000-0000-0000-0000-000000000000",
                "01.json",
                409,
                false,
                null
            }
        };
    }

    @DataProvider(name = "deleteUser")
    public Object[][] deleteUserCases() {
        return new Object[][] {
            {
                "aaec9d73-d7d6-490e-a49f-98ac02db1cca",
                200,
                true
            },
            {
                "aaec9d73-d7d6-490e-a49f-98ac02db1cca",
                409,
                false
            }
        };
    }

    @AfterGroups("createUser")
    public void cleanupCreate() throws Exception {
        SimpleHttpRequestTester.makeHttpRequest(
            HttpMethod.DELETE,
            BASE_URL + "/aaec9d73-d7d6-490e-a49f-98ac02db1cca",
            null
        );
    }

    @BeforeGroups("updateUser")
    public void setupUpdate() throws Exception {
        SimpleHttpRequestTester.makeHttpRequest(
            HttpMethod.POST,
            BASE_URL,
            PAYLOAD_DIR + "/create/00.json"
        );
    }

    @AfterGroups("updateUser")
    public void cleanupUpdate() throws Exception {
        SimpleHttpRequestTester.makeHttpRequest(
            HttpMethod.DELETE,
            BASE_URL + "/aaec9d73-d7d6-490e-a49f-98ac02db1cca",
            null
        );
    }

    @BeforeGroups("deleteUser")
    public void setupDelete() throws Exception {
        SimpleHttpRequestTester.makeHttpRequest(
            HttpMethod.POST,
            BASE_URL,
            PAYLOAD_DIR + "/create/00.json"
        );
    }

    @Test(groups = "indexUser")
    public void testGetPassportUsers() throws Exception {
        SimpleHttpRequestTester.getRequestAndTest(
            BASE_URL,
            200,
            true,
            RESPONSE_DIR + "/index/00.json"
        );
    }

    @Test(dataProvider = "showUser", groups = "showUser")
    public void testShowPassportUser(String id, int expStatus, boolean expSuccess, String expFilename) throws Exception {
        SimpleHttpRequestTester.getRequestAndTest(
            BASE_URL + "/" + id,
            expStatus,
            expSuccess,
            RESPONSE_DIR + "/show/" + expFilename
        );
    }

    @Test(dataProvider = "createUser", groups = "createUser")
    public void testCreatePassportUser(String payloadFilename, int expStatus, boolean expSuccess, String expFilename) throws Exception {
        SimpleHttpRequestTester.postRequestAndTest(
            BASE_URL,
            PAYLOAD_DIR + "/create/" + payloadFilename,
            expStatus,
            expSuccess,
            RESPONSE_DIR + "/create/" + expFilename
        );
    }

    @Test(dataProvider = "updateUser", groups = "updateUser", enabled = false)
    public void testUpdatePassportUser(String id, String payloadFilename, int expStatus, boolean expSuccess, String expFilename) throws Exception {
        HttpResponse<String> response = SimpleHttpRequestTester.makeHttpRequest(
            HttpMethod.PUT,
            BASE_URL + "/" + id,
            PAYLOAD_DIR + "/update/" + payloadFilename
        );

        Assert.assertEquals(response.statusCode(), expStatus);
        if (expSuccess) {
            String responseBody = response.body();
            // modify response body to use hardcoded timestamps
            ObjectMapper mapper = new ObjectMapper();
            PassportUser user = mapper.readValue(responseBody, PassportUser.class);
            for (PassportVisaAssertion assertion : user.getPassportVisaAssertions()) {
                Assert.assertNotNull(assertion.getAssertedAt());
                assertion.setAssertedAt(Long.valueOf(1652187600));
            }
            responseBody = mapper.writeValueAsString(user);
            
            String expResponseBody = ResourceLoader.load(RESPONSE_DIR + "/update/" + expFilename);
            Assert.assertEquals(responseBody, expResponseBody);
        }
    }

    @Test(dataProvider = "deleteUser", groups = "deleteUser")
    public void testDeletePassportUser(String id, int expStatus, boolean expSuccess) throws Exception {
        SimpleHttpRequestTester.deleteRequestAndTest(
            BASE_URL + "/" + id,
            expStatus,
            expSuccess,
            RESPONSE_DIR + "/delete/" + "empty.json"
        );
    }
    */
}