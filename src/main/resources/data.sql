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
    'bb@mail.com',
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
    'bb@mail.com',
    '2022-01-01 12:00:00'
);

-------------------
-- inserting decks
-------------------

INSERT INTO `DECK` (
    ID,
    DECKNAME,
    CREATIONDATE,
    USER_ID,
    VISIBILITY
) VALUES (
    1,
    'test_1',
    '2022-01-01 12:00:00',
    1,
    0
);

INSERT INTO `DECK` (
    ID,
    DECKNAME,
    CREATIONDATE,
    USER_ID,
    VISIBILITY
) VALUES (
    2,
    'test_2',
    '2022-01-01 12:00:00',
    2,
    0
);

-------------------
-- inserting cards
-------------------

-- insert card 1 of deck 1
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

-- insert card 2 of deck 1
INSERT INTO `MC_CARD` (
    ID,
    ANSWER,
    QUESTION,
    DECK_ID
) VALUES (
    2,
    'not test_1',
    'Which one is NOT correct?',
    1
);

INSERT INTO `MULTIPLE_CHOICE_CARD_OPTIONS` (
    MULTIPLE_CHOICE_CARD_ID,
    OPTIONS
) VALUES (
    2,
    'not test_1'
);

INSERT INTO `MULTIPLE_CHOICE_CARD_OPTIONS` (
    MULTIPLE_CHOICE_CARD_ID,
    OPTIONS
) VALUES (
    2,
    'not test_2'
);

INSERT INTO `MULTIPLE_CHOICE_CARD_OPTIONS` (
    MULTIPLE_CHOICE_CARD_ID,
    OPTIONS
) VALUES (
    2,
    'not test_3'
);

INSERT INTO `MULTIPLE_CHOICE_CARD_OPTIONS` (
    MULTIPLE_CHOICE_CARD_ID,
    OPTIONS
) VALUES (
    2,
    'not test_4'
);

-- insert card 1 of deck 2
INSERT INTO `MC_CARD` (
    ID,
    ANSWER,
    QUESTION,
    DECK_ID
) VALUES (
    3,
    'test_1',
    'Which answer of card1/deck2 is correct?',
    2
);

INSERT INTO `MULTIPLE_CHOICE_CARD_OPTIONS` (
    MULTIPLE_CHOICE_CARD_ID,
    OPTIONS
) VALUES (
    3,
    'test_1'
);

INSERT INTO `MULTIPLE_CHOICE_CARD_OPTIONS` (
    MULTIPLE_CHOICE_CARD_ID,
    OPTIONS
) VALUES (
    3,
    'test_2'
);

INSERT INTO `MULTIPLE_CHOICE_CARD_OPTIONS` (
    MULTIPLE_CHOICE_CARD_ID,
    OPTIONS
) VALUES (
    3,
    'test_3'
);

INSERT INTO `MULTIPLE_CHOICE_CARD_OPTIONS` (
    MULTIPLE_CHOICE_CARD_ID,
    OPTIONS
) VALUES (
    3,
    'test_4'
);

-- insert card 2 of deck 2
INSERT INTO `MC_CARD` (
    ID,
    ANSWER,
    QUESTION,
    DECK_ID
) VALUES (
    4,
    'not test_1',
    'Which answer of card1/deck2 is NOT correct?',
    2
);

INSERT INTO `MULTIPLE_CHOICE_CARD_OPTIONS` (
    MULTIPLE_CHOICE_CARD_ID,
    OPTIONS
) VALUES (
    4,
    'not test_1'
);

INSERT INTO `MULTIPLE_CHOICE_CARD_OPTIONS` (
    MULTIPLE_CHOICE_CARD_ID,
    OPTIONS
) VALUES (
    4,
    'not test_2'
);

INSERT INTO `MULTIPLE_CHOICE_CARD_OPTIONS` (
    MULTIPLE_CHOICE_CARD_ID,
    OPTIONS
) VALUES (
    4,
    'not test_3'
);

INSERT INTO `MULTIPLE_CHOICE_CARD_OPTIONS` (
    MULTIPLE_CHOICE_CARD_ID,
    OPTIONS
) VALUES (
    4,
    'not test_4'
);