CREATE TABLE `item`
(
    `id`         int PRIMARY KEY AUTO_INCREMENT,
    `upc`        varchar(100) UNIQUE NOT NULL,
    `name`       varchar(250)        NOT NULL,
    `url`        varchar(250) UNIQUE NOT NULL,
    `img`        varchar(250),
    `price`      double              NOT NULL,
    `delta`      double              NOT NULL,
    `found_time`  datetime            NOT NULL,
    `found_where` varchar(250)        NOT NULL,
    `url_id`     int                 NOT NULL
);

CREATE TABLE `price`
(
    `id`        int PRIMARY KEY AUTO_INCREMENT,
    `item_id`   int      NOT NULL,
    `found_time` datetime NOT NULL,
    `price`     double   NOT NULL,
    `delta`     double   NOT NULL
);

ALTER TABLE `item`
    ADD FOREIGN KEY (`url_id`) REFERENCES `url` (`id`);

ALTER TABLE `price`
    ADD FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);