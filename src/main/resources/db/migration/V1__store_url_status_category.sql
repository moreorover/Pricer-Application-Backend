CREATE TABLE `store`
(
    `id`   int PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(250)        NOT NULL,
    `url`  varchar(250) UNIQUE NOT NULL,
    `logo` varchar(250) UNIQUE NOT NULL
);

CREATE TABLE `url`
(
    `id`         int PRIMARY KEY AUTO_INCREMENT,
    `url`        varchar(250) UNIQUE NOT NULL,
    `checked_at` datetime,
    `store_id`   int                 NOT NULL,
    `status_id`  int                 NOT NULL
);

CREATE TABLE `status`
(
    `id`     int PRIMARY KEY AUTO_INCREMENT,
    `status` varchar(50) UNIQUE NOT NULL
);

CREATE TABLE `category`
(
    `id`       int PRIMARY KEY AUTO_INCREMENT,
    `category` varchar(50) UNIQUE NOT NULL
);

CREATE TABLE `url_category`
(
    `url_id`      int NOT NULL,
    `category_id` int NOT NULL
);

ALTER TABLE `url`
    ADD FOREIGN KEY (`store_id`) REFERENCES `store` (`id`);

ALTER TABLE `url`
    ADD FOREIGN KEY (`status_id`) REFERENCES `status` (`id`);

ALTER TABLE `url_category`
    ADD FOREIGN KEY (`url_id`) REFERENCES `url` (`id`);

ALTER TABLE `url_category`
    ADD FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

INSERT INTO `status` (id, status) VALUES
(1, 'Ready'),
(2, 'Processing'),
(3, 'Disabled');

INSERT INTO `store` (id, name, url, logo) VALUES
(1, 'Creation Watches', 'https://www.creationwatches.com', 'https://www.complaintsboard.com/img/business/116976/182x300/creation-watches.jpg'),
(2, 'First Class Watches', 'https://www.firstclasswatches.co.uk', 'https://pbs.twimg.com/profile_images/1080753708953157633/sv90SvHJ.jpg'),
(3, 'Argos', 'https://www.argos.co.uk', 'https://media.4rgos.it/i/Argos/Argos_Pride_Logo_2?w=120&fmt=png'),
(4, 'Superdrug', 'https://www.superdrug.com', 'https://pbs.twimg.com/profile_images/1010086590536929280/Dy5TpFig_400x400.jpg'),
(5, 'H. Samuel', 'https://www.hsamuel.co.uk', 'https://static.cv-library.co.uk/2019/08/29/10-23/hsamuellogo.png'),
(6, 'Debenhams', 'https://www.debenhams.com', 'https://www.stickpng.com/assets/images/5a1ac8e0f65d84088faf1376.png'),
(7, 'Ernest Jones', 'https://www.ernestjones.co.uk', 'https://www.marieclaire.co.uk/vouchers/vfiles/33-bec523919c46dd5643c48945ce1c794b.png'),
(8, 'Watch Shop', 'https://www.watchshop.com', 'http://www.watchshop.com/time-of-your-life/img/watchshop-logo.png'),
(9, 'AMJ Watches', 'https://amjwatches.co.uk', 'https://s3-eu-west-1.amazonaws.com/tpd/logos/52dfcaca0000640005780190/0x0.png'),
(10, 'Gold Smiths', 'https://www.goldsmiths.co.uk', 'https://lh3.googleusercontent.com/-Ck01XdjIITQ/AAAAAAAAAAI/AAAAAAAAA9o/iz6zLZRdIZ0/s250-c/photo.jpg'),
(11, 'Tic Watches', 'https://www.ticwatches.co.uk', 'https://www.ticwatches.co.uk/images/modules/promo_units/1575387494-68252500.png'),
(12, 'Watcho', 'https://www.watcho.co.uk', 'https://cdn11.bigcommerce.com/s-f06f69/images/stencil/250x100/final-logo-small_1571639906__21982.original.png'),
(13, 'Simpkins Jewellers', 'https://simpkinsjewellers.co.uk', 'https://simpkinsjewellers.co.uk/image/catalog/Banners/Simpkins%20Logo%202.png'),
(14, 'Place Hodler', 'place holder', 'place holder');

