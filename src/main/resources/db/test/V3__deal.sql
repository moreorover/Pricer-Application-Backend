CREATE TABLE `deal`
(
    `id`         int PRIMARY KEY AUTO_INCREMENT,
    `item_id`     int                 NOT NULL,
    `dealAvailable` boolean NOT NULL,
    `foundTime` datetime NOT NULL
);

ALTER TABLE `deal`
    ADD FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);