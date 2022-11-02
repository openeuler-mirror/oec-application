/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2022-2022. All rights reserved.
 * southbound-service is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

CREATE DATABASE  IF NOT EXISTS `south_dash_board`;

USE `south_dash_board`;

/*Table structure for table `board_community` */

DROP TABLE IF EXISTS `board_community`;

CREATE TABLE `board_community` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `architecture` VARCHAR(100) DEFAULT NULL COMMENT '架构',
  `board_model` VARCHAR(100) DEFAULT NULL COMMENT '板卡型号',
  `chip_model` VARCHAR(100) DEFAULT NULL COMMENT '芯片型号',
  `chip_vendor` VARCHAR(100) DEFAULT NULL COMMENT '芯片厂家(chip_vendor)',
  `device_id` VARCHAR(100) DEFAULT NULL,
  `download_link` VARCHAR(2048) DEFAULT NULL,
  `driver_date` VARCHAR(100) DEFAULT NULL,
  `driver_name` VARCHAR(100) DEFAULT NULL,
  `driver_size` VARCHAR(100) DEFAULT NULL,
  `item` VARCHAR(100) DEFAULT NULL,
  `lang` VARCHAR(100) DEFAULT NULL,
  `os` VARCHAR(100) DEFAULT NULL,
  `sha256` VARCHAR(100) DEFAULT NULL,
  `ss_id` VARCHAR(100) DEFAULT NULL,
  `sv_id` VARCHAR(100) DEFAULT NULL,
  `type` VARCHAR(100) DEFAULT NULL,
  `vendor_id` VARCHAR(100) DEFAULT NULL,
  `version` VARCHAR(100) DEFAULT NULL,
  `update_time` VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) COMMENT='板卡社区表';;

/*Table structure for table `board_factory` */

DROP TABLE IF EXISTS `board_factory`;

CREATE TABLE `board_factory` (
  `board_id` INT NOT NULL AUTO_INCREMENT COMMENT '全局唯一标识符',
  `board_type` VARCHAR(100) NOT NULL COMMENT '板卡类型',
  `chip_id` INT NOT NULL COMMENT '芯片厂商ID',
  `typical_board_model` VARCHAR(100) NOT NULL COMMENT '典型板卡型号',
  `board_item` VARCHAR(100) DEFAULT NULL COMMENT '物料编码',
  `extend_board_model` VARCHAR(1024) DEFAULT NULL COMMENT '扩展板卡型号',
  `extend_board_model_item` VARCHAR(1024) DEFAULT NULL COMMENT '扩展板卡物料编码',
  `x86_priority` VARCHAR(10) DEFAULT NULL COMMENT 'x86_64优先级',
  `arm_priority` VARCHAR(10) DEFAULT NULL COMMENT 'aarch64优先级',
  `demand_source` VARCHAR(30) DEFAULT NULL COMMENT '需求来源',
  `interface_person` VARCHAR(30) DEFAULT NULL COMMENT '接口人',
  `contact` VARCHAR(500) DEFAULT NULL COMMENT '联系方式',
  `middleman` VARCHAR(100) DEFAULT NULL COMMENT '对应BD',
  `create_time` VARCHAR(100) DEFAULT NULL COMMENT '填表时间',
  PRIMARY KEY (`board_id`),
  UNIQUE KEY `board_type` (`board_type`,`chip_id`),
  KEY `board_factory_chip_id` (`chip_id`),
  CONSTRAINT `board_factory_chip_id` FOREIGN KEY (`chip_id`) REFERENCES `chip_factory` (`chip_id`)
) COMMENT='板卡厂商表';

/*Table structure for table `board_overall` */

DROP TABLE IF EXISTS `board_overall`;

CREATE TABLE `board_overall` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `board_id` INT NOT NULL COMMENT '板卡厂商id',
  `version_id` INT NOT NULL COMMENT '操作系统id',
  `create_time` VARCHAR(20) DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `board_id` (`board_id`,`version_id`),
  KEY `board_overall_version_id` (`version_id`),
  CONSTRAINT `board_overall_board_id` FOREIGN KEY (`board_id`) REFERENCES `board_factory` (`board_id`),
  CONSTRAINT `board_overall_version_id` FOREIGN KEY (`version_id`) REFERENCES `version_plan` (`version_id`)
) COMMENT='全景板卡表';

