-------------------
-- inserting users
-------------------
INSERT INTO `USER` (
    ID,
    FIRST_NAME,
    LAST_NAME,
    USERNAME,
    PASSWORD,
    TOKEN,
    STATUS,
    BIRTHDAY,
    EMAIL,
    CREATIONDATE
) VALUES (
    1,
    'first_aa',
    'last_aa',
    'user_aa',
    'pw_aa',
    1,
    1,
    NULL,
    'bb@mail',
    '2022-01-01 12:00:00'
);

INSERT INTO `USER` (
    ID,
    FIRST_NAME,
    LAST_NAME,
    USERNAME,
    PASSWORD,
    TOKEN,
    STATUS,
    BIRTHDAY,
    EMAIL,
    CREATIONDATE
) VALUES (
    2,
    'first_bb',
    'last_bb',
    'user_bb',
    'pw_bb',
    2,
    1,
    NULL,
    'bb@mail',
    '2022-01-01 12:00:00'
);

-------------------
-- inserting decks
-------------------

INSERT INTO `DECK` (
    ID,
    DECKNAME,
    CREATIONDATE,
    USER_ID
) VALUES (
    1,
    'test_1',
    '2022-01-01 12:00:00',
    1
);

INSERT INTO `DECK` (
    ID,
    DECKNAME,
    CREATIONDATE,
    USER_ID
) VALUES (
    2,
    'test_2',
    '2022-01-01 12:00:00',
    2
);

-------------------
-- inserting cards
-------------------

INSERT INTO `MC_CARD` (
    ID,
    ANSWER,
    QUESTION,
    DECK_ID
) VALUES (
    1,
    'test_1',
    'Which one is correct?',
    1
);

INSERT INTO `MULTIPLE_CHOICE_CARD_OPTIONS` (
    MULTIPLE_CHOICE_CARD_ID,
    OPTIONS
) VALUES (
    1,
    'test_1'
);

INSERT INTO `MULTIPLE_CHOICE_CARD_OPTIONS` (
    MULTIPLE_CHOICE_CARD_ID,
    OPTIONS
) VALUES (
    1,
    'test_2'
);

INSERT INTO `MULTIPLE_CHOICE_CARD_OPTIONS` (
    MULTIPLE_CHOICE_CARD_ID,
    OPTIONS
) VALUES (
    1,
    'test_3'
);

INSERT INTO `MULTIPLE_CHOICE_CARD_OPTIONS` (
    MULTIPLE_CHOICE_CARD_ID,
    OPTIONS
) VALUES (
    1,
    'test_4'
);