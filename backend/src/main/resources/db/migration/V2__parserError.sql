CREATE TABLE `parser_error`
(
    `id`               int PRIMARY KEY AUTO_INCREMENT,
    `url_id`           int          NOT NULL,
    `step_number`      int          NOT NULL,
    `parser_operation` varchar(50)  NOT NULL,
    `url`              varchar(250) NOT NULL
);

ALTER TABLE `parser_error`
    ADD FOREIGN KEY (`url_id`) REFERENCES `url` (`id`);