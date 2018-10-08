/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : bookstore

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-10-08 17:37:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `bid` char(32) NOT NULL,
  `bname` varchar(100) DEFAULT NULL,
  `price` decimal(5,1) DEFAULT NULL,
  `author` varchar(20) DEFAULT NULL,
  `image` varchar(200) DEFAULT NULL,
  `cid` char(32) DEFAULT NULL,
  `del` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`bid`),
  KEY `cid` (`cid`),
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `category` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', 'Java编程思想（第3版）', '76.5', '布鲁斯·艾克儿', 'book_img/9317290-1_l.jpg', '1', '0');
INSERT INTO `book` VALUES ('2', 'Java核心技术卷1', '68.5', 'qdmmy6', 'book_img/20285763-1_l.jpg', '1', '0');
INSERT INTO `book` VALUES ('3', 'Java就业培训教程', '39.9', '张孝祥', 'book_img/8758723-1_l.jpg', '1', '0');
INSERT INTO `book` VALUES ('4', 'Head First java', '47.5', '（美）塞若', 'book_img/9265169-1_l.jpg', '1', '0');
INSERT INTO `book` VALUES ('5', 'JavaWeb开发详解', '83.3', '孙鑫', 'book_img/22788412-1_l.jpg', '2', '0');
INSERT INTO `book` VALUES ('6', 'Struts2深入详解', '63.2', '孙鑫', 'book_img/20385925-1_l.jpg', '2', '0');
INSERT INTO `book` VALUES ('6772ADDEB1BF4FFB84564B9EB13267B3', 'SpringMVC学习指南', '123.8', 'knight', 'book_img/25D4DFAEEA0743FA8D6815252B7B431F_springMVC.jpg', '1', '1');
INSERT INTO `book` VALUES ('7', '精通Hibernate', '30.0', '孙卫琴', 'book_img/8991366-1_l.jpg', '2', '0');
INSERT INTO `book` VALUES ('8', '精通Spring2.x', '63.2', '陈华雄', 'book_img/20029394-1_l.jpg', 'D87E6555F78D45B6A23392289A5714D5', '0');
INSERT INTO `book` VALUES ('9', 'Javascript权威指南', '93.6', '（美）弗兰纳根', 'book_img/22722790-1_l.jpg', '3', '0');

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `cid` char(32) NOT NULL,
  `cname` varchar(100) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', 'JavaSE');
INSERT INTO `category` VALUES ('2', 'JavaEE');
INSERT INTO `category` VALUES ('3', 'Javascript');
INSERT INTO `category` VALUES ('42C36E2635CB4982913CCA7B227566F5', 'SpringMVC');
INSERT INTO `category` VALUES ('D87E6555F78D45B6A23392289A5714D5', 'Spring');

