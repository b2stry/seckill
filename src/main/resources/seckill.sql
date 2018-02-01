
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(64) DEFAULT NULL COMMENT '商品的图片',
  `goods_detail` longtext DEFAULT NULL COMMENT '商品的详情介绍',
  `goods_price` decimal(10,2) DEFAULT 0.00 COMMENT '商品单价',
  `goods_stock` int(11) DEFAULT 0 COMMENT '商品库存，-1表示没有限制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES ('1', 'iPhone X', 'Apple iPhone X (A1865) 64GB 深空灰色 移动联通电信4G手机', '/img/iphonex.png', 'Apple iPhone X (A1865) 64GB 深空灰色 移动联通电信4G手机', '8388.00', '8888');
INSERT INTO `goods` VALUES ('2', 'MacBook Pro', 'Apple MacBook Pro 15.4英寸笔记本电脑 银色', '/img/macbookpro.png', 'i7处理器，大容量固态硬盘，外设接口丰富，配备绚丽的retina显示屏，强大而专业！选购AppleCare Protection Plan，获得长达3年来自Apple的额外硬件服务选项。购买勾选：保障服务、原厂保3年。', '13599.00', '6666');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `delivery_addr_id` bigint(20) DEFAULT NULL COMMENT '收货地址ID',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '冗余过来的商品名称',
  `goods_count` int(11) DEFAULT NULL COMMENT '商品数量',
  `goods_price` decimal(10,2) DEFAULT 0.00 COMMENT '商品单价',
  `order_channel` tinyint(4) DEFAULT 0 COMMENT '1pc, 2android, 3ios',
  `status` tinyint(4) DEFAULT 0 COMMENT '订单状态，0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `create_time` datetime DEFAULT NULL ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=185 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('151', '13000000000', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('152', '13000000002', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('153', '13000000014', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('154', '13000000020', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('155', '13000000023', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('156', '13000000015', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('157', '13000000011', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('158', '13000000007', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('159', '13000000019', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('160', '13000000003', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('161', '13000000016', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('162', '13000000017', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('163', '13000000010', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('164', '13000000006', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('165', '13000000032', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('166', '13000000029', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('167', '13000000034', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('168', '13000000037', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('169', '13000000038', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('170', '13000000039', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('171', '13000000049', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('172', '13000000013', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('173', '13000000012', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('174', '13000000001', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('175', '13000000018', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('176', '13000000024', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('177', '13000000004', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('178', '13000000008', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('179', '13000000005', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('180', '13000000036', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('181', '13000000027', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('182', '13000000009', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('183', '13000000026', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');
INSERT INTO `order_info` VALUES ('184', '13000000030', '1', null, 'iPhone X', null, '0.01', '1', '0', null, '2018-02-01 19:24:30');

-- ----------------------------
-- Table structure for seckill_goods
-- ----------------------------
DROP TABLE IF EXISTS `seckill_goods`;
CREATE TABLE `seckill_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀的商品',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `seckill_price` decimal(10,2) DEFAULT 0.00 COMMENT '秒杀价',
  `stock_count` int(11) DEFAULT NULL COMMENT '库存数量',
  `start_time` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of seckill_goods
-- ----------------------------
INSERT INTO `seckill_goods` VALUES ('1', '1', '0.01', '-14', '2017-11-05 15:18:00', '2018-02-13 14:00:18');
INSERT INTO `seckill_goods` VALUES ('2', '2', '0.01', '9', '2018-02-05 14:00:00', '2018-02-13 14:00:18');

-- ----------------------------
-- Table structure for seckill_order
-- ----------------------------
DROP TABLE IF EXISTS `seckill_order`;
CREATE TABLE `seckill_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `order_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6391 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of seckill_order
-- ----------------------------

-- ----------------------------
-- Table structure for seckill_user
-- ----------------------------
DROP TABLE IF EXISTS `seckill_user`;
CREATE TABLE `seckill_user` (
  `id` bigint(20) NOT NULL COMMENT '用户ID，手机号码',
  `nickname` varchar(255) NOT NULL COMMENT '昵称',
  `password` varchar(32) DEFAULT 'b7797cce01b4b131b433b6acf4add449' COMMENT 'MD5(MD5(pass明文+固定salt)+随机salt)默认123456',
  `salt` varchar(10) DEFAULT '1a2b3c4d',
  `head` varchar(128) DEFAULT NULL COMMENT '头像，云存储的ID',
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime DEFAULT NULL COMMENT '上次登录时间',
  `login_count` int(11) DEFAULT 0 COMMENT '登录次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of seckill_user
-- ----------------------------
INSERT INTO `seckill_user` VALUES ('15812341234', 'shallowan', 'b7797cce01b4b131b433b6acf4add449', '1a2b3c4d', null, '2018-01-31 22:30:04', '2018-01-31 22:30:37', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '11111');
INSERT INTO `user` VALUES ('2', '22222');
