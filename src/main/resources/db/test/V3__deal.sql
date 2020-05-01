CREATE TABLE `deal`
(
    `id`         int PRIMARY KEY AUTO_INCREMENT,
    `item_id`     int                 NOT NULL,
    `deal_available` boolean NOT NULL,
    `found_time` datetime NOT NULL
);

ALTER TABLE `deal`
    ADD FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

INSERT INTO `item` (id, upc, name, url, img, price, delta, found_time, found_where, url_id)
VALUES (1,
        'A_8869559',
        'Plants vs Zombies Garden Warfare 2 PS4 Hits Game',
        'https://www.argos.co.uk/product/8869559',
        '//media.4rgos.it/s/Argos/8869559_R_SET?w=270&h=270&qlt=75&fmt.jpeg.interlaced=true',
        6.0,
        -50.0,
        TIMESTAMP '2020-02-21 13:16:08.355',
        'https://www.argos.co.uk/browse/technology/video-games-and-consoles/ps4/ps4-games/c:30037/opt/page:8',
        3);

INSERT INTO `price` (id, item_id, found_time, price, delta)
VALUES (1, 1, TIMESTAMP '2020-02-10 13:16:08.355', 5, 0.0),
       (2, 1, TIMESTAMP '2020-02-11 13:16:08.355', 10, 100),
       (3, 1, TIMESTAMP '2020-02-12 13:16:08.355', 8, -20),
       (4, 1, TIMESTAMP '2020-02-13 13:16:08.355', 4, -50),
       (5, 1, TIMESTAMP '2020-02-14 13:16:08.355', 16, 400),
       (6, 1, TIMESTAMP '2020-02-15 13:16:08.355', 12, -25),
       (7, 1, TIMESTAMP '2020-02-16 13:16:08.355', 6, -50);

INSERT INTO `deal` (id, item_id, deal_available, found_time)
VALUES (1, 1, false, TIMESTAMP '2020-02-12 13:16:08.355'),
       (2, 1, false, TIMESTAMP '2020-02-13 13:16:08.355'),
       (3, 1, false, TIMESTAMP '2020-02-15 13:16:08.355'),
       (4, 1, true, TIMESTAMP '2020-02-16 13:16:08.355');