/*Table structure for table `board_plan` */

DROP TABLE IF EXISTS `board_plan`;

CREATE TABLE `board_plan` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `chip_factory` VARCHAR(100) NOT NULL COMMENT '芯片厂商',
  `version_id` INT NOT NULL COMMENT '操作系统ID',
  `beta_list` VARCHAR(500) DEFAULT NULL COMMENT 'beta阶段',
  `release_list` VARCHAR(500) DEFAULT NULL COMMENT 'release阶段',
  `remark` VARCHAR(100) DEFAULT NULL COMMENT '备注、',
  `create_time` VARCHAR(100) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `chip_factory` (`chip_factory`,`version_id`),
  KEY `board_plan_version_id` (`version_id`),
  CONSTRAINT `board_plan_version_id` FOREIGN KEY (`version_id`) REFERENCES `version_plan` (`version_id`)
) COMMENT='板卡计划表';

/*Table structure for table `business_log` */

DROP TABLE IF EXISTS `business_log`;

CREATE TABLE `business_log` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` VARCHAR(50) DEFAULT NULL COMMENT '模块',
  `operation` VARCHAR(50) DEFAULT NULL COMMENT '操作',
  `host` VARCHAR(64) DEFAULT NULL COMMENT '操作主机ip',
  `date_time` VARCHAR(32) DEFAULT NULL COMMENT '日期',
  `result` VARCHAR(100) DEFAULT NULL COMMENT '结果',
  `detail` VARCHAR(2048) DEFAULT NULL COMMENT '详情',
  PRIMARY KEY (`id`)
) COMMENT='板卡计划表';

/*Table structure for table `chip_factory` */

DROP TABLE IF EXISTS `chip_factory`;

CREATE TABLE `chip_factory` (
  `chip_id` INT NOT NULL AUTO_INCREMENT COMMENT '全局唯一标识符',
  `chip_factory` VARCHAR(100) NOT NULL COMMENT '芯片厂商',
  `chip_model` VARCHAR(100) NOT NULL COMMENT '芯片型号',
  `create_time` VARCHAR(100) DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`chip_id`),
  UNIQUE KEY `chip_factory` (`chip_factory`,`chip_model`)
) COMMENT='芯片厂商表';

/*Table structure for table `chip_version` */

DROP TABLE IF EXISTS `chip_version`;

CREATE TABLE `chip_version` (
  `chip_id` INT NOT NULL DEFAULT '0',
  `version_id` INT NOT NULL DEFAULT '0',
  PRIMARY KEY (`chip_id`,`version_id`),
  KEY `chip_version_vid` (`version_id`),
  CONSTRAINT `chip_version_cid` FOREIGN KEY (`chip_id`) REFERENCES `chip_factory` (`chip_id`),
  CONSTRAINT `chip_version_vid` FOREIGN KEY (`version_id`) REFERENCES `version_plan` (`version_id`)
) COMMENT='芯片厂商与版本关联表';

/*Table structure for table `cpu_factory` */

DROP TABLE IF EXISTS `cpu_factory`;

CREATE TABLE `cpu_factory` (
  `cpu_id` INT NOT NULL AUTO_INCREMENT COMMENT '全局唯一标识符',
  `cpu_factory` VARCHAR(100) NOT NULL COMMENT 'CPU厂商',
  `cpu_model` VARCHAR(100) NOT NULL COMMENT 'CPU型号',
  `architecture` VARCHAR(30) NOT NULL COMMENT '架构',
  `release_time` VARCHAR(100) DEFAULT NULL COMMENT '发布时间',
  `create_time` VARCHAR(100) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`cpu_id`),
  UNIQUE KEY `cpu_factory` (`cpu_factory`,`cpu_model`)
) COMMENT='CPU厂商表';

/*Table structure for table `cpu_version` */

DROP TABLE IF EXISTS `cpu_version`;

CREATE TABLE `cpu_version` (
  `cpu_id` INT NOT NULL DEFAULT '0',
  `version_id` INT NOT NULL DEFAULT '0',
  PRIMARY KEY (`cpu_id`,`version_id`),
  KEY `cpu_version_vid` (`version_id`),
  CONSTRAINT `cpu_version_cid` FOREIGN KEY (`cpu_id`) REFERENCES `cpu_factory` (`cpu_id`),
  CONSTRAINT `cpu_version_vid` FOREIGN KEY (`version_id`) REFERENCES `version_plan` (`version_id`)
) COMMENT='CPU厂商与版本关联表';

/*Table structure for table `driver_manage` */

DROP TABLE IF EXISTS `driver_manage`;

CREATE TABLE `driver_manage` (
  `driver_id` INT NOT NULL AUTO_INCREMENT,
  `chip_factory` VARCHAR(100) DEFAULT NULL COMMENT '芯片厂商',
  `driver_name` VARCHAR(100) DEFAULT NULL COMMENT '驱动名称',
  `kernel_driver_publish` VARCHAR(100) DEFAULT NULL COMMENT '内核驱动发布',
  `kernel_driver_version` VARCHAR(100) DEFAULT NULL COMMENT '内核驱动版本',
  `exterior_driver_publish` VARCHAR(100) DEFAULT NULL COMMENT '外部驱动发布',
  `exterior_driver_publish_time` VARCHAR(100) DEFAULT NULL COMMENT '外部版本发布时间',
  `exterior_driver_version` VARCHAR(100) DEFAULT NULL COMMENT '外部驱动版本',
  `web_driver_url` VARCHAR(2083) DEFAULT NULL COMMENT '官网驱动链接',
  `all_driver_url` VARCHAR(2083) DEFAULT NULL COMMENT '软件驱动链接',
  `create_time` VARCHAR(50) DEFAULT NULL COMMENT '创建日期',
  `version_id` INT DEFAULT NULL COMMENT '操作体统id',
  PRIMARY KEY (`driver_id`),
  UNIQUE KEY `driver_manage_UN` (`chip_factory`,`driver_name`)
) COMMENT='驱动管理表';

/*Table structure for table `south_user` */

DROP TABLE IF EXISTS `south_user`;

CREATE TABLE `south_user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` VARCHAR(100) NOT NULL COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `role` INT NOT NULL COMMENT '角色',
  `create_time` VARCHAR(100) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) COMMENT='南向看板用户表';

/*Table structure for table `version_plan` */

DROP TABLE IF EXISTS `version_plan`;

CREATE TABLE `version_plan` (
  `version_id` INT NOT NULL AUTO_INCREMENT,
  `version_name` VARCHAR(100) NOT NULL COMMENT '版本名称',
  `alpha_start_date` VARCHAR(100) DEFAULT NULL COMMENT 'alpha阶段开始时间',
  `alpha_end_date` VARCHAR(100) DEFAULT NULL COMMENT 'alpha阶段结束时间',
  `alpha_detail` LONGTEXT COMMENT 'alpha阶段详情',
  `beta_start_date` VARCHAR(100) DEFAULT NULL COMMENT 'beta阶段开始时间',
  `beta_end_date` VARCHAR(100) DEFAULT NULL COMMENT 'beta阶段结束时间',
  `release_start_date` VARCHAR(100) DEFAULT NULL COMMENT 'release开始结束时间',
  `release_end_date` VARCHAR(100) DEFAULT NULL COMMENT 'release阶段结束时间',
  `create_time` VARCHAR(100) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`version_id`),
  UNIQUE KEY `version_name` (`version_name`)
) COMMENT='版本计划表';

/*Table structure for table `whole_community` */

DROP TABLE IF EXISTS `whole_community`;

CREATE TABLE `whole_community` (
  `id` INT DEFAULT NULL,
  `architecture` VARCHAR(100) DEFAULT NULL COMMENT '架构',
  `bios_uefi` VARCHAR(100) DEFAULT NULL,
  `hardware_factory` VARCHAR(100) DEFAULT NULL COMMENT '整机厂商(hardware_factory)',
  `certification_addr` VARCHAR(100) DEFAULT NULL,
  `certification_time` VARCHAR(100) DEFAULT NULL,
  `commit_id` VARCHAR(100) DEFAULT NULL,
  `computer_type` VARCHAR(100) DEFAULT NULL,
  `cpu` VARCHAR(100) DEFAULT NULL COMMENT 'cpu',
  `date` VARCHAR(100) DEFAULT NULL,
  `friendly_link` VARCHAR(2048) DEFAULT NULL,
  `hard_disk_drive` VARCHAR(100) DEFAULT NULL,
  `hardware_model` VARCHAR(100) DEFAULT NULL COMMENT '整机型号(whole_model)',
  `host_bus_adapter` VARCHAR(100) DEFAULT NULL,
  `lang` VARCHAR(100) DEFAULT NULL,
  `mainboard_model` VARCHAR(100) DEFAULT NULL,
  `os_version` VARCHAR(100) DEFAULT NULL,
  `ports_bus_types` VARCHAR(100) DEFAULT NULL,
  `product_information` VARCHAR(2048) DEFAULT NULL,
  `ram` VARCHAR(100) DEFAULT NULL,
  `video_adapter` VARCHAR(100) DEFAULT NULL,
  `update_time` VARCHAR(100) DEFAULT NULL,
  `compatibility_configuration` VARCHAR(100) DEFAULT NULL,
  `board_cards` VARCHAR(100) DEFAULT NULL,
  KEY `CPU` (`cpu`),
  KEY `architecture` (`architecture`),
  KEY `hardware_factory` (`hardware_factory`),
  KEY `hardware_model` (`hardware_model`),
  KEY `os_version` (`os_version`)
) COMMENT='整机社区数据表';

/*Table structure for table `whole_factory` */

DROP TABLE IF EXISTS `whole_factory`;

CREATE TABLE `whole_factory` (
  `whole_id` INT NOT NULL AUTO_INCREMENT COMMENT '整机厂商ID',
  `whole_factory` VARCHAR(100) NOT NULL COMMENT '整机厂商',
  `cpu_id` INT NOT NULL COMMENT 'CPU厂商ID',
  `hardware_model` VARCHAR(1000) DEFAULT NULL COMMENT '典型机型',
  `extend_model` VARCHAR(2000) DEFAULT NULL COMMENT '扩展机型型号',
  `interface_person` VARCHAR(30) DEFAULT NULL COMMENT '接口人',
  `contact` VARCHAR(500) DEFAULT NULL COMMENT '联系方式',
  `middleman` VARCHAR(30) DEFAULT NULL COMMENT '对应BD',
  `create_time` VARCHAR(30) DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`whole_id`),
  UNIQUE KEY `whole_factory` (`whole_factory`,`cpu_id`),
  KEY `whole_factory_cpu_id` (`cpu_id`),
  CONSTRAINT `whole_factory_cpu_id` FOREIGN KEY (`cpu_id`) REFERENCES `cpu_factory` (`cpu_id`)
) COMMENT='整机厂商表';

/*Table structure for table `whole_overall` */

DROP TABLE IF EXISTS `whole_overall`;

CREATE TABLE `whole_overall` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `whole_id` INT NOT NULL COMMENT '整机厂商id',
  `version_id` INT NOT NULL COMMENT '操作系统id',
  `create_time` VARCHAR(20) DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `whole_overall` (`whole_id`,`version_id`),
  KEY `version_id` (`version_id`),
  KEY `whole_id` (`whole_id`),
  CONSTRAINT `whole_overall_ibfk_1` FOREIGN KEY (`version_id`) REFERENCES `version_plan` (`version_id`),
  CONSTRAINT `whole_overall_ibfk_2` FOREIGN KEY (`whole_id`) REFERENCES `whole_factory` (`whole_id`)
) COMMENT='全景整机表';

/*Table structure for table `whole_plan` */

DROP TABLE IF EXISTS `whole_plan`;

CREATE TABLE `whole_plan` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `whole_factory` VARCHAR(100) NOT NULL COMMENT '整机厂商',
  `version_id` INT NOT NULL COMMENT '操作系统ID',
  `beta_list` VARCHAR(1000) DEFAULT NULL COMMENT 'beta阶段',
  `release_list` VARCHAR(1000) DEFAULT NULL COMMENT 'release阶段',
  `remark` VARCHAR(1000) DEFAULT NULL COMMENT '备注',
  `create_time` VARCHAR(100) DEFAULT NULL COMMENT '填表时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `whole_factory` (`whole_factory`,`version_id`),
  KEY `whole_plan_version_id` (`version_id`),
  CONSTRAINT `whole_plan_version_id` FOREIGN KEY (`version_id`) REFERENCES `version_plan` (`version_id`)
) COMMENT='整机计划表';