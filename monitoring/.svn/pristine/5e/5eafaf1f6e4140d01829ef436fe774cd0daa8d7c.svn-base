/*
Navicat MySQL Data Transfer

Source Server         : 192.168.13.187
Source Server Version : 50624
Source Host           : 192.168.13.187:50030
Source Database       : monitor_center

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2015-11-23 14:47:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_base
-- ----------------------------
DROP TABLE IF EXISTS `user_base`;
CREATE TABLE `user_base` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_name` varchar(30) NOT NULL COMMENT '用户名称',
  `user_real_name` varchar(30) DEFAULT NULL COMMENT '用户真实姓名',
  `user_passwd` varchar(50) NOT NULL COMMENT '用户密码',
  `user_mobile` varchar(20) DEFAULT NULL COMMENT '用户手机',
  `user_mail` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `user_status` int(2) NOT NULL COMMENT '用户状态',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user_base
-- ----------------------------
INSERT INTO `user_base` VALUES ('1', 'admin', '超级管理员', '0192023a7bbd73250516f069df18b500', '18500061826', 'zengshuai@ttmv.com', '系统超级管理员', '1', '2015-11-01 11:11:11');
