/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1-本地库
 Source Server Type    : MySQL
 Source Server Version : 80028 (8.0.28)
 Source Host           : localhost:3306
 Source Schema         : yf_boot

 Target Server Type    : MySQL
 Target Server Version : 80028 (8.0.28)
 File Encoding         : 65001

 Date: 04/09/2023 15:13:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for el_cfg_base
-- ----------------------------
DROP TABLE IF EXISTS `el_cfg_base`;
CREATE TABLE `el_cfg_base` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `site_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '系统名称',
  `login_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录LOGO',
  `login_bg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录背景',
  `back_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '后台LOGO',
  `copy_right` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '版权信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统设置';

-- ----------------------------
-- Records of el_cfg_base
-- ----------------------------
BEGIN;
INSERT INTO `el_cfg_base` (`id`, `site_name`, `login_logo`, `login_bg`, `back_logo`, `copy_right`) VALUES ('1', '快速开发平台', 'http://localhost:8080/upload/file/2023/09/01/1697543225524461569.png', 'https://www.kaoshifeng.com/img/advantages/4.png', 'https://files.yfhl.net/2022/11/21/1669011274378-914f43d8.png', '&copy; 2022北京云帆互联科技有限公司 <a href=\"https://www.yfhl.net\" target=\"_blank\">企业官网</a> | <a href=\"https://www.yfhl.net/contactus.html\" target=\"_blank\">联系我们</a>');
COMMIT;

-- ----------------------------
-- Table structure for el_cfg_switch
-- ----------------------------
DROP TABLE IF EXISTS `el_cfg_switch`;
CREATE TABLE `el_cfg_switch` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '功能名称',
  `val` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '开关或值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='功能配置';

-- ----------------------------
-- Records of el_cfg_switch
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for el_cfg_upload
-- ----------------------------
DROP TABLE IF EXISTS `el_cfg_upload`;
CREATE TABLE `el_cfg_upload` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `provider` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服务提供商',
  `enabled` tinyint DEFAULT NULL COMMENT '是否启用',
  `data` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '配置数据',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='上传配置';

-- ----------------------------
-- Records of el_cfg_upload
-- ----------------------------
BEGIN;
INSERT INTO `el_cfg_upload` (`id`, `provider`, `enabled`, `data`, `remark`) VALUES ('1526038580764360705', 'qiniu', 0, '{\"bucket\":\"yf-exam\",\"accessKeyId\":\"edyB3_QoqGQWLXoKIONXUi63qvOGuMhpZAwfmitA\",\"endpoint\":\"https://upload-z2.qiniup.com\",\"accessKeySecret\":\"rn-M1FEN2OgZcFqfLJCb3LdfJDbLcY-_zlsg9toT\",\"url\":\"http://qiniu-cdn.jeegen.com/\"}', '');
INSERT INTO `el_cfg_upload` (`id`, `provider`, `enabled`, `data`, `remark`) VALUES ('1526040155742289922', 'cos', 0, '{\"bucket\":\"yfhl-1252868612\",\"secretKey\":\"5zd1ImTyKTN0QcaIo7FnUv60jEVWliqI\",\"secretId\":\"AKID91mzwNDR5WfXGjrgkGZbMs78s2z9p4JN\",\"region\":\"ap-beijing\",\"url\":\"https://yfhl-1252868612.cos.ap-beijing.myqcloud.com/\"}', '');
INSERT INTO `el_cfg_upload` (`id`, `provider`, `enabled`, `data`, `remark`) VALUES ('1536906975202283521', 'local', 0, '{\"localDir\":\"/Users/van/work/upload/\",\"url\":\"http://localhost:8080\"}', '');
INSERT INTO `el_cfg_upload` (`id`, `provider`, `enabled`, `data`, `remark`) VALUES ('1537629737109762049', 'oss', 1, '{\"accessKeyId\":\"LTAI5t5kUQde7fGu83s8cC23\",\"pipeline\":\"fabae21dbe264fa5aad0bdb17295d9e3\",\"bucket\":\"yfhl-files\",\"accessKeySecret\":\"Exli25RLrjpMWGxJUurj7Z566vVSNq\",\"endpoint\":\"oss-cn-beijing\",\"localDir\":\"/Users/van/work/upload/\",\"project\":\"bjconv\",\"url\":\"https://cdn.jeegen.com/\"}', '');
COMMIT;

