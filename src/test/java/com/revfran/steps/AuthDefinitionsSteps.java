package com.revfran.steps;

import com.revfran.common.SerenityVariables;
import io.cucumber.core.exception.CucumberException;
import net.serenitybdd.core.Serenity;
import org.junit.Assert;


import static io.restassured.RestAssured.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Optional;

public class AuthDefinitionsSteps {
    /**
     * Checks if token is defined, and places it in a serenity session variable
     *
     * @throws CucumberException if token is not defined
     */
    public void checkAndSetToken() throws CucumberException {
        Optional<String> token = Optional.ofNullable(System.getProperty(SerenityVariables.TWITTER_TOKEN_ENV_NAME));
        if (!token.isPresent()) {
            throw new CucumberException(String.format("System property '%s' needs to be configured for the tests", SerenityVariables.TWITTER_TOKEN_ENV_NAME));
        }
        Serenity.setSessionVariable(SerenityVariables.TWITTER_TOKEN_SESSION_VAR_NAME).to(token.get());
    }

    public void assertStatusCode(int expectedStatusCode) {
        int actualStatusCode = Serenity.sessionVariableCalled(SerenityVariables.RESPONSE_CODE_SESSION_VAR_NAME);
        assertThat("Status code mismatch", actualStatusCode, is(expectedStatusCode));
    }
}
