package com.revfran.steps;

import com.revfran.common.SerenityVariables;
import io.cucumber.core.exception.CucumberException;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class AuthDefinitionsSteps {


    /**
     * Do a request to https://api.twitter.com/2/tweets/{tweetId}
     *
     * @param token   Bearer token to use
     * @param tweetId TweetId for the url
     */
    public void retrieveTweetInformation(String token, String tweetId) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(tweetId)) {
            throw new CucumberException("Invalid required token or tweetId");
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", String.format("Bearer %s", token));

        SerenityRest.given().contentType("application/json")
                .headers(headers)
                .when()
                .get("https://api.twitter.com/2/tweets/" + tweetId);

        Response response = SerenityRest.then().extract().response();
        Serenity.setSessionVariable(SerenityVariables.RESPONSE_CODE_SESSION_VAR_NAME).to(response.statusCode());
        Serenity.setSessionVariable(SerenityVariables.RESPONSE_BODY_SESSION_VAR_NAME).to(response.body().print());
    }
}
