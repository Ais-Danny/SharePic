/*
 Navicat Premium Data Transfer

 Source Server         : service
 Source Server Type    : MySQL
 Source Server Version : 50650
 Source Host           : aisdanny.top:3306
 Source Schema         : pic

 Target Server Type    : MySQL
 Target Server Version : 50650
 File Encoding         : 65001

 Date: 29/11/2022 01:43:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pic
-- ----------------------------
DROP TABLE IF EXISTS `pic`;
CREATE TABLE `pic`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `src` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `src`(`src`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
