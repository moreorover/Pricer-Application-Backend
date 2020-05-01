CREATE TABLE `deal`
(
    `id`         int PRIMARY KEY AUTO_INCREMENT,
    `item_id`     int                 NOT NULL,
    `deal_available` boolean NOT NULL,
    `found_time` datetime NOT NULL
);

ALTER TABLE `deal`
    ADD FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);
