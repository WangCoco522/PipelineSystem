/*
 Navicat Premium Data Transfer

 Source Server         : 172.19.155.182
 Source Server Type    : PostgreSQL
 Source Server Version : 90614
 Source Host           : 172.19.155.182:5432
 Source Catalog        : thingsboard
 Source Schema         : forweb

 Target Server Type    : PostgreSQL
 Target Server Version : 90614
 File Encoding         : 65001

 Date: 14/07/2020 14:46:03
*/


-- ----------------------------
-- Table structure for gas_pass_condition
-- ----------------------------
DROP TABLE IF EXISTS "forweb"."gas_pass_condition";
CREATE TABLE "forweb"."gas_pass_condition" (
  "id" int8 NOT NULL DEFAULT nextval('"forweb".seq_attribute'::regclass),
  "user_id" int8 NOT NULL,
  "totalflow_upperlimit" numeric(64,2),
  "totalflow_floorlimit" numeric(64,2),
  "rssi_upperlimit" numeric(64,2),
  "rssi_floorlimit" numeric(64,2),
  "swrlse" varchar(255) COLLATE "pg_catalog"."default",
  "ts" int8
)
;
COMMENT ON COLUMN "forweb"."gas_pass_condition"."user_id" IS 'gas_user ID';
COMMENT ON COLUMN "forweb"."gas_pass_condition"."swrlse" IS '版本号，取上报版本号的后两位';
COMMENT ON COLUMN "forweb"."gas_pass_condition"."ts" IS '规则配置时间';
COMMENT ON TABLE "forweb"."gas_pass_condition" IS '出厂条件，通过检查';

