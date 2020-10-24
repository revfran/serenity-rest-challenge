@functional @twitter_auth
Feature: oauth2 requests to twitter API

  Background:
    Given a configured token

  @twitter_auth:001 @smoke
  Scenario Outline: Authentication is successful
    When a "<ApiVersion>" request to get tweet "<TweetID>" is done with token "<Token>"
    Then status code is <OkStatusCode>
    And response body contains param "<ResponseParam>" with value "<TweetID>"

    Examples:
      | ApiVersion | TweetID             | Token            | OkStatusCode | ResponseParam |
      | v1.1       | 1319902909635715072 | CONFIGURED_TOKEN | 200          | id            |
      | v2         | 1319902909635715072 | CONFIGURED_TOKEN | 200          | data.id       |

  @twitter_auth:002 @smoke
  Scenario Outline: Authentication is not successful with invalid token
    When a "<ApiVersion>" request to get tweet "<TweetID>" is done with token "<Token>"
    Then status code is <UnauthorisedStatusCode>
    And response body contains param "<ResponseParam>" with value "<UnauthorisedErrorTitle>"

    Examples:
      | ApiVersion | TweetID             | Token         | UnauthorisedStatusCode | ResponseParam     | UnauthorisedErrorTitle    |
      | v1.1       | 1319902909635715072 | Invalid_token | 401                    | errors[0].message | Invalid or expired token. |
      | v2         | 1319902909635715072 | Invalid_token | 401                    | title             | Unauthorized              |

  @twitter_auth:003
  Scenario Outline: Authentication is not successful with wrong input
    When a "<ApiVersion>" request to get tweet "<TweetID>" is done with token "<Token>"
    Then status code is <BadRequestStatusCode>
    And response body contains param "<ResponseParam>" with value "<BadRequestErrorTitle>"

    Examples:
      | ApiVersion | TweetID          | Token            | BadRequestStatusCode | ResponseParam     | BadRequestErrorTitle                |
      | v1.1       | Invalid_tweet_id | CONFIGURED_TOKEN | 404                  | errors[0].message | No data available for specified ID. |
      | v2         | Invalid_tweet_id | CONFIGURED_TOKEN | 400                  | title             | Invalid Request                     |

  @twitter_auth:004
  Scenario Outline: Unauthorised in session endpoints
    When a "<ApiVersion>" request to get home timeline is done with token "<Token>"
    Then status code is <ForbiddenStatusCode>
    And response body contains param "<ResponseParam>" with value "<ForbiddenErrorMessage>"

  @Pending @twitter_auth:005 @bonus_invalidate_token
  Scenario: Refresh bearer token invalidates previous token
    When something

  @Pending @twitter_auth:006 @bonus_invalidate_token
  Scenario: Refresh bearer token is not authorised with wrong credentials
    When something
