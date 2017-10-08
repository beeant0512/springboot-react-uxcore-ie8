DROP TABLE IF EXISTS `application_user_role`;
CREATE TABLE `application_user_role` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `create_at` datetime NOT NULL,
  `create_by` bigint(20) NOT NULL,
  `update_at` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_ur_userid` (`user_id`),
  KEY `idx_ur_roleid` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `application_user`;
CREATE TABLE `application_user` (
  `user_id` bigint(20) NOT NULL COMMENT 'user id',
  `user_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'login account',
  `user_password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'login password bcrypt encoded',
  `status` enum('inactive','active','off','locked','delete') COLLATE utf8mb4_unicode_ci DEFAULT 'inactive' COMMENT 'inactive, active, off, locked',
  `last_login_at` datetime DEFAULT NULL COMMENT 'last login time',
  `create_at` datetime NOT NULL COMMENT 'when created',
  `create_by` bigint(20) NOT NULL COMMENT 'who created',
  `update_at` datetime DEFAULT NULL COMMENT 'last update time',
  `update_by` bigint(20) DEFAULT NULL COMMENT 'last update user_id',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `application_sql_log`;
CREATE TABLE `application_sql_log` (
  `id` bigint(20) NOT NULL,
  `method` enum('update','delete','select','create') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `table_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `actor_id` bigint(20) unsigned NOT NULL,
  `client_ip` char(17) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `user_agent` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `server_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `server_port` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `statment` text COLLATE utf8mb4_unicode_ci,
  `create_at` datetime NOT NULL,
  `create_by` bigint(20) unsigned NOT NULL,
  `update_at` datetime DEFAULT NULL,
  `update_by` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `application_role_menu`;
CREATE TABLE `application_role_menu` (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  `create_at` datetime NOT NULL,
  `create_by` bigint(20) NOT NULL,
  `update_at` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_rm_roleid` (`role_id`),
  KEY `idx_rm_menuid` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `application_role`;
CREATE TABLE `application_role` (
  `role_id` bigint(20) unsigned NOT NULL COMMENT 'role id',
  `role_key` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT 'role key',
  `role_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'role name',
  `create_at` datetime NOT NULL COMMENT 'create time',
  `create_by` bigint(20) NOT NULL COMMENT 'create by',
  `update_at` datetime DEFAULT NULL COMMENT 'last update time',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT 'last update by',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `application_menu`;
CREATE TABLE `application_menu` (
  `menu_id` bigint(20) unsigned NOT NULL,
  `parent_menu_id` bigint(20) DEFAULT '0' COMMENT 'parent menu id in a tree node',
  `order_number` int(4) unsigned DEFAULT '0' COMMENT 'orders number',
  `type` enum('link','button','') COLLATE utf8mb4_unicode_ci DEFAULT 'link',
  `menu_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `menu_url` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `menu_perm` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'permision',
  `full_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `icon` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `level` int(4) DEFAULT '1' COMMENT 'menu level in a tree node',
  `position` enum('top','left','right') COLLATE utf8mb4_unicode_ci DEFAULT 'top',
  `create_at` datetime DEFAULT NULL,
  `create_by` bigint(20) unsigned DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  `update_by` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `application_log_user_login`;
CREATE TABLE `application_log_user_login` (
  `log_login_id` bigint(20) NOT NULL COMMENT 'id',
  `ip` char(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'ip address',
  `agent` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'login agent info',
  `create_at` datetime NOT NULL COMMENT 'login time',
  `create_by` bigint(20) DEFAULT NULL COMMENT 'login user id',
  `update_at` datetime DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`log_login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;