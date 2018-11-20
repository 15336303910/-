/*
Navicat MySQL Data Transfer

Source Server         : JLyiCai
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : im

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-07-03 09:57:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for polelinesegmentinfo
-- ----------------------------
DROP TABLE IF EXISTS `polelinesegmentinfo`;
CREATE TABLE `polelinesegmentinfo` (
  `poleLineSegmentId` int(11) NOT NULL AUTO_INCREMENT,
  `poleLineSegmentName` varchar(100) DEFAULT NULL,
  `poleLineSegmentCode` varchar(100) DEFAULT NULL,
  `maintenanceAreaId` int(11) DEFAULT NULL,
  `poleLineId` int(11) DEFAULT NULL,
  `poleLineSegmentLength` varchar(255) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `startDeviceId` int(11) DEFAULT NULL,
  `endDeviceId` int(11) DEFAULT NULL,
  `constructionSharingEnumId` int(11) DEFAULT NULL,
  `constructionSharingOrg` varchar(100) DEFAULT NULL,
  `sharingTypeEnumId` int(11) DEFAULT NULL,
  `resourceLifePeriodEnumId` int(11) DEFAULT NULL,
  `creationDate` datetime DEFAULT NULL,
  `lastUpdateDate` datetime DEFAULT NULL,
  `deletedFlag` varchar(2) DEFAULT NULL,
  `deletionDate` date DEFAULT NULL,
  `provinceId` varchar(100) DEFAULT NULL,
  `cuser` varchar(50) DEFAULT NULL,
  `cstate` varchar(2) DEFAULT NULL,
  `areano` varchar(50) DEFAULT NULL,
  `maintenanceAreaName` varchar(255) DEFAULT NULL,
  `areaname` varchar(100) DEFAULT NULL,
  `redunBearCableSegmentId` varchar(255) DEFAULT NULL,
  `bearCableSegmentId` varchar(255) DEFAULT NULL,
  `dataQualityPrincipal` varchar(255) DEFAULT NULL,
  `parties` varchar(200) DEFAULT NULL,
  `resNum` varchar(100) DEFAULT NULL,
  `resMotion` varchar(45) DEFAULT NULL COMMENT 'i   新增\nd  删除\nu 更新',
  `maintainLength` varchar(45) DEFAULT NULL,
  `maintain_company` varchar(512) DEFAULT NULL,
  `maintain_manager` varchar(512) DEFAULT NULL,
  `maintain_manager_phone` varchar(512) DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`poleLineSegmentId`),
  KEY `IpoleStart` (`startDeviceId`),
  KEY `IpoleEnd` (`endDeviceId`),
  KEY `ipoleSegid` (`poleLineId`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of polelinesegmentinfo
-- ----------------------------
INSERT INTO `polelinesegmentinfo` VALUES ('24', '测试电杆085-测试电杆087杆路段', null, null, '33', '75.02', null, '424', '426', '1', '0', null, null, '2017-01-16 00:00:00', '2018-02-01 14:53:24', '1', '2017-01-16', null, null, '2', null, '北京_东城区_', null, null, '', '管理员', '管理员', '', null, '74.95', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('25', '电段1', null, '3', '33', '357.15', null, '445', '444', '5', '0', null, null, '2017-01-16 00:00:00', '2018-07-02 14:31:39', '0', '2017-01-16', null, null, '2', null, '北京市_西城区_', null, null, null, '管理员', '管理员', '', null, '31.52', '2', '33', '22', '11');
INSERT INTO `polelinesegmentinfo` VALUES ('26', '测试电杆087-测试电杆088杆路段', null, null, '0', '48.21', null, '426', '427', '0', '0', null, null, '2017-01-16 00:00:00', '2018-01-24 15:07:11', '1', null, null, null, '2', null, '北京_东城区_', null, null, '', '管理员', '管理员', '', null, '48.1', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('27', '东直门桥东杆路_东4号杆-东直门桥东杆路_东5号杆杆路', null, null, '33', '28.35', null, '361', '362', '0', '0', null, null, '2017-01-16 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', '', null, '28.35', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('28', '东直门桥东杆路_东5号杆-东直门桥东杆路_东6号杆杆路', null, null, '33', '31.83', null, '362', '363', '0', '0', null, null, '2017-01-16 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', '', null, '31.83', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('29', '东直门桥东杆路_东6号杆-东直门桥东杆路_东7号杆杆路', null, null, '33', '28.77', null, '363', '364', '0', '0', null, null, '2017-01-16 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', '', null, '28.77', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('30', '东直门桥东杆路_东7号杆-东直门桥东杆路_东8号杆杆路', null, null, '33', '69.74', null, '364', '365', '0', '0', null, null, '2017-01-16 00:00:00', null, '0', null, null, null, '2', null, null, null, null, null, '管理员', '管理员', null, null, '69.74', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('31', '东直门桥东杆路_东8号杆-东直门桥东杆路_东9号杆杆路', null, null, '33', '368159.55', null, '365', '366', '0', '0', null, null, '2017-01-19 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '368159.55', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('32', '东直门桥东杆路_东9号杆-东直门桥东杆路_东10号杆杆路', null, null, '33', '48.21', null, '366', '367', '0', '0', null, null, '2017-01-19 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '48.21', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('33', '东直门桥东杆路_东10号杆-东直门桥东杆路_东11号杆杆路', null, null, '33', '38.32', null, '367', '368', '0', '0', null, null, '2017-01-19 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '38.32', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('34', '东直门桥东杆路_东11号杆-东直门桥东杆路_东12号杆杆路', null, null, '33', '36.62', null, '368', '369', '0', '0', null, null, '2017-01-19 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '36.62', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('35', '东直门桥东杆路_东12号杆-东直门桥东杆路_东13号杆杆路', null, null, '33', '28.00', null, '369', '370', '0', '0', null, null, '2017-01-19 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '28.00', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('36', '东直门桥东杆路_东13号杆-东直门桥东杆路_东14号杆杆路', null, null, '33', '35.00', null, '370', '371', '0', '0', null, null, '2017-01-19 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '35.00', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('37', '东直门桥东杆路_东14号杆-东直门桥东杆路_东15号杆杆路', null, null, '33', '30.62', null, '371', '372', '0', '0', null, null, '2017-01-19 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '30.62', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('38', '东直门桥东杆路_东15号杆-东直门桥东杆路_东16号杆杆路', null, null, '33', '40.79', null, '372', '373', '0', '0', null, null, '2017-01-19 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '40.79', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('39', '台基厂头条14号院-1号楼东杆路_东1号杆-台基厂头条14号院-1号楼东杆路_东2号杆杆路', null, null, '44', '66.51', null, '375', '376', '0', '0', null, null, '2017-03-08 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '66.51', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('40', '台基厂头条14号院-1号楼东杆路_东2号杆-台基厂头条14号院-1号楼东杆路_东3号杆杆路', null, null, '44', '146.78', null, '376', '377', '0', '0', null, null, '2017-03-08 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '146.78', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('41', '台基厂头条14号院-1号楼东杆路_东3号杆-台基厂头条14号院-1号楼东杆路_东4号杆杆路', null, null, '44', '365902.60', null, '377', '379', '0', '0', null, null, '2017-06-03 00:00:00', null, '1', '2017-06-03', null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '365902.60', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('42', '台基厂头条14号院-1号楼东杆路_东3号杆-台基厂头条14号院-1号楼东杆路_东4号杆杆路', null, null, '44', '365853.12', null, '377', '380', '0', '0', null, null, '2017-06-03 00:00:00', null, '1', '2017-06-03', null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '365853.12', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('43', '台基厂头条14号院-1号楼东杆路_东3号杆-台基厂头条14号院-1号楼东杆路_东4号杆杆路', null, null, '44', '365901.39', null, '377', '381', '0', '0', null, null, '2017-06-03 00:00:00', null, '1', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '365901.39', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('44', '台基厂头条14号院-1号楼东杆路_东4号杆-台基厂头条14号院-1号楼东杆路_东5号杆杆路', null, null, '44', '71.74', null, '381', '382', '0', '0', null, null, '2017-06-03 00:00:00', null, '1', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '71.74', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('45', '台基厂头条14号院-1号楼东杆路_东5号杆-台基厂头条14号院-1号楼东杆路_东6号杆杆路', null, null, '44', '106.57', null, '382', '383', '0', '0', null, null, '2017-06-03 00:00:00', null, '1', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '106.57', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('46', '台基厂头条14号院-1号楼东杆路_东3号杆-台基厂头条14号院-1号楼东杆路_东4号杆', null, null, '44', '365966.05', null, '377', '384', '0', '0', null, null, '2017-06-03 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '365966.05', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('47', '台基厂头条14号院-1号楼东杆路_东4号杆-台基厂头条14号院-1号楼东杆路_东5号杆', null, null, '44', '97.64', null, '384', '385', '0', '0', null, null, '2017-06-03 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '97.64', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('48', '台基厂头条14号院-1号楼东杆路_东5号杆-台基厂头条14号院-1号楼东杆路_东6号杆杆路', null, null, '44', '141.02', null, '385', '386', '0', '0', null, null, '2017-06-03 00:00:00', null, '1', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '141.02', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('49', '测试_南1号杆-测试_南2号杆', null, null, '49', '41.48', null, '387', '388', '0', '0', null, null, '2017-07-04 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '41.48', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('50', '测试_南2号杆-测试_南3号杆杆路', null, null, '49', '50.16', null, '388', '389', '0', '0', null, null, '2017-07-04 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '50.16', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('51', '测试_南3号杆-测试_南4号杆杆路', null, null, '49', '454.15', null, '389', '390', '0', '0', null, null, '2017-08-08 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '454.15', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('52', '测试_南4号杆-测试_南5号杆杆路', null, null, '49', '65.79', null, '390', '391', '0', '0', null, null, '2017-08-08 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '65.79', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('53', '测试_南5号杆-测试_南6号杆杆路', null, null, '49', '56.41', null, '391', '392', '0', '0', null, null, '2017-08-08 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '56.41', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('54', '测试_南6号杆-测试_南7号杆杆路', null, null, '49', '86.09', null, '392', '393', '0', '0', null, null, '2017-08-08 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '86.09', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('55', '测试_南7号杆-测试_南8号杆杆路', null, null, '49', '75.70', null, '393', '394', '0', '0', null, null, '2017-08-08 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '75.70', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('56', '测试_南8号杆-测试_南9号杆杆路', null, null, '49', '108.14', null, '394', '395', '0', '0', null, null, '2017-08-08 00:00:00', null, '0', null, null, null, '2', null, '北京_东城区_', null, null, null, '管理员', '管理员', null, null, '108.14', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('57', '北京市东城区豆瓣胡同杆路_南1号杆-北京市东城区豆瓣胡同杆路_南2号杆杆路段', null, null, '53', '57.85', null, '396', '397', '0', '0', null, null, '2017-09-11 08:41:27', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '城一测试2', '城一测试2', null, null, '57.85', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('58', '北京市东城区豆瓣胡同杆路_南2号杆-北京市东城区豆瓣胡同杆路_南3号杆杆路段', null, null, '53', '45.00', null, '397', '398', '0', '0', null, null, '2017-09-11 08:41:29', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '城一测试2', '城一测试2', null, null, '45.00', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('59', '北京市东城区豆瓣胡同杆路_南3号杆-北京市东城区豆瓣胡同杆路_南4号杆杆路段', null, null, '53', '369117.68', null, '398', '399', '0', '0', null, null, '2017-09-11 11:36:19', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '城一测试2', '城一测试2', null, null, '369117.68', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('60', '山东省济南市历城区工业北路杆路_南1号杆-山东省济南市历城区工业北路杆路_南2号杆杆路段', null, null, '56', '23.73', null, '400', '401', '0', '0', null, null, '2017-09-24 21:27:05', null, '0', null, null, null, '2', null, '山东省_济南市_历城区', null, null, null, '城一测试2', '城一测试2', null, null, '23.73', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('61', '山东省济南市历城区工业北路杆路_南2号杆-山东省济南市历城区工业北路杆路_南3号杆杆路段', null, null, '56', '28.57', null, '401', '402', '0', '0', null, null, '2017-09-25 09:42:33', null, '0', null, null, null, '2', null, '山东省_济南市_历城区', null, null, null, '城一测试2', '城一测试2', '', null, '28.57', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('62', '山东省济南市历城区工业北路杆路_南3号杆-山东省济南市历城区工业北路杆路_南4号杆杆路段', null, null, '56', '25.80', null, '402', '403', '0', '0', null, null, '2017-09-25 09:42:39', null, '0', null, null, null, '2', null, '山东省_济南市_历城区', null, null, null, '城一测试2', '城一测试2', '', null, '25.80', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('63', '山东省济南市历城区工业北路杆路_南4号杆-山东省济南市历城区工业北路杆路_南5号杆杆路段', null, null, '56', '29.13', null, '403', '404', '0', '0', null, null, '2017-09-25 09:42:41', null, '0', null, null, null, '2', null, '山东省_济南市_历城区', null, null, null, '城一测试2', '城一测试2', '', null, '29.13', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('64', '山东省济南市历城区工业北路杆路_南5号杆-山东省济南市历城区工业北路杆路_南6号杆杆路段', null, null, '56', '17.27', null, '404', '405', '0', '0', null, null, '2017-09-25 09:43:13', null, '0', null, null, null, '2', null, '山东省_济南市_历城区', null, null, null, '城一测试2', '城一测试2', '', null, '17.27', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('65', '山东省济南市历城区工业北路杆路_南6号杆-山东省济南市历城区工业北路杆路_南7号杆杆路段', null, null, '56', '24.61', null, '405', '406', '0', '0', null, null, '2017-09-25 09:43:17', null, '0', null, null, null, '2', null, '山东省_济南市_历城区', null, null, null, '城一测试2', '城一测试2', '', null, '24.61', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('66', '山东省济南市历城区工业北路杆路_南7号杆-山东省济南市历城区工业北路杆路_南8号杆杆路段', null, null, '56', '15.16', null, '406', '407', '0', '0', null, null, '2017-09-25 09:43:20', null, '0', null, null, null, '2', null, '山东省_济南市_历城区', null, null, null, '城一测试2', '城一测试2', '', null, '15.16', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('67', '山东省济南市历下区草山岭西路杆路_南1号杆-山东省济南市历下区草山岭西路杆路_南2号杆', null, null, '58', '907.52', null, '409', '410', '0', '0', null, null, '2017-10-10 14:57:48', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '907.52', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('68', '山东省济南市历下区草山岭西路杆路_南2号杆-山东省济南市历下区草山岭西路杆路_南3号杆杆路段', null, null, '58', '734.86', null, '410', '411', '0', '0', null, null, '2017-10-10 14:57:49', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '734.86', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('69', '山东省济南市历下区草山岭西路杆路_南3号杆-山东省济南市历下区草山岭西路杆路_南4号杆杆路段', null, null, '58', '533.27', null, '411', '412', '0', '0', null, null, '2017-10-10 14:57:51', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '533.27', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('70', '山东省济南市历下区草山岭西路杆路_南4号杆-山东省济南市历下区草山岭西路杆路_南5号杆杆路段', null, null, '58', '1098.25', null, '412', '413', '0', '0', null, null, '2017-10-10 14:57:53', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '1098.25', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('71', '山东省济南市历下区草山岭西路杆路_南5号杆-山东省济南市历下区草山岭西路杆路_南6号杆杆路段', null, null, '58', '1137202.75', null, '413', '414', '0', '0', null, null, '2017-11-20 09:24:13', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '1137202.75', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('72', '山东省济南市历下区草山岭西路杆路_南6号杆-山东省济南市历下区草山岭西路杆路_南7号杆杆路段', null, null, '58', '80.31', null, '414', '415', '0', '0', null, null, '2017-11-20 09:24:44', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '80.31', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('73', '山东省济南市历下区草山岭西路杆路_南1号杆-山东省济南市历下区草山岭西路杆路_南2号杆杆路段', null, null, '58', '43.00', null, '416', '417', '0', '0', null, null, '2017-11-24 09:06:42', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '43.00', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('74', '山东省济南市历下区草山岭西路杆路_南2号杆-山东省济南市历下区草山岭西路杆路_南3号杆杆路段', null, null, '58', '54.97', null, '417', '418', '0', '0', null, null, '2017-11-24 09:06:45', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '54.97', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('75', '山东省济南市历下区草山岭西路杆路_南4号杆-山东省济南市历下区草山岭西路杆路_南5号杆杆路段', null, null, '58', '39.38', null, '419', '420', '0', '0', null, null, '2018-01-22 10:15:33', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '39.38', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('76', '山东省济南市历下区草山岭西路杆路_南5号杆-山东省济南市历下区草山岭西路杆路_南6号杆杆路段', null, null, '58', '285.72', null, '420', '421', '0', '0', null, null, '2018-01-22 10:15:36', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '285.72', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('77', '山东省济南市历下区草山岭西路杆路_南6号杆-山东省济南市历下区草山岭西路杆路_南7号杆杆路段', null, null, '58', '230.63', null, '421', '422', '0', '0', null, null, '2018-01-22 10:15:38', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '230.63', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('78', '浪潮测试杆路_东1号杆-浪潮测试杆路_东2号杆杆路段', null, null, '59', '63.54', null, '428', '429', '0', '0', null, null, '2018-01-23 15:14:53', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '63.54', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('79', '浪潮测试杆路_东2号杆-浪潮测试杆路_东3号杆杆路段', null, null, '59', '74.63', null, '429', '430', '0', '0', null, null, '2018-01-23 15:14:55', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '74.63', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('80', '浪潮测试杆路_东3号杆-浪潮测试杆路_东4号杆杆路段', null, null, '59', '73.08', null, '430', '431', '0', '0', null, null, '2018-01-23 15:14:58', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '73.08', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('81', '浪潮测试杆路_东4号杆-浪潮测试杆路_东5号杆杆路段', null, null, '59', '79.29', null, '431', '432', '0', '0', null, null, '2018-01-23 15:14:59', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '79.29', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('82', '浪潮测试杆路_东5号杆-浪潮测试杆路_东6号杆杆路段', null, null, '59', '69.13', null, '432', '433', '0', '0', null, null, '2018-01-23 15:15:01', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '69.13', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('83', '浪潮测试杆路_东6号杆-浪潮测试杆路_东7号杆杆路段', null, null, '59', '75.36', null, '433', '434', '0', '0', null, null, '2018-01-23 15:15:03', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '75.36', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('84', '浪潮测试杆路_东7号杆-浪潮测试杆路_东8号杆杆路段', null, null, '59', '62.99', null, '434', '435', '0', '0', null, null, '2018-01-23 15:15:05', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '62.99', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('85', '浪潮测试杆路_东8号杆-浪潮测试杆路_东9号杆杆路段', null, null, '59', '78.67', null, '435', '436', '0', '0', null, null, '2018-01-23 15:15:07', null, '0', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, '管理员', '管理员', null, null, '78.67', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('86', '测试电杆085-测试电杆088杆路段', null, null, null, '122.46', null, '424', '427', '0', '0', null, null, '2018-01-24 11:16:56', null, '1', null, null, null, '2', null, '山东省_济南市_历下区', null, null, null, null, '管理员', null, null, '122.28', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('87', '测试电杆085-测试电杆0896669杆路段', null, null, null, '53.08', null, '424', '425', '0', '0', null, null, '2018-02-01 14:49:29', null, '1', null, null, null, '2', null, '河北省_石家庄市_长安区', null, null, null, null, '管理员', null, null, '53.1', null, null, null, null);
INSERT INTO `polelinesegmentinfo` VALUES ('89', 'gld', null, '4', null, '357.15', null, '444', '445', '4', null, null, null, '2018-07-02 11:30:12', '2018-07-02 17:01:43', '0', null, null, null, '2', null, '北京市_东城区_', null, null, null, null, '管理员', null, null, null, '5', 'ghj', 'hhhj', '0');
INSERT INTO `polelinesegmentinfo` VALUES ('90', '电1-电杆chenwenhua杆路段', null, '0', null, '357.15', null, '445', '444', '0', null, null, null, '2018-07-02 14:32:51', null, '0', null, null, null, '2', null, '北京市_东城区_', null, null, null, null, '管理员', null, null, null, '0', '', '', '				');
INSERT INTO `polelinesegmentinfo` VALUES ('91', '测试', null, '0', null, '357.15', null, '445', '444', '0', null, null, null, '2018-07-02 14:34:49', null, '0', null, null, null, '2', null, '北京市_东城区_', null, null, null, null, '管理员', null, null, null, '0', '', '', '				');
