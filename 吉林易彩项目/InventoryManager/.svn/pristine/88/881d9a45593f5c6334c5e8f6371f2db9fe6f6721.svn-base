/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : im

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 30/06/2018 18:04:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for RMS_OPTELE_CASE 光交接箱
-- ----------------------------
DROP TABLE IF EXISTS `rms_optele_case`;
CREATE TABLE `rms_optele_case`  (
  `int_id` int(11)  NOT NULL AUTO_INCREMENT COMMENT '系统内部编码',
  `zh_label` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '中文名称',
  `fibercab_no` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '光交接箱编号',
  `city_id` int(11)  NOT NULL COMMENT '地市id',
  `county_id` int(11)  NOT NULL COMMENT '所属区县id',
  `states` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备状态',
  `vendor` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备厂家',
  `location` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '具体位置',
  `longitude` varchar(100) NOT NULL COMMENT '经度',
  `latitude` varchar(100) NOT NULL COMMENT '纬度',
  `model` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '型号',
  `ownership` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '产权',
  `purpose` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用途',
  `cab_user` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '使用单位',
  `res_owner` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所有权人',
  `design_capacity` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '容量',
  `maintain_company` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '维护单位',
  `maintain_area` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '综合维护区域',
  `maintain_manager` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '综合维护区域组长',
  `maintain_manager_phone` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '维护组长电话',
  `remark` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `change_reason` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '变更原因',
  `time_stamp` timestamp(6) NOT NULL COMMENT '时间戳',
  `stateflag` int(11)  NOT NULL COMMENT '删除标识',
  `creator` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人',
  `creat_time` datetime NOT NULL COMMENT '创建时间',
  `modifier` varchar(100)  CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`int_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 439 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

create unique index RMS_OPTELE_CASE_INDEX on rms_optele_case (CITY_ID, COUNTY_ID, ZH_LABEL, STATEFLAG);
create unique index RMS_OPTELE_CASE_MODIFY on rms_optele_case (INT_ID, STATEFLAG);
