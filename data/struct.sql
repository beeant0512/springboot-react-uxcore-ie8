/*
 Navicat Premium Data Transfer

 Source Server         : mac
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : spring

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 18/09/2017 08:44:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for application_log_user_login
-- ----------------------------
DROP TABLE IF EXISTS `application_log_user_login`;
CREATE TABLE `application_log_user_login` (
  `log_login_id` bigint(20) NOT NULL COMMENT 'id',
  `ip` char(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'ip address',
  `agent` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'login agent info',
  `create_at` datetime NOT NULL COMMENT 'login time',
  `create_by` bigint(20) DEFAULT NULL COMMENT 'login user id',
  `update_at` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`log_login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for application_menu
-- ----------------------------
DROP TABLE IF EXISTS `application_menu`;
CREATE TABLE `application_menu` (
  `menu_id` bigint(20) unsigned NOT NULL,
  `parent_menu_id` bigint(20) DEFAULT '0' COMMENT 'parent menu id in a tree node',
  `menu_name` varchar(50) COLLATE utf8mb4_bin DEFAULT '',
  `menu_url` varchar(100) COLLATE utf8mb4_bin DEFAULT '',
  `menu_perm` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'permision',
  `level` int(4) DEFAULT '1' COMMENT 'menu level in a tree node',
  `create_at` datetime DEFAULT NULL,
  `create_by` bigint(20) unsigned DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  `update_by` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for application_user
-- ----------------------------
DROP TABLE IF EXISTS `application_user`;
CREATE TABLE `application_user` (
  `user_id` bigint(20) NOT NULL COMMENT 'user id',
  `user_name` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT 'login account',
  `user_password` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT 'login password bcrypt encoded',
  `create_at` datetime NOT NULL COMMENT 'when created',
  `create_by` bigint(20) NOT NULL COMMENT 'who created',
  `update_at` datetime DEFAULT NULL COMMENT 'last update time',
  `update_by` bigint(20) DEFAULT NULL COMMENT 'last update user_id',
  `last_login_at` datetime DEFAULT NULL COMMENT 'last login time',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
