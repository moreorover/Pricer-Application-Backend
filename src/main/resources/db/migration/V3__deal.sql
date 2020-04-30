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
VALUES (1, 'A_8869559', 'Plants vs Zombies Garden Warfare 2 PS4 Hits Game', 'https://www.argos.co.uk/product/8869559',
        '//media.4rgos.it/s/Argos/8869559_R_SET?w=270&h=270&qlt=75&fmt.jpeg.interlaced=true', 16.99, 0.0,
        TIMESTAMP '2020-02-21 13:16:08.355',
        'https://www.argos.co.uk/browse/technology/video-games-and-consoles/ps4/ps4-games/c:30037/opt/page:8', 3),
       (2,
        'A_8849861',
        'Senran Kagura Burst Renewal PS4 Game',
        'https://www.argos.co.uk/product/8849861',
        '//media.4rgos.it/s/Argos/8849861_R_SET?w=270&h=270&qlt=75&fmt.jpeg.interlaced=true',
        29.99,
        0.0,
        TIMESTAMP '2020-02-21 13:16:08.355',
        'https://www.argos.co.uk/browse/technology/video-games-and-consoles/ps4/ps4-games/c:30037/opt/page:8',
        3),
       (3,
        'A_8849737',
        'Fate Extella Link PS4 Game',
        'https://www.argos.co.uk/product/8849737',
        '//media.4rgos.it/s/Argos/8849737_R_SET?w=270&h=270&qlt=75&fmt.jpeg.interlaced=true',
        37.99,
        0.0,
        TIMESTAMP '2020-02-21 13:16:08.355',
        'https://www.argos.co.uk/browse/technology/video-games-and-consoles/ps4/ps4-games/c:30037/opt/page:8',
        3),
       (4,
        'A_8841546',
        'Shovel Knight: Treasure Trove PS4 Pre-Order Game',
        'https://www.argos.co.uk/product/8841546',
        '//media.4rgos.it/s/Argos/8841546_R_SET?w=270&h=270&qlt=75&fmt.jpeg.interlaced=true',
        39.99,
        0.0,
        TIMESTAMP '2020-02-21 13:16:08.355',
        'https://www.argos.co.uk/browse/technology/video-games-and-consoles/ps4/ps4-games/c:30037/opt/page:8',
        3);

INSERT INTO `price` (id, item_id, found_time, price, delta)
VALUES (1, 1, TIMESTAMP '2020-02-21 13:16:08.355', 16.99, 0.0),

       (2, 2, TIMESTAMP '2020-02-15 13:16:08.355', 100, 0.0),
       (3, 2, TIMESTAMP '2020-02-16 13:16:08.355', 50, -50),
       (4, 2, TIMESTAMP '2020-02-17 13:16:08.355', 25, -50),

       (5, 3, TIMESTAMP '2020-02-18 13:16:08.355', 4, 0.0),
       (6, 3, TIMESTAMP '2020-02-19 13:16:08.355', 8, 100),
       (7, 3, TIMESTAMP '2020-02-20 13:16:08.355', 6, -25),

       (8, 4, TIMESTAMP '2020-02-21 13:16:08.355', 10, 0.0),
       (9, 4, TIMESTAMP '2020-02-21 13:16:08.355', 20, 100),
       (10, 4, TIMESTAMP '2020-02-21 13:16:08.355', 15, -25);

INSERT INTO `deal` (id, item_id, deal_available, found_time)
VALUES (1, 2, false, TIMESTAMP '2020-02-16 13:16:08.355'),
       (2, 2, true, TIMESTAMP '2020-02-17 13:16:08.355'),

       (3, 3, true, TIMESTAMP '2020-02-20 13:16:08.355'),

       (4, 4, true, TIMESTAMP '2020-02-17 13:16:08.355');
