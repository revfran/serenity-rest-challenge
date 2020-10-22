@functional @twitter_auth
Feature: oauth2 requests to twitter API

  Background:
    Given a configured token

  @Pending @twitter_auth:001 @smoke
  Scenario: Authentication is successful
    When I do a request
    Then I should obtain some result

  @Pending @twitter_auth:002
  Scenario: Authentication is not successful with wrong credentials

  @Pending @twitter_auth:003
  Scenario: Authentication is not successful with wrong input

  @Pending @twitter_auth:004
  Scenario: Unauthorised in session endpoints

    @Pending @twitter_auth:005 @bonus_invalidate_token
      Scenario: Refresh bearer token invalidates previous token

  @Pending @twitter_auth:006 @bonus_invalidate_token
  Scenario: Refresh bearer token is not authorised with wrong credentials
