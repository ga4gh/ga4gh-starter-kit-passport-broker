package docker.controller;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testutils.HttpMethod;
import testutils.SimpleHttpRequestTester;

public class DockerVisasTest {

    private static final String BASE_URL = "http://localhost:4501/admin/ga4gh/passport/v1/visas";
    private static final String PAYLOAD_DIR = "/payloads/visas/";
    private static final String RESPONSE_DIR = "/responses/visas/";

    @DataProvider(name = "showVisa")
    public Object[][] showVisaCases() {
        return new Object[][] {
            {
                "670cc2e7-9a9c-4273-9334-beb40d364e5c",
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

    @DataProvider(name = "createVisa")
    public Object[][] createVisaCases() {
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

    @DataProvider(name = "updateVisa")
    public Object[][] updateVisaCases() {
        return new Object[][] {
            {
                "407e4891-2463-4f6f-b428-fe0f1850cf0c",
                "00.json",
                200,
                true,
                "00.json",
            },
            {
                "670cc2e7-9a9c-4273-9334-beb40d364e5c",
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

    @DataProvider(name = "deleteVisa")
    public Object[][] deleteVisaCases() {
        return new Object[][] {
            {
                "407e4891-2463-4f6f-b428-fe0f1850cf0c",
                200,
                true
            },
            {
                "407e4891-2463-4f6f-b428-fe0f1850cf0c",
                409,
                false
            }
        };
    }

    @AfterGroups("createVisa")
    public void cleanupCreate() throws Exception {
        SimpleHttpRequestTester.makeHttpRequest(
            HttpMethod.DELETE,
            BASE_URL + "/407e4891-2463-4f6f-b428-fe0f1850cf0c",
            null
        );
    }

    @BeforeGroups("updateVisa")
    public void setupUpdate() throws Exception {
        SimpleHttpRequestTester.makeHttpRequest(
            HttpMethod.POST,
            BASE_URL,
            PAYLOAD_DIR + "/create/00.json"
        );
    }

    @AfterGroups("updateVisa")
    public void cleanupUpdate() throws Exception {
        SimpleHttpRequestTester.makeHttpRequest(
            HttpMethod.DELETE,
            BASE_URL + "/407e4891-2463-4f6f-b428-fe0f1850cf0c",
            null
        );
    }

    @BeforeGroups("deleteVisa")
    public void setupDelete() throws Exception {
        SimpleHttpRequestTester.makeHttpRequest(
            HttpMethod.POST,
            BASE_URL,
            PAYLOAD_DIR + "/create/00.json"
        );
    }

    @Test(groups = "indexVisa")
    public void testGetPassportVisas() throws Exception {
        SimpleHttpRequestTester.getRequestAndTest(
            BASE_URL,
            200,
            true,
            RESPONSE_DIR + "/index/00.json"
        );
    }

    @Test(dataProvider = "showVisa", groups = "showVisa")
    public void testShowPassportVisa(String id, int expStatus, boolean expSuccess, String expFilename) throws Exception {
        SimpleHttpRequestTester.getRequestAndTest(
            BASE_URL + "/" + id,
            expStatus,
            expSuccess,
            RESPONSE_DIR + "/show/" + expFilename
        );
    }

    @Test(dataProvider = "createVisa", groups = "createVisa")
    public void testCreatePassportVisa(String payloadFilename, int expStatus, boolean expSuccess, String expFilename) throws Exception {
        SimpleHttpRequestTester.postRequestAndTest(
            BASE_URL,
            PAYLOAD_DIR + "/create/" + payloadFilename,
            expStatus,
            expSuccess,
            RESPONSE_DIR + "/create/" + expFilename
        );
    }

    @Test(dataProvider = "updateVisa", groups = "updateVisa", enabled = false)
    public void testUpdatePassportVisa(String id, String payloadFilename, int expStatus, boolean expSuccess, String expFilename) throws Exception {
        SimpleHttpRequestTester.putRequestAndTest(
            BASE_URL + "/" + id,
            PAYLOAD_DIR + "/update/" + payloadFilename,
            expStatus,
            expSuccess,
            RESPONSE_DIR + "/update/" + expFilename
        );
    }

    @Test(dataProvider = "deleteVisa", groups = "deleteVisa")
    public void testDeletePassportVisa(String id, int expStatus, boolean expSuccess) throws Exception {
        SimpleHttpRequestTester.deleteRequestAndTest(
            BASE_URL + "/" + id,
            expStatus,
            expSuccess,
            RESPONSE_DIR + "/delete/" + "empty.json"
        );
    }
}