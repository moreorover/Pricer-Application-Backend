INSERT INTO `store` (`id`, `name`, `url`, `logo`) VALUES
(1, 'Argos', 'https://www.argos.co.uk', 'https://media.4rgos.it/i/Argos/Argos_Pride_Logo_2?w=120&fmt=png'),
(2, 'Superdrug', 'https://www.superdrug.com', 'https://www.superdrug.com/_ui/common/images/sd-logo.png'),
(3, 'The Fragrance Shop', 'https://www.thefragranceshop.co.uk', 'https://www.thelexiconbracknell.com/getmedia/2c8e4346-d1c7-4c62-a535-86aaafc57aae/the-fragrance-shop-logo?width=378&height=284&ext=.png'),
(4, 'H. Samuel', 'https://www.hsamuel.co.uk/', 'https://d113iaclfvkn13.cloudfront.net/logos/logo.svg'),
(5, 'Debenhams Watches', 'https://www.debenhams.com/', 'https://www.stickpng.com/assets/images/5a1ac8e0f65d84088faf1376.png'),
(6, 'Ernest Jones', 'https://www.ernestjones.co.uk', 'https://d2r6ga9xnxj9b6.cloudfront.net/logos/logo.svg'),
(7, 'Watch Shop', 'https://www.watchshop.com', 'http://www.watchshop.com/time-of-your-life/img/watchshop-logo.png');

INSERT INTO `store_url` (`id`, `store_id`, `url_link`, `last_checked`, `status`) VALUES
(316, 1, 'https://www.argos.co.uk/browse/jewellery-and-watches/watches/mens-watches/c:29307/opt/page:1/', TIMESTAMP '2019-10-24 12:13:00.02315', 'DISABLED'),
(317, 1, 'https://www.argos.co.uk/browse/jewellery-and-watches/watches/ladies-watches/c:29306/opt/page:1/', TIMESTAMP '2019-10-24 12:32:00.02315', 'DISABLED'),
(318, 1, 'https://www.argos.co.uk/browse/technology/video-games-and-consoles/ps4/ps4-games/c:30037/opt/page:1/', TIMESTAMP '2019-10-24 12:26:00.02315', 'DISABLED'),
(319, 1, 'https://www.argos.co.uk/browse/technology/video-games-and-consoles/xbox-one/xbox-one-games/c:30031/opt/page:1/', TIMESTAMP '2019-10-24 10:06:00.02315', 'DISABLED'),
(320, 1, 'https://www.argos.co.uk/browse/technology/video-games-and-consoles/nintendo-switch/nintendo-switch-games/c:30292/opt/page:1/', TIMESTAMP '2019-10-24 12:09:00.02315', 'DISABLED'),
(321, 1, 'https://www.argos.co.uk/browse/technology/ipod-mp3-and-headphones/headphones-and-earphones/c:30128/opt/page:1/', TIMESTAMP '2019-10-24 12:19:00.02315', 'DISABLED'),
(322, 1, 'https://www.argos.co.uk/browse/technology/smart-technology/smart-lighting/c:30285/opt/page:1/', TIMESTAMP '2019-10-24 12:17:00.02315', 'DISABLED'),
(323, 1, 'https://www.argos.co.uk/browse/technology/smart-technology/smart-plugs/c:30287/', TIMESTAMP '2019-10-24 10:09:00.02315', 'DISABLED'),
(324, 1, 'https://www.argos.co.uk/browse/technology/smart-technology/smart-home-monitoring/c:30288/opt/page:1/', TIMESTAMP '2019-10-24 10:08:00.02315', 'DISABLED'),
(325, 1, 'https://www.argos.co.uk/browse/technology/smart-technology/smart-speakers/c:658417/opt/page:1/', TIMESTAMP '2019-10-24 12:18:00.02315', 'DISABLED'),
(326, 1, 'https://www.argos.co.uk/browse/technology/smart-technology/smart-heating/c:30286/', TIMESTAMP '2019-10-24 10:08:00.02315', 'DISABLED'),
(327, 1, 'https://www.argos.co.uk/browse/health-and-beauty/perfume-and-aftershaves/ladies-fragrance/c:29282/opt/page:1/', TIMESTAMP '2019-10-24 10:06:00.02315', 'DISABLED'),
(328, 1, 'https://www.argos.co.uk/browse/health-and-beauty/perfume-and-aftershaves/mens-fragrance/c:29283/opt/page:1/', TIMESTAMP '2019-10-24 10:09:00.02315', 'DISABLED'),
(329, 2, 'https://www.superdrug.com/Fragrance/Perfume-For-Women/c/fragranceforher?q=%3AbestBiz%3AinStockFlag%3Atrue&text=&minSel=1.0&maxSel=189.0&minSlider=1.0&maxSlider=189.0&resultsForPage=60&sort=bestBiz&inStockFlagtrue=on//', TIMESTAMP '2019-10-24 12:13:00.02315', 'DISABLED'),
(330, 2, 'https://www.superdrug.com/Fragrance/Fragrance-For-Him/c/fragranceforhim?q=%3AbestBiz%3AinStockFlag%3Atrue&text=&minSel=5.0&maxSel=88.0&minSlider=5.0&maxSlider=88.0&resultsForPage=60&sort=bestBiz&inStockFlagtrue=on//', TIMESTAMP '2019-10-24 13:07:00.02315', 'DISABLED'),
(332, 3, 'https://www.thefragranceshop.co.uk/womens-perfumes-and-fragrances.aspx', TIMESTAMP '2019-10-24 12:57:00.02315', 'DISABLED'),
(333, 3, 'https://www.thefragranceshop.co.uk/mens-aftershaves-and-fragrances.aspx', TIMESTAMP '2019-10-24 12:57:00.02315', 'DISABLED'),
(334, 4, 'https://www.hsamuel.co.uk/webstore/l/watches/recipient%7Cher/?Pg=1', TIMESTAMP '2019-10-24 12:18:00.02315', 'DISABLED'),
(335, 4, 'https://www.hsamuel.co.uk/webstore/l/watches/recipient%7Chim/?Pg=1', TIMESTAMP '2019-10-24 10:09:00.02315', 'DISABLED'),
(336, 5, 'https://www.debenhams.com/women/accessories/watches?shipToCntry=GB', TIMESTAMP '2019-10-24 12:37:00.02315', 'DISABLED'),
(337, 5, 'https://www.debenhams.com/men/watches?shipToCntry=GB', TIMESTAMP '2019-10-24 12:57:00.02315', 'DISABLED'),
(338, 6, 'https://www.ernestjones.co.uk/webstore/l/watches/recipient%7Cher/?Pg=1', TIMESTAMP '2019-10-24 12:36:00.02315', 'READY'),
(339, 6, 'https://www.ernestjones.co.uk/webstore/l/watches/recipient%7Chim/?Pg=1', TIMESTAMP '2019-10-24 12:31:00.02315', 'READY'),
(340, 7, 'https://www.watchshop.com/mens-watches.html?show=200&page=1', '2020-09-03 21:13:00.02315', 'DISABLED'),
(341, 7, 'https://www.watchshop.com/ladies-watches.html?show=200&page=1', '2020-09-03 21:12:00.02315', 'DISABLED');

INSERT INTO `category` (`id`, `name`) VALUES
(1, 'Men'),
(2, 'Women'),
(3, 'Watch'),
(4, 'Jewelery'),
(5, 'Fragrance');

INSERT INTO `store_url_categories` (`store_url_id`, `category_id`) VALUES
(334, 2),
(334, 3),
(335, 1),
(335, 3),
(338, 2),
(338, 3),
(339, 1),
(339, 3);
