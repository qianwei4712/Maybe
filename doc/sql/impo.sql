/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 127.0.0.1:3306
 Source Schema         : impo

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 09/12/2019 10:19:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `del_flag` int(1) NULL DEFAULT 0 COMMENT '逻辑删除，0-正常，1-删除',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '时间',
  `create_by` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '创建对象',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统-地区表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `del_flag` int(1) NULL DEFAULT 0 COMMENT '逻辑删除，0-正常，1-删除',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '时间',
  `create_by` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '创建对象',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统-字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `pid` bigint(20) NULL DEFAULT 0 COMMENT '上级菜单id',
  `pids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级部门id字符串，\",\"分割',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名',
  `sort` int(10) NULL DEFAULT 0 COMMENT '排序',
  `href` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接地址',
  `target` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小图标',
  `is_show` tinyint(1) NULL DEFAULT 1 COMMENT '是否在菜单中显示,0-不显示，1-显示',
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `del_flag` int(1) NULL DEFAULT 0 COMMENT '逻辑删除，0-正常，1-删除',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '时间',
  `create_by` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '创建对象',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统-菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, -1, '-1,', '主页', 0, '#', '', 'layui-icon-home', 1, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (2, 1, '-1,1,', '控制台', 0, '/manage/console', '', 'layui-icon-snowflake', 1, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (7, -1, '-1,', '基础设置', 999, '#', '', 'layui-icon-set', 1, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (8, 7, '-1,7,', '用户管理', 30, '/manage/user', '', '', 1, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (9, 7, '-1,7,', '部门管理', 60, '/manage/office', '', '', 1, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (12, 7, '-1,7,', '菜单管理', 0, '/manage/menu', '', '', 1, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (13, 7, '-1,7,', '角色管理', 0, '/manage/role', '', '', 1, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (14, -1, '-1,', '系统设置', 9999, '', '', 'layui-icon-engine', 1, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (15, 14, '-1,14,', '系统监控', 0, '/manage/supervisory', '', '', 1, NULL, 0, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_office`;
CREATE TABLE `sys_office`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `pid` bigint(20) NULL DEFAULT 0 COMMENT '上级部门id',
  `pids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级部门id字符串，\",\"分割',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `sort` int(10) NULL DEFAULT 0 COMMENT '排序',
  `grade` int(4) NULL DEFAULT 1 COMMENT '部门等级，0-顶级',
  `telephone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门座机',
  `del_flag` int(1) NULL DEFAULT 0 COMMENT '逻辑删除，0-正常，1-删除',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '时间',
  `create_by` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '创建对象',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统-部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_office
-- ----------------------------
INSERT INTO `sys_office` VALUES (1, -1, '-1,', '甘肃电子口岸有限公司', 0, 0, '0574-8640838', 0, NULL, NULL, NULL);
INSERT INTO `sys_office` VALUES (5, 1, NULL, '运营维护部', 0, 1, '123', 0, NULL, NULL, NULL);
INSERT INTO `sys_office` VALUES (6, 1, NULL, '客服部', 0, 1, '', 0, NULL, NULL, NULL);
INSERT INTO `sys_office` VALUES (7, 5, NULL, '单一窗口项目组', 0, 1, '', 0, NULL, NULL, NULL);
INSERT INTO `sys_office` VALUES (8, 1, NULL, '财务部', NULL, NULL, '', 0, '2019-12-08 13:24:35', 1, NULL);
INSERT INTO `sys_office` VALUES (9, 1, NULL, '总经办', NULL, NULL, '', 0, '2019-12-08 13:24:52', 1, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `enname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色英文名',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态，0-正常，1-禁用',
  `del_flag` int(1) NULL DEFAULT 0 COMMENT '逻辑删除，0-正常，1-删除',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '时间',
  `create_by` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '创建对象',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统-角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', 'admin', '0', 0, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (2, '游客', 'guest', '0', 0, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统-角色菜单中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 7);
INSERT INTO `sys_role_menu` VALUES (1, 8);
INSERT INTO `sys_role_menu` VALUES (1, 9);
INSERT INTO `sys_role_menu` VALUES (1, 12);
INSERT INTO `sys_role_menu` VALUES (1, 13);
INSERT INTO `sys_role_menu` VALUES (2, 7);
INSERT INTO `sys_role_menu` VALUES (2, 8);
INSERT INTO `sys_role_menu` VALUES (2, 9);
INSERT INTO `sys_role_menu` VALUES (2, 12);
INSERT INTO `sys_role_menu` VALUES (2, 13);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `office` bigint(20) NULL DEFAULT 0 COMMENT '部门id',
  `office_ids` varchar(2550) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门id字符串拼接，“,”分割',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录名/账号',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码，加密后',
  `mobile` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `telephone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '座机',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '生日',
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别，0-男，1-女，2-其他',
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像链接',
  `status` varchar(10) CHARACTER SET utf16 COLLATE utf16_general_ci NULL DEFAULT '0' COMMENT '状态，0-启用，1-禁用',
  `del_flag` int(1) NULL DEFAULT 0 COMMENT '逻辑删除，0-正常，1-删除',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '时间',
  `create_by` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '创建对象',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统-用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '超级管理员', 1, '1,', 'admin', '8c024c73f7111c6124dbabe5edd1cd40', '110,110', '2', '1', NULL, '', NULL, '0', 0, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (6, '测试姓名', 7, NULL, 'test', 'a2d00b3586063e91b5986781cf7a6dea', ',', '', '', NULL, '', NULL, '0', 0, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统-用户角色中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (6, 1);

SET FOREIGN_KEY_CHECKS = 1;