-- ----------------------------
-- Table structure for `orderitem`
-- ----------------------------
DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE `orderitem` (
  `iid` char(32) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `subtotal` decimal(10,2) DEFAULT NULL,
  `oid` char(32) DEFAULT NULL,
  `bid` char(32) DEFAULT NULL,
  PRIMARY KEY (`iid`),
  KEY `oid` (`oid`),
  KEY `bid` (`bid`),
  CONSTRAINT `orderitem_ibfk_1` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`),
  CONSTRAINT `orderitem_ibfk_2` FOREIGN KEY (`bid`) REFERENCES `book` (`bid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderitem
-- ----------------------------
INSERT INTO `orderitem` VALUES ('028214D9FEC84553B7E65CFFA23C161C', '2', '151.20', '7743B2AF866346D9B05F1E919399075A', '1');
INSERT INTO `orderitem` VALUES ('093D93EE7C2A444CBE843F11BF48B46B', '1', '93.60', '7743B2AF866346D9B05F1E919399075A', '9');
INSERT INTO `orderitem` VALUES ('09DC3A7E46344092A35F6F55BB33916B', '1', '63.20', 'E727C883E6FF41188B8A5FDCAC397D3C', '8');
INSERT INTO `orderitem` VALUES ('0AABCF6B4C6C4BFEB0F43EF12F458850', '1', '63.20', '12A527BF97334D86ADE1E35F166D9AB2', '6');
INSERT INTO `orderitem` VALUES ('259C3D7514AF442EA676C1438CBDFF46', '4', '374.40', '87C6F9E76FD54B13A7421A6CBB4A1093', '9');
INSERT INTO `orderitem` VALUES ('57D6570133514267B93C5105712599AA', '1', '30.00', '12A527BF97334D86ADE1E35F166D9AB2', '7');
INSERT INTO `orderitem` VALUES ('AA9B4C8F3CE14247A22AB321EB69726B', '1', '93.60', '97F0723F91AF47BFBECC32F3B74D3238', '9');
INSERT INTO `orderitem` VALUES ('C50B51203B6348A98293F617E9191789', '1', '76.50', '97F0723F91AF47BFBECC32F3B74D3238', '1');
INSERT INTO `orderitem` VALUES ('C5EF036EE6E247D5B4FD26C0773A63D9', '2', '187.20', 'E727C883E6FF41188B8A5FDCAC397D3C', '9');
INSERT INTO `orderitem` VALUES ('C85722DDDA334730925C0D854C5315F1', '1', '63.20', '97F0723F91AF47BFBECC32F3B74D3238', '6');
INSERT INTO `orderitem` VALUES ('CBF788FB1AC34A7A9F35DC8FD5FAFC96', '1', '83.30', '79968162C1A949EE903CCFE02F9E574A', '5');
INSERT INTO `orderitem` VALUES ('CDE76B8AF2F04DD6BE33D8EF2283A19F', '3', '205.50', '2F065A9F65004AD28FE4D5AB9A421A17', '2');

-- ----------------------------
-- Table structure for `orders`
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `oid` char(32) NOT NULL,
  `ordertime` datetime DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `state` smallint(1) DEFAULT NULL,
  `uid` char(32) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  KEY `uid` (`uid`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `tb_user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('12A527BF97334D86ADE1E35F166D9AB2', '2018-07-11 10:46:25', '93.20', '4', '1BCF1777269548969FB7B765BDFDD6B8', null);
INSERT INTO `orders` VALUES ('2F065A9F65004AD28FE4D5AB9A421A17', '2018-07-13 15:11:19', '205.50', '1', '1BCF1777269548969FB7B765BDFDD6B8', null);
INSERT INTO `orders` VALUES ('7743B2AF866346D9B05F1E919399075A', '2018-07-11 16:19:03', '244.80', '4', '1BCF1777269548969FB7B765BDFDD6B8', null);
INSERT INTO `orders` VALUES ('79968162C1A949EE903CCFE02F9E574A', '2018-07-25 11:17:15', '83.30', '1', '1BCF1777269548969FB7B765BDFDD6B8', null);
INSERT INTO `orders` VALUES ('87C6F9E76FD54B13A7421A6CBB4A1093', '2018-07-11 12:01:27', '374.40', '1', '1BCF1777269548969FB7B765BDFDD6B8', null);
INSERT INTO `orders` VALUES ('97F0723F91AF47BFBECC32F3B74D3238', '2018-08-05 17:39:24', '233.30', '1', '395AD59732D4424DAE6BDF838CB728F1', null);
INSERT INTO `orders` VALUES ('E727C883E6FF41188B8A5FDCAC397D3C', '2018-07-11 10:50:04', '250.40', '2', '1BCF1777269548969FB7B765BDFDD6B8', null);

-- ----------------------------
-- Table structure for `t_book`
-- ----------------------------
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book` (
  `bid` char(32) NOT NULL,
  `bname` varchar(100) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `category` int(11) DEFAULT NULL,
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_book
-- ----------------------------
INSERT INTO `t_book` VALUES ('b1', 'JavaSE_1', '10.00', '1');
INSERT INTO `t_book` VALUES ('b10', 'springStudy', '10.00', '3');
INSERT INTO `t_book` VALUES ('b2', 'JavaSE_2', '15.00', '1');
INSERT INTO `t_book` VALUES ('b3', 'JavaSE_3', '20.00', '1');
INSERT INTO `t_book` VALUES ('b4', 'JavaSE_4', '25.00', '1');
INSERT INTO `t_book` VALUES ('b5', 'JavaEE_1', '30.00', '2');
INSERT INTO `t_book` VALUES ('b6', 'JavaEE_2', '35.00', '2');
INSERT INTO `t_book` VALUES ('b7', 'JavaEE_3', '40.00', '2');
INSERT INTO `t_book` VALUES ('b8', 'Java_framework_1', '45.00', '3');
INSERT INTO `t_book` VALUES ('b9', 'Java_framework_2', '50.00', '3');

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `uid` char(32) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `code` char(64) NOT NULL,
  `state` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1BCF1777269548969FB7B765BDFDD6B8', '李潇潇', '123', '1766376716@qq.com', 'B96F188D601745AEB52C8E50FA1BB33AD7785365274344E8B28869769A279ACC', '1');
INSERT INTO `tb_user` VALUES ('395AD59732D4424DAE6BDF838CB728F1', 'liping', '051609', '1120077645@qq.com', 'CA8CE9501BD3436581804D2F44C79086F74BEC1781BE4480B22008938D6674B4', '1');
INSERT INTO `tb_user` VALUES ('662E69D8E25D4A838F9EFBE2C5E27ED0', '123', '123', '123@qq.com', 'E53B83A76C2F4BBC96CD4A6A659ECCF0D5F2B4DB890A4B568296DA8A062DB885', '0');
INSERT INTO `tb_user` VALUES ('98667D236059441CB8161E2C4151A8E0', 'admin', '123', 'zhangSan@163.com', 'F77A5A3A357F4C97A95A04077FDB9704EFDF1E5995C74EC3959DEED5EF97A5BE', '1');
