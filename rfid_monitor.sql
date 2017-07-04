/*
Navicat MySQL Data Transfer

Source Server         : 115.154.191.5
Source Server Version : 50621
Source Host           : 115.154.191.5:3306
Source Database       : rfid_monitor

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2017-06-13 20:04:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for event
-- ----------------------------
DROP TABLE IF EXISTS `event`;
CREATE TABLE `event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `workpieceId` varchar(255) DEFAULT NULL,
  `processId` varchar(255) DEFAULT NULL,
  `event` int(3) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of event
-- ----------------------------
INSERT INTO `event` VALUES ('1', '2017gp00103030201cl00002', 'p1', '1', '2017-05-04 15:10:53');
INSERT INTO `event` VALUES ('2', '2017gp00103030201cl00002', 'p1', '2', '2017-05-04 15:11:17');
INSERT INTO `event` VALUES ('3', '2017gp00103030201cl00002', 'p1', '3', '2017-05-04 15:11:27');
INSERT INTO `event` VALUES ('4', '2017gp00103030201cl00002', 'p1', '4', '2017-05-04 15:11:45');
INSERT INTO `event` VALUES ('5', '2017gp00103030201cl00002', 'p1', '5', '2017-05-04 15:11:55');
INSERT INTO `event` VALUES ('6', '2017gp00103030201cl00002', 'p1', '6', '2017-05-04 15:12:15');
INSERT INTO `event` VALUES ('7', '2017gp00503030201CL001', 'p1', '1', '2017-05-04 15:17:55');
INSERT INTO `event` VALUES ('8', '2017gp00503030201CL001', 'p1', '2', '2017-05-04 15:18:07');
INSERT INTO `event` VALUES ('9', '2017gp00503030201CL001', 'p1', '3', '2017-05-04 15:18:17');
INSERT INTO `event` VALUES ('10', '2017gp00503030201CL001', 'p1', '4', '2017-05-04 15:20:59');
INSERT INTO `event` VALUES ('11', '2017gp00503030201CL001', 'p1', '5', '2017-05-04 15:21:28');
INSERT INTO `event` VALUES ('12', '2017gp00503030201CL001', 'p1', '6', '2017-05-04 15:22:39');
INSERT INTO `event` VALUES ('13', '2017gp00503030201CL001', 'p2', '1', '2017-05-04 15:23:07');
INSERT INTO `event` VALUES ('14', '2017gp00503030201CL001', 'p2', '2', '2017-05-04 15:23:17');
INSERT INTO `event` VALUES ('15', '2017gp00503030201CL001', 'p2', '3', '2017-05-04 15:23:34');
INSERT INTO `event` VALUES ('16', '2017gp00503030201CL002', 'p1', '1', '2017-05-04 15:23:41');
INSERT INTO `event` VALUES ('17', '2017gp00503030201CL001', 'p2', '4', '2017-05-04 15:24:54');
INSERT INTO `event` VALUES ('18', '2017gp00503030201CL001', 'p2', '5', '2017-05-04 15:25:04');
INSERT INTO `event` VALUES ('19', '2017gp00503030201CL002', 'p1', '2', '2017-05-04 15:25:05');
INSERT INTO `event` VALUES ('20', '2017gp00503030201CL002', 'p1', '3', '2017-05-04 15:25:17');
INSERT INTO `event` VALUES ('21', '2017gp00503030201CL003', 'p1', '1', '2017-05-04 15:25:36');
INSERT INTO `event` VALUES ('22', '2017gp00503030201CL001', 'p2', '6', '2017-05-04 15:25:54');
INSERT INTO `event` VALUES ('23', '2017gp00503030201CL002', 'p1', '4', '2017-05-04 15:27:20');
INSERT INTO `event` VALUES ('24', '2017gp00503030201CL002', 'p1', '5', '2017-05-04 15:27:35');
INSERT INTO `event` VALUES ('25', '2017gp00503030201CL003', 'p1', '2', '2017-05-04 15:27:36');
INSERT INTO `event` VALUES ('26', '2017gp00503030201CL003', 'p1', '3', '2017-05-04 15:27:52');
INSERT INTO `event` VALUES ('27', '2017gp00503030201CL002', 'p1', '6', '2017-05-04 15:28:15');
INSERT INTO `event` VALUES ('28', '2017gp00503030201CL002', 'p2', '1', '2017-05-04 15:28:32');
INSERT INTO `event` VALUES ('29', '2017gp00503030201CL002', 'p2', '2', '2017-05-04 15:28:39');
INSERT INTO `event` VALUES ('30', '2017gp00503030201CL002', 'p2', '3', '2017-05-04 15:28:53');
INSERT INTO `event` VALUES ('31', '2017gp00503030201CL002', 'p2', '4', '2017-05-04 15:29:19');
INSERT INTO `event` VALUES ('32', '2017gp00503030201CL002', 'p2', '5', '2017-05-04 15:29:34');
INSERT INTO `event` VALUES ('33', '2017gp00503030201CL003', 'p1', '4', '2017-05-04 15:29:37');
INSERT INTO `event` VALUES ('34', '2017gp00503030201CL002', 'p2', '6', '2017-05-04 15:29:40');
INSERT INTO `event` VALUES ('35', '2017gp00503030201CL002', 'p3', '1', '2017-05-04 15:29:58');
INSERT INTO `event` VALUES ('36', '2017gp00503030201CL003', 'p1', '5', '2017-05-04 15:30:01');
INSERT INTO `event` VALUES ('37', '2017gp00503030201CL002', 'p3', '2', '2017-05-04 15:30:16');
INSERT INTO `event` VALUES ('38', '2017gp00503030201CL003', 'p1', '6', '2017-05-04 15:30:25');
INSERT INTO `event` VALUES ('39', '2017gp00503030201CL002', 'p3', '3', '2017-05-04 15:30:25');
INSERT INTO `event` VALUES ('40', '2017gp00503030201CL003', 'p2', '1', '2017-05-04 15:30:36');
INSERT INTO `event` VALUES ('41', '2017gp00503030201CL003', 'p2', '2', '2017-05-04 15:30:53');
INSERT INTO `event` VALUES ('42', '2017gp00503030201CL003', 'p2', '3', '2017-05-04 15:31:07');
INSERT INTO `event` VALUES ('43', '2017gp00503030201CL002', 'p3', '4', '2017-05-04 15:31:33');
INSERT INTO `event` VALUES ('44', '2017gp00503030201CL003', 'p2', '4', '2017-05-04 15:31:52');
INSERT INTO `event` VALUES ('45', '2017gp00503030201CL002', 'p3', '5', '2017-05-04 15:31:53');
INSERT INTO `event` VALUES ('46', '2017gp00503030201CL003', 'p2', '5', '2017-05-04 15:32:10');
INSERT INTO `event` VALUES ('47', '2017gp00503030201CL002', 'p3', '6', '2017-05-04 15:32:11');
INSERT INTO `event` VALUES ('48', '2017gp00503030201CL003', 'p2', '6', '2017-05-04 15:32:35');
INSERT INTO `event` VALUES ('49', '2017gp00503030201CL003', 'p3', '1', '2017-05-04 15:32:44');
INSERT INTO `event` VALUES ('50', '2017gp00503030201CL003', 'p3', '2', '2017-05-04 15:32:58');
INSERT INTO `event` VALUES ('51', '2017gp00503030201CL003', 'p3', '3', '2017-05-04 15:33:05');
INSERT INTO `event` VALUES ('52', '2017gp00503030201CL003', 'p3', '4', '2017-05-04 15:34:11');
INSERT INTO `event` VALUES ('53', '2017gp00503030201CL003', 'p3', '5', '2017-05-04 15:34:27');
INSERT INTO `event` VALUES ('54', '2017gp00503030201CL003', 'p3', '6', '2017-05-04 15:34:36');
INSERT INTO `event` VALUES ('55', '2017gp00503030201CL004', 'p1', '1', '2017-05-04 15:37:31');
INSERT INTO `event` VALUES ('56', '2017gp00503030201CL004', 'p1', '2', '2017-05-04 15:37:44');
INSERT INTO `event` VALUES ('57', '2017gp00503030201CL004', 'p1', '3', '2017-05-04 15:37:52');
INSERT INTO `event` VALUES ('58', '2017gp00503030201CL004', 'p1', '4', '2017-05-04 15:50:10');
INSERT INTO `event` VALUES ('59', '2017gp00503030201CL004', 'p1', '5', '2017-05-04 15:50:21');
INSERT INTO `event` VALUES ('60', '2017gp00503030201CL004', 'p1', '6', '2017-05-04 15:50:34');
INSERT INTO `event` VALUES ('61', '2017gp00503030201CL004', 'p2', '1', '2017-05-04 15:50:55');
INSERT INTO `event` VALUES ('62', '2017gp00503030201CL004', 'p2', '2', '2017-05-04 15:51:13');
INSERT INTO `event` VALUES ('63', '2017gp00503030201CL004', 'p2', '3', '2017-05-04 15:51:15');
INSERT INTO `event` VALUES ('64', '2017gp00503030201CL004', 'p2', '4', '2017-05-04 15:53:02');
INSERT INTO `event` VALUES ('65', '2017gp00503030201CL004', 'p2', '5', '2017-05-04 15:53:30');
INSERT INTO `event` VALUES ('66', '2017gp00503030201CL004', 'p2', '6', '2017-05-04 15:53:46');
INSERT INTO `event` VALUES ('67', '2017gp00503030201CL004', 'p3', '1', '2017-05-04 15:54:08');
INSERT INTO `event` VALUES ('68', '2017gp00503030201CL004', 'p3', '2', '2017-05-04 15:54:28');
INSERT INTO `event` VALUES ('69', '2017gp00503030201CL004', 'p3', '3', '2017-05-04 15:54:42');
INSERT INTO `event` VALUES ('70', '2017gp00503030201CL004', 'p3', '4', '2017-05-04 15:55:43');
INSERT INTO `event` VALUES ('71', '2017gp00503030201CL004', 'p3', '5', '2017-05-04 15:56:01');
INSERT INTO `event` VALUES ('72', '2017gp00503030201CL004', 'p3', '6', '2017-05-04 15:56:37');

-- ----------------------------
-- Table structure for machine
-- ----------------------------
DROP TABLE IF EXISTS `machine`;
CREATE TABLE `machine` (
  `machName` varchar(255) DEFAULT NULL,
  `flag` varchar(5) NOT NULL DEFAULT '0',
  `machId` varchar(255) NOT NULL,
  PRIMARY KEY (`machId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of machine
-- ----------------------------
INSERT INTO `machine` VALUES ('车床', '1', 'm1');
INSERT INTO `machine` VALUES ('立式铣床', '1', 'm2');
INSERT INTO `machine` VALUES ('加工中心', '1', 'm3');

-- ----------------------------
-- Table structure for mach_rfid
-- ----------------------------
DROP TABLE IF EXISTS `mach_rfid`;
CREATE TABLE `mach_rfid` (
  `machId` varchar(255) NOT NULL DEFAULT '0',
  `rfidSysId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`machId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mach_rfid
-- ----------------------------
INSERT INTO `mach_rfid` VALUES ('m1', 's1');
INSERT INTO `mach_rfid` VALUES ('m2', 's2');
INSERT INTO `mach_rfid` VALUES ('m3', 's3');

-- ----------------------------
-- Table structure for rfidsystem
-- ----------------------------
DROP TABLE IF EXISTS `rfidsystem`;
CREATE TABLE `rfidsystem` (
  `rfidSysId` varchar(255) NOT NULL DEFAULT '0',
  `rfidName` varchar(255) DEFAULT NULL,
  `rfidModel` varchar(255) DEFAULT NULL,
  `rfidPort` int(11) DEFAULT NULL,
  `rfidIp` varchar(255) DEFAULT NULL,
  `flag` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`rfidSysId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rfidsystem
-- ----------------------------
INSERT INTO `rfidsystem` VALUES ('s1', '易联147', null, null, '192.168.1.147', '1');
INSERT INTO `rfidsystem` VALUES ('s2', '易联163', null, null, '192.168.1.163', '1');
INSERT INTO `rfidsystem` VALUES ('s3', '易联129', null, null, '192.168.1.129', '1');
INSERT INTO `rfidsystem` VALUES ('writer', '易联129', null, null, '192.168.1.129', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` varchar(11) NOT NULL,
  `password` varchar(20) NOT NULL DEFAULT '0',
  `userName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2017gp001', '2017gp001', '第一组');
INSERT INTO `user` VALUES ('2017gp002', '2017gp002', '第二组');
INSERT INTO `user` VALUES ('2017gp003', '2017gp003', '第三组');
INSERT INTO `user` VALUES ('2017gp004', '2017gp004', '第四组');
INSERT INTO `user` VALUES ('2017gp005', '2017gp005', '第五组');
INSERT INTO `user` VALUES ('2017gp006', '2017gp006', '第六组');
INSERT INTO `user` VALUES ('2017gp007', '2017gp007', '第七组');
INSERT INTO `user` VALUES ('2017gp008', '2017gp008', '第八组');
INSERT INTO `user` VALUES ('admin', 'admin', '管理员1');

-- ----------------------------
-- Table structure for workpiece
-- ----------------------------
DROP TABLE IF EXISTS `workpiece`;
CREATE TABLE `workpiece` (
  `workpieceId` varchar(255) NOT NULL,
  `workpieceName` varchar(255) DEFAULT NULL,
  `workpieceRfid` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `flag` varchar(1) NOT NULL DEFAULT '0',
  `userId` varchar(11) DEFAULT NULL,
  `finish` varchar(1) NOT NULL DEFAULT '0',
  `processCount` int(3) NOT NULL DEFAULT '0',
  `isBanding` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`workpieceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of workpiece
-- ----------------------------
INSERT INTO `workpiece` VALUES ('2017gp00103030201cl00001', '试验1', '2017gp00103030201cl00001', '2017-05-04 15:02:44', '1', '2017gp001', '0', '1', '1');
INSERT INTO `workpiece` VALUES ('2017gp00103030201cl00002', 'shiyan2', '2017gp00103030201cl00002', '2017-05-04 15:07:49', '1', '2017gp001', '1', '1', '1');
INSERT INTO `workpiece` VALUES ('2017gp00503030201CL001', '阶梯轴', '2017gp00503030201CL001', '2017-05-04 15:15:21', '1', '2017gp005', '1', '2', '1');
INSERT INTO `workpiece` VALUES ('2017gp00503030201CL002', '螺母', '2017gp00503030201CL002', '2017-05-04 15:19:30', '1', '2017gp005', '1', '3', '1');
INSERT INTO `workpiece` VALUES ('2017gp00503030201CL003', '螺母', '2017gp00503030201CL003', '2017-05-04 15:24:12', '1', '2017gp005', '1', '3', '1');
INSERT INTO `workpiece` VALUES ('2017gp00503030201CL004', '螺栓', '2017gp00503030201CL004', '2017-05-04 15:35:59', '1', '2017gp005', '1', '3', '1');

-- ----------------------------
-- Table structure for workpiece_process
-- ----------------------------
DROP TABLE IF EXISTS `workpiece_process`;
CREATE TABLE `workpiece_process` (
  `Num` int(11) NOT NULL AUTO_INCREMENT,
  `workpieceId` varchar(255) NOT NULL DEFAULT '0',
  `processName` varchar(255) DEFAULT NULL,
  `processId` varchar(255) DEFAULT NULL,
  `machId` varchar(255) DEFAULT NULL,
  `level` int(3) DEFAULT NULL,
  `state` int(3) NOT NULL DEFAULT '0' COMMENT 'rfid读取状态数，0-初始化状态，1-进入入缓存区，2-离开入缓存区，3-进入车床，4-离开车床，5-进入出缓存区，6-离开出缓存区',
  PRIMARY KEY (`Num`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of workpiece_process
-- ----------------------------
INSERT INTO `workpiece_process` VALUES ('1', '2017gp00103030201cl00001', 'gongxu1', 'p1', 'm1', '1', '0');
INSERT INTO `workpiece_process` VALUES ('2', '2017gp00103030201cl00002', 'gongx1', 'p1', 'm2', '1', '6');
INSERT INTO `workpiece_process` VALUES ('3', '2017gp00503030201CL001', '车端面', 'p1', 'm1', '1', '6');
INSERT INTO `workpiece_process` VALUES ('4', '2017gp00503030201CL001', '铣键槽', 'p2', 'm2', '2', '6');
INSERT INTO `workpiece_process` VALUES ('5', '2017gp00503030201CL002', '铣平面', 'p1', 'm2', '1', '6');
INSERT INTO `workpiece_process` VALUES ('6', '2017gp00503030201CL002', '倒角', 'p2', 'm1', '2', '6');
INSERT INTO `workpiece_process` VALUES ('7', '2017gp00503030201CL002', '攻螺纹', 'p3', 'm3', '3', '6');
INSERT INTO `workpiece_process` VALUES ('8', '2017gp00503030201CL003', '铣平面', 'p1', 'm2', '1', '6');
INSERT INTO `workpiece_process` VALUES ('9', '2017gp00503030201CL003', '倒角', 'p2', 'm1', '2', '6');
INSERT INTO `workpiece_process` VALUES ('10', '2017gp00503030201CL003', '攻螺纹', 'p3', 'm3', '3', '6');
INSERT INTO `workpiece_process` VALUES ('11', '2017gp00503030201CL004', '车端面', 'p1', 'm1', '1', '6');
INSERT INTO `workpiece_process` VALUES ('12', '2017gp00503030201CL004', '铣六角', 'p2', 'm2', '2', '6');
INSERT INTO `workpiece_process` VALUES ('13', '2017gp00503030201CL004', '外螺纹', 'p3', 'm3', '3', '6');
