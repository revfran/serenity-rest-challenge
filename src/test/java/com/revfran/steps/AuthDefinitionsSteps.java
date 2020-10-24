package com.revfran.steps;

import com.revfran.common.SerenityVariables;
import io.cucumber.core.exception.CucumberException;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AuthDefinitionsSteps {

    private final String API_V1_VERSION = "v1.1";
    private final String API_V2_VERSION = "v2";

    public enum ApiVersion {
        API_V1_VERSION, API_V2_VERSION
    }

    /**
     * Do a request to get a tweet by ID
     *
     * @param token   Bearer token to use
     * @param tweetId TweetId for the url
     */
    public void retrieveTweetInformation(String gherkinApiVersion, String token, String tweetId) {
        if (StringUtils.isEmpty(gherkinApiVersion) || StringUtils.isEmpty(token) || StringUtils.isEmpty(tweetId)) {
            throw new CucumberException("Invalid required gherkinApiVersion, token or tweetId");
        }

        ApiVersion apiVersion = resolveAPIversionFromGherkin(gherkinApiVersion);

        String tweetIdUrl;
        switch (apiVersion) {
            case API_V1_VERSION:
                tweetIdUrl = "https://api.twitter.com/1.1/statuses/show.json?id=" + tweetId;
                break;
            case API_V2_VERSION:
            default:
                tweetIdUrl = "https://api.twitter.com/2/tweets/" + tweetId;
                break;
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", String.format("Bearer %s", token));

        Response response = SerenityRest.given().contentType("application/json")
                .headers(headers)
                .when()
                .get(tweetIdUrl);

        Serenity.setSessionVariable(SerenityVariables.RESPONSE_CODE_SESSION_VAR_NAME).to(response.statusCode());
        Serenity.setSessionVariable(SerenityVariables.RESPONSE_BODY_SESSION_VAR_NAME).to(response.body().print());
    }

    /**
     * Do a request to get home_timeline information
     *
     * @param gherkinApiVersion
     * @param token
     */
    public void retrieveHomeTimeline(String gherkinApiVersion, String token) {
        if (StringUtils.isEmpty(gherkinApiVersion) || StringUtils.isEmpty(token)) {
            throw new CucumberException("Invalid required gherkinApiVersion or token");
        }

        ApiVersion apiVersion = resolveAPIversionFromGherkin(gherkinApiVersion);

        String homeTimelineUrl;
        switch (apiVersion) {
            case API_V1_VERSION:
            default:
                homeTimelineUrl = "https://api.twitter.com/1.1/statuses/home_timeline.json";
                break;
            case API_V2_VERSION:
                // No endpoints for v2 without app auth as it is still in public beta
                throw new CucumberException("No URL configured for v2 endpoint to retrieve home_timeline");
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", String.format("Bearer %s", token));

        Response response = SerenityRest.given().contentType("application/json")
                .headers(headers)
                .when()
                .get(homeTimelineUrl);

        Serenity.setSessionVariable(SerenityVariables.RESPONSE_CODE_SESSION_VAR_NAME).to(response.statusCode());
        Serenity.setSessionVariable(SerenityVariables.RESPONSE_BODY_SESSION_VAR_NAME).to(response.body().print());
    }

    public void requestInvalidateToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new CucumberException("Invalid required gherkinApiVersion or token");
        }

        Optional<Boolean> invalidationRequestDone = Optional.ofNullable(Serenity.sessionVariableCalled(SerenityVariables.TOKEN_INVALIDATION_STATUS_SESSION_VAR_NAME));

        /*
        if (invalidationRequestDone.isPresent() && invalidationRequestDone.get().booleanValue()){
            return;
        }*/


        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", String.format("Basic BASIC_AUTH_VALUE"));

        String invalidateTokenURL = "https://api.twitter.com/oauth2/invalidate_token";
        Response response = SerenityRest.given().contentType("application/x-www-form-urlencoded")
                .headers(headers)
                .body("access_token=ACCESS_TOKEN_VALUE")
                .when()
                .post(invalidateTokenURL);

        Serenity.setSessionVariable(SerenityVariables.RESPONSE_CODE_SESSION_VAR_NAME).to(response.statusCode());
        Serenity.setSessionVariable(SerenityVariables.RESPONSE_BODY_SESSION_VAR_NAME).to(response.body().print());

        // Set SerenitySessionVariable for next user
        /*
        if (response.statusCode() == HttpStatus.OK_200)
        {
            Serenity.setSessionVariable(SerenityVariables.TOKEN_INVALIDATION_STATUS_SESSION_VAR_NAME).to(Optional.ofNullable(true));
        }*/
    }

    private ApiVersion resolveAPIversionFromGherkin(String version) {
        switch (version) {
            case API_V1_VERSION:
                return ApiVersion.API_V1_VERSION;
            case API_V2_VERSION:
                return ApiVersion.API_V2_VERSION;
            default:
                throw new CucumberException(String.format("Unknown API version: '%s'", version));
        }
    }
}
