package com.revfran.steps;

import com.revfran.common.SerenityVariables;
import io.cucumber.core.exception.CucumberException;
import net.serenitybdd.core.Serenity;

import java.util.Optional;

public class TokenSteps {

    private static final String CONFIG_TOKEN_GHERKIN_NAME = "CONFIGURED_TOKEN";
    private static final String CONFIG_ACCESS_TOKEN_GHERKIN_NAME = "CONFIGURED_ACCESS_TOKEN";

    /**
     * Checks if token is defined, and places it in a serenity session variable
     *
     * @throws CucumberException if token is not defined
     */
    public void checkAndSetToken() throws CucumberException {
        Optional<String> token = Optional.ofNullable(System.getProperty(SerenityVariables.TWITTER_BEARER_TOKEN_ENV_NAME));
        if (!token.isPresent()) {
            throw new CucumberException(String.format("System property '%s' needs to be configured for the tests", SerenityVariables.TWITTER_BEARER_TOKEN_ENV_NAME));
        }

        Optional<String> access_token = Optional.ofNullable(System.getProperty(SerenityVariables.TWITTER_ACCESS_TOKEN_ENV_NAME));
        if (!access_token.isPresent()) {
            throw new CucumberException(String.format("System property '%s' needs to be configured for the tests", SerenityVariables.TWITTER_ACCESS_TOKEN_ENV_NAME));
        }

        Serenity.setSessionVariable(SerenityVariables.TWITTER_BEARER_TOKEN_SESSION_VAR_NAME).to(token.get());
        Serenity.setSessionVariable(SerenityVariables.TWITTER_ACCESS_TOKEN_SESSION_VAR_NAME).to(access_token.get());
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
            return Serenity.sessionVariableCalled(SerenityVariables.TWITTER_BEARER_TOKEN_SESSION_VAR_NAME);
        }else if(CONFIG_ACCESS_TOKEN_GHERKIN_NAME.equalsIgnoreCase(gherkinToken)){
            return Serenity.sessionVariableCalled(SerenityVariables.TWITTER_ACCESS_TOKEN_SESSION_VAR_NAME);
        }
        else{ return gherkinToken;}
    }
}
