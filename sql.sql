#########################seata_order库
use database seata_order;
CREATE TABLE `orders` (
  `id` mediumint(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `COUNT` int(11) DEFAULT NULL COMMENT '数量',
  `pay_amount` decimal(10,2) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `add_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8

#########################seata_pay库
use database seata_pay;
DROP TABLE account;
CREATE TABLE `account` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` BIGINT(11) DEFAULT NULL COMMENT '用户id',
  `total` DECIMAL(10,0) DEFAULT NULL COMMENT '总额度',
  `used` DECIMAL(10,0) DEFAULT NULL COMMENT '已用余额',
  `balance` DECIMAL(10,0) DEFAULT '0' COMMENT '剩余可用额度',
  `last_update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO `seata_pay`.`account` (`id`, `user_id`, `total`, `used`, `balance`) VALUES ('1', '1', '1000', '0', '100');

#########################seata_storage库
use database seata_storage;
CREATE TABLE `storage` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `product_id` BIGINT(11) DEFAULT NULL COMMENT '产品id',
  `total` INT(11) DEFAULT NULL COMMENT '总库存',
  `used` INT(11) DEFAULT NULL COMMENT '已用库存',
  `residue` INT(11) DEFAULT NULL COMMENT '剩余库存',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
INSERT INTO `seata_storage`.`storage` (`id`, `product_id`, `total`, `used`, `residue`) VALUES ('1', '1', '100', '0', '100');