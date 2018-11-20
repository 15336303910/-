/*
Navicat MySQL Data Transfer

Source Server         : JLyiCai
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : im

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-07-03 09:57:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for buriedpartinfo
-- ----------------------------
DROP TABLE IF EXISTS `buriedpartinfo`;
CREATE TABLE `buriedpartinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `buriedId` varchar(45) DEFAULT NULL COMMENT '关联直埋',
  `buriedPartName` varchar(100) DEFAULT NULL COMMENT '直埋段名称',
  `buriedPartArea` varchar(100) DEFAULT NULL COMMENT '维护区域',
  `buriedPartLength` varchar(100) DEFAULT NULL COMMENT '直埋段长度',
  `buriedPartStart` varchar(100) DEFAULT NULL COMMENT '开始设施',
  `buriedPartEnd` varchar(100) DEFAULT NULL COMMENT '终止设施',
  `buriedPartOptical` varchar(1000) DEFAULT NULL COMMENT '承载光缆断',
  `propertyRight` int(11) DEFAULT NULL COMMENT '产权性质',
  `propertyDept` int(11) DEFAULT NULL COMMENT '产权单位',
  `dataQualitier` varchar(100) DEFAULT NULL COMMENT '质量责任人',
  `maintainer` varchar(100) DEFAULT NULL COMMENT '一线维护人',
  `deleteflag` varchar(100) DEFAULT NULL COMMENT '删除标识',
  `createTime` varchar(100) DEFAULT NULL COMMENT '创建时间',
  `creater` varchar(100) DEFAULT NULL COMMENT '创建人',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `lastUpTime` varchar(100) DEFAULT NULL COMMENT '最近更改时间',
  `lastUpMan` varchar(100) DEFAULT NULL COMMENT '最近更改人',
  `buriedPartOpticalId` varchar(200) DEFAULT NULL COMMENT '承载光缆段id',
  `buriedPartStartId` varchar(100) DEFAULT NULL COMMENT '起始设备id',
  `buriedPartEndId` varchar(100) DEFAULT NULL COMMENT '终止设备id ',
  `resNum` varchar(100) DEFAULT NULL,
  `resMotion` varchar(45) DEFAULT NULL COMMENT 'i   新增\nd  删除\nu 更新',
  `maintainLength` varchar(45) DEFAULT NULL,
  `maintain_company` varchar(512) DEFAULT NULL,
  `maintain_manager` varchar(512) DEFAULT NULL,
  `maintain_manager_phone` varchar(512) DEFAULT NULL,
  `maintain_area` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `buriedStart` (`buriedPartStartId`),
  KEY `buriedEnd` (`buriedPartEndId`),
  KEY `iburied_end` (`buriedPartEndId`),
  KEY `i_buriedId` (`buriedId`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of buriedpartinfo
-- ----------------------------
INSERT INTO `buriedpartinfo` VALUES ('82', '98', '哼哼唧唧', '北京市_东城区_', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '0', '0', '管理员', '管理员', '0', '2017-01-16 14:59:22', null, '	好好过', '2018-06-29 09:17:45', null, null, '95', '93', null, null, '36.82', '0', 'uiimn', '斤斤计较', null);
INSERT INTO `buriedpartinfo` VALUES ('83', '98', '哼哼唧唧', '北京市_东城区_', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '0', '0', '管理员', '管理员', '0', '2017-01-16 14:59:24', null, '	好好过', '2018-06-29 09:17:45', null, null, '95', '93', null, null, '48.32', '0', 'uiimn', '斤斤计较', null);
INSERT INTO `buriedpartinfo` VALUES ('84', '98', '哼哼唧唧', '北京市_东城区_', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '0', '0', '管理员', '管理员', '1', '2017-01-16 14:59:27', null, '	好好过', '2018-06-29 09:17:45', null, null, '95', '93', null, null, '37.90', '0', 'uiimn', '斤斤计较', null);
INSERT INTO `buriedpartinfo` VALUES ('85', '98', '哼哼唧唧', '北京市_东城区_', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '0', '0', '管理员', '管理员', '1', '2017-01-16 14:59:29', null, '	好好过', '2018-06-29 09:17:45', null, null, '95', '93', null, null, '30.63', '0', 'uiimn', '斤斤计较', null);
INSERT INTO `buriedpartinfo` VALUES ('86', '98', '哼哼唧唧', '北京市_东城区_', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '0', '0', '管理员', '管理员', '0', '2017-01-16 14:59:31', null, '	好好过', '2018-06-29 09:17:45', null, null, '95', '93', null, null, '33.31', '0', 'uiimn', '斤斤计较', null);
INSERT INTO `buriedpartinfo` VALUES ('87', '98', '哼哼唧唧', '北京市_东城区_', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '0', '0', '管理员', '管理员', '1', '2017-01-16 14:59:34', null, '	好好过', '2018-06-29 09:17:45', null, null, '95', '93', null, null, '38.53', '0', 'uiimn', '斤斤计较', null);
INSERT INTO `buriedpartinfo` VALUES ('88', '98', '哼哼唧唧', '北京市_东城区_', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '0', '0', '管理员', '管理员', '0', '2017-01-16 14:59:40', null, '	好好过', '2018-06-29 09:17:45', null, null, '95', '93', null, null, '20.0', '0', 'uiimn', '斤斤计较', null);
INSERT INTO `buriedpartinfo` VALUES ('89', '98', '哼哼唧唧', '北京市_东城区_', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '0', '0', '管理员', '管理员', '0', '2017-01-16 14:59:40', null, '	好好过', '2018-06-29 09:17:45', null, null, '95', '93', null, null, '20.0', '0', 'uiimn', '斤斤计较', null);
INSERT INTO `buriedpartinfo` VALUES ('90', '98', '哼哼唧唧', '北京市_东城区_', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '0', '0', '管理员', '管理员', '0', '2017-01-16 14:59:53', null, '	好好过', '2018-06-29 09:17:45', null, null, '95', '93', null, null, '68.54', '0', 'uiimn', '斤斤计较', null);
INSERT INTO `buriedpartinfo` VALUES ('91', '98', '哼哼唧唧', '北京市_东城区_', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '0', '0', '管理员', '管理员', '1', '2017-02-03 15:51:24', null, '	好好过', '2018-06-29 09:17:45', null, null, '95', '93', null, null, '46.06', '0', 'uiimn', '斤斤计较', null);
INSERT INTO `buriedpartinfo` VALUES ('92', '98', '哼哼唧唧', '北京市_东城区_', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '0', '0', '管理员', '管理员', '0', '2017-02-03 15:51:27', null, '	好好过', '2018-06-29 09:17:45', null, null, '95', '93', null, null, '47.05', '0', 'uiimn', '斤斤计较', null);
INSERT INTO `buriedpartinfo` VALUES ('93', '98', '哼哼唧唧', '北京市_东城区_', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '0', '0', '管理员', '管理员', '0', '2017-02-03 15:51:31', null, '	好好过', '2018-06-29 09:17:45', null, null, '95', '93', null, null, '41.30', '0', 'uiimn', '斤斤计较', null);
INSERT INTO `buriedpartinfo` VALUES ('94', '98', '哼哼唧唧', '北京市_东城区_', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '0', '0', '管理员', '管理员', '0', '2017-02-03 15:52:25', null, '	好好过', '2018-06-29 09:17:45', null, null, '95', '93', null, null, '24.0', '0', 'uiimn', '斤斤计较', null);
INSERT INTO `buriedpartinfo` VALUES ('95', '98', '哼哼唧唧', '北京市_东城区_', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '0', '0', '管理员', '管理员', '0', '2017-02-03 15:52:25', null, '	好好过', '2018-06-29 09:17:45', null, null, '95', '93', null, null, '24.1', '0', 'uiimn', '斤斤计较', null);
INSERT INTO `buriedpartinfo` VALUES ('96', '98', 'yy', '北京市_崇文区_', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '4', '0', null, '管理员', '0', '2018-02-09 14:45:49', 'root', '0000', '2018-06-29 16:59:39', null, null, '95', '93', null, null, '48.67', '5', '22', '111', '2');
INSERT INTO `buriedpartinfo` VALUES ('97', '98', 'kk', '4', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '0', null, null, '管理员', '0', '2018-06-28 12:17:37', 'root', '	222', '2018-06-29 16:02:01', null, null, '95', '93', null, null, null, '5', '44', '333', null);
INSERT INTO `buriedpartinfo` VALUES ('98', '98', '哼该喝喝', 'null', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '0', null, null, '管理员', '0', '2018-06-28 12:18:49', 'root', '	好好过', '2018-06-29 14:51:04', null, null, '95', '93', null, null, null, '0', 'uiimn', '斤斤计较', null);
INSERT INTO `buriedpartinfo` VALUES ('99', '98', '哼y', 'null', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '4', null, null, '管理员', '0', '2018-06-29 00:08:07', 'root', '	好好过', '2018-06-29 14:38:44', null, null, '95', '93', null, null, null, '0', 'uiimn', '斤斤计较', null);
INSERT INTO `buriedpartinfo` VALUES ('100', '98', '哼标', 'null', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '5', null, null, '管理员', '0', '2018-06-29 00:08:23', 'root', '	好好过', '2018-06-29 11:03:17', null, null, '95', '93', null, null, null, '6', '黄家驹', '斤斤计较', null);
INSERT INTO `buriedpartinfo` VALUES ('101', '98', 'gg', '3', '68.54', '东直门桥东直埋_东5号标石', '东直门桥东直埋_东3号标石', null, '4', null, null, '管理员', '0', '2018-06-29 00:09:05', 'root', '	好好过', '2018-06-29 14:37:15', null, null, '95', '93', null, null, null, '0', 'uiimn', '斤斤计较', null);
