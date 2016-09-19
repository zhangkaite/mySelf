/*
Navicat MySQL Data Transfer

Source Server         : 192.168.13.186
Source Server Version : 50624
Source Host           : 192.168.13.186:50030
Source Database       : monitor_center

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2015-10-30 17:09:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for alerter_info
-- ----------------------------
DROP TABLE IF EXISTS `alerter_info`;
CREATE TABLE `alerter_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `alerter_name` varchar(50) DEFAULT NULL COMMENT '报警器名称',
  `alerter_type` varchar(10) DEFAULT NULL COMMENT '通知类型',
  `alerter_count` int(10) DEFAULT NULL COMMENT '报警次数',
  `alerter_users` varchar(500) DEFAULT NULL COMMENT '通知用户',
  `alerter_msg` int(5) DEFAULT NULL COMMENT '报警信息模板',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='报警器信息';

-- ----------------------------
-- Records of alerter_info
-- ----------------------------
INSERT INTO `alerter_info` VALUES ('32', '默认报警器', '2', '1', '1|2', '1');
