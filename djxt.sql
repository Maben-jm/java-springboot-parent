/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : djxt

Target Server Type    : MYSQL
Target Server Version : 50723ccccc
File Encoding         : 65001

Date: 2020-06-08 14:44:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `REFRESH_TOKEN_VALIDITY` decimal(10,0) DEFAULT NULL COMMENT 'refresh_token_validity||设定客户端的refresh_token的有效时间值(单位:秒)',
  `ADDITIONAL_INFORMATION` text COMMENT 'additional_information||这是一个预留的字段',
  `AUTOAPPROVE` text COMMENT 'autoapprove||设置用户是否自动Approval操作',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT 'create_time||数据的创建时间,精确到秒',
  `ARCHIVED` text COMMENT 'archived||用于标识客户端是否已存档(即实现逻辑删除),默认值为0',
  `TRUSTED` text COMMENT 'trusted||设置客户端是否为受信任的,默认为0(即不受信任的,1为受信任的)',
  `CLIENT_ID` varchar(128) NOT NULL COMMENT 'client_id||主键,必须唯一,不能为空.',
  `RESOURCE_IDS` text COMMENT 'resource_ids||客户端所能访问的资源id集合',
  `CLIENT_SECRET` text COMMENT 'CLIENT_SECRET||用于指定客户端(client)的访问密匙',
  `SCOPE` text COMMENT 'scope||指定客户端申请的权限范围',
  `AUTHORIZED_GRANT_TYPES` text COMMENT 'authorized_grant_types||指定客户端支持的grant_type',
  `WEB_SERVER_REDIRECT_URI` text COMMENT 'web_server_redirect_uri||客户端的重定向URI可为空',
  `AUTHORITIES` text COMMENT 'AUTHORITIES||指定客户端所拥有的Spring Security的权限值',
  `ACCESS_TOKEN_VALIDITY` decimal(10,0) DEFAULT NULL COMMENT 'ACCESS_TOKEN_VALIDITY||设定客户端的access_token的有效时间值(单位:秒)',
  PRIMARY KEY (`CLIENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='oauth2客户端表';

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES (null, null, 'false', '2018-07-10 17:47:01', '0', '0', 'system', null, '$2a$10$rgPcrNWqrCaQcm9EUpyriemLpmCmkKlU5QEnrV3EvWJ3wnCIskpXe', 'read,write', 'password,refresh_token', null, null, '43200');
INSERT INTO `oauth_client_details` VALUES (null, null, 'false', '2019-06-21 09:59:55', '0', '0', 'system1', null, '$2a$10$rgPcrNWqrCaQcm9EUpyriemLpmCmkKlU5QEnrV3EvWJ3wnCIskpXe', 'read,write', 'password', null, null, '864000');

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `log_type` int(11) DEFAULT NULL,
  `request_ip` varchar(255) DEFAULT NULL,
  `exception_code` varchar(255) DEFAULT NULL,
  `exception_detail` varchar(255) DEFAULT NULL,
  `params` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_route_config
-- ----------------------------
DROP TABLE IF EXISTS `t_route_config`;
CREATE TABLE `t_route_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `route_id` varchar(255) NOT NULL,
  `route_uri` varchar(255) NOT NULL,
  `route_order` int(11) DEFAULT NULL COMMENT '0:项目必须加上项目名  1:不需要加项目名和zuul一样',
  `predicates` varchar(255) NOT NULL,
  `filters` varchar(255) DEFAULT NULL,
  `enabled` int(11) NOT NULL,
  `deleted` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_route_config
-- ----------------------------
INSERT INTO `t_route_config` VALUES ('1', 'jsp', 'lb://JSP', null, '[    {        \"name\":\"Path\",        \"args\":{            \"pattern\":\"/jsp/**\"        }    }]', null, '1', '0', '2020-05-20 17:15:14', '2020-05-22 11:52:07');
INSERT INTO `t_route_config` VALUES ('2', 'oauth2', 'lb://OAUTH', null, '[\r\n    {\r\n        \"name\":\"Path\",\r\n        \"args\":{\r\n            \"pattern\":\"/oauth/**\"\r\n        }\r\n    }\r\n]', null, '1', '0', '2020-05-22 11:30:14', null);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'maben', '$2a$10$5jsMI0NesMRWlwyMCccwJe.e4j.N8nzI./k1gXiTTcIW1D/jrhWru', '0');
