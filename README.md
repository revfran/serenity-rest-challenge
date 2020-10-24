# serenity-rest-challenge
A simple challenge to run tests against an API

# Requisites
- An twitter application following https://developer.twitter.com to have an associated TWITTER_API_TOKEN

# How to execute

This command will execute tests and create serenity reports

$ mvn -DTW_TOKEN=${TWITTER_API_TOKEN} test serenity:aggregate

Reports will be available after execution in [./target/site/serenity/index.html]

# Description of the building process

- Start from maven-quick-start archetype
- Add dependencies according to [serenity-cucumber-starter](https://github.com/serenity-bdd/serenity-cucumber-starter/blob/master/pom.xml)
- Build skeleton, first test and documentation to run the project
- Changed dependencies from failsafe to surefire (as this is not a project including code or unit tests, maven 'test' stage makes more sense)

# Testing ideas
- Auth should be checked in every endpoint exposed. However, for the sake of brevity, only one of the endpoints was tested.
- To know which endpoint may be relevant I went through the use cases and found this as useful, where you get data from [a single tweet](https://developer.twitter.com/en/docs/tutorials/measure-tweet-performance)
- To test authentication functionality, I didn't want to create or modify data as that would require additional steps to keep data in the same status before/after the test. With GET method it is enough to test auth.



# References
-  https://developer.twitter.com/en/docs/authentication/oauth-2-0/application-only