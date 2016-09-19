/*
Navicat MySQL Data Transfer

Source Server         : 192.168.13.186
Source Server Version : 50624
Source Host           : 192.168.13.186:50030
Source Database       : monitor_center

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2015-10-30 17:09:13
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
-- Table structure for alerter_record
-- ----------------------------
DROP TABLE IF EXISTS `alerter_record`;
CREATE TABLE `alerter_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `ip` varchar(20) DEFAULT NULL COMMENT '报警服务的ip',
  `server_type` varchar(50) DEFAULT NULL COMMENT '报警服务',
  `alert_type` varchar(10) DEFAULT NULL COMMENT 'baojingleixing',
  `alert_time` datetime DEFAULT NULL COMMENT '报警时间',
  `alert_msg` text COMMENT '报警信息',
  `server_id` varchar(20) DEFAULT NULL COMMENT '服务器ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=357 DEFAULT CHARSET=utf8 COMMENT='报警历史表记录信息';

-- ----------------------------
-- Table structure for data_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `data_dictionary`;
CREATE TABLE `data_dictionary` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `data_key` varchar(30) NOT NULL COMMENT '字典KEY',
  `data_value` varchar(30) NOT NULL COMMENT '字典VAlUE',
  `type` varchar(30) DEFAULT NULL,
  `extend_field2` varchar(30) DEFAULT NULL COMMENT '扩展字段2',
  `extend_field3` varchar(30) DEFAULT NULL COMMENT '扩展字段3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- ----------------------------
-- Table structure for hp_server_data
-- ----------------------------
DROP TABLE IF EXISTS `hp_server_data`;
CREATE TABLE `hp_server_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `server_type` varchar(30) DEFAULT NULL COMMENT '服务器类型',
  `server_id` varchar(30) DEFAULT NULL COMMENT '服务器ID',
  `ip` varchar(30) DEFAULT NULL COMMENT '服务器IP',
  `port` int(5) unsigned DEFAULT NULL COMMENT '服务器端口',
  `timestamp` datetime DEFAULT NULL COMMENT '采样时间',
  `work_thread_sum` int(11) DEFAULT NULL COMMENT '工作线程数',
  `run_time` int(11) DEFAULT NULL COMMENT '持续运行时间：秒',
  `cpu` int(3) DEFAULT NULL COMMENT 'CPU占用百分比',
  `disk` int(5) DEFAULT NULL COMMENT '硬盘空间占用单位GB',
  `mem` int(10) DEFAULT NULL COMMENT '内存占用，单位MB',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23942 DEFAULT CHARSET=utf8 COMMENT='秀场HTTPPROXY服务器数据表';

-- ----------------------------
-- Table structure for ip_allowed
-- ----------------------------
DROP TABLE IF EXISTS `ip_allowed`;
CREATE TABLE `ip_allowed` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` int(11) DEFAULT NULL COMMENT 'ip显示类型',
  `start_ip` varchar(20) DEFAULT NULL COMMENT '开始段ip',
  `end_ip` varchar(20) DEFAULT NULL COMMENT '结束段ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='有访问权限的ip表';

-- ----------------------------
-- Table structure for lbs_server_data
-- ----------------------------
DROP TABLE IF EXISTS `lbs_server_data`;
CREATE TABLE `lbs_server_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `server_type` varchar(30) DEFAULT NULL COMMENT '服务器类型',
  `server_id` varchar(30) DEFAULT NULL COMMENT '服务器ID',
  `ip` varchar(30) DEFAULT NULL COMMENT '服务器IP',
  `port` int(5) unsigned DEFAULT NULL COMMENT '服务器端口',
  `timestamp` datetime DEFAULT NULL COMMENT '采样时间',
  `work_thread_sum` int(11) DEFAULT NULL COMMENT '工作线程数',
  `run_time` int(11) DEFAULT NULL COMMENT '持续运行时间：秒',
  `cpu` int(3) DEFAULT NULL COMMENT 'CPU占用百分比',
  `disk` int(5) DEFAULT NULL COMMENT '硬盘空间占用单位GB',
  `mem` int(10) DEFAULT NULL COMMENT '内存占用，单位MB',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='秀场LBS服务器数据表';

-- ----------------------------
-- Table structure for live_room_info
-- ----------------------------
DROP TABLE IF EXISTS `live_room_info`;
CREATE TABLE `live_room_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `root_id` varchar(30) DEFAULT NULL COMMENT '房间号',
  `union_flag` int(2) DEFAULT NULL COMMENT '连麦标志',
  `capture_status` int(2) DEFAULT NULL COMMENT '截图状态',
  `output_status` int(2) DEFAULT NULL COMMENT '输出状态',
  `anchor_id` bigint(20) DEFAULT NULL COMMENT '主播ID',
  `partner_id` bigint(20) DEFAULT NULL COMMENT '连麦者ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='直播房间信息表';

-- ----------------------------
-- Table structure for mds_server_data
-- ----------------------------
DROP TABLE IF EXISTS `mds_server_data`;
CREATE TABLE `mds_server_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `server_type` varchar(30) DEFAULT NULL COMMENT '服务器类型',
  `server_id` varchar(30) DEFAULT NULL COMMENT '服务器ID',
  `ip` varchar(30) DEFAULT NULL COMMENT '服务器IP',
  `port` int(5) unsigned DEFAULT NULL COMMENT '服务器端口',
  `timestamp` datetime DEFAULT NULL COMMENT '采样时间',
  `work_thread_sum` int(11) DEFAULT NULL COMMENT '工作线程数',
  `run_time` int(11) DEFAULT NULL COMMENT '持续运行时间：秒',
  `cpu` int(3) DEFAULT NULL COMMENT 'CPU占用百分比',
  `disk` int(5) DEFAULT NULL COMMENT '硬盘空间占用单位GB',
  `mem` int(10) DEFAULT NULL COMMENT '内存占用，单位MB',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `group_count` int(11) DEFAULT NULL COMMENT '群组数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1149 DEFAULT CHARSET=utf8 COMMENT='秀场MDS服务器数据表';

-- ----------------------------
-- Table structure for media_control_data
-- ----------------------------
DROP TABLE IF EXISTS `media_control_data`;
CREATE TABLE `media_control_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `server_type` varchar(30) DEFAULT NULL COMMENT '服务器类型',
  `server_id` varchar(30) NOT NULL COMMENT '服务器ID',
  `ip` varchar(30) NOT NULL COMMENT '服务器IP',
  `port` int(5) unsigned NOT NULL COMMENT '服务器端口',
  `timestamp` datetime NOT NULL COMMENT '采样时间',
  `created_room_count` int(5) NOT NULL COMMENT '创建房间的数量',
  `media_transmission_servers` varchar(200) NOT NULL COMMENT '媒体传输服务器列表',
  `input_messages` int(10) NOT NULL COMMENT '输入消息数量',
  `output_messages` int(10) NOT NULL COMMENT '输出消息数量',
  `cpu` int(3) NOT NULL COMMENT 'cpu占用百分比',
  `disk` int(5) NOT NULL COMMENT '硬盘空间占用，单位GB',
  `mem` int(10) NOT NULL COMMENT '内存占用，单位MB',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2048 DEFAULT CHARSET=utf8 COMMENT='媒体控制服务器数据';

-- ----------------------------
-- Table structure for media_forward_data
-- ----------------------------
DROP TABLE IF EXISTS `media_forward_data`;
CREATE TABLE `media_forward_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `server_type` varchar(30) DEFAULT NULL COMMENT '服务器类型',
  `server_id` varchar(30) NOT NULL COMMENT '服务器ID',
  `ip` varchar(30) NOT NULL COMMENT '服务器IP',
  `port` int(5) unsigned NOT NULL COMMENT '服务器端口',
  `timestamp` datetime NOT NULL COMMENT '采样时间',
  `udx_connection_length` int(20) NOT NULL COMMENT '当前的连接列表长度',
  `room_count` int(10) DEFAULT NULL,
  `cpu` int(3) NOT NULL COMMENT 'cpu占用百分比',
  `disk` int(5) NOT NULL COMMENT '硬盘空间占用，单位GB',
  `mem` int(10) NOT NULL COMMENT '内存占用，单位MB',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=139848 DEFAULT CHARSET=utf8 COMMENT='媒体控制服务器数据';

-- ----------------------------
-- Table structure for mss_server_data
-- ----------------------------
DROP TABLE IF EXISTS `mss_server_data`;
CREATE TABLE `mss_server_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `server_type` varchar(30) DEFAULT NULL COMMENT '服务器类型',
  `server_id` varchar(30) DEFAULT NULL COMMENT '服务器ID',
  `ip` varchar(30) DEFAULT NULL COMMENT '服务器IP',
  `port` int(5) unsigned DEFAULT NULL COMMENT '服务器端口',
  `timestamp` datetime DEFAULT NULL COMMENT '采样时间',
  `work_thread_sum` int(11) DEFAULT NULL COMMENT '工作线程数',
  `run_time` int(11) DEFAULT NULL COMMENT '持续运行时间：秒',
  `cpu` int(3) DEFAULT NULL COMMENT 'CPU占用百分比',
  `disk` int(5) DEFAULT NULL COMMENT '硬盘空间占用单位GB',
  `mem` int(10) DEFAULT NULL COMMENT '内存占用，单位MB',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=866 DEFAULT CHARSET=utf8 COMMENT='秀场MSS服务器数据表';

-- ----------------------------
-- Table structure for mts_server_data
-- ----------------------------
DROP TABLE IF EXISTS `mts_server_data`;
CREATE TABLE `mts_server_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `server_type` varchar(30) DEFAULT NULL COMMENT '服务器类型',
  `server_id` varchar(30) DEFAULT NULL COMMENT '服务器ID',
  `ip` varchar(30) DEFAULT NULL COMMENT '服务器IP',
  `port` int(5) unsigned DEFAULT NULL COMMENT '服务器端口',
  `timestamp` datetime DEFAULT NULL COMMENT '采样时间',
  `work_thread_sum` int(11) DEFAULT NULL COMMENT '工作线程数',
  `run_time` int(11) DEFAULT NULL COMMENT '持续运行时间：秒',
  `client_connection_sum` int(11) DEFAULT NULL COMMENT '客户端链接数',
  `cpu` int(3) DEFAULT NULL COMMENT 'CPU占用百分比',
  `disk` int(5) DEFAULT NULL COMMENT '硬盘空间占用单位GB',
  `mem` int(10) DEFAULT NULL COMMENT '内存占用，单位MB',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1145 DEFAULT CHARSET=utf8 COMMENT='秀场MTS服务器数据表';

-- ----------------------------
-- Table structure for php_manager_server_data
-- ----------------------------
DROP TABLE IF EXISTS `php_manager_server_data`;
CREATE TABLE `php_manager_server_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `server_type` varchar(30) DEFAULT NULL COMMENT '服务器类型',
  `server_id` varchar(30) DEFAULT NULL COMMENT '服务器ID',
  `ip` varchar(30) DEFAULT NULL COMMENT '服务器IP',
  `port` int(5) unsigned DEFAULT NULL COMMENT '服务器端口',
  `timestamp` datetime DEFAULT NULL COMMENT '采样时间',
  `sys_load` int(10) DEFAULT NULL COMMENT '系统平均负载系数',
  `net_connections` int(5) DEFAULT NULL COMMENT '网络连接数',
  `net_load` bigint(20) DEFAULT NULL COMMENT '网络流量MB数',
  `cpu` int(3) DEFAULT NULL COMMENT 'CPU占用百分比',
  `disk` int(5) DEFAULT NULL COMMENT '硬盘空间占用单位GB',
  `mem` int(10) DEFAULT NULL COMMENT '内存占用，单位MB',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='PH管控中心服务器数据表';

-- ----------------------------
-- Table structure for php_server_data
-- ----------------------------
DROP TABLE IF EXISTS `php_server_data`;
CREATE TABLE `php_server_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `server_type` varchar(30) DEFAULT NULL COMMENT '服务器类型',
  `server_id` varchar(30) DEFAULT NULL COMMENT '服务器ID',
  `ip` varchar(30) DEFAULT NULL COMMENT '服务器IP',
  `port` int(5) unsigned DEFAULT NULL COMMENT '服务器端口',
  `timestamp` datetime DEFAULT NULL COMMENT '采样时间',
  `sys_load` int(10) DEFAULT NULL COMMENT '系统平均负载系数',
  `net_connections` int(5) DEFAULT NULL COMMENT '网络连接数',
  `net_load` bigint(20) DEFAULT NULL COMMENT '网络流量MB数',
  `cpu` int(3) DEFAULT NULL COMMENT 'CPU占用百分比',
  `disk` int(5) DEFAULT NULL COMMENT '硬盘空间占用单位GB',
  `mem` int(10) DEFAULT NULL COMMENT '内存占用，单位MB',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='PHP服务器数据表';

-- ----------------------------
-- Table structure for php_video_server_data
-- ----------------------------
DROP TABLE IF EXISTS `php_video_server_data`;
CREATE TABLE `php_video_server_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `server_type` varchar(30) DEFAULT NULL COMMENT '服务器类型',
  `server_id` varchar(30) DEFAULT NULL COMMENT '服务器ID',
  `ip` varchar(30) DEFAULT NULL COMMENT '服务器IP',
  `port` int(5) unsigned DEFAULT NULL COMMENT '服务器端口',
  `timestamp` datetime DEFAULT NULL COMMENT '采样时间',
  `sys_load` int(10) DEFAULT NULL COMMENT '系统平均负载系数',
  `net_connections` int(5) DEFAULT NULL COMMENT '网络连接数',
  `net_load` bigint(20) DEFAULT NULL COMMENT '网络流量MB数',
  `cpu` int(3) DEFAULT NULL COMMENT 'CPU占用百分比',
  `disk` int(5) DEFAULT NULL COMMENT '硬盘空间占用单位GB',
  `mem` int(10) DEFAULT NULL COMMENT '内存占用，单位MB',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='PHP点播服务器数据表';

-- ----------------------------
-- Table structure for prs_server_data
-- ----------------------------
DROP TABLE IF EXISTS `prs_server_data`;
CREATE TABLE `prs_server_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `server_type` varchar(30) DEFAULT NULL COMMENT '服务器类型',
  `server_id` varchar(30) DEFAULT NULL COMMENT '服务器ID',
  `ip` varchar(30) DEFAULT NULL COMMENT '服务器IP',
  `port` int(5) unsigned DEFAULT NULL COMMENT '服务器端口',
  `timestamp` datetime DEFAULT NULL COMMENT '采样时间',
  `work_thread_sum` int(11) DEFAULT NULL COMMENT '工作线程数',
  `run_time` int(11) DEFAULT NULL COMMENT '持续运行时间：秒',
  `cpu` int(3) DEFAULT NULL COMMENT 'CPU占用百分比',
  `disk` int(5) DEFAULT NULL COMMENT '硬盘空间占用单位GB',
  `mem` int(10) DEFAULT NULL COMMENT '内存占用，单位MB',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='秀场PRS服务器数据表';

-- ----------------------------
-- Table structure for rms_server_data
-- ----------------------------
DROP TABLE IF EXISTS `rms_server_data`;
CREATE TABLE `rms_server_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `server_type` varchar(30) DEFAULT NULL COMMENT '服务器类型',
  `server_id` varchar(30) DEFAULT NULL COMMENT '服务器ID',
  `ip` varchar(30) DEFAULT NULL COMMENT '服务器IP',
  `port` int(5) unsigned DEFAULT NULL COMMENT '服务器端口',
  `timestamp` datetime DEFAULT NULL COMMENT '采样时间',
  `work_thread_sum` int(11) DEFAULT NULL COMMENT '工作线程数',
  `run_time` int(11) DEFAULT NULL COMMENT '持续运行时间：秒',
  `input_cmds` int(11) DEFAULT NULL COMMENT '已收到命令包数',
  `output_cmds` int(11) DEFAULT NULL COMMENT '已相应的命令包数',
  `cpu` int(3) DEFAULT NULL COMMENT 'CPU占用百分比',
  `disk` int(5) DEFAULT NULL COMMENT '硬盘空间占用单位GB',
  `mem` int(10) DEFAULT NULL COMMENT '内存占用，单位MB',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='秀场RMS服务器数据表';

-- ----------------------------
-- Table structure for screen_shot_data
-- ----------------------------
DROP TABLE IF EXISTS `screen_shot_data`;
CREATE TABLE `screen_shot_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `server_type` varchar(30) DEFAULT NULL COMMENT '服务器类型',
  `server_id` varchar(30) DEFAULT NULL COMMENT '服务器ID',
  `ip` varchar(30) DEFAULT NULL COMMENT '服务器IP',
  `port` int(5) unsigned DEFAULT NULL COMMENT '服务器端口',
  `timestamp` datetime DEFAULT NULL COMMENT '采样时间',
  `rooms` varchar(5000) DEFAULT NULL,
  `cpu` int(3) DEFAULT NULL COMMENT 'CPU占用百分比',
  `disk` int(5) DEFAULT NULL COMMENT '硬盘空间占用单位GB',
  `mem` int(10) DEFAULT NULL COMMENT '内存占用，单位MB',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2004 DEFAULT CHARSET=utf8 COMMENT='截图服务器数据表';

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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tas_server_data
-- ----------------------------
DROP TABLE IF EXISTS `tas_server_data`;
CREATE TABLE `tas_server_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `server_type` varchar(30) DEFAULT NULL COMMENT '服务器类型',
  `server_id` varchar(30) DEFAULT NULL COMMENT '服务器ID',
  `ip` varchar(30) DEFAULT NULL COMMENT '服务器IP',
  `port` int(5) unsigned DEFAULT NULL COMMENT '服务器端口',
  `timestamp` datetime DEFAULT NULL COMMENT '采样时间',
  `work_thread_sum` int(11) DEFAULT NULL COMMENT '工作线程数',
  `run_time` int(11) DEFAULT NULL COMMENT '持续运行时间：秒',
  `client_connection_sum` int(11) DEFAULT NULL COMMENT '客户端链接数',
  `cpu` int(3) DEFAULT NULL COMMENT 'CPU占用百分比',
  `disk` int(5) DEFAULT NULL COMMENT '硬盘空间占用单位GB',
  `mem` int(10) DEFAULT NULL COMMENT '内存占用，单位MB',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='秀场TAS服务器数据表';

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
) ENGINE=InnoDB AUTO_INCREMENT=609 DEFAULT CHARSET=utf8 COMMENT='阀值信息表';

-- ----------------------------
-- Table structure for transcoding_data
-- ----------------------------
DROP TABLE IF EXISTS `transcoding_data`;
CREATE TABLE `transcoding_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `server_type` varchar(30) DEFAULT NULL COMMENT '服务器类型',
  `server_id` varchar(30) DEFAULT NULL COMMENT '服务器ID',
  `ip` varchar(30) DEFAULT NULL COMMENT '服务器IP',
  `port` int(5) unsigned DEFAULT NULL COMMENT '服务器端口',
  `timestamp` datetime DEFAULT NULL COMMENT '采样时间',
  `rooms` varchar(5000) DEFAULT NULL,
  `cpu` int(3) DEFAULT NULL COMMENT 'CPU占用百分比',
  `disk` int(5) DEFAULT NULL COMMENT '硬盘空间占用单位GB',
  `mem` int(10) DEFAULT NULL COMMENT '内存占用，单位MB',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2848 DEFAULT CHARSET=utf8 COMMENT='转码服务器数据表';

-- ----------------------------
-- Table structure for ums_server_data
-- ----------------------------
DROP TABLE IF EXISTS `ums_server_data`;
CREATE TABLE `ums_server_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `server_type` varchar(30) DEFAULT NULL COMMENT '服务器类型',
  `server_id` varchar(30) DEFAULT NULL COMMENT '服务器ID',
  `ip` varchar(30) DEFAULT NULL COMMENT '服务器IP',
  `port` int(5) unsigned DEFAULT NULL COMMENT '服务器端口',
  `timestamp` datetime DEFAULT NULL COMMENT '采样时间',
  `work_thread_sum` int(11) DEFAULT NULL COMMENT '工作线程数',
  `run_time` int(11) DEFAULT NULL COMMENT '持续运行时间：秒',
  `cpu` int(3) DEFAULT NULL COMMENT 'CPU占用百分比',
  `disk` int(5) DEFAULT NULL COMMENT '硬盘空间占用单位GB',
  `mem` int(10) DEFAULT NULL COMMENT '内存占用，单位MB',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='秀场UMS服务器数据表';

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
