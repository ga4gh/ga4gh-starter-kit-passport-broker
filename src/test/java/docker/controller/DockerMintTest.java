package docker.controller;

import java.net.http.HttpResponse;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import testutils.HttpMethod;
import testutils.SimpleHttpRequestTester;
import testutils.TestJwtVerifier;

public class DockerMintTest {
    /*
    private static final String URL = "http://localhost:4501/admin/ga4gh/passport/v1/mint";
    private static final String PAYLOAD_DIR = "/payloads/mint/";

    @DataProvider(name = "mintCases")
    public Object[][] mintCases() {
        return new Object[][] {
            { "success-00", 200, true },
            { "invalid-user", 400, false },
            { "invalid-visa-request", 400, false }
        };
    }
    
    @Test(dataProvider = "mintCases")
    public void testMintPassport(String payloadFileKey, int expStatus, boolean expSuccess) throws Exception {
        HttpResponse<String> response = SimpleHttpRequestTester.makeHttpRequest(
            HttpMethod.POST,
            URL,
            PAYLOAD_DIR + payloadFileKey + ".json"
        );

        Assert.assertEquals(response.statusCode(), expStatus);
        if (expSuccess) {
            String rawPassportJwt = response.body();
            TestJwtVerifier.verify(rawPassportJwt);
        }
    }
    */
}