-- ----------------------------
-- Table structure for el_sys_depart
-- ----------------------------
DROP TABLE IF EXISTS `el_sys_depart`;
CREATE TABLE `el_sys_depart` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `dept_type` int NOT NULL DEFAULT '1' COMMENT '1公司2部门',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '所属上级',
  `dept_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `dept_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门编码',
  `dept_level` int NOT NULL DEFAULT '0' COMMENT '部门层级',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `dept_code` (`dept_code`) USING BTREE,
  KEY `parent_id` (`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='部门信息';

-- ----------------------------
-- Records of el_sys_depart
-- ----------------------------
BEGIN;
INSERT INTO `el_sys_depart` (`id`, `dept_type`, `parent_id`, `dept_name`, `dept_code`, `dept_level`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1441328268501381121', 1, '0', '云帆互联', 'A01', 0, 2, '2021-09-24 17:06:52', '2021-09-24 17:06:52', '', '');
INSERT INTO `el_sys_depart` (`id`, `dept_type`, `parent_id`, `dept_name`, `dept_code`, `dept_level`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1447829893265002498', 1, '1441328268501381121', '技术部', 'A01A01', 0, 3, '2021-10-12 15:42:01', '2021-10-12 15:42:00', '10001', '');
INSERT INTO `el_sys_depart` (`id`, `dept_type`, `parent_id`, `dept_name`, `dept_code`, `dept_level`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1447829920330846210', 1, '1441328268501381121', '财务部', 'A01A02', 0, 1, '2021-10-12 15:42:07', '2021-10-12 15:42:07', '10001', '');
INSERT INTO `el_sys_depart` (`id`, `dept_type`, `parent_id`, `dept_name`, `dept_code`, `dept_level`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1447829960050905090', 1, '1441328268501381121', '商务部', 'A01A03', 0, 2, '2021-10-12 15:42:17', '2021-10-12 15:42:16', '10001', '');
INSERT INTO `el_sys_depart` (`id`, `dept_type`, `parent_id`, `dept_name`, `dept_code`, `dept_level`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1557278968304906242', 1, '0', '某公司2', 'A03', 0, 3, '2022-08-10 16:13:33', '2022-08-10 16:13:33', '', '');
INSERT INTO `el_sys_depart` (`id`, `dept_type`, `parent_id`, `dept_name`, `dept_code`, `dept_level`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1557282632381771777', 1, '1557278968304906242', '某部门1', 'A03A01', 0, 1, '2022-08-10 16:28:07', '2022-08-10 16:28:07', '', '');
INSERT INTO `el_sys_depart` (`id`, `dept_type`, `parent_id`, `dept_name`, `dept_code`, `dept_level`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1557282652489265153', 1, '1557278968304906242', '某部门2', 'A03A02', 0, 2, '2022-08-10 16:28:11', '2022-08-10 16:28:11', '', '');
COMMIT;

-- ----------------------------
-- Table structure for el_sys_dic
-- ----------------------------
DROP TABLE IF EXISTS `el_sys_dic`;
CREATE TABLE `el_sys_dic` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典编码',
  `type` int DEFAULT NULL COMMENT '1分类字典,2数据字典',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典名称',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='分类字典';

-- ----------------------------
-- Records of el_sys_dic
-- ----------------------------
BEGIN;
INSERT INTO `el_sys_dic` (`id`, `code`, `type`, `title`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1399935914490269698', 'data_scope', 1, '数据权限', '角色数据权限范围', '2021-06-02 11:48:26', '2021-06-02 11:48:26', '', '');
INSERT INTO `el_sys_dic` (`id`, `code`, `type`, `title`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1492388856845103105', 'state', 1, '通用状态', '系统全局通用状态', '2022-02-12 14:43:25', '2022-02-12 14:43:25', '', '');
INSERT INTO `el_sys_dic` (`id`, `code`, `type`, `title`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1546399651832299522', 'user_state', 1, '用户状态', '用户状态', '2022-07-11 15:43:02', '2022-07-11 15:43:02', '', '');
INSERT INTO `el_sys_dic` (`id`, `code`, `type`, `title`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1552482298987851778', 'menu_type', 1, '菜单类型', '目录/菜单/功能', '2022-07-28 10:33:18', '2022-07-28 10:33:18', '', '');
INSERT INTO `el_sys_dic` (`id`, `code`, `type`, `title`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1552554336742055937', 'dic_type', 1, '字典类型', '数据字典类型枚举', '2022-07-28 15:19:33', '2022-07-28 15:19:33', '', '');
COMMIT;

-- ----------------------------
-- Table structure for el_sys_dic_value
-- ----------------------------
DROP TABLE IF EXISTS `el_sys_dic_value`;
CREATE TABLE `el_sys_dic_value` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID/字典编码',
  `dic_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '归属字典',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '子项编码',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分类名称',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '上级ID',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='分类字典值';

-- ----------------------------
-- Records of el_sys_dic_value
-- ----------------------------
BEGIN;
INSERT INTO `el_sys_dic_value` (`id`, `dic_code`, `value`, `title`, `parent_id`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1356065526639505409', 'exam_open_type', '1', '完全公开', '0', NULL, '2021-02-01 10:23:10', '2021-02-01 10:23:10', '', '');
INSERT INTO `el_sys_dic_value` (`id`, `dic_code`, `value`, `title`, `parent_id`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1399936107176595458', 'data_scope', '1', '仅本人数据', '0', '仅本人数据', '2021-06-02 11:49:12', '2021-06-02 11:49:12', '', '');
INSERT INTO `el_sys_dic_value` (`id`, `dic_code`, `value`, `title`, `parent_id`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1399936146162651137', 'data_scope', '2', '本部门数据', '0', '本部门数据', '2021-06-02 11:49:21', '2021-06-02 11:49:21', '', '');
INSERT INTO `el_sys_dic_value` (`id`, `dic_code`, `value`, `title`, `parent_id`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1399936188902608897', 'data_scope', '3', '本部门及以下', '0', '本部门及以下', '2021-06-02 11:49:31', '2021-06-02 11:49:31', '', '');
INSERT INTO `el_sys_dic_value` (`id`, `dic_code`, `value`, `title`, `parent_id`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1399936230719819777', 'data_scope', '4', '全部数据', '0', '全部数据', '2021-06-02 11:49:41', '2021-06-02 11:49:41', '', '');
INSERT INTO `el_sys_dic_value` (`id`, `dic_code`, `value`, `title`, `parent_id`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1492388894015025153', 'state', '0', '正常', '0', NULL, '2022-02-12 14:43:34', '2022-02-12 14:43:34', '', '');
INSERT INTO `el_sys_dic_value` (`id`, `dic_code`, `value`, `title`, `parent_id`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1492388933202407426', 'state', '1', '禁用', '0', NULL, '2022-02-12 14:43:43', '2022-02-12 14:43:43', '', '');
INSERT INTO `el_sys_dic_value` (`id`, `dic_code`, `value`, `title`, `parent_id`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1546399693989249025', 'user_state', '0', '正常', '0', NULL, '2022-07-11 15:43:12', '2022-07-11 15:43:12', '', '');
INSERT INTO `el_sys_dic_value` (`id`, `dic_code`, `value`, `title`, `parent_id`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1546399745172340738', 'user_state', '1', '已禁用', '0', NULL, '2022-07-11 15:43:24', '2022-07-11 15:43:24', '', '');
INSERT INTO `el_sys_dic_value` (`id`, `dic_code`, `value`, `title`, `parent_id`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1546399778231844866', 'user_state', '2', '待审核', '0', NULL, '2022-07-11 15:43:32', '2022-07-11 15:43:32', '', '');
INSERT INTO `el_sys_dic_value` (`id`, `dic_code`, `value`, `title`, `parent_id`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1552483045594935298', 'menu_type', '1', '目录', '0', NULL, '2022-07-28 10:36:16', '2022-07-28 10:36:16', '', '');
INSERT INTO `el_sys_dic_value` (`id`, `dic_code`, `value`, `title`, `parent_id`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1552483057787777025', 'menu_type', '2', '菜单', '0', NULL, '2022-07-28 10:36:19', '2022-07-28 10:36:19', '', '');
INSERT INTO `el_sys_dic_value` (`id`, `dic_code`, `value`, `title`, `parent_id`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1552483066511929345', 'menu_type', '3', '功能', '0', NULL, '2022-07-28 10:36:21', '2022-07-28 10:36:21', '', '');
INSERT INTO `el_sys_dic_value` (`id`, `dic_code`, `value`, `title`, `parent_id`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1552554710609731586', 'dic_type', '1', '数据字典', '0', NULL, '2022-07-28 15:21:02', '2022-07-28 15:21:02', '', '');
INSERT INTO `el_sys_dic_value` (`id`, `dic_code`, `value`, `title`, `parent_id`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1552554758366076929', 'dic_type', '2', '分类字典', '0', NULL, '2022-07-28 15:21:14', '2022-07-28 15:21:14', '', '');
COMMIT;

-- ----------------------------
-- Table structure for el_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `el_sys_menu`;
CREATE TABLE `el_sys_menu` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `parent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '上级菜单',
  `menu_type` int NOT NULL DEFAULT '1' COMMENT '1菜单2功能',
  `permission_tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '访问路径',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '视图或Layout',
  `redirect` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '跳转地址',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `meta_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由标题',
  `meta_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由标题',
  `meta_active_menu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '高亮菜单',
  `meta_no_cache` tinyint DEFAULT NULL COMMENT '是否缓存',
  `hidden` tinyint DEFAULT NULL COMMENT '是否隐藏',
  `sort` int DEFAULT NULL COMMENT '越小越前',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `permission_tag` (`permission_tag`) USING BTREE,
  UNIQUE KEY `path` (`path`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE,
  KEY `parent_id` (`parent_id`) USING BTREE,
  KEY `menu_type` (`menu_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统菜单';

-- ----------------------------
-- Records of el_sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1367010529435996174', '0', 1, NULL, '/admin/sys', '#', '/admin/sys/menu', 'Sys', '系统设置', 'carbon:settings', NULL, 1, 0, 13, '2021-03-07 11:08:18', '2022-01-06 18:10:33', '', '10001');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1367010529435996176', '1367010529435996174', 2, NULL, '/admin/sys/menu', 'views/System/Menu/Menu', NULL, 'SysMenu', '菜单管理', '', NULL, 1, 0, 2, '2021-03-07 11:08:18', '2021-05-31 10:14:26', '', '10001');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1367010529435996178', '1367010529435996174', 2, NULL, '/admin/sys/role', 'views/System/Role/Role', NULL, 'SysRole', '角色管理', '', NULL, 1, 0, 3, '2021-03-07 11:08:18', '2022-01-06 18:10:17', '', '10001');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1367010529435996179', '1367010529435996174', 2, NULL, '/admin/sys/dict', 'views/System/DataDict/DataDict', NULL, 'SysDataDict', '数据字典', '', NULL, 1, 0, 3, '2021-03-07 11:08:18', '2022-01-06 18:10:17', '', '10001');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1399266644605394945', '1367010529435996176', 3, 'sys:menu:add', NULL, NULL, NULL, NULL, '添加', NULL, NULL, 1, 1, 1, '2021-05-31 15:29:00', '2021-05-31 15:28:59', '10001', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1399266713060630529', '1367010529435996176', 3, 'sys:menu:update', NULL, NULL, NULL, NULL, '修改', NULL, NULL, 1, 1, 2, '2021-05-31 15:29:16', '2021-05-31 15:29:16', '10001', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1399266787438223361', '1367010529435996176', 3, 'sys:menu:delete', NULL, NULL, NULL, NULL, '删除', NULL, NULL, 1, 1, 3, '2021-05-31 15:29:34', '2021-05-31 15:29:33', '10001', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1399266868300210177', '1367010529435996176', 3, 'sys:menu:sort', NULL, NULL, NULL, NULL, '排序', NULL, NULL, 1, 1, 4, '2021-05-31 15:29:53', '2021-05-31 15:29:53', '10001', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1552509704679235586', '1367010529435996178', 3, 'sys:role:paging', '', '', NULL, '', '查看', '', NULL, NULL, 1, 1, '2022-07-28 12:22:12', '2022-07-28 12:22:12', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1552510807093321730', '1367010529435996178', 3, 'sys:role:add', NULL, NULL, NULL, NULL, '添加', NULL, NULL, NULL, 1, 2, '2022-07-28 12:26:35', '2022-07-28 12:26:35', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1552510872314748929', '1367010529435996178', 3, 'sys:role:edit', NULL, NULL, NULL, NULL, '修改', NULL, NULL, NULL, 1, 3, '2022-07-28 12:26:50', '2022-07-28 12:26:50', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1552510963301785601', '1367010529435996178', 3, 'sys:role:delete', NULL, NULL, NULL, NULL, '删除', NULL, NULL, NULL, 1, 4, '2022-07-28 12:27:12', '2022-07-28 12:27:12', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1552547259067965442', '0', 1, NULL, '/admin/org', '#', NULL, NULL, '组织架构', 'carbon:category', NULL, NULL, 0, 12, '2022-07-28 14:51:26', '2022-07-28 14:51:26', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1552549085188235265', '1367010529435996176', 3, 'sys:menu:paging', NULL, NULL, NULL, NULL, '查看', NULL, NULL, NULL, 1, 5, '2022-07-28 14:58:41', '2022-07-28 14:58:41', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1552615713615716353', '1367010529435996179', 3, 'sys:dict:add', NULL, NULL, NULL, NULL, '添加', NULL, NULL, NULL, NULL, 1, '2022-07-28 19:23:26', '2022-07-28 19:23:26', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1552615821107339266', '1367010529435996179', 3, 'sys:dict:edit', NULL, NULL, NULL, NULL, '修改', NULL, NULL, NULL, NULL, 2, '2022-07-28 19:23:52', '2022-07-28 19:23:52', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1552615880121196546', '1367010529435996179', 3, 'sys:dict:delete', NULL, NULL, NULL, NULL, '删除', NULL, NULL, NULL, NULL, 3, '2022-07-28 19:24:06', '2022-07-28 19:24:06', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1552616085025529857', '1367010529435996179', 3, 'sys:dict:paging', NULL, NULL, NULL, NULL, '查看', NULL, NULL, NULL, NULL, 4, '2022-07-28 19:24:55', '2022-07-28 19:24:55', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1552842968174489602', '1552547259067965442', 2, NULL, '/admin/org/depart', 'views/System/Depart/Depart', NULL, 'OrgDepart', '部门管理', NULL, NULL, NULL, NULL, 1, '2022-07-29 10:26:28', '2022-07-29 10:26:28', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1553180631951761409', '1367010529435996178', 3, 'sys:role:grant', NULL, NULL, NULL, NULL, '角色授权', NULL, NULL, NULL, NULL, 5, '2022-07-30 08:48:14', '2022-07-30 08:48:14', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1556906844813914114', '1552547259067965442', 2, NULL, '/admin/org/user', 'views/System/User/User', NULL, 'OrgUser', '人员管理', NULL, NULL, NULL, NULL, 2, '2022-08-09 15:34:52', '2022-08-09 15:34:52', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1556907609842380801', '1556906844813914114', 3, 'sys:user:edit', NULL, NULL, NULL, NULL, '修改', NULL, NULL, NULL, 1, 3, '2022-08-09 15:37:54', '2022-08-09 15:37:54', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1556907683607605249', '1556906844813914114', 3, 'sys:user:add', NULL, NULL, NULL, NULL, '添加', NULL, NULL, NULL, 1, 2, '2022-08-09 15:38:12', '2022-08-09 15:38:12', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1556907994690744321', '1556906844813914114', 3, 'sys:user:paging', NULL, NULL, NULL, NULL, '查看', NULL, NULL, NULL, 1, 1, '2022-08-09 15:39:26', '2022-08-09 15:39:26', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1556908088521519105', '1556906844813914114', 3, 'sys:user:delete', NULL, NULL, NULL, NULL, '删除', NULL, NULL, NULL, 1, 4, '2022-08-09 15:39:48', '2022-08-09 15:39:48', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1556911576896446466', '1556906844813914114', 3, 'sys:user:state', NULL, NULL, NULL, NULL, '启用/禁用', NULL, NULL, NULL, 1, 5, '2022-08-09 15:53:40', '2022-08-09 15:53:40', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1557199944846876674', '1552842968174489602', 3, 'sys:depart:view', NULL, NULL, NULL, NULL, '查看', NULL, NULL, NULL, 1, 3, '2022-08-10 10:59:32', '2022-08-10 10:59:32', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1557199992607416322', '1552842968174489602', 3, 'sys:depart:add', NULL, NULL, NULL, NULL, '添加', NULL, NULL, NULL, 1, 1, '2022-08-10 10:59:44', '2022-08-10 10:59:44', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1557200059632394242', '1552842968174489602', 3, 'sys:depart:edit', NULL, NULL, NULL, NULL, '修改', NULL, NULL, NULL, 1, 4, '2022-08-10 11:00:00', '2022-08-10 11:00:00', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1557200122416930817', '1552842968174489602', 3, 'sys:depart:delete', NULL, NULL, NULL, NULL, '删除', NULL, NULL, NULL, 1, 2, '2022-08-10 11:00:15', '2022-08-10 11:00:15', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1564510476430225410', '1367010529435996174', 2, NULL, '/admin/sys/config', 'views/System/Config/Config', NULL, 'SysConfig', '个性配置', NULL, NULL, NULL, NULL, 4, '2022-08-30 15:08:59', '2022-08-30 15:08:59', '', '');
INSERT INTO `el_sys_menu` (`id`, `parent_id`, `menu_type`, `permission_tag`, `path`, `component`, `redirect`, `name`, `meta_title`, `meta_icon`, `meta_active_menu`, `meta_no_cache`, `hidden`, `sort`, `create_time`, `update_time`, `create_by`, `update_by`) VALUES ('1566612797037527042', '1367010529435996174', 2, NULL, '/admin/sys/plugin', 'views/System/Plugin/Plugin', NULL, 'Plugin', '插件管理', NULL, NULL, NULL, NULL, 5, '2022-09-05 10:22:51', '2022-09-05 10:22:51', '', '');
COMMIT;

-- ----------------------------
-- Table structure for el_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `el_sys_role`;
CREATE TABLE `el_sys_role` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `data_scope` int NOT NULL COMMENT '数据权限',
  `role_level` int NOT NULL COMMENT '越大越高',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色';

-- ----------------------------
-- Records of el_sys_role
-- ----------------------------
BEGIN;
INSERT INTO `el_sys_role` (`id`, `role_name`, `data_scope`, `role_level`, `remark`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('1552872688874184706', '业务员', 1, 1, NULL, '2022-07-29 12:24:34', '', '2022-07-29 12:24:34', '');
INSERT INTO `el_sys_role` (`id`, `role_name`, `data_scope`, `role_level`, `remark`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('admin', '超级管理员', 4, 999, '', '2020-12-03 16:52:16', '', '2022-06-15 15:47:31', '10001');
COMMIT;

-- ----------------------------
-- Table structure for el_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `el_sys_role_menu`;
CREATE TABLE `el_sys_role_menu` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
  `menu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  `data_flag` int NOT NULL DEFAULT '0' COMMENT '数据标识',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `role_id` (`role_id`) USING BTREE,
  KEY `menu_id` (`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色菜单授权';

-- ----------------------------
-- Records of el_sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553195055651512321', '1552872688874184706', '1367010529435996174', '2022-07-30 09:45:32', '2022-07-30 09:45:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553195055655706626', '1552872688874184706', '1367010529435996176', '2022-07-30 09:45:32', '2022-07-30 09:45:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553195055655706627', '1552872688874184706', '1399266644605394945', '2022-07-30 09:45:32', '2022-07-30 09:45:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553195055655706628', '1552872688874184706', '1399266713060630529', '2022-07-30 09:45:32', '2022-07-30 09:45:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553195055659900930', '1552872688874184706', '1399266787438223361', '2022-07-30 09:45:32', '2022-07-30 09:45:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553195055659900931', '1552872688874184706', '1399266868300210177', '2022-07-30 09:45:32', '2022-07-30 09:45:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553195055659900932', '1552872688874184706', '1552549085188235265', '2022-07-30 09:45:32', '2022-07-30 09:45:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553195055664095234', '1552872688874184706', '1367010529435996178', '2022-07-30 09:45:32', '2022-07-30 09:45:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553195055664095235', '1552872688874184706', '1552509704679235586', '2022-07-30 09:45:32', '2022-07-30 09:45:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553195055664095236', '1552872688874184706', '1552510807093321730', '2022-07-30 09:45:32', '2022-07-30 09:45:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553195055664095237', '1552872688874184706', '1552510872314748929', '2022-07-30 09:45:32', '2022-07-30 09:45:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553195055664095238', '1552872688874184706', '1552510963301785601', '2022-07-30 09:45:32', '2022-07-30 09:45:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553195055668289538', '1552872688874184706', '1553180631951761409', '2022-07-30 09:45:32', '2022-07-30 09:45:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553195055668289539', '1552872688874184706', '1367010529435996179', '2022-07-30 09:45:32', '2022-07-30 09:45:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553195055668289540', '1552872688874184706', '1552615713615716353', '2022-07-30 09:45:32', '2022-07-30 09:45:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553195055672483841', '1552872688874184706', '1552615821107339266', '2022-07-30 09:45:32', '2022-07-30 09:45:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553195055672483842', '1552872688874184706', '1552615880121196546', '2022-07-30 09:45:32', '2022-07-30 09:45:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553195055672483843', '1552872688874184706', '1552616085025529857', '2022-07-30 09:45:32', '2022-07-30 09:45:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317679837185', 'admin', '1367010529435996174', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317679837186', 'admin', '1367010529435996176', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317684031489', 'admin', '1399266644605394945', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317684031490', 'admin', '1399266713060630529', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317684031491', 'admin', '1399266787438223361', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317684031492', 'admin', '1399266868300210177', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317688225794', 'admin', '1552549085188235265', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317688225795', 'admin', '1367010529435996178', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317688225796', 'admin', '1552509704679235586', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317688225797', 'admin', '1552510807093321730', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317692420098', 'admin', '1552510872314748929', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317692420099', 'admin', '1552510963301785601', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317692420100', 'admin', '1553180631951761409', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317692420101', 'admin', '1367010529435996179', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317692420102', 'admin', '1552615713615716353', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317696614402', 'admin', '1552615821107339266', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317696614403', 'admin', '1552615880121196546', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317696614404', 'admin', '1552616085025529857', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317696614405', 'admin', '1552547259067965442', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1553196317696614406', 'admin', '1552842968174489602', '2022-07-30 09:50:33', '2022-07-30 09:50:33', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1555025091098619905', 'admin', '1555025091056676866', '2022-08-04 10:57:27', '2022-08-04 10:57:27', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1556902577088622593', 'admin', '1556902577034096641', '2022-08-09 15:17:54', '2022-08-09 15:17:54', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1556904863445676033', 'admin', '1556904863424704514', '2022-08-09 15:26:59', '2022-08-09 15:26:59', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1556906844826497025', 'admin', '1556906844813914114', '2022-08-09 15:34:52', '2022-08-09 15:34:52', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1556907609884323842', 'admin', '1556907609842380801', '2022-08-09 15:37:54', '2022-08-09 15:37:54', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1556907683632771073', 'admin', '1556907683607605249', '2022-08-09 15:38:12', '2022-08-09 15:38:12', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1556907994728493058', 'admin', '1556907994690744321', '2022-08-09 15:39:26', '2022-08-09 15:39:26', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1556908088546684930', 'admin', '1556908088521519105', '2022-08-09 15:39:48', '2022-08-09 15:39:48', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1556911576934195201', 'admin', '1556911576896446466', '2022-08-09 15:53:40', '2022-08-09 15:53:40', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1557199944872042498', 'admin', '1557199944846876674', '2022-08-10 10:59:32', '2022-08-10 10:59:32', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1557199992640970753', 'admin', '1557199992607416322', '2022-08-10 10:59:44', '2022-08-10 10:59:44', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1557200059661754370', 'admin', '1557200059632394242', '2022-08-10 11:00:00', '2022-08-10 11:00:00', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1557200122437902337', 'admin', '1557200122416930817', '2022-08-10 11:00:15', '2022-08-10 11:00:15', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1564510476497334274', 'admin', '1564510476430225410', '2022-08-30 15:08:59', '2022-08-30 15:08:59', '', '', 0);
INSERT INTO `el_sys_role_menu` (`id`, `role_id`, `menu_id`, `create_time`, `update_time`, `create_by`, `update_by`, `data_flag`) VALUES ('1566612797104635906', 'admin', '1566612797037527042', '2022-09-05 10:22:51', '2022-09-05 10:22:51', '', '', 0);
COMMIT;

-- ----------------------------
-- Table structure for el_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `el_sys_user`;
CREATE TABLE `el_sys_user` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '真实姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '头像',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码盐',
  `state` int NOT NULL DEFAULT '0' COMMENT '状态',
  `id_card` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '身份证号码',
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `dept_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门编码',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `dept_code` (`dept_code`) USING BTREE,
  KEY `user_name` (`user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='管理用户';

-- ----------------------------
-- Records of el_sys_user
-- ----------------------------
BEGIN;
INSERT INTO `el_sys_user` (`id`, `user_name`, `real_name`, `avatar`, `password`, `salt`, `state`, `id_card`, `mobile`, `email`, `dept_code`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('1000000000000000001', 'admin', '超管A', 'https://files.yfhl.net/2023/4/25/1682414346430-6e2b5b35.jpg', '39d45d8d86476537c495cee6231a151a', 'UgGORx', 0, '360782198910105217', '', '', 'A01A01', '2020-12-03 16:52:10', '', '2021-06-07 17:10:29', '10001');
INSERT INTO `el_sys_user` (`id`, `user_name`, `real_name`, `avatar`, `password`, `salt`, `state`, `id_card`, `mobile`, `email`, `dept_code`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('1557544252961996802', 'sasa', '张三', '', '', '', 0, '360782198910105217', '18682216559', NULL, 'A01A03', '2022-08-11 09:47:42', '', '2022-08-11 09:47:42', '');
INSERT INTO `el_sys_user` (`id`, `user_name`, `real_name`, `avatar`, `password`, `salt`, `state`, `id_card`, `mobile`, `email`, `dept_code`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES ('1557552992415170561', 'dora', '郭银花', '', '', '', 0, '368782199006162228', '18603038204', NULL, 'A01A02', '2022-08-11 10:22:26', '', '2022-08-11 10:22:26', '');
COMMIT;

-- ----------------------------
-- Table structure for el_sys_user_bind
-- ----------------------------
DROP TABLE IF EXISTS `el_sys_user_bind`;
CREATE TABLE `el_sys_user_bind` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户ID',
  `login_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录类型',
  `open_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '三方ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='登录绑定';

-- ----------------------------
-- Records of el_sys_user_bind
-- ----------------------------
BEGIN;
INSERT INTO `el_sys_user_bind` (`id`, `user_id`, `login_type`, `open_id`, `create_time`, `update_time`) VALUES ('1557552631587586049', '1557544252961996802', 'mobile', '18682216559', NULL, NULL);
INSERT INTO `el_sys_user_bind` (`id`, `user_id`, `login_type`, `open_id`, `create_time`, `update_time`) VALUES ('1557552992536805378', '1557552992415170561', 'mobile', '18603038204', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for el_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `el_sys_user_role`;
CREATE TABLE `el_sys_user_role` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户角色';

-- ----------------------------
-- Records of el_sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `el_sys_user_role` (`id`, `user_id`, `role_id`) VALUES ('1', '1000000000000000001', 'admin');
INSERT INTO `el_sys_user_role` (`id`, `user_id`, `role_id`) VALUES ('1557552631474339841', '1557544252961996802', '1552872688874184706');
INSERT INTO `el_sys_user_role` (`id`, `user_id`, `role_id`) VALUES ('1557552631482728450', '1557544252961996802', 'admin');
INSERT INTO `el_sys_user_role` (`id`, `user_id`, `role_id`) VALUES ('1557552992465502210', '1557552992415170561', '1552872688874184706');
INSERT INTO `el_sys_user_role` (`id`, `user_id`, `role_id`) VALUES ('1557552992469696513', '1557552992415170561', 'admin');
COMMIT;

-- ----------------------------
-- Table structure for pl_plugin_data
-- ----------------------------
DROP TABLE IF EXISTS `pl_plugin_data`;
CREATE TABLE `pl_plugin_data` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '插件名称',
  `schema_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '元数据ID',
  `group_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分组ID',
  `config_data` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '配置数据',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '前端页面',
  `in_use` tinyint NOT NULL COMMENT '是否使用',
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '插件状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='插件信息';

-- ----------------------------
-- Records of pl_plugin_data
-- ----------------------------
BEGIN;
INSERT INTO `pl_plugin_data` (`id`, `title`, `schema_id`, `group_id`, `config_data`, `component`, `in_use`, `state`) VALUES ('10001', '阿里云上传', 'upload_aliyun', 'upload', NULL, ' ', 0, '0');
INSERT INTO `pl_plugin_data` (`id`, `title`, `schema_id`, `group_id`, `config_data`, `component`, `in_use`, `state`) VALUES ('10002', '本地上传', 'upload_local', 'upload', '{\"localDir\":\"/data/upload/\", \"visitUrl\": \"https://exam.yfhl.net\"}', ' ', 0, '0');
COMMIT;

-- ----------------------------
-- Table structure for pl_plugin_group
-- ----------------------------
DROP TABLE IF EXISTS `pl_plugin_group`;
CREATE TABLE `pl_plugin_group` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组名称',
  `single` tinyint DEFAULT NULL COMMENT '独立排斥',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='插件分组';

-- ----------------------------
-- Records of pl_plugin_group
-- ----------------------------
BEGIN;
INSERT INTO `pl_plugin_group` (`id`, `title`, `single`) VALUES ('upload', '上传组件', 1);
COMMIT;

-- ----------------------------
-- Table structure for pl_plugin_schema
-- ----------------------------
DROP TABLE IF EXISTS `pl_plugin_schema`;
CREATE TABLE `pl_plugin_schema` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `schema_data` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '元数据',
  `group_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='插件元数据';

-- ----------------------------
-- Records of pl_plugin_schema
-- ----------------------------
BEGIN;
INSERT INTO `pl_plugin_schema` (`id`, `schema_data`, `group_id`) VALUES ('upload_aliyun', '[{\"title\":\"EndPoint\",\"name\":\"endPoint\",\"type\":\"text\"},{\"title\":\"Bucket\",\"name\":\"bucket\",\"type\":\"text\"},{\"title\":\"AccessKeyId\",\"name\":\"accessKeyId\",\"type\":\"text\"},{\"title\":\"AcccessKeySecret\",\"name\":\"acccessKeySecret\",\"type\":\"text\"},{\"title\":\"访问URL\",\"name\":\"visitUrl\",\"type\":\"text\"}]', 'upload');
INSERT INTO `pl_plugin_schema` (`id`, `schema_data`, `group_id`) VALUES ('upload_local', '[{\"title\":\"本地目录\",\"name\":\"localDir\",\"type\":\"text\"},{\"title\":\"访问地址\",\"name\":\"visitUrl\",\"type\":\"text\"}]', 'upload');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `FIRED_TIME` bigint NOT NULL,
  `SCHED_TIME` bigint NOT NULL,
  `PRIORITY` int NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`) USING BTREE,
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE,
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`) USING BTREE,
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`) USING BTREE,
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_locks` (`SCHED_NAME`, `LOCK_NAME`) VALUES ('ExamScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` (`SCHED_NAME`, `LOCK_NAME`) VALUES ('examScheduler', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_scheduler_state` (`SCHED_NAME`, `INSTANCE_NAME`, `LAST_CHECKIN_TIME`, `CHECKIN_INTERVAL`) VALUES ('examScheduler', 'MacBook-Pro-16.local1693811200522', 1693811583186, 10000);
COMMIT;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `INT_PROP_1` int DEFAULT NULL,
  `INT_PROP_2` int DEFAULT NULL,
  `LONG_PROP_1` bigint DEFAULT NULL,
  `LONG_PROP_2` bigint DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint DEFAULT NULL,
  `PREV_FIRE_TIME` bigint DEFAULT NULL,
  `PRIORITY` int DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `START_TIME` bigint NOT NULL,
  `END_TIME` bigint DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `MISFIRE_INSTR` smallint DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`) USING BTREE,
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`) USING BTREE,
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`) USING BTREE,
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
