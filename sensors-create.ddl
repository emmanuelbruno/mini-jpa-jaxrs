CREATE TABLE MAISON (ID BIGINT NOT NULL, BIG LONGVARBINARY, DATEDECONSTRUCTION TIMESTAMP, NAME VARCHAR, SURFACE INTEGER, VETUSTE INTEGER, PRIMARY KEY (ID))
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT NUMERIC(38), PRIMARY KEY (SEQ_NAME))
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0)