﻿-- Create sequences for each table
CREATE SEQUENCE TBL_REFRESH_TOKEN_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE TBL_USER_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE TBL_BOARD_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE TBL_ATTACH_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE TBL_LETTER_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE TBL_COMMENT_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE TBL_TEMPLATE_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE TBL_BOARD_LIKE_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE TBL_TEMPLATE_LIKE_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE SEQUENCE TBL_TEMPLATE_TAG_SEQ START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

-- Create tables with primary key defaulting to sequence next value
CREATE TABLE TBL_REFRESH_TOKEN (
    REFRESH_TOKEN_ID NUMBER DEFAULT TBL_REFRESH_TOKEN_SEQ.NEXTVAL NOT NULL,
    USER_ID VARCHAR2(2000) NOT NULL,
    TOKEN_VALUE VARCHAR2(3000) NULL
);

CREATE TABLE TBL_USER (
    USER_ID VARCHAR2(2000) DEFAULT TBL_USER_SEQ.NEXTVAL NOT NULL,
    USER_NAME VARCHAR2(1000) NOT NULL,
    USER_EMAIL VARCHAR2(1000) NOT NULL,
    USER_NICKNAME VARCHAR2(1000) NOT NULL,
    USER_IMAGE VARCHAR2(2000) NOT NULL,
    USER_SIGNUP_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE TBL_BOARD (
    BOARD_NO NUMBER DEFAULT TBL_BOARD_SEQ.NEXTVAL NOT NULL,
    USER_ID VARCHAR2(2000) NOT NULL,
    BOARD_TITLE VARCHAR2(2000) NOT NULL,
    BOARD_CONTENT CLOB NOT NULL,
    REGIST_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CHANGE_DT TIMESTAMP NULL,
    USE_YN VARCHAR2(1) DEFAULT 'Y' NOT NULL,
    BOARD_VIEW NUMBER DEFAULT 0 NOT NULL,
    BOARD_THUMBNAIL VARCHAR2(2000) NULL
);

CREATE TABLE TBL_ATTACH (
    ATTACH_NO NUMBER DEFAULT TBL_ATTACH_SEQ.NEXTVAL NOT NULL,
    BOARD_NO NUMBER NOT NULL,
    REGIST_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    USE_YN VARCHAR2(1) DEFAULT 'Y' NOT NULL,
    CHANGE_DT TIMESTAMP NULL,
    FILE_PATH VARCHAR2(2000) NULL,
    ORIGINAL_FILE_NM VARCHAR2(2000) NULL,
    SAVE_FILE_NM VARCHAR2(2000) NULL
);

CREATE TABLE TBL_LETTER (
    LETTER_NO NUMBER DEFAULT TBL_LETTER_SEQ.NEXTVAL NOT NULL,
    LETTER_RECEIVE_ID VARCHAR2(2000) NOT NULL,
    LETTER_SEND_ID VARCHAR2(2000) NOT NULL,
    LETTER_TITLE VARCHAR2(2000) NOT NULL,
    LETTER_CONTENT CLOB NOT NULL,
    LETTER_URL VARCHAR2(2000) NOT NULL,
    REGIST_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    KAKAO_SEND_YN VARCHAR2(1) DEFAULT 'N' NOT NULL,
    LETTER_COLOR_NO VARCHAR2(1000) NULL,
    LETTER_RECEIVE_YN VARCHAR2(1) DEFAULT 'N' NOT NULL
);

CREATE TABLE TBL_COMMENT (
    COMMENT_ID NUMBER DEFAULT TBL_COMMENT_SEQ.NEXTVAL NOT NULL,
    BOARD_NO NUMBER NOT NULL,
    USER_ID VARCHAR2(2000) NOT NULL,
    COMMENT_CONTENT VARCHAR2(1000) NOT NULL,
    REGIST_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CHANGE_DT TIMESTAMP NULL,
    USE_YN VARCHAR2(1) DEFAULT 'Y' NOT NULL
);

CREATE TABLE TBL_TEMPLATE (
    TEMPLATE_NO NUMBER DEFAULT TBL_TEMPLATE_SEQ.NEXTVAL NOT NULL,
    TEMPLATE_TITLE VARCHAR2(2000) NOT NULL,
    TEMPLATE_CONTENT CLOB NOT NULL,
    REGIST_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE TBL_BOARD_LIKE (
    BOARD_LIKE_NO NUMBER DEFAULT TBL_BOARD_LIKE_SEQ.NEXTVAL NOT NULL,
    USER_ID VARCHAR2(2000) NOT NULL,
    BOARD_NO NUMBER NOT NULL,
    LIKE_YN VARCHAR2(1) DEFAULT 'Y' NOT NULL,
    REGIST_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CHANGE_DT TIMESTAMP NULL
);

CREATE TABLE TBL_TEMPLATE_LIKE (
    TEMPLATE_LIKE_NO NUMBER DEFAULT TBL_TEMPLATE_LIKE_SEQ.NEXTVAL NOT NULL,
    USER_ID VARCHAR2(2000) NOT NULL,
    TEMPLATE_NO NUMBER NOT NULL,
    LIKE_YN VARCHAR2(1) DEFAULT 'Y' NOT NULL,
    REGIST_DT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CHANGE_DT TIMESTAMP NULL
);

CREATE TABLE TBL_TEMPLATE_TAG (
    TEMPLATE_TAG_NO NUMBER DEFAULT TBL_TEMPLATE_TAG_SEQ.NEXTVAL NOT NULL,
    TEMPLATE_NO NUMBER NOT NULL,
    TEMPLATE_TAG_CONTENT VARCHAR2(1000) NOT NULL,
    USE_YN VARCHAR2(1) DEFAULT 'N' NOT NULL
);

-- Primary Key and Foreign Key Constraints
-- ...

-- Note: Continue with the constraints and foreign key settings as per your original script.

ALTER TABLE TBL_REFRESH_TOKEN ADD CONSTRAINT PK_TBL_REFRESH_TOKEN PRIMARY KEY (
    REFRESH_TOKEN_ID
);

ALTER TABLE TBL_USER ADD CONSTRAINT PK_TBL_USER PRIMARY KEY (
    USER_ID
);

ALTER TABLE TBL_BOARD ADD CONSTRAINT PK_TBL_BOARD PRIMARY KEY (
    BOARD_NO
);

ALTER TABLE TBL_ATTACH ADD CONSTRAINT PK_TBL_ATTACH PRIMARY KEY (
    ATTACH_NO
);

ALTER TABLE TBL_LETTER ADD CONSTRAINT PK_TBL_LETTER PRIMARY KEY (
    LETTER_NO
);

ALTER TABLE TBL_COMMENT ADD CONSTRAINT PK_TBL_COMMENT PRIMARY KEY (
    COMMENT_ID
);

ALTER TABLE TBL_TEMPLATE ADD CONSTRAINT PK_TBL_TEMPLATE PRIMARY KEY (
    TEMPLATE_NO
);

ALTER TABLE TBL_BOARD_LIKE ADD CONSTRAINT PK_TBL_BOARD_LIKE PRIMARY KEY (
    BOARD_LIKE_NO
);

ALTER TABLE TBL_TEMPLATE_LIKE ADD CONSTRAINT PK_TBL_TEMPLATE_LIKE PRIMARY KEY (
    TEMPLATE_LIKE_NO
);

ALTER TABLE TBL_TEMPLATE_TAG ADD CONSTRAINT PK_TBL_TEMPLATE_TAG PRIMARY KEY (
    TEMPLATE_TAG_NO
);

ALTER TABLE TBL_REFRESH_TOKEN ADD CONSTRAINT FK_TBL_USER_TO_TBL_REFRESH_TOKEN_1 FOREIGN KEY (
    USER_ID
)
REFERENCES TBL_USER (
    USER_ID
);

ALTER TABLE TBL_BOARD ADD CONSTRAINT FK_TBL_USER_TO_TBL_BOARD_1 FOREIGN KEY (
    USER_ID
)
REFERENCES TBL_USER (
    USER_ID
);

ALTER TABLE TBL_ATTACH ADD CONSTRAINT FK_TBL_BOARD_TO_TBL_ATTACH_1 FOREIGN KEY (
    BOARD_NO
)
REFERENCES TBL_BOARD (
    BOARD_NO
);

ALTER TABLE TBL_LETTER ADD CONSTRAINT FK_TBL_USER_TO_TBL_LETTER_1 FOREIGN KEY (
    LETTER_RECEIVE_ID
)
REFERENCES TBL_USER (
    USER_ID
);

ALTER TABLE TBL_LETTER ADD CONSTRAINT FK_TBL_USER_TO_TBL_LETTER_2 FOREIGN KEY (
    LETTER_SEND_ID
)
REFERENCES TBL_USER (
    USER_ID
);

ALTER TABLE TBL_COMMENT ADD CONSTRAINT FK_TBL_BOARD_TO_TBL_COMMENT_1 FOREIGN KEY (
    BOARD_NO
)
REFERENCES TBL_BOARD (
    BOARD_NO
);

ALTER TABLE TBL_COMMENT ADD CONSTRAINT FK_TBL_USER_TO_TBL_COMMENT_1 FOREIGN KEY (
    USER_ID
)
REFERENCES TBL_USER (
    USER_ID
);

ALTER TABLE TBL_BOARD_LIKE ADD CONSTRAINT FK_TBL_USER_TO_TBL_BOARD_LIKE_1 FOREIGN KEY (
    USER_ID
)
REFERENCES TBL_USER (
    USER_ID
);

ALTER TABLE TBL_BOARD_LIKE ADD CONSTRAINT FK_TBL_BOARD_TO_TBL_BOARD_LIKE_1 FOREIGN KEY (
    BOARD_NO
)
REFERENCES TBL_BOARD (
    BOARD_NO
);

ALTER TABLE TBL_TEMPLATE_LIKE ADD CONSTRAINT FK_TBL_USER_TO_TBL_TEMPLATE_LIKE_1 FOREIGN KEY (
    USER_ID
)
REFERENCES TBL_USER (
    USER_ID
);

ALTER TABLE TBL_TEMPLATE_LIKE ADD CONSTRAINT FK_TBL_TEMPLATE_TO_TBL_TEMPLATE_LIKE_1 FOREIGN KEY (
    TEMPLATE_NO
)
REFERENCES TBL_TEMPLATE (
    TEMPLATE_NO
);

ALTER TABLE TBL_TEMPLATE_TAG ADD CONSTRAINT FK_TBL_TEMPLATE_TO_TBL_TEMPLATE_TAG_1 FOREIGN KEY (
    TEMPLATE_NO
)
REFERENCES TBL_TEMPLATE (
    TEMPLATE_NO
);


/*

-- Drop Foreign Key Constraints First to Avoid Dependency Issues
ALTER TABLE TBL_REFRESH_TOKEN DROP CONSTRAINT FK_TBL_USER_TO_TBL_REFRESH_TOKEN_1;
ALTER TABLE TBL_BOARD DROP CONSTRAINT FK_TBL_USER_TO_TBL_BOARD_1;
ALTER TABLE TBL_ATTACH DROP CONSTRAINT FK_TBL_BOARD_TO_TBL_ATTACH_1;
ALTER TABLE TBL_LETTER DROP CONSTRAINT FK_TBL_USER_TO_TBL_LETTER_1;
ALTER TABLE TBL_LETTER DROP CONSTRAINT FK_TBL_USER_TO_TBL_LETTER_2;
ALTER TABLE TBL_COMMENT DROP CONSTRAINT FK_TBL_BOARD_TO_TBL_COMMENT_1;
ALTER TABLE TBL_COMMENT DROP CONSTRAINT FK_TBL_USER_TO_TBL_COMMENT_1;
ALTER TABLE TBL_BOARD_LIKE DROP CONSTRAINT FK_TBL_USER_TO_TBL_BOARD_LIKE_1;
ALTER TABLE TBL_BOARD_LIKE DROP CONSTRAINT FK_TBL_BOARD_TO_TBL_BOARD_LIKE_1;
ALTER TABLE TBL_TEMPLATE_LIKE DROP CONSTRAINT FK_TBL_USER_TO_TBL_TEMPLATE_LIKE_1;
ALTER TABLE TBL_TEMPLATE_LIKE DROP CONSTRAINT FK_TBL_TEMPLATE_TO_TBL_TEMPLATE_LIKE_1;
ALTER TABLE TBL_TEMPLATE_TAG DROP CONSTRAINT FK_TBL_TEMPLATE_TO_TBL_TEMPLATE_TAG_1;

-- Drop Tables
DROP TABLE TBL_REFRESH_TOKEN;
DROP TABLE TBL_USER;
DROP TABLE TBL_BOARD;
DROP TABLE TBL_ATTACH;
DROP TABLE TBL_LETTER;
DROP TABLE TBL_COMMENT;
DROP TABLE TBL_TEMPLATE;
DROP TABLE TBL_BOARD_LIKE;
DROP TABLE TBL_TEMPLATE_LIKE;
DROP TABLE TBL_TEMPLATE_TAG;

-- Drop Sequences
DROP SEQUENCE TBL_REFRESH_TOKEN_SEQ;
DROP SEQUENCE TBL_USER_SEQ;
DROP SEQUENCE TBL_BOARD_SEQ;
DROP SEQUENCE TBL_ATTACH_SEQ;
DROP SEQUENCE TBL_LETTER_SEQ;
DROP SEQUENCE TBL_COMMENT_SEQ;
DROP SEQUENCE TBL_TEMPLATE_SEQ;
DROP SEQUENCE TBL_BOARD_LIKE_SEQ;
DROP SEQUENCE TBL_TEMPLATE_LIKE_SEQ;
DROP SEQUENCE TBL_TEMPLATE_TAG_SEQ;
*/