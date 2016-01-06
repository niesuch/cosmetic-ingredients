CREATE TABLE IF NOT EXISTS `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(80) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;


INSERT INTO `products` (`id`, `name`) VALUES
(1, 'Test 1'),
(2, 'Test 2'),
(3, 'Test 3'),
(4, 'Test 4');

CREATE TABLE IF NOT EXISTS `products_qr_code` (
  `id` int(11) NOT NULL,
  `qr_code` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `products_qr_code` (`id`, `qr_code`) VALUES
(1, '5901887029113'),
(2, '5907608614774'),
(3, '7478452147'),
(4, '4005808675753');

CREATE TABLE IF NOT EXISTS `products_ingredient` (
  `id_product` int(11) NOT NULL,
  `id_ingredient` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `products_ingredient` (`id_product`, `id_ingredient`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(4, 1),
(4, 2),
(4, 3),
(4, 4);

CREATE TABLE IF NOT EXISTS `ingredients` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `desc` text CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

INSERT INTO `ingredients` (`id`, `name`, `desc`) VALUES
(1, 'Ingredient 1', 'Test'),
(2, 'Ingredient 2', 'Test'),
(3, 'Ingredient 3', 'Test'),
(4, 'Ingredient 4', 'Test');

CREATE TABLE IF NOT EXISTS `reports_modifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_ingredient` int(11) NOT NULL,
  `user_desc` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0-authorization, 1-accept, 2-cancell',
  `id_operator` int(11) DEFAULT NULL,
  `date_register_notification` datetime NOT NULL,
  `date_implement_change` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `reports_modifications_products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_product` int(11) NOT NULL,
  `user_desc` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0-authorization, 1-accept, 2-cancell',
  `id_operator` int(11) DEFAULT NULL,
  `date_register_notification` datetime NOT NULL,
  `date_implement_change` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `reports_suggestions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `desc` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0-authorization, 1-accept, 2-cancell',
  `id_operator` int(11) DEFAULT NULL,
  `date_register_notification` datetime NOT NULL,
  `date_implement_change` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `reports_suggestions_products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0-authorization, 1-accept, 2-cancell',
  `id_operator` int(11) DEFAULT NULL,
  `date_register_notification` datetime NOT NULL,
  `date_implement_change` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;
