package com.revfran.definitions;

import com.revfran.common.SerenityVariables;
import com.revfran.steps.AuthDefinitionsSteps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.NotImplementedException;
import net.thucydides.core.annotations.Steps;

public class AuthDefinitions {

    @Steps
    AuthDefinitionsSteps authDefinitionsSteps;

    @Given("a configured token")
    public void aConfiguredToken() {
        authDefinitionsSteps.checkAndSetToken();
    }

    @When("a GET request is done with valid token")
    public void aGETRequestIsDoneWithValidToken() {
        Serenity.setSessionVariable(SerenityVariables.RESPONSE_CODE_SESSION_VAR_NAME).to(100);
    }

    @Then("status code is {int}")
    public void statusCodeIs(int statusCode) {
        authDefinitionsSteps.assertStatusCode(statusCode);
    }
}
