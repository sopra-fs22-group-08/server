# SoPra FS22 Group 08 - NoBrainer

## Introduction

Our project aims to create an alternative flashcard learning tool to the big names
of 'Anki' and 'Quizlet'.
We try to make the learning as easy and seamless as possible and the feedback unobtrusive.
Part of our motivation was that we also wanted to work on something that would
provide useful functionalities to us as students after the completion of the SoPra course.
We know that learning can sometimes be very "unfun" and therefore we thought of
making the process more competitive by adding a player vs player mode apart from
the classic "solo" learning experience.

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
    contains the logic for 'Duels', aka 1v1 lobbies, where players face off against
    each other, to prove their knowledge against each other.

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

-   On the landing page is a register-button to create a user.
    <img width="720" alt="Screenshot 2022-05-21 at 13 30 28" src="https://user-images.githubusercontent.com/91237594/169659835-a56f6806-1b40-4807-a00e-5fa84d6905ce.png">

-   After the registration, the homepage is still empty. Therefore, we create some decks with the creator.
    <img width="720" alt="Screenshot 2022-05-21 at 13 33 15" src="https://user-images.githubusercontent.com/91237594/169659854-dda46e8d-41c8-49f6-a488-66883c28896c.png">

-   When a deck is created, there is an overview-page. Where you can edit the deck or challenge other users.
    <img width="720" alt="Screenshot 2022-05-21 at 13 37 41" src="https://user-images.githubusercontent.com/91237594/169659870-8681df9e-fe05-4e4c-8021-d1d8fa4d66db.png">

-   If a user challenges you to a game. You are going to see an invitation on the home screen.
    <img width="720" alt="Screenshot 2022-05-21 at 13 38 42" src="https://user-images.githubusercontent.com/91237594/169659878-f4ddf246-4e07-49e8-9e9c-0859b279ba22.png">

-   The cards in the game consists of one question and four possible answers.
    <img width="720" alt="Screenshot 2022-05-21 at 13 39 05" src="https://user-images.githubusercontent.com/91237594/169659890-4001d986-6363-467e-9701-8dad58ff4436.png">

## Roadmap

-   [ ] add feature to add and challenge friends
-   [ ] add feature for open question cards
-   [ ] Adding LaTeX Questions and Answers To Cards
-   [ ] Adding Picture Questions and Answers To Cards
-   [ ] Embed a Payment System for Card Decks created by other Users

## Authors & Acknowledgements

-   [Andrin Reding](https://github.com/aredin69)
-   [Nico Reding](https://github.com/niredi)
-   [Robert Hemenguel](https://github.com/RibelH)
-   [Tim Portmann](https://github.com/tportmann-uzh)
-   [Armin Veres](https://github.com/arminveres)

## License

[Apache License, 2.0](./LICENSE)