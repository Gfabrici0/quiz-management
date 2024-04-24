CREATE TABLE "user" (
    id UUID PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE quiz_answer (
    id UUID PRIMARY KEY,
    quiz_id UUID NOT NULL,
    user_id UUID NOT NULL,
    created_at DATE NOT NULL
);

CREATE TABLE quiz (
    id UUID PRIMARY KEY,
    created_at DATE NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    creation_user_id UUID NOT NULL
);

CREATE TABLE question (
    id UUID PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    quiz_id UUID NOT NULL
);

CREATE TABLE question_answer (
    id UUID PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    question_id UUID NOT NULL
);
