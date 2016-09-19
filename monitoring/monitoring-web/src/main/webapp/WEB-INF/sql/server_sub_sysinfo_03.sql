/*
Navicat MySQL Data Transfer

Source Server         : 192.168.13.187
Source Server Version : 50624
Source Host           : 192.168.13.187:50030
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2015-11-05 18:42:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for server_sub_sysinfo
-- ----------------------------
DROP TABLE IF EXISTS `server_sub_sysinfo`;
CREATE TABLE `server_sub_sysinfo` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `sys_type` varchar(100) DEFAULT NULL COMMENT '系统类型',
  `sys_name` varchar(100) DEFAULT NULL COMMENT '子系统名称',
  `sys_key` varchar(100) DEFAULT NULL COMMENT '子系统标示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of server_sub_sysinfo
-- ----------------------------
INSERT INTO `server_sub_sysinfo` VALUES ('19', '1', '流媒体转发', 'AvServerTransmitService', null);
INSERT INTO `server_sub_sysinfo` VALUES ('20', '1', '流媒体控制', 'AvServerControlService', null);
INSERT INTO `server_sub_sysinfo` VALUES ('21', '2', 'LBS', 'ImShowLbsService', null);
INSERT INTO `server_sub_sysinfo` VALUES ('22', '2', 'MDS', 'ImShowMdsService', null);
INSERT INTO `server_sub_sysinfo` VALUES ('23', '2', 'MTS', 'ImShowMtsService', null);
INSERT INTO `server_sub_sysinfo` VALUES ('24', '2', 'TAS', 'ImShowTasService', null);
INSERT INTO `server_sub_sysinfo` VALUES ('25', '2', 'UMS', 'ImShowUmsService', null);
INSERT INTO `server_sub_sysinfo` VALUES ('26', '2', 'PRS', 'ImShowPrsService', null);
INSERT INTO `server_sub_sysinfo` VALUES ('27', '2', 'MSS', 'ImShowMssService', null);
INSERT INTO `server_sub_sysinfo` VALUES ('28', '2', 'RMS', 'ImShowRmsService', null);
INSERT INTO `server_sub_sysinfo` VALUES ('29', '2', 'HTTPPROXY', 'ImShowHttpProxyService', null);
INSERT INTO `server_sub_sysinfo` VALUES ('30', '3', 'PHP直播', 'PhpLiveService', null);
INSERT INTO `server_sub_sysinfo` VALUES ('31', '3', 'PHP点播', 'PhpVideoService', null);
INSERT INTO `server_sub_sysinfo` VALUES ('32', '3', 'PHP管控', 'PhpManageService', null);
INSERT INTO `server_sub_sysinfo` VALUES ('33', '4', '用户中心', 'DcUserCenterService', null);
INSERT INTO `server_sub_sysinfo` VALUES ('34', '4', '支付中心', 'DcPayCenterService', null);
INSERT INTO `server_sub_sysinfo` VALUES ('35', '5', '转码服务', 'TranscodingService', null);
INSERT INTO `server_sub_sysinfo` VALUES ('36', '5', '截图服务', 'ScreenshotService', null);
