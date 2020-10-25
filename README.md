# serenity-rest-challenge
A simple challenge to run tests against an API

# Requisites
- An twitter application following https://developer.twitter.com to have an associated TWITTER_API_TOKEN
- Maven with java 8

# How to execute

This command will execute tests and create serenity reports

```
$ mvn -DTW_TOKEN=${TWITTER_API_TOKEN} test serenity:aggregate
```

Reports will be available after execution in [./target/site/serenity/index.html]

Sample reports are available at [index.html](./sampleExecution/index.html)

# Run specific tests

Use tags parameter. For instance, to run tests tagged with smoke tag:

```
$ mvn -DTW_TOKEN=${TWITTER_API_TOKEN} -Dtags=@smoke test serenity:aggregate
```

# Description of the building process

- Start from maven-quick-start archetype
- Add dependencies according to [serenity-cucumber-starter](https://github.com/serenity-bdd/serenity-cucumber-starter/blob/master/pom.xml)
- Build skeleton, first test and documentation to run the project
- Changed dependencies from failsafe to surefire (as this is not a project including code or unit tests, maven 'test' stage makes more sense)
- Built tests for v2
- Added examples for v1
- Explore invalidation in separate branch

# Testing ideas

- Auth should be checked in every endpoint exposed. However, for the sake of brevity, only one of the endpoints was tested.
- To know which endpoint may be relevant I went through the use cases and found this as useful, where you get data from [a single tweet](https://developer.twitter.com/en/docs/tutorials/measure-tweet-performance)
- To test authentication functionality, I didn't want to create or modify data as that would require additional steps to keep data in the same status before/after the test. With GET method it is enough to test auth.
- To test forbidden access to endpoints that don't allow application only auth I obtained statuses/home_timeline endpoint from [link](https://developer.twitter.com/en/docs/authentication/oauth-2-0/application-only). It seems that there are no v2 endpoints that I can use for this case, so to be consistent in the API versioning I added version in the Examples so both v1.1 and v2 versions of the twitter API are targeted.
- Token invalidation TCs require a little trick. Both v1.1 and v2 APIs should be affected by invalidation, so that invalidation is independent and should only happen ONCE. Hence I control that with a serenityVariable flag. The other problem associated is that this test would affect others (CONFIGURED_TOKEN wouldn't work) so after invalidation variable needs to be reset. However this is a bit counter-intuitive for the user, as we are invalidating the input for the tests with another one.
- Exploring creation/invalidation of oauth2 tokens was a bit tricky. Documentation is a bit confusing, so I decided for now to leave them out. Until I get good examples of it via Postman I don't want to add confusing Scenarios to the code.


# References
-  https://developer.twitter.com/en/docs/authentication/oauth-2-0/application-only