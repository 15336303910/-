/*
Navicat MySQL Data Transfer

Source Server         : JLyiCai
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : im

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-07-03 09:57:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for stoneinfo
-- ----------------------------
DROP TABLE IF EXISTS `stoneinfo`;
CREATE TABLE `stoneinfo` (
  `stoneId` int(11) NOT NULL AUTO_INCREMENT,
  `stoneName` varchar(100) DEFAULT NULL COMMENT '标石名称',
  `stonePosition` int(11) DEFAULT NULL COMMENT '标石方位',
  `stoneNum` varchar(100) DEFAULT NULL COMMENT '标石序号',
  `stoneArea` varchar(100) DEFAULT NULL COMMENT '所属区域',
  `longitude` varchar(255) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(255) DEFAULT NULL COMMENT '纬度',
  `propertyNature` int(11) DEFAULT NULL COMMENT '产权性质',
  `propertyComp` int(11) DEFAULT NULL COMMENT '产权单位',
  `dataQualitier` varchar(45) DEFAULT NULL COMMENT '数据质量责任人',
  `maintainer` varchar(45) DEFAULT NULL COMMENT '一线维护人',
  `deleteflag` varchar(45) DEFAULT NULL COMMENT '删除标识',
  `createTime` varchar(80) DEFAULT NULL,
  `creater` varchar(45) DEFAULT NULL COMMENT '创建人',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `lastUpTime` varchar(100) DEFAULT NULL COMMENT '最近修改时间',
  `lastUpMan` varchar(100) DEFAULT NULL COMMENT '最近修改人',
  `stoneSubName` varchar(100) DEFAULT NULL,
  `buriedId` varchar(100) DEFAULT NULL,
  `previousStoneID` int(11) DEFAULT NULL,
  `previousStoneName` varchar(100) DEFAULT NULL,
  `chengzaidian_type` int(11) DEFAULT NULL,
  `resNum` varchar(100) DEFAULT NULL,
  `resMotion` varchar(45) DEFAULT NULL COMMENT 'i   新增\nd  删除\nu 更新',
  `maintenanceOrg` varchar(45) DEFAULT NULL,
  `maintenanceArea` varchar(45) DEFAULT NULL,
  `maintenanceLeader` varchar(45) DEFAULT NULL,
  `leaderPhone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`stoneId`)
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stoneinfo
-- ----------------------------
INSERT INTO `stoneinfo` VALUES ('74', '经十路辅路东1111111号标石', '2', '123', '北京_东城区_', '117.1235902224', '36.6618580421', '0', '0', '', '管理员', '0', '2017-01-03 11:23:54', 'root', null, '2017-07-26 10:44:10', null, null, null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('75', '经十路辅路东2号标石', '1', '2', '山东_济南_历下区', '117.1235206916', '36.6616703903', '0', '0', '', '管理员', '0', '2017-01-03 11:27:30', 'root', null, '2017-04-06 15:20:59', null, null, null, null, null, '0', null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('76', '北京直埋1_东1号标石', '1', '1', '北京_东城区_', '117.123425308', '36.661742789', null, null, '管理员', '管理员', '0', '2017-01-09 14:47:10', null, null, null, null, null, '22', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('77', '北京直埋1_东2号标石', '1', '2', '北京_东城区_', '117.1235374862', '36.6611960643', null, null, '管理员', '管理员', '0', '2017-01-09 14:47:13', null, null, null, null, null, '22', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('78', '北京直埋1_东3号标石', '1', '3', '北京_东城区_', '117.1240693444', '36.6616067428', null, null, '管理员', '管理员', '0', '2017-01-09 16:26:44', null, null, null, null, null, '22', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('79', '北京直埋1_东4号标石', '1', '4', '北京_东城区_', '117.1249495274', '36.6615090619', null, null, '管理员', '管理员', '1', '2017-01-09 16:26:47', null, null, '2017-01-09 16:27:06', null, null, '22', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('80', '北京直埋1_东5号标石', '1', '5', '北京_东城区_', '117.1247857519', '36.6622367824', null, null, '管理员', '管理员', '0', '2017-01-09 16:26:49', null, null, null, null, null, '22', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('81', '北京直埋1_东6号标石', '1', '6', '北京_东城区_', '117.124582258', '36.6627602639', null, null, '管理员', '管理员', '1', '2017-01-09 16:26:51', null, null, '2017-01-09 16:26:57', null, null, '22', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('82', '北京直埋1_东6号标石', '1', '6', '北京_东城区_', '117.1250054562', '36.6629084675', null, null, '管理员', '管理员', '1', '2017-01-09 16:27:32', null, null, '2017-01-09 16:28:57', null, null, '22', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('83', '北京直埋1_东7号标石', '1', '7', '北京_东城区_', '117.1246741296', '36.6634427729', null, null, '管理员', '管理员', '0', '2017-01-09 16:27:34', null, null, null, null, null, '22', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('84', '北京直埋1_东8号标石', '1', '8', '北京_东城区_', '117.1246069846', '36.6639994348', null, null, '管理员', '管理员', '1', '2017-01-09 16:35:29', null, null, '2017-01-09 16:35:43', null, null, '22', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('85', '北京直埋1_东9号标石', '1', '9', '北京_东城区_', '117.124403756', '36.6644266856', null, null, '管理员', '管理员', '0', '2017-01-09 16:35:31', null, null, null, null, null, '22', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('86', '北京直埋1_东10号标石', '1', '10', '北京_东城区_', '117.1242993135', '36.6648408344', null, null, '管理员', '管理员', '1', '2017-01-09 16:35:48', null, null, '2017-01-09 16:35:56', null, null, '22', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('87', '北京直埋1_东11号标石', '1', '11', '北京_东城区_', '117.1237271204', '36.6655577926', '0', '0', '管理员', '管理员', '0', '2017-01-09 16:35:51', null, null, '2017-04-06 15:21:16', null, null, '22', null, null, '0', null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('88', '经十路辅路东中国电信直埋_东1号标石', '1', '1', '北京_东城区_', '117.1234694716', '36.6615614651', '3', '2', '管理员', '管理员', '0', '2017-01-10 09:51:49', null, null, null, null, null, '28', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('89', '经十路辅路东中国电信直埋_东2号标石', '1', '2', '山东_济南_历下区', '117.1234722783', '36.6612912556', '3', '2', '管理员', '管理员', '0', '2017-01-10 09:51:50', null, null, null, null, null, '28', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('90', '经十路辅路东中国电信直埋_东3号标石', '1', '3', '山东_济南_历下区', '117.1235298576', '36.6609822855', '3', '2', '管理员', '管理员', '0', '2017-01-10 09:51:52', null, null, null, null, null, '28', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('91', '东直门桥东直埋_东1号标石', '1', '1', '北京_东城区_', '116.433047976', '39.9399443746', '0', '0', '管理员', '管理员', '0', '2017-01-16 14:59:17', null, null, null, null, null, '35', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('92', '东直门桥东直埋_东2号标石', '1', '2', '北京_东城区_', '116.4334796859', '39.9399238644', '0', '0', '管理员', '管理员', '0', '2017-01-16 14:59:22', null, null, null, null, null, '35', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('93', '东直门桥东直埋_东3号标石', '1', '3', '北京_东城区_', '116.4340451873', '39.9399525538', '0', '0', '管理员', '管理员', '0', '2017-01-16 14:59:24', null, null, null, null, null, '35', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('94', '东直门桥东直埋_东4号标石', '1', '4', '北京_东城区_', '116.4344891349', '39.9399602314', '0', '0', '管理员', '管理员', '1', '2017-01-16 14:59:27', null, null, '2017-01-16 14:59:53', null, null, '35', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('95', '东直门桥东直埋_东5号标石', '1', '5', '北京_东城区_', '116.4348479743', '39.9399663686', '0', '0', '管理员', '管理员', '0', '2017-01-16 14:59:29', null, null, null, null, null, '35', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('96', '东直门桥东直埋_东6号标石', '1', '6', '北京_东城区_', '116.4352372768', '39.9399447856', '0', '0', '管理员', '管理员', '0', '2017-01-16 14:59:31', null, null, null, null, null, '35', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('97', '东直门桥东直埋_东7号标石', '1', '7', '北京_东城区_', '116.4356874181', '39.9399710845', '0', '0', '管理员', '管理员', '0', '2017-01-16 14:59:34', null, null, null, null, null, '35', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('98', '东直门桥东直埋_东6-?号标石', '1', '6', '北京_东城区_', '116.4354622938', '39.9399582578', '0', '0', '管理员', '管理员', '0', '2017-01-16 14:59:40', null, '', '2018-06-27 15:52:30', null, null, '35', null, null, null, null, null, '5', '0', '听音乐', '');
INSERT INTO `stoneinfo` VALUES ('99', '二环东直埋_东1号标石', '1', '1', '北京_东城区_', '116.4273774668', '39.9289415026', '0', '0', '管理员', '管理员', '0', '2017-02-03 15:51:19', null, null, null, null, null, '38', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('100', '二环东直埋_东2号标石', '1', '2', '北京_东城区_', '116.427420041', '39.928529038', '0', '0', '管理员', '管理员', '0', '2017-02-03 15:51:24', null, null, null, null, null, '38', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('101', '二环东直埋_东3号标石', '1', '3', '北京_东城区_', '116.427420308', '39.9281057171', '0', '0', '管理员', '管理员', '0', '2017-02-03 15:51:27', null, null, '2018-02-09 15:05:09', null, null, '38', null, null, '0', null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('102', '二环东直埋_东4号标石', '1', '4', '北京_东城区_', '116.427444417', '39.927735789', '0', '0', '管理员', '管理员', '0', '2017-02-03 15:51:31', null, null, null, null, null, '38', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('103', '二环东直埋_东1-?号标石', '1', '1', '北京_东城区_', '116.4273987539', '39.9287352703', '0', '0', '管理员', '管理员', '0', '2017-02-03 15:52:24', null, null, null, null, null, '38', null, null, null, null, null, null, null, null, null);
INSERT INTO `stoneinfo` VALUES ('157', 'GG', null, null, '北京_东城区_', '116.9945377267', '36.6694738131', '4', null, null, '管理员', '0', '2018-06-29 08:47:46', 'root', '			成功	', null, null, null, null, null, null, null, null, null, '5', '4', '头疼', '很难办');
INSERT INTO `stoneinfo` VALUES ('158', 'GG', null, null, null, '116.9945378305', '36.669474505', '4', null, null, '管理员', '0', '2018-06-29 09:04:49', null, '			成功	', null, 'root', null, 'undefined', null, null, null, null, null, '5', '4', '头疼', '很难办');
INSERT INTO `stoneinfo` VALUES ('159', 'GG', null, null, null, '116.9945378305', '36.669474505', '4', null, null, '管理员', '0', '2018-06-29 09:04:55', null, '			成功	', null, 'root', null, 'undefined', null, null, null, null, null, '5', '4', '头疼', '很难办');
INSERT INTO `stoneinfo` VALUES ('160', 'GG', null, null, null, '116.9945378305', '36.669474505', '4', null, null, '管理员', '0', '2018-06-29 14:51:23', null, '			成功	', null, 'root', null, 'undefined', null, null, null, null, null, '5', 'null', '头疼', '很难办');
INSERT INTO `stoneinfo` VALUES ('161', 'GG', null, null, null, '116.9945378305', '36.669474505', '4', null, null, '管理员', '0', '2018-06-29 14:51:37', null, '			成功	', null, 'root', null, 'undefined', null, null, null, null, null, '5', 'null', '头疼', '很难办');
INSERT INTO `stoneinfo` VALUES ('162', 'GG', null, null, null, '116.9945378305', '36.669474505', '4', null, null, '管理员', '0', '2018-06-29 14:52:05', null, '			成功	', null, 'root', null, 'undefined', null, null, null, null, null, '5', 'null', '头疼', '很难办');
INSERT INTO `stoneinfo` VALUES ('163', 'GG', null, null, null, '116.9945378305', '36.669474505', '4', null, null, '管理员', '0', '2018-06-29 16:05:15', null, '			成功	', null, 'root', null, 'undefined', null, null, null, null, null, '5', 'null', '头疼', '很难办');
