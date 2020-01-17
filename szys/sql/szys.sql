/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50638
Source Host           : localhost:3306
Source Database       : szys

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2020-01-17 17:20:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for numrank
-- ----------------------------
DROP TABLE IF EXISTS `numrank`;
CREATE TABLE `numrank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) NOT NULL,
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '123分别表示（10道，30道，50道）',
  `calcu` int(11) NOT NULL DEFAULT '0' COMMENT '1234表示加减乘除',
  `digit` int(11) NOT NULL DEFAULT '0' COMMENT '12345表示12345位',
  `operate` int(11) NOT NULL DEFAULT '0' COMMENT '操作数 2 3',
  `timelast` double(11,1) NOT NULL DEFAULT '0.0' COMMENT '耗时',
  `accuracy` double(11,1) NOT NULL DEFAULT '0.0' COMMENT '正确率',
  `data` text NOT NULL COMMENT '答题数据',
  `insert_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 COMMENT='限定数目模式';

-- ----------------------------
-- Table structure for timerank
-- ----------------------------
DROP TABLE IF EXISTS `timerank`;
CREATE TABLE `timerank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) NOT NULL,
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '123分别表示（10道，30道，50道）',
  `calcu` int(11) NOT NULL DEFAULT '0' COMMENT '1234表示加减乘除',
  `digit` int(11) NOT NULL DEFAULT '0' COMMENT '12345表示12345位',
  `operate` int(11) NOT NULL DEFAULT '0' COMMENT '操作数 2 3',
  `timelast` double(11,1) NOT NULL DEFAULT '0.0' COMMENT '耗时',
  `accuracy` double(11,1) NOT NULL DEFAULT '0.0' COMMENT '正确率',
  `data` text NOT NULL COMMENT '答题数据',
  `insert_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 COMMENT='限定数目模式';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `is_new` int(11) NOT NULL,
  `register_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '注册时间',
  `update_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '登陆时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
