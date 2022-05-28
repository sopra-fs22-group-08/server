# SoPra FS22 Group 08 - NoBrainer

## Introduction

Our project aims to create an alternative flashcard learning tool to the big names
of 'Anki' and 'Quizlet'.

We wanted to create something useful after the completion of the SoPra course and
as we know that learning can sometimes be very "unfun", therefore we try to make
the learning as easy and seamless as possible and give a satisfying feedback to
the user.

We thought of making the process more fun and competitive by adding a 'player vs player'
mode, called 'Duel', apart from the classic 'solo' learning experience.

[Click here to visit our website!](https://sopra-fs22-group08-client.herokuapp.com/)

## Technologies

The backend side of the project is written in 'Java'

-   using the 'Spring Boot Framework',
-   'JPA', to handle database connections
-   'Gradle' is used to manage our dependencies
-   we are deploying to 'Heroku'
-   our code gets checked with 'SonarCube'

## High-Level Components

-   [`UserService`](https://github.com/sopra-fs22-group-08/server/blob/master/src/main/java/ch/uzh/ifi/hase/soprafs22/service/UserService.java):
    As a central focal point of our backend the `UserService` class contains the logic
    to create, edit and save the users (to the repositories).
-   [`InvitationService`](https://github.com/sopra-fs22-group-08/server/blob/master/src/main/java/ch/uzh/ifi/hase/soprafs22/service/InvitationService.java):
    contains the logic to invite other players, handle the deletion of the
    invitations upon acceptance.
-   [`DuelService`](https://github.com/sopra-fs22-group-08/server/blob/master/src/main/java/ch/uzh/ifi/hase/soprafs22/service/DuelService.java):
    contains the logic for 'Duels', aka 1v1 lobbies, where players face off each
    other, to prove their knowledge against each other.

## Launch & Deployment

### Building with Gradle

You can use the local Gradle Wrapper to build the application.

-   macOS/Linux: `./gradlew`
-   Windows: `./gradlew.bat`

More Information about [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) and [Gradle](https://gradle.org/docs/).

#### Build

```bash
./gradlew build
```

#### Run

```bash
./gradlew bootRun
```

#### Test

```bash
./gradlew test
```

#### Development Mode

You can start the backend in development mode, this will automatically trigger a new build and reload the application
once the content of a file has been changed and you save the file.

Start two terminal windows and run:

`./gradlew build --continuous`

and in the other one:

`./gradlew bootRun`

If you want to avoid running all tests with every change, use the following command instead:

`./gradlew build --continuous -xtest`

## Roadmap

-   [ ] add feature to add and challenge friends
-   [ ] add feature for open question cards
-   [ ] add possibility to add LaTeX formatting to questions and answers
-   [ ] add possibility to add pictures to questions and answers
-   [ ] embed a payment system for decks created by other users

## Authors & Acknowledgements

-   [Andrin Reding](https://github.com/aredin69)
-   [Nico Reding](https://github.com/niredi)
-   [Robert Hemenguel](https://github.com/RibelH)
-   [Tim Portmann](https://github.com/tportmann-uzh)
-   [Armin Veres](https://github.com/arminveres)

## License

[Apache License, 2.0](./LICENSE)