INSERT INTO `url` (id, url, checked_at, store_id, status_id) VALUES
(1, 'https://www.creationwatches.com/products/timex-watches-434/index-1-5d.html?currency=GBP', null, 1, 3),
(2, 'https://www.creationwatches.com/products/orient-watches-252/index-1-5d.html?currency=GBP', null, 1, 3),
(3, 'https://www.creationwatches.com/products/tissot-247/index-1-5d.html?currency=GBP', null, 1, 3),
(4, 'https://www.creationwatches.com/products/citizen-74/index-1-5d.html?currency=GBP', null, 1, 3),
(5, 'https://www.creationwatches.com/products/hamilton-watches-250/index-1-5d.html?currency=GBP', null, 1, 3),
(6, 'https://www.creationwatches.com/products/bulova-watches-271/index-1-5d.html?currency=GBP', null, 1, 3),
(7, 'https://www.creationwatches.com/products/seiko-75/index-1-5d.html?currency=GBP', null, 1, 3),
(8, 'https://www.creationwatches.com/products/casio-watches-73/index-1-5d.html?currency=GBP', null, 1, 3),
(9, 'https://www.firstclasswatches.co.uk/mens-watches/?show_stock=y&f50=Mens+Watches&per_page=180&page=1', null, 2, 3),
(10, 'https://www.firstclasswatches.co.uk/womens-watches/?show_stock=y&f50=Womens+Watches&per_page=180&page=1', null, 2, 3),
(11, 'https://www.argos.co.uk/browse/jewellery-and-watches/watches/ladies-watches/c:29306/opt/page:1/', null, 3, 3),
(12, 'https://www.argos.co.uk/browse/health-and-beauty/perfume-and-aftershaves/mens-fragrance/c:29283/opt/page:1/', null, 3, 3),
(13, 'https://www.argos.co.uk/browse/technology/smart-technology/smart-speakers/c:658417/opt/page:1/', null, 3, 3),
(14, 'https://www.argos.co.uk/browse/jewellery-and-watches/watches/mens-watches/c:29307/opt/page:1/', null, 3, 3),
(15, 'https://www.argos.co.uk/browse/technology/smart-technology/smart-home-monitoring/c:30288/opt/page:1/', null, 3, 3),
(16, 'https://www.argos.co.uk/browse/technology/smart-technology/smart-heating/c:30286/', null, 3, 3),
(17, 'https://www.argos.co.uk/browse/technology/ipod-mp3-and-headphones/headphones-and-earphones/c:30128/opt/page:1/', null, 3, 3),
(18, 'https://www.argos.co.uk/browse/technology/smart-technology/smart-plugs/c:30287/', null, 3, 3),
(19, 'https://www.argos.co.uk/browse/technology/video-games-and-consoles/ps4/ps4-games/c:30037/opt/page:1/', null, 3, 3),
(20, 'https://www.argos.co.uk/browse/technology/smart-technology/smart-lighting/c:30285/opt/page:1/', null, 3, 3),
(21, 'https://www.argos.co.uk/browse/technology/video-games-and-consoles/nintendo-switch/nintendo-switch-games/c:30292/opt/page:1/', null, 3, 3),
(22, 'https://www.argos.co.uk/browse/technology/video-games-and-consoles/xbox-one/xbox-one-games/c:30031/opt/page:1/', null, 3, 3),
(23, 'https://www.argos.co.uk/browse/health-and-beauty/perfume-and-aftershaves/ladies-fragrance/c:29282/opt/page:1/', null, 3, 3),
(24, 'https://www.superdrug.com/Fragrance/Perfume-For-Women/c/fragranceforher?q=%3AbestBiz%3AinStockFlag%3Atrue&page=0&resultsForPage=60&sort=bestBiz', null, 4, 3),
(25, 'https://www.superdrug.com/Fragrance/Fragrance-For-Him/c/fragranceforhim?q=%3AbestBiz%3AinStockFlag%3Atrue&page=0&resultsForPage=60&sort=bestBiz', null, 4, 3),
(26, 'https://www.hsamuel.co.uk/webstore/l/watches/recipient%7Cher/?Pg=1', null, 5, 3),
(27, 'https://www.hsamuel.co.uk/webstore/l/watches/recipient%7Chim/?Pg=1', null, 5, 3),
(28, 'https://www.debenhams.com/men/accessories/watches?pn=1&?shipToCntry=GB', null, 6, 3),
(29, 'https://www.debenhams.com/women/accessories/watches?pn=1&?shipToCntry=GB', null, 6, 3),
(30, 'https://www.ernestjones.co.uk/webstore/l/watches/recipient%7Cher/?Pg=1', null, 7, 3),
(31, 'https://www.ernestjones.co.uk/webstore/l/watches/recipient%7Chim/?Pg=1', null, 7, 3),
(32, 'https://www.watchshop.com/ladies-watches.html?show=192&page=1', null, 8, 3),
(33, 'https://www.watchshop.com/mens-watches.html?show=192&page=1', null, 8, 3),
(34, 'https://amjwatches.co.uk/mens.html?page=1', null, 9, 3),
(35, 'https://amjwatches.co.uk/ladies.html?page=1', null, 9, 3),
(36, 'https://www.goldsmiths.co.uk/c/Watches/Ladies-Watches/filter/Page_1/Psize_96/Show_Page/', null, 10, 3),
(37, 'https://www.goldsmiths.co.uk/c/Watches/Mens-Watches/filter/Page_1/Psize_96/Show_Page/', null, 10, 3),
(38, 'https://www.ticwatches.co.uk/womens-watches-c2', null, 11, 3),
(39, 'https://www.ticwatches.co.uk/mens-watches-c1', null, 11, 3),
(40, 'https://www.watcho.co.uk/watches/citizen.html?page=1', null, 12, 3),
(41, 'https://www.watcho.co.uk/watches/casio/casio-g-shock-watches.html?page=1', null, 12, 3),
(42, 'https://www.watcho.co.uk/watches/garmin.html?page=1', null, 12, 3),
(43, 'https://www.watcho.co.uk/watches/seiko.html?page=1', null, 12, 3),
(44, 'https://simpkinsjewellers.co.uk/watches/seiko?limit=100&page=1', null, 13, 3),
(45, 'https://simpkinsjewellers.co.uk/watches/citizen?limit=100&page=1', null, 13, 3),
(46, 'https://simpkinsjewellers.co.uk/watches/casio?limit=100&page=1', null, 13, 3),
(47, 'https://simpkinsjewellers.co.uk/watches/victorinox?limit=100&page=1', null, 13, 3),
(48, 'https://simpkinsjewellers.co.uk/watches/police?limit=100&page=1', null, 13, 3),
(49, 'https://simpkinsjewellers.co.uk/watches/rotary?limit=100&page=1', null, 13, 3),
(50, 'https://simpkinsjewellers.co.uk/watches/pulsar?limit=100&page=1', null, 13, 3),
(51, 'https://simpkinsjewellers.co.uk/watches/Mondaine?limit=100&page=1', null, 13, 3),
(52, 'https://simpkinsjewellers.co.uk/watches/bulova?limit=100&page=1', null, 13, 3),
(53, 'https://simpkinsjewellers.co.uk/watches/lorus?limit=100&page=1', null, 13, 3),
(54, 'https://simpkinsjewellers.co.uk/watches/AVI-8%20Aviator%20Watches?limit=100&page=1', null, 13, 3),
(55, 'https://simpkinsjewellers.co.uk/watches/accurist?limit=100&page=1', null, 13, 3),
(56, 'https://simpkinsjewellers.co.uk/watches/bering?limit=100&page=1', null, 13, 3),
(57, 'place holder', null, 14, 3);

INSERT INTO category (id, category) VALUES
(1, 'Watch'),
(2, 'Men'),
(3, 'Women'),
(4, 'Fragrance'),
(5, 'Technology');

INSERT INTO url_category (url_id, category_id) VALUES
(1, 1),(2, 1),(3, 1),(4, 1),(5, 1),(6, 1),(7, 1),(8, 1),
(9, 1),(9, 2),
(10, 1),(10, 3),
(11, 1),(11, 3),
(12, 2),(12, 4),
(13, 5),
(14, 1),(14, 2),
(15, 5),
(16, 5),
(17, 5),
(18, 5),
(19, 5),
(20, 5),
(21, 5),
(22, 5),
(23, 3),(23, 4),
(24, 3),(24, 4),
(25, 2),(25, 4),
(26, 1),(26, 3),
(27, 1),(27, 2),
(28, 1),(28, 2),
(29, 1),(29, 3),
(30, 1),(30, 3),
(31, 1),(31, 2),
(32, 1),(32, 3),
(33, 1),(33, 2),
(34, 1),(34, 2),
(35, 1),(35, 3),
(36, 1),(36, 2),
(37, 1),(37, 3),
(38, 1),(38, 3),
(39, 1),(39, 2),
(40, 1),(41, 1),(42, 1),(43, 1),
(44, 1),(45, 1),(46, 1),(47, 1),(48, 1),(49, 1),(50, 1),(51, 1),(52, 1),(53, 1),(54, 1),(55, 1),(56, 1);