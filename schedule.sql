USE schedule;

#LV 1~2
CREATE TABLE schedule
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    todo        VARCHAR(200) NOT NULL,
    writer      VARCHAR(50)  NOT NULL,
    password    VARCHAR(100) NOT NULL,
    create_at   DATETIME     NOT NULL,
    modified_at DATETIME     NOT NULL
);