-- ----------------------------
-- Table structure for gas_permission
-- ----------------------------
DROP TABLE IF EXISTS "forweb"."gas_permission";
CREATE TABLE "forweb"."gas_permission" (
  "id" int4 NOT NULL,
  "permission_code" varchar(255) COLLATE "pg_catalog"."default",
  "permission_name" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "forweb"."gas_permission"."permission_code" IS '权限code';
COMMENT ON COLUMN "forweb"."gas_permission"."permission_name" IS '权限名';

-- ----------------------------
-- Records of gas_permission
-- ----------------------------
INSERT INTO "forweb"."gas_permission" VALUES (1, 'query_device', '查看设备');
INSERT INTO "forweb"."gas_permission" VALUES (2, 'update_attribute', '更新信息');

-- ----------------------------
-- Table structure for gas_request_path
-- ----------------------------
DROP TABLE IF EXISTS "forweb"."gas_request_path";
CREATE TABLE "forweb"."gas_request_path" (
  "id" int4 NOT NULL,
  "url" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "forweb"."gas_request_path"."url" IS '请求路径';
COMMENT ON COLUMN "forweb"."gas_request_path"."description" IS '路径描述';

-- ----------------------------
-- Records of gas_request_path
-- ----------------------------
INSERT INTO "forweb"."gas_request_path" VALUES (1, '/device/list', '查询列表');
INSERT INTO "forweb"."gas_request_path" VALUES (2, '/device/findFailure', '查询上报失败设备');

-- ----------------------------
-- Table structure for gas_request_path_permission_relation
-- ----------------------------
DROP TABLE IF EXISTS "forweb"."gas_request_path_permission_relation";
CREATE TABLE "forweb"."gas_request_path_permission_relation" (
  "id" int4 NOT NULL,
  "url_id" int4,
  "permission_id" int4
)
;
COMMENT ON COLUMN "forweb"."gas_request_path_permission_relation"."url_id" IS '请求路径id';
COMMENT ON COLUMN "forweb"."gas_request_path_permission_relation"."permission_id" IS '权限id';

-- ----------------------------
-- Records of gas_request_path_permission_relation
-- ----------------------------
INSERT INTO "forweb"."gas_request_path_permission_relation" VALUES (1, 1, 1);
INSERT INTO "forweb"."gas_request_path_permission_relation" VALUES (2, 2, 2);

-- ----------------------------
-- Table structure for gas_role
-- ----------------------------
DROP TABLE IF EXISTS "forweb"."gas_role";
CREATE TABLE "forweb"."gas_role" (
  "id" int4 NOT NULL,
  "role_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "role_description" varchar(255) COLLATE "pg_catalog"."default",
  "role_code" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "forweb"."gas_role"."role_name" IS '角色名';
COMMENT ON COLUMN "forweb"."gas_role"."role_description" IS '角色描述';

-- ----------------------------
-- Records of gas_role
-- ----------------------------
INSERT INTO "forweb"."gas_role" VALUES (1, '管理员', '管理所有权限', 'admin');
INSERT INTO "forweb"."gas_role" VALUES (2, '普通用户', '拥有部分权限', 'user');

-- ----------------------------
-- Table structure for gas_role_permission_relation
-- ----------------------------
DROP TABLE IF EXISTS "forweb"."gas_role_permission_relation";
CREATE TABLE "forweb"."gas_role_permission_relation" (
  "id" int4 NOT NULL,
  "role_id" int4,
  "permission_id" int4
)
;
COMMENT ON COLUMN "forweb"."gas_role_permission_relation"."role_id" IS '角色id';
COMMENT ON COLUMN "forweb"."gas_role_permission_relation"."permission_id" IS '权限id';

-- ----------------------------
-- Records of gas_role_permission_relation
-- ----------------------------
INSERT INTO "forweb"."gas_role_permission_relation" VALUES (1, 1, 1);
INSERT INTO "forweb"."gas_role_permission_relation" VALUES (2, 2, 2);

-- ----------------------------
-- Table structure for gas_user
-- ----------------------------
DROP TABLE IF EXISTS "forweb"."gas_user";
CREATE TABLE "forweb"."gas_user" (
  "id" int8 NOT NULL DEFAULT nextval('"forweb".seq_user'::regclass),
  "username" varchar(255) COLLATE "pg_catalog"."default",
  "password" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of gas_user
-- ----------------------------
INSERT INTO "forweb"."gas_user" VALUES (1, 'admin', '$2a$10$47lsFAUlWixWG17Ca3M/r.EPJVIb7Tv26ZaxhzqN65nXVcAhHQM4i');
INSERT INTO "forweb"."gas_user" VALUES (2, 'est', '$2a$10$47lsFAUlWixWG17Ca3M/r.EPJVIb7Tv26ZaxhzqN65nXVcAhHQM4i');

-- ----------------------------
-- Table structure for gas_user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS "forweb"."gas_user_role_relation";
CREATE TABLE "forweb"."gas_user_role_relation" (
  "id" int4 NOT NULL,
  "user_id" int4,
  "role_id" int4
)
;
COMMENT ON COLUMN "forweb"."gas_user_role_relation"."user_id" IS '用户id';
COMMENT ON COLUMN "forweb"."gas_user_role_relation"."role_id" IS '角色id';

-- ----------------------------
-- Records of gas_user_role_relation
-- ----------------------------
INSERT INTO "forweb"."gas_user_role_relation" VALUES (1, 1, 1);
INSERT INTO "forweb"."gas_user_role_relation" VALUES (2, 2, 2);

-- ----------------------------
-- Primary Key structure for table gas_pass_condition
-- ----------------------------
ALTER TABLE "forweb"."gas_pass_condition" ADD CONSTRAINT "gas_pass_condition_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table gas_permission
-- ----------------------------
ALTER TABLE "forweb"."gas_permission" ADD CONSTRAINT "gas_permisson_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table gas_request_path
-- ----------------------------
ALTER TABLE "forweb"."gas_request_path" ADD CONSTRAINT "gas_request_path_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table gas_request_path_permission_relation
-- ----------------------------
ALTER TABLE "forweb"."gas_request_path_permission_relation" ADD CONSTRAINT "gas_request_path_permission_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table gas_role
-- ----------------------------
ALTER TABLE "forweb"."gas_role" ADD CONSTRAINT "gas_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table gas_role_permission_relation
-- ----------------------------
ALTER TABLE "forweb"."gas_role_permission_relation" ADD CONSTRAINT "gas_role_permission_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table gas_user
-- ----------------------------
ALTER TABLE "forweb"."gas_user" ADD CONSTRAINT "gas_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table gas_user_role_relation
-- ----------------------------
ALTER TABLE "forweb"."gas_user_role_relation" ADD CONSTRAINT "gas_user_role_relation_pkey" PRIMARY KEY ("id");
