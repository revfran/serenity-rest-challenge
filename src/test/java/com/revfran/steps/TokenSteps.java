package com.revfran.steps;

import com.revfran.common.SerenityVariables;
import io.cucumber.core.exception.CucumberException;
import net.serenitybdd.core.Serenity;

import java.util.Optional;

public class TokenSteps {

    private static final String CONFIG_TOKEN_GHERKIN_NAME = "CONFIGURED_TOKEN";

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


    /**
     * Returns a token translated from Gherkin syntax.
     *
     * @param gherkinToken token from Gherkin table
     * @return gherkinToken, except when value is {@value #CONFIG_TOKEN_GHERKIN_NAME}, then it returns configured variable.
     */
    public String resolveTokenFromGherkin(String gherkinToken) {
        String result;
        if (CONFIG_TOKEN_GHERKIN_NAME.equalsIgnoreCase(gherkinToken)) {
            return Serenity.sessionVariableCalled(SerenityVariables.TWITTER_TOKEN_SESSION_VAR_NAME);
        } else return gherkinToken;
    }
}
