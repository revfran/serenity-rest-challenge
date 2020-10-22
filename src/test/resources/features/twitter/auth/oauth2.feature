@functional @twitter_auth
Feature: oauth2 requests to twitter API

  Background:
    Given a configured token

  @twitter_auth:001 @smoke
  Scenario Outline: Authentication is successful
    When a GET request is done with valid token
    Then status code is <OkStatusCode>

    Examples:
      | OkStatusCode |
      | 200          |

  @Pending @twitter_auth:002
  Scenario: Authentication is not successful with wrong credentials
    When something

  @Pending @twitter_auth:003
  Scenario: Authentication is not successful with wrong input
    When something

  @Pending @twitter_auth:004
  Scenario: Unauthorised in session endpoints
    When something

  @Pending @twitter_auth:005 @bonus_invalidate_token
  Scenario: Refresh bearer token invalidates previous token
    When something

  @Pending @twitter_auth:006 @bonus_invalidate_token
  Scenario: Refresh bearer token is not authorised with wrong credentials
    When something
