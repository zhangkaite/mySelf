/*
Navicat MySQL Data Transfer

Source Server         : 192.168.13.186
Source Server Version : 50624
Source Host           : 192.168.13.186:50030
Source Database       : monitor_test

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2015-11-23 14:41:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for threshold_info
-- ----------------------------
DROP TABLE IF EXISTS `threshold_info`;
CREATE TABLE `threshold_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `threshold_name` varchar(100) DEFAULT NULL COMMENT '阀值名称',
  `threshold_value` varchar(100) DEFAULT NULL COMMENT '阀值指标',
  `threshold_type` varchar(50) DEFAULT NULL COMMENT '阀值所属系统',
  `threshold_alerter_ids` varchar(50) DEFAULT NULL,
  `threshold_set` bigint(20) DEFAULT NULL COMMENT '阀值系统',
  `threshold_show_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=591 DEFAULT CHARSET=utf8 COMMENT='阀值信息表';

-- ----------------------------
-- Records of threshold_info
-- ----------------------------
INSERT INTO `threshold_info` VALUES ('6', 'CPU', '80', 'AvServerTransmitService', '32', '1', 'CPU占用率');
INSERT INTO `threshold_info` VALUES ('7', 'Disk', '800', 'AvServerTransmitService', '32', '10', '硬盘空间');
INSERT INTO `threshold_info` VALUES ('8', 'MEM', '3500', 'AvServerTransmitService', '32', '10', '内存占用');
INSERT INTO `threshold_info` VALUES ('11', 'UdxConnectionLength', '900', 'AvServerTransmitService', '32', null, '当前的连接列表长度');
INSERT INTO `threshold_info` VALUES ('14', 'RoomCount', '900', 'AvServerTransmitService', '32', null, '房间数量');
INSERT INTO `threshold_info` VALUES ('22', 'CPU', '9999', 'PhpLiveService', '32', null, 'CPU占用率');
INSERT INTO `threshold_info` VALUES ('23', 'Disk', '9999', 'PhpLiveService', '32', null, '硬盘空间');
INSERT INTO `threshold_info` VALUES ('24', 'MEM', '9999', 'PhpLiveService', '32', null, '内存占用');
INSERT INTO `threshold_info` VALUES ('25', 'SysLoad', '9999', 'PhpLiveService', '32', null, '系统平均负载系数');
INSERT INTO `threshold_info` VALUES ('26', 'NetConnections', '9999', 'PhpLiveService', '32', null, '网络连接数');
INSERT INTO `threshold_info` VALUES ('32', 'CPU', '80', 'AvServerControlService', '32', null, 'CPU占用率');
INSERT INTO `threshold_info` VALUES ('33', 'Disk', '4000', 'AvServerControlService', '32', null, '硬盘空间');
INSERT INTO `threshold_info` VALUES ('34', 'MEM', '5000', 'AvServerControlService', '32', null, '内存占用');
INSERT INTO `threshold_info` VALUES ('35', 'CreatedRoomCount', '1000', 'AvServerControlService', '32', null, '创建房间的数量');
INSERT INTO `threshold_info` VALUES ('37', 'InputMessages', '100000', 'AvServerControlService', '32', null, '输入消息数量');
INSERT INTO `threshold_info` VALUES ('38', 'OutputMessages', '100000', 'AvServerControlService', '32', null, '输出消息数量');
INSERT INTO `threshold_info` VALUES ('39', 'CPU', '9999', 'ScreenshotService', '32', null, 'CPU占用率');
INSERT INTO `threshold_info` VALUES ('40', 'Disk', '9999', 'ScreenshotService', '32', null, '硬盘空间');
INSERT INTO `threshold_info` VALUES ('41', 'MEM', '9999', 'ScreenshotService', '32', null, '内存占用');
INSERT INTO `threshold_info` VALUES ('43', 'CPU', '9999', 'TranscodingService', '32', null, 'CPU占用率');
INSERT INTO `threshold_info` VALUES ('44', 'Disk', '9999', 'TranscodingService', '32', null, '硬盘空间');
INSERT INTO `threshold_info` VALUES ('45', 'MEM', '9999', 'TranscodingService', '32', null, '内存占用');
INSERT INTO `threshold_info` VALUES ('47', 'CPU', '80', 'ImShowLbsService', '32', null, 'CPU占用率');
INSERT INTO `threshold_info` VALUES ('48', 'Disk', '900', 'ImShowLbsService', '32', null, '硬盘空间');
INSERT INTO `threshold_info` VALUES ('49', 'MEM', '900', 'ImShowLbsService', '32', null, '内存占用');
INSERT INTO `threshold_info` VALUES ('50', 'WorkThread', '900', 'ImShowLbsService', '32', null, '工作线程');
INSERT INTO `threshold_info` VALUES ('54', 'CPU', '9999', 'ImShowMdsService', '32', null, 'CPU占用率');
INSERT INTO `threshold_info` VALUES ('55', 'Disk', '9999', 'ImShowMdsService', '32', null, '硬盘空间');
INSERT INTO `threshold_info` VALUES ('56', 'MEM', '9999', 'ImShowMdsService', '32', null, '内存占用');
INSERT INTO `threshold_info` VALUES ('57', 'WorkThread', '9999', 'ImShowMdsService', '32', null, '工作线程');
INSERT INTO `threshold_info` VALUES ('58', 'CPU', '9999', 'ImShowMtsService', '32', null, 'CPU占用率');
INSERT INTO `threshold_info` VALUES ('59', 'Disk', '9999', 'ImShowMtsService', '32', null, '硬盘空间');
INSERT INTO `threshold_info` VALUES ('60', 'MEM', '9999', 'ImShowMtsService', '32', null, '内存占用');
INSERT INTO `threshold_info` VALUES ('61', 'WorkThread', '9999', 'ImShowMtsService', '32', null, '工作线程');
INSERT INTO `threshold_info` VALUES ('69', 'CPU', '9999', 'ImShowTasService', '32', null, 'CPU占用率');
INSERT INTO `threshold_info` VALUES ('70', 'Disk', '9999', 'ImShowTasService', '32', null, '硬盘空间');
INSERT INTO `threshold_info` VALUES ('71', 'MEM', '9999', 'ImShowTasService', '32', null, '内存占用');
INSERT INTO `threshold_info` VALUES ('72', 'WorkThread', '9999', 'ImShowTasService', '32', null, '工作线程');
INSERT INTO `threshold_info` VALUES ('73', 'CPU', '9999', 'ImShowUmsService', '32', null, 'CPU占用率');
INSERT INTO `threshold_info` VALUES ('74', 'Disk', '9999', 'ImShowUmsService', '32', null, '硬盘空间');
INSERT INTO `threshold_info` VALUES ('75', 'MEM', '9999', 'ImShowUmsService', '32', null, '内存占用');
INSERT INTO `threshold_info` VALUES ('76', 'WorkThread', '9999', 'ImShowUmsService', '32', null, '工作线程');
INSERT INTO `threshold_info` VALUES ('77', 'CPU', '9999', 'ImShowPrsService', '32', null, 'CPU占用率');
INSERT INTO `threshold_info` VALUES ('78', 'Disk', '9999', 'ImShowPrsService', '32', null, '硬盘空间');
INSERT INTO `threshold_info` VALUES ('79', 'MEM', '9999', 'ImShowPrsService', '32', null, '内存占用');
INSERT INTO `threshold_info` VALUES ('80', 'WorkThread', '9999', 'ImShowPrsService', '32', null, '工作线程');
INSERT INTO `threshold_info` VALUES ('81', 'CPU', '9999', 'ImShowMssService', '32', null, 'CPU占用率');
INSERT INTO `threshold_info` VALUES ('82', 'Disk', '9999', 'ImShowMssService', '32', null, '硬盘空间');
INSERT INTO `threshold_info` VALUES ('83', 'MEM', '9999', 'ImShowMssService', '32', null, '内存占用');
INSERT INTO `threshold_info` VALUES ('84', 'WorkThread', '9999', 'ImShowMssService', '32', null, '工作线程');
INSERT INTO `threshold_info` VALUES ('100', 'CPU', '9999', 'ImShowHttpProxyService', '32', null, 'CPU占用率');
INSERT INTO `threshold_info` VALUES ('101', 'Disk', '9999', 'ImShowHttpProxyService', '32', null, '硬盘空间');
INSERT INTO `threshold_info` VALUES ('102', 'MEM', '9999', 'ImShowHttpProxyService', '32', null, '内存占用');
INSERT INTO `threshold_info` VALUES ('103', 'WorkThread', '999', 'ImShowHttpProxyService', '32', null, '工作线程');
INSERT INTO `threshold_info` VALUES ('545', 'CPU', '9999', 'ImShowRmsService', '32', null, 'CPU占用率');
INSERT INTO `threshold_info` VALUES ('546', 'Disk', '9999', 'ImShowRmsService', '32', null, '硬盘空间');
INSERT INTO `threshold_info` VALUES ('547', 'MEM', '9999', 'ImShowRmsService', '32', null, '内存占用');
INSERT INTO `threshold_info` VALUES ('548', 'WorkThread', '9999', 'ImShowRmsService', '32', null, '工作线程');
INSERT INTO `threshold_info` VALUES ('551', 'GroupCount', '9999', 'ImShowMdsService', '32', null, '群组数量');
INSERT INTO `threshold_info` VALUES ('553', 'ClientConnectionCount', '9999', 'ImShowMtsService', '32', null, '客户端连接数量');
INSERT INTO `threshold_info` VALUES ('562', 'NetLoad', '1024', 'PhpLiveService', '32', null, '网络流量Mb数');
INSERT INTO `threshold_info` VALUES ('563', 'CPU', '9999', 'PhpManageService', '32', null, 'CPU占用率');
INSERT INTO `threshold_info` VALUES ('564', 'Disk', '9999', 'PhpManageService', '32', null, '硬盘空间');
INSERT INTO `threshold_info` VALUES ('565', 'MEM', '9999', 'PhpManageService', '32', null, '内存占用');
INSERT INTO `threshold_info` VALUES ('566', 'SysLoad', '9999', 'PhpManageService', '32', null, '系统平均负载系数');
INSERT INTO `threshold_info` VALUES ('567', 'NetConnections', '9999', 'PhpManageService', '32', null, '网络连接数');
INSERT INTO `threshold_info` VALUES ('573', 'NetLoad', '1024', 'PhpManageService', '32', null, '网络流量Mb数');
INSERT INTO `threshold_info` VALUES ('578', 'CPU', '9999', 'PhpVideoService', '32', null, 'CPU占用率');
INSERT INTO `threshold_info` VALUES ('579', 'Disk', '9999', 'PhpVideoService', '32', null, '硬盘空间');
INSERT INTO `threshold_info` VALUES ('580', 'MEM', '9999', 'PhpVideoService', '32', null, '内存占用');
INSERT INTO `threshold_info` VALUES ('581', 'SysLoad', '9999', 'PhpVideoService', '32', null, '系统平均负载系数');
INSERT INTO `threshold_info` VALUES ('582', 'NetConnections', '9999', 'PhpVideoService', '32', null, '网络连接数');
INSERT INTO `threshold_info` VALUES ('588', 'NetLoad', '1024', 'PhpVideoService', '32', null, '网络流量Mb数');
INSERT INTO `threshold_info` VALUES ('589', 'CPU', '100', 'DcUserCenterService', '32', null, 'CPU');
INSERT INTO `threshold_info` VALUES ('590', 'CPU', '100', 'DcPayCenterService', '32', null, 'CPU');
