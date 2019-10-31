/*
MySQL Backup
Database: view
Backup Time: 2019-10-31 17:35:33
*/

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `view`.`t_background`;
DROP TABLE IF EXISTS `view`.`t_code`;
DROP TABLE IF EXISTS `view`.`t_data`;
DROP TABLE IF EXISTS `view`.`t_file`;
DROP TABLE IF EXISTS `view`.`t_program`;
DROP TABLE IF EXISTS `view`.`t_role`;
DROP TABLE IF EXISTS `view`.`t_theme`;
DROP TABLE IF EXISTS `view`.`t_user`;
CREATE TABLE `t_background` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `theme_id` int(20) NOT NULL COMMENT '主题ID',
  `background_content` text COMMENT '背景内容',
  `background_html` text COMMENT '背景html',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `t_code` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `theme_id` int(20) NOT NULL COMMENT '主题ID',
  `code_content` text COMMENT '实现代码内容内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `t_data` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `theme_id` int(20) NOT NULL COMMENT '主题ID',
  `data_content` text COMMENT '数据集内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `t_file` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `theme_id` int(20) DEFAULT NULL COMMENT '主题id',
  `business_id` int(20) DEFAULT NULL COMMENT '业务主键id',
  `business_name` varchar(128) DEFAULT NULL COMMENT '业务名称',
  `file_name` varchar(128) DEFAULT NULL COMMENT '文件名称',
  `file_path` varchar(200) DEFAULT NULL COMMENT '文件全路径',
  `file_addr` varchar(200) DEFAULT NULL COMMENT '文件展示路径',
  `is_delete` int(1) DEFAULT '0' COMMENT '是否有效 0:有效,1:无效',
  `type` int(1) DEFAULT NULL COMMENT 'md中的图片0,其他附件1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
CREATE TABLE `t_program` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `theme_id` int(20) NOT NULL COMMENT '主题ID',
  `program_content` text COMMENT '技术方案内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `t_role` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(20) DEFAULT NULL COMMENT '用户id',
  `role` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色',
  `permission` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
CREATE TABLE `t_theme` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `theme` varchar(64) NOT NULL COMMENT '主题',
  `theme_pic` varchar(100) DEFAULT NULL COMMENT '主题图片',
  `introduction_html` text COMMENT '简介html',
  `introduction_content` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '简介内容',
  `user_id` int(128) DEFAULT NULL COMMENT '录入人id',
  `user_name` varchar(64) DEFAULT NULL COMMENT '录入人姓名',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `xgr` int(128) DEFAULT NULL COMMENT '修改人id',
  `xgr_name` varchar(64) DEFAULT NULL COMMENT '修改人姓名',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int(1) DEFAULT '0' COMMENT '逻辑删除 0:未删除 1:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
CREATE TABLE `t_user` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `headImg` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像图片',
  `phone` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `sex` int(10) DEFAULT NULL COMMENT '性别 0:男 1:女',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `state` int(10) DEFAULT '0' COMMENT '账号状态 0:未注销 1:已注销',
  `lrr` int(128) DEFAULT NULL COMMENT '录入人',
  `lrr_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '录入人姓名',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `xgr` int(128) DEFAULT NULL COMMENT '修改人',
  `xgr_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '修改人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
BEGIN;
LOCK TABLES `view`.`t_background` WRITE;
DELETE FROM `view`.`t_background`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `view`.`t_code` WRITE;
DELETE FROM `view`.`t_code`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `view`.`t_data` WRITE;
DELETE FROM `view`.`t_data`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `view`.`t_file` WRITE;
DELETE FROM `view`.`t_file`;
INSERT INTO `view`.`t_file` (`id`,`theme_id`,`business_id`,`business_name`,`file_name`,`file_path`,`file_addr`,`is_delete`,`type`) VALUES (20, NULL, NULL, NULL, 'Event.jpg', 'D:\\attachment\\content\\Event.jpg', 'http://localhost/view-server/static/content/Event.jpg', 0, 1);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `view`.`t_program` WRITE;
DELETE FROM `view`.`t_program`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `view`.`t_role` WRITE;
DELETE FROM `view`.`t_role`;
INSERT INTO `view`.`t_role` (`id`,`user_id`,`role`,`permission`) VALUES (1, 1, 'admin', 'view,edit'),(2, 2, 'user', 'view'),(3, 3, 'user', 'view');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `view`.`t_theme` WRITE;
DELETE FROM `view`.`t_theme`;
INSERT INTO `view`.`t_theme` (`id`,`theme`,`theme_pic`,`introduction_html`,`introduction_content`,`user_id`,`user_name`,`create_time`,`xgr`,`xgr_name`,`update_time`,`is_deleted`) VALUES (1, '', NULL, '', '', 1, 'admin', '2019-10-31 14:56:08', NULL, NULL, NULL, 0),(2, '3424', NULL, '<p>234234</p>\n', '234234', 1, 'admin', '2019-10-31 14:57:14', NULL, NULL, NULL, 0),(3, '', NULL, '', '', 1, 'admin', '2019-10-31 14:58:48', NULL, NULL, NULL, 0),(4, '', NULL, '', '', 1, 'admin', '2019-10-31 14:59:12', NULL, NULL, NULL, 0),(5, '', NULL, '', '', 1, 'admin', '2019-10-31 15:00:27', NULL, NULL, NULL, 0),(6, '', NULL, '', '', 1, 'admin', '2019-10-31 15:01:46', NULL, NULL, NULL, 0),(7, '', NULL, '', '', 1, 'admin', '2019-10-31 15:03:30', NULL, NULL, NULL, 0),(8, '', NULL, '', '', 1, 'admin', '2019-10-31 15:04:29', NULL, NULL, NULL, 0),(9, '', NULL, '', '', 1, 'admin', '2019-10-31 15:12:42', NULL, NULL, NULL, 0),(10, '', NULL, '', '', 1, 'admin', '2019-10-31 15:13:42', NULL, NULL, NULL, 0),(11, 'theme', NULL, '<p>123123123</p>\n', '123123123', 1, 'admin', '2019-10-31 16:18:02', NULL, NULL, NULL, 0),(12, 'rqweq', NULL, '<p>12312</p>\n', '12312', 1, 'admin', '2019-10-31 16:22:38', NULL, NULL, NULL, 0),(13, 'asda', NULL, '', '', 1, 'admin', '2019-10-31 16:23:55', NULL, NULL, NULL, 0),(14, 'asdsad', NULL, '', '', 1, 'admin', '2019-10-31 16:30:24', NULL, NULL, NULL, 0),(15, '132123', NULL, '', '', 1, 'admin', '2019-10-31 16:36:50', NULL, NULL, NULL, 0),(16, '1111111', NULL, '', '', 1, 'admin', '2019-10-31 16:42:34', NULL, NULL, NULL, 0);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `view`.`t_user` WRITE;
DELETE FROM `view`.`t_user`;
INSERT INTO `view`.`t_user` (`id`,`name`,`password`,`headImg`,`phone`,`email`,`sex`,`create_time`,`state`,`lrr`,`lrr_name`,`update_time`,`xgr`,`xgr_name`) VALUES (1, 'admin', '123', '111', '111', '111', 0, '2019-10-24 09:12:37', 0, NULL, NULL, NULL, NULL, NULL),(2, 'howei', '456', '123', '123', '123', 1, '2019-10-24 09:14:02', 0, NULL, NULL, NULL, NULL, NULL),(3, 'swit', '789', '1111', '1111', '1111', 0, '2019-10-24 09:16:06', 0, NULL, NULL, NULL, NULL, NULL);
UNLOCK TABLES;
COMMIT;
