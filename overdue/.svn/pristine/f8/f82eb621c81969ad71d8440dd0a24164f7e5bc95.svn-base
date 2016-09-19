/*
Navicat MySQL Data Transfer

Source Server         : 192.168.13.152
Source Server Version : 50624
Source Host           : 192.168.13.152:50030
Source Database       : expire_center

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2015-12-21 15:53:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for good_number_expire
-- ----------------------------
DROP TABLE IF EXISTS `good_number_expire`;
CREATE TABLE `good_number_expire` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
  `end_time` datetime DEFAULT NULL COMMENT '到期时间',
  `remind_time` datetime DEFAULT NULL,
  `good_number_id` bigint(20) DEFAULT NULL COMMENT '靓号ID',
  `flag` varchar(10) DEFAULT NULL COMMENT '是否绑定',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='靓号时间到期表';

-- ----------------------------
-- Table structure for luxury_expire
-- ----------------------------
DROP TABLE IF EXISTS `luxury_expire`;
CREATE TABLE `luxury_expire` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
  `end_time` datetime DEFAULT NULL COMMENT '到期时间',
  `remind_time` datetime DEFAULT NULL,
  `car_id` bigint(20) DEFAULT NULL COMMENT '豪车ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='豪车时间到期表';

-- ----------------------------
-- Table structure for nobility_expire
-- ----------------------------
DROP TABLE IF EXISTS `nobility_expire`;
CREATE TABLE `nobility_expire` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
  `end_time` datetime DEFAULT NULL COMMENT '到期时间',
  `remind_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='爵位时间到期表';

-- ----------------------------
-- Table structure for scan_record
-- ----------------------------
DROP TABLE IF EXISTS `scan_record`;
CREATE TABLE `scan_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `expire_type` int(11) DEFAULT NULL COMMENT '到期业务类型',
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8 COMMENT='各类到期业务扫描记录表';

-- ----------------------------
-- Table structure for user_forbidden_expire
-- ----------------------------
DROP TABLE IF EXISTS `user_forbidden_expire`;
CREATE TABLE `user_forbidden_expire` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
  `end_time` datetime DEFAULT NULL COMMENT '到期时间',
  `remind_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户冻结时间到期表';

-- ----------------------------
-- Table structure for vip_expire
-- ----------------------------
DROP TABLE IF EXISTS `vip_expire`;
CREATE TABLE `vip_expire` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
  `end_time` datetime DEFAULT NULL COMMENT '到期时间',
  `remind_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='VIP用户时间到期表';


DROP TABLE IF EXISTS `cmp`;
CREATE TABLE `cmp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `type` varchar(50) DEFAULT NULL,
  `tag` varchar(50) DEFAULT NULL,
  `warntime` varchar(50) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
