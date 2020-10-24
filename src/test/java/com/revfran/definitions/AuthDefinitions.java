package com.revfran.definitions;

import com.revfran.steps.AssertionSteps;
import com.revfran.steps.AuthDefinitionsSteps;
import com.revfran.steps.TokenSteps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

public class AuthDefinitions {

    @Steps
    TokenSteps tokenSteps;

    @Steps
    AuthDefinitionsSteps authDefinitionsSteps;

    @Steps
    AssertionSteps assertionSteps;

    @Given("a configured token")
    public void aConfiguredToken() {
        this.tokenSteps.checkAndSetToken();
    }

    @When("a {string} request to get tweet {string} is done with token {string}")
    public void aGETRequestToGetTweetIsDoneWithToken(String gherkinApiVersion, String tweetID, String gherkinToken) {
        String token = this.tokenSteps.resolveTokenFromGherkin(gherkinToken);
        this.authDefinitionsSteps.retrieveTweetInformation(gherkinApiVersion, token, tweetID);
    }

    @When("a {string} request to get home timeline is done with token {string}")
    public void aRequestToGetHomeTimelineIsDoneWithToken(String gherkinApiVersion, String gherkinToken) {
        String token = this.tokenSteps.resolveTokenFromGherkin(gherkinToken);
        this.authDefinitionsSteps.retrieveHomeTimeline(gherkinApiVersion, token);
    }

    @When("a request to invalidate token is done with token {string}")
    public void aRequestToInvalidateTokenIsDone(String gherkinToken) {
        String token = this.tokenSteps.resolveTokenFromGherkin(gherkinToken);
        this.authDefinitionsSteps.requestInvalidateToken(token);
    }

    @Then("status code is {int}")
    public void statusCodeIs(int statusCode) {
        this.assertionSteps.assertStatusCode(statusCode);
    }

    @Then("response body contains param {string} with value {string}")
    public void responseBodyContainsParamWithValue(String param, String expectedValue) {
        this.assertionSteps.assertStringParamInBody(param, expectedValue);
    }
}
