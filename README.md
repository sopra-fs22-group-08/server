# SoPra FS22 Group 08 - NoBrainer

## Introduction

Our project aims to create an alternative flashcard learning tool to the big names
of 'Anki' and 'Quizlet'.
We try to make the learning as easy and seamless as possible and the feedback unobtrusive.

## Technologies

The project is written in 'Java' using the Spring Boot Framework, as well as JPA
'Gradle' is used to manage our dependencies and the deployment happens on 'Heroku'

## High-Level Components

-   [`UserService`](https://github.com/sopra-fs22-group-08/server/blob/master/src/main/java/ch/uzh/ifi/hase/soprafs22/service/UserService.java): contains the logic to create and edit users.
-   [`InvitationService`](https://github.com/sopra-fs22-group-08/server/blob/master/src/main/java/ch/uzh/ifi/hase/soprafs22/service/InvitationService.java): contains the logic to invite other players, handle the
    deletion of the invitations upon acceptance.
-   [`DuelService`](https://github.com/sopra-fs22-group-08/server/blob/master/src/main/java/ch/uzh/ifi/hase/soprafs22/service/DuelService.java): contains the logic to 'Duel', also 1v1 lobbies, where players
    play against each other.

TODO:

-   [ ] expand description

## Launch & Deployment

### Building with Gradle

You can use the local Gradle Wrapper to build the application.

-   macOS: `./gradlew`
-   Linux: `./gradlew`
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

## Illustrations

TODO:

-   [ ] add illustrations

## Roadmap

-   [ ] add feature to add and challenge friends
-   [ ] add feature for open question cards
-   [ ] ...

## Authors & Acknowledgements

-   [Andrin Reding](https://github.com/aredin69)
-   [Nico Reding](https://github.com/niredi)
-   [Robert Hemenguel](https://github.com/RibelH)
-   [Tim Portmann](https://github.com/tportmann-uzh)
-   [Armin Veres](https://github.com/arminveres)

## License

[Apache License, 2.0](./LICENSE)
