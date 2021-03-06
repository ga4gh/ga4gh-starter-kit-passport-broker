package org.ga4gh.starterkit.passport.broker.model;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class PassportVisaTest {

    @DataProvider(name = "cases")
    public Object[][] getData() {
        return new Object[][] {
            {
                "670cc2e7-9a9c-4273-9334-beb40d364e5c",
                "StarterKitDatasetsControlledAccessGrants",
                "https://datasets.starterkit.ga4gh.org/",
                "Controlled access dev datasets for the GA4GH Starter Kit",
                new ArrayList<PassportVisaAssertion>() {{
                    add(new PassportVisaAssertion(0L, "active"));
                }}
            }
        };
    }

    @Test(dataProvider = "cases")
    public void testPassportVisaNoArgsConstructor(
        String id, String visaName, String visaIssuer, String visaDescription, List<PassportVisaAssertion> passportVisaAssertions
    ) {
        PassportVisa visa = new PassportVisa();
        visa.setId(id);
        visa.setVisaName(visaName);
        visa.setVisaIssuer(visaIssuer);
        visa.setVisaDescription(visaDescription);
        visa.setPassportVisaAssertions(passportVisaAssertions);

        Assert.assertEquals(visa.getId(), id);
        Assert.assertEquals(visa.getVisaIssuer(), visaIssuer);
        Assert.assertEquals(visa.getVisaDescription(), visaDescription);
        Assert.assertEquals(visa.getVisaName(), visaName);
        Assert.assertEquals(visa.getPassportVisaAssertions(), passportVisaAssertions);
    }
}
