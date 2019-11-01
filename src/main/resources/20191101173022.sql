/*
MySQL Backup
Database: view
Backup Time: 2019-11-01 17:30:22
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
CREATE TABLE `t_code` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `theme_id` int(20) NOT NULL COMMENT '主题ID',
  `code_content` text COMMENT '实现代码内容内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
CREATE TABLE `t_data` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `theme_id` int(20) NOT NULL COMMENT '主题ID',
  `data_content` text COMMENT '数据集内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;
CREATE TABLE `t_program` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `theme_id` int(20) NOT NULL COMMENT '主题ID',
  `program_content` text COMMENT '技术方案内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
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
  `is_delete` int(1) DEFAULT '0' COMMENT '逻辑删除 0:未删除 1:已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
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
INSERT INTO `view`.`t_background` (`id`,`theme_id`,`background_content`,`background_html`) VALUES (10, 31, NULL, '<h6 id=\"h6-the-camelyon16-challenge-has-ended-in-november-2016-please-check-out-camelyon17-\"><a name=\"The CAMELYON16 challenge has ended in November 2016 PLEASE CHECK OUT CAMELYON17:\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>The CAMELYON16 challenge has ended in November 2016 PLEASE CHECK OUT CAMELYON17:</h6><p><a href=\"https://camelyon17.grand-challenge.org\">https://camelyon17.grand-challenge.org</a><br><strong>Background</strong><br>In this challenge, we will focus on the detection of micro- and</p>\n<p>macro-metastases in lymph node digitized images. This subject is highly relevant; lymph node metastases occur in most cancer types (e.g. breast, prostate, colon). Lymph nodes are small glands that filter lymph, the fluid that circulates through the lymphatic system. The lymph nodes in the underarm are the first place breast cancer is likely to spread. Metastatic involvement of lymph nodes is one of the most important prognostic variables in breast cancer. Prognosis is poorer when cancer has spread to the lymph nodes. The diagnostic procedure for pathologists is, however, tedious and time-consuming and prone to misinterpretation.</p>\n'),(11, 32, NULL, '<h2 id=\"h2-details\"><a name=\"Details\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Details</h2><h4 id=\"h4-task\"><a name=\"Task\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Task</h4><p>The segmentation of gray matter, white matter, cerebrospinal fluid, and other structures on multi-sequence brain MR images with and without (large) pathologies.</p>\n<h4 id=\"h4-background\"><a name=\"Background\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Background</h4><p>Many algorithms for segmenting brain structures in MRI scans have been proposed over the years. Especially in such a well-established research area, there is a tremendous need for fair comparison of these methods with respect to accuracy and robustness. Although there is an increasing awareness of the importance of comparing different algorithms on the same data, many methods are still compared to previous versions of the same type of algorithm on privately held data. This complicates the choice for a certain brain segmentation method among a wide variety of available methods.</p>\n<p>This challenge aims to directly compare automated brain segmentation methods. The output will be a ranking of techniques that robustly and accurately segment brain structure on MR brain images, both with and without pathology. We welcome both multi- and single-sequence (i.e. T1-weighted only) approaches.</p>\n'),(12, 33, NULL, NULL),(13, 34, NULL, NULL),(14, 35, NULL, NULL);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `view`.`t_code` WRITE;
DELETE FROM `view`.`t_code`;
INSERT INTO `view`.`t_code` (`id`,`theme_id`,`code_content`) VALUES (10, 31, NULL),(11, 32, NULL),(12, 33, NULL),(13, 34, NULL),(14, 35, NULL);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `view`.`t_data` WRITE;
DELETE FROM `view`.`t_data`;
INSERT INTO `view`.`t_data` (`id`,`theme_id`,`data_content`) VALUES (10, 31, NULL),(11, 32, NULL),(12, 33, NULL),(13, 34, NULL),(14, 35, NULL);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `view`.`t_file` WRITE;
DELETE FROM `view`.`t_file`;
INSERT INTO `view`.`t_file` (`id`,`theme_id`,`business_id`,`business_name`,`file_name`,`file_path`,`file_addr`,`is_delete`,`type`) VALUES (62, NULL, NULL, NULL, 'Event.jpg', 'D:\\attachment\\content\\Event.jpg', 'http://localhost/view-server/static/content/Event.jpg', 0, 0),(63, 31, 31, '主题图片', 'camelyon16.png', 'D:\\attachment\\camelyon16.png', 'http://localhost/view-server/static/camelyon16.png', 0, 1),(64, 31, 10, '数据集附件', 'dist_1_.zip', 'D:\\attachment\\dist_1_.zip', 'http://localhost/view-server/static/dist_1_.zip', 0, 1),(65, 31, 10, '设计附件', 'dist2019.11.1.zip', 'D:\\attachment\\dist2019.11.1.zip', 'http://localhost/view-server/static/dist2019.11.1.zip', 0, 1),(66, 31, 10, '代码附件', 'dist18090.zip', 'D:\\attachment\\dist18090.zip', 'http://localhost/view-server/static/dist18090.zip', 0, 1),(67, 32, 32, '主题图片', 'miccai-2018-logo-300x160.png', 'D:\\attachment\\miccai-2018-logo-300x160.png', 'http://localhost/view-server/static/miccai-2018-logo-300x160.png', 0, 1),(68, NULL, NULL, NULL, 'Low_Resolution.png', 'D:\\attachment\\content\\Low_Resolution.png', 'http://localhost/view-server/static/content/Low_Resolution.png', 0, 0),(69, 35, 35, '主题图片', 'camelyon16.png', 'D:\\attachment\\camelyon16.png', 'http://localhost/view-server/static/camelyon16.png', 0, 1),(70, 35, 14, '数据集附件', '22_项目周报（xx项目x月x日）模板.rar', 'D:\\attachment\\22_项目周报（xx项目x月x日）模板.rar', 'http://localhost/view-server/static/22_项目周报（xx项目x月x日）模板.rar', 0, 1);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `view`.`t_program` WRITE;
DELETE FROM `view`.`t_program`;
INSERT INTO `view`.`t_program` (`id`,`theme_id`,`program_content`) VALUES (10, 31, NULL),(11, 32, NULL),(12, 33, NULL),(13, 34, NULL),(14, 35, NULL);
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
INSERT INTO `view`.`t_theme` (`id`,`theme`,`theme_pic`,`introduction_html`,`introduction_content`,`user_id`,`user_name`,`create_time`,`xgr`,`xgr_name`,`update_time`,`is_delete`) VALUES (31, 'camelyon16', 'http://localhost/view-server/static/content/camelyon16.png', '<h6 id=\"h6-the-camelyon16-challenge-has-ended-in-november-2016-please-check-out-camelyon17-\"><a name=\"The CAMELYON16 challenge has ended in November 2016 PLEASE CHECK OUT CAMELYON17:\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>The CAMELYON16 challenge has ended in November 2016 PLEASE CHECK OUT CAMELYON17:</h6><p>   <a href=\"https://camelyon17.grand-challenge.org\">https://camelyon17.grand-challenge.org</a><br><strong>Overview</strong><br>The goal of this challenge is to evaluate new and existing algorithms for automated detection of metastases in hematoxylin and eosin (H&amp;E) stained whole-slide images of lymph node sections. This task has a high clinical relevance but requires large amounts of reading time from pathologists. Therefore, a successful solution would hold great promise to reduce the workload of the pathologists while at the same time reduce the subjectivity in diagnosis. This will be the first challenge using whole-slide images in histopathology. The challenge will run for two years. The 2016 challenge will focus on sentinel lymph nodes of breast cancer patients and will provide a large dataset from both the Radboud University Medical Center (Nijmegen, the Netherlands), as well as the University Medical Center Utrecht (Utrecht, the Netherlands).<br><strong>CAMELYON16 in news and media</strong><br><img src=\"http://localhost/view-server/static/content/Event.jpg\" alt=\"\"><br>Camelyon16 was a highly successful challenge with 32 submissions from as many as 23 teams. The results of our challenge were widely reflected in the news and reports including:</p>\n<ul>\n<li>Google Research Blog</li><li>White House report on “The national AI research and development strategic plan”</li><li>Nvidia blog</li><li>Tonic on the Google results</li><li>Engadget</li><li>Dailymail</li><li>Azorobotics</li><li>and many more articles.<br><strong>Publication</strong><br>Ehteshami Bejnordi B, Veta M, Johannes van Diest P, van Ginneken B, Karssemeijer N, Litjens G, van der Laak JAWM, and the CAMELYON16 Consortium. Diagnostic Assessment of Deep Learning Algorithms for Detection of Lymph Node Metastases in Women With Breast Cancer. JAMA. 2017;318(22):2199–2210. doi:10.1001/jama.2017.14585</li></ul>\n', '######          The CAMELYON16 challenge has ended in November 2016 PLEASE CHECK OUT CAMELYON17:\n   https://camelyon17.grand-challenge.org\n**Overview**   \nThe goal of this challenge is to evaluate new and existing algorithms for automated detection of metastases in hematoxylin and eosin (H&E) stained whole-slide images of lymph node sections. This task has a high clinical relevance but requires large amounts of reading time from pathologists. Therefore, a successful solution would hold great promise to reduce the workload of the pathologists while at the same time reduce the subjectivity in diagnosis. This will be the first challenge using whole-slide images in histopathology. The challenge will run for two years. The 2016 challenge will focus on sentinel lymph nodes of breast cancer patients and will provide a large dataset from both the Radboud University Medical Center (Nijmegen, the Netherlands), as well as the University Medical Center Utrecht (Utrecht, the Netherlands).\n**CAMELYON16 in news and media**\n![](http://localhost/view-server/static/content/Event.jpg)\nCamelyon16 was a highly successful challenge with 32 submissions from as many as 23 teams. The results of our challenge were widely reflected in the news and reports including:\n- Google Research Blog\n- White House report on \"The national AI research and development strategic plan\"\n- Nvidia blog\n- Tonic on the Google results\n- Engadget\n- Dailymail\n- Azorobotics\n- and many more articles.\n**Publication**\nEhteshami Bejnordi B, Veta M, Johannes van Diest P, van Ginneken B, Karssemeijer N, Litjens G, van der Laak JAWM, and the CAMELYON16 Consortium. Diagnostic Assessment of Deep Learning Algorithms for Detection of Lymph Node Metastases in Women With Breast Cancer. JAMA. 2017;318(22):2199–2210. doi:10.1001/jama.2017.14585', 1, 'admin', '2019-11-01 13:55:28', NULL, NULL, NULL, 0),(32, 'MRBrainS18', 'http://localhost/view-server/static/miccai-2018-logo-300x160.png', '<h3 id=\"h3-aim\"><a name=\"Aim\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Aim</h3><p>The purpose of this challenge is to directly compare methods for segmentation of gray matter, white matter, cerebrospinal fluid, and other structures on 3T MRI scans of the brain, and to assess the effect of (large) pathologies on segmentation and volumetry.</p>\n<h3 id=\"h3-outline\"><a name=\"Outline\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Outline</h3><p>To participate in the challenge, interested teams can register on this website. After registration, training data can be downloaded. This data consists of 7 sets of brain MR images (T1, T1 inversion recovery, and T2-FLAIR) with manual segmentations of ten brain structures. These manual segmentations have been made by experts in brain segmentation. Unlike the MRBrainS13 challenge, the training- and test-data now also include images with (large) pathologies.</p>\n', '### Aim\nThe purpose of this challenge is to directly compare methods for segmentation of gray matter, white matter, cerebrospinal fluid, and other structures on 3T MRI scans of the brain, and to assess the effect of (large) pathologies on segmentation and volumetry.\n### Outline\nTo participate in the challenge, interested teams can register on this website. After registration, training data can be downloaded. This data consists of 7 sets of brain MR images (T1, T1 inversion recovery, and T2-FLAIR) with manual segmentations of ten brain structures. These manual segmentations have been made by experts in brain segmentation. Unlike the MRBrainS13 challenge, the training- and test-data now also include images with (large) pathologies.\n', 1, 'admin', '2019-11-01 16:13:28', NULL, NULL, NULL, 0),(33, 'Digital Pathology', NULL, '<p>Overview<br>Grading and diagnosis of tumors in cancer patients have traditionally been done by examination of tissue specimens under a powerful microscope by expert pathologists. While this process continues to be widely applied in clinical settings, it is not scalable to translational and clinical research studies involving hundreds or thousands of tissue specimens. State-of-the-art digitizing microscopy instruments are capable of capturing high-resolution images of whole slide tissue specimens rapidly. Computer aided segmentation and classification has the potential to improve the tumor diagnosis and grading process as well as to enable quantitative studies of the mechanisms underlying disease onset and progression.</p>\n<p>The objective of this challenge is to evaluate and compare segmentation algorithms and to encourage the biomedical imaging community to design and implement more accurate and efficient algorithms. The challenge will evaluate the performance of algorithms for detection and segmentation of nuclei in a tissue image. Participants are asked to detect and segment all the nuclear material in a given set of image tiles extracted from whole slide tissue images.</p>\n<p>This challenge uses image tiles from whole slide tissue images to reduce computational and memory requirements. The image tiles are rectangular regions extracted from a set of Glioblastoma and Lower Grade Glioma whole slide tissue images. Nuclei in each image tile in the training set have been manually segmented. Note that the tiles are not of the same size.</p>\n', 'Overview\nGrading and diagnosis of tumors in cancer patients have traditionally been done by examination of tissue specimens under a powerful microscope by expert pathologists. While this process continues to be widely applied in clinical settings, it is not scalable to translational and clinical research studies involving hundreds or thousands of tissue specimens. State-of-the-art digitizing microscopy instruments are capable of capturing high-resolution images of whole slide tissue specimens rapidly. Computer aided segmentation and classification has the potential to improve the tumor diagnosis and grading process as well as to enable quantitative studies of the mechanisms underlying disease onset and progression.\n\nThe objective of this challenge is to evaluate and compare segmentation algorithms and to encourage the biomedical imaging community to design and implement more accurate and efficient algorithms. The challenge will evaluate the performance of algorithms for detection and segmentation of nuclei in a tissue image. Participants are asked to detect and segment all the nuclear material in a given set of image tiles extracted from whole slide tissue images.\n\nThis challenge uses image tiles from whole slide tissue images to reduce computational and memory requirements. The image tiles are rectangular regions extracted from a set of Glioblastoma and Lower Grade Glioma whole slide tissue images. Nuclei in each image tile in the training set have been manually segmented. Note that the tiles are not of the same size.', 1, 'admin', '2019-11-01 16:18:33', NULL, NULL, NULL, 0),(34, '2018 Data Science Bowl', NULL, '<p>Spot Nuclei. Speed Cures.<br>Imagine speeding up research for almost every disease, from lung cancer and heart disease to rare disorders. The 2018 Data Science Bowl offers our most ambitious mission yet: create an algorithm to automate nucleus detection.</p>\n<p>We’ve all seen people suffer from diseases like cancer, heart disease, chronic obstructive pulmonary disease, Alzheimer’s, and diabetes. Many have seen their loved ones pass away. Think how many lives would be transformed if cures came faster.</p>\n<p>By automating nucleus detection, you could help unlock cures faster—from rare disorders to the common cold. Want a snapshot about the 2018 Data Science Bowl? View this video.</p>\n<p>Why nuclei?<br>Identifying the cells’ nuclei is the starting point for most analyses because most of the human body’s 30 trillion cells contain a nucleus full of DNA, the genetic code that programs each cell. Identifying nuclei allows researchers to identify each individual cell in a sample, and by measuring how cells react to various treatments, the researcher can understand the underlying biological processes at work.</p>\n<p>By participating, teams will work to automate the process of identifying nuclei, which will allow for more efficient drug testing, shortening the 10 years it takes for each new drug to come to market. Check out this video overview to find out more.</p>\n<p>What will participants do?<br>Teams will create a computer model that can identify a range of nuclei across varied conditions. By observing patterns, asking questions, and building a model, participants will have a chance to push state-of-the-art technology farther.</p>\n<p>Visit DataScienceBowl.com to:<br>• Sign up to receive news about the competition<br>• Learn about the history of the Data Science Bowl and past competitions<br>• Read our latest insights on emerging analytics techniques</p>\n', 'Spot Nuclei. Speed Cures.\nImagine speeding up research for almost every disease, from lung cancer and heart disease to rare disorders. The 2018 Data Science Bowl offers our most ambitious mission yet: create an algorithm to automate nucleus detection.\n\nWe’ve all seen people suffer from diseases like cancer, heart disease, chronic obstructive pulmonary disease, Alzheimer’s, and diabetes. Many have seen their loved ones pass away. Think how many lives would be transformed if cures came faster.\n\nBy automating nucleus detection, you could help unlock cures faster—from rare disorders to the common cold. Want a snapshot about the 2018 Data Science Bowl? View this video.\n\nWhy nuclei?\nIdentifying the cells’ nuclei is the starting point for most analyses because most of the human body’s 30 trillion cells contain a nucleus full of DNA, the genetic code that programs each cell. Identifying nuclei allows researchers to identify each individual cell in a sample, and by measuring how cells react to various treatments, the researcher can understand the underlying biological processes at work.\n\nBy participating, teams will work to automate the process of identifying nuclei, which will allow for more efficient drug testing, shortening the 10 years it takes for each new drug to come to market. Check out this video overview to find out more.\n\nWhat will participants do?\nTeams will create a computer model that can identify a range of nuclei across varied conditions. By observing patterns, asking questions, and building a model, participants will have a chance to push state-of-the-art technology farther.\n\nVisit DataScienceBowl.com to:\n• Sign up to receive news about the competition\n• Learn about the history of the Data Science Bowl and past competitions\n• Read our latest insights on emerging analytics techniques', 1, 'admin', '2019-11-01 16:20:37', NULL, NULL, NULL, 0),(35, 'Spot Nuclei. Speed Cures.', 'http://localhost/view-server/static/camelyon16.png', '<h2 id=\"h2-spot-nuclei-speed-cures-\"><a name=\"Spot Nuclei. Speed Cures.\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>Spot Nuclei. Speed Cures.</h2><p>Imagine speeding up research for almost every disease, from lung cancer and heart disease to rare disorders. The 2018 Data Science Bowl offers our most ambitious mission yet: create an algorithm to automate nucleus detection.</p>\n<p>We’ve all seen people suffer from diseases like cancer, heart disease, chronic obstructive pulmonary disease, Alzheimer’s, and diabetes. Many have seen their loved ones pass away. Think how many lives would be transformed if cures came faster.</p>\n<p>By automating nucleus detection, you could help unlock cures faster—from rare disorders to the common cold. Want a snapshot about the 2018 Data Science Bowl? View this video.<br><img src=\"http://localhost/view-server/static/content/Low_Resolution.png\" alt=\"\"></p>\n', '## Spot Nuclei. Speed Cures.\nImagine speeding up research for almost every disease, from lung cancer and heart disease to rare disorders. The 2018 Data Science Bowl offers our most ambitious mission yet: create an algorithm to automate nucleus detection.\n\nWe’ve all seen people suffer from diseases like cancer, heart disease, chronic obstructive pulmonary disease, Alzheimer’s, and diabetes. Many have seen their loved ones pass away. Think how many lives would be transformed if cures came faster.\n\nBy automating nucleus detection, you could help unlock cures faster—from rare disorders to the common cold. Want a snapshot about the 2018 Data Science Bowl? View this video.\n![](http://localhost/view-server/static/content/Low_Resolution.png)', 1, 'admin', '2019-11-01 16:48:04', NULL, NULL, NULL, 0);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `view`.`t_user` WRITE;
DELETE FROM `view`.`t_user`;
INSERT INTO `view`.`t_user` (`id`,`name`,`password`,`headImg`,`phone`,`email`,`sex`,`create_time`,`state`,`lrr`,`lrr_name`,`update_time`,`xgr`,`xgr_name`) VALUES (1, 'admin', '123', '111', '111', '111', 0, '2019-10-24 09:12:37', 0, NULL, NULL, NULL, NULL, NULL),(2, 'howei', '456', '123', '123', '123', 1, '2019-10-24 09:14:02', 0, NULL, NULL, NULL, NULL, NULL),(3, 'swit', '789', '1111', '1111', '1111', 0, '2019-10-24 09:16:06', 0, NULL, NULL, NULL, NULL, NULL);
UNLOCK TABLES;
COMMIT;
