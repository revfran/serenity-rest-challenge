package com.revfran.steps;

import com.revfran.common.SerenityVariables;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.core.Serenity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AssertionSteps {

    public void assertStatusCode(int expectedStatusCode) {
        int actualStatusCode = Serenity.sessionVariableCalled(SerenityVariables.RESPONSE_CODE_SESSION_VAR_NAME);
        assertThat("Status code mismatch", actualStatusCode, is(expectedStatusCode));
    }

    public void assertStringParamInBody(String param, String expectedValue) {
        String body = Serenity.sessionVariableCalled(SerenityVariables.RESPONSE_BODY_SESSION_VAR_NAME);

        JsonPath jsonBody = JsonPath.from(body);
        assertThat(String.format("Param '%s' mismatch", param), jsonBody.getString(param), is(expectedValue));
    }
}
