-- 1. 删除旧表（按依赖逆序删除，避免外键问题；此处无外键，可直接删）
DROP TABLE IF EXISTS x_user_role;
DROP TABLE IF EXISTS x_user_dept;
DROP TABLE IF EXISTS x_role_menu;
DROP TABLE IF EXISTS x_user;
DROP TABLE IF EXISTS x_role;
DROP TABLE IF EXISTS x_menu;
DROP TABLE IF EXISTS x_dept;

-- 2. 创建部门表 x_dept
CREATE TABLE x_dept
(
    id          BIGINT AUTO_INCREMENT COMMENT '部门id' PRIMARY KEY,
    parent_id   BIGINT      DEFAULT 0  NULL COMMENT '父部门id',
    ancestors   VARCHAR(50) DEFAULT '' NULL COMMENT '祖级列表',
    dept_name   VARCHAR(30) DEFAULT '' NULL COMMENT '部门名称',
    order_num   INT(4)      DEFAULT 0  NULL COMMENT '显示顺序',
    leader      VARCHAR(20)            NULL COMMENT '负责人',
    phone       VARCHAR(11)            NULL COMMENT '联系电话',
    email       VARCHAR(50)            NULL COMMENT '邮箱',
    status      INT         DEFAULT 0  NULL COMMENT '部门状态（0正常 1停用）',
    deleted     INT         DEFAULT 0  NULL COMMENT '删除标志（0代表存在 2代表删除）',
    create_by   VARCHAR(64) DEFAULT '' NULL COMMENT '创建者',
    create_time DATETIME               NULL COMMENT '创建时间',
    update_by   VARCHAR(64) DEFAULT '' NULL COMMENT '更新者',
    update_time DATETIME               NULL COMMENT '更新时间'
) COMMENT '部门表';

-- 3. 创建菜单表 x_menu
CREATE TABLE x_menu
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(64)                            NOT NULL COMMENT '菜单编码',
    path       VARCHAR(255)                           NULL,
    component  VARCHAR(100) DEFAULT 'Layout'          NOT NULL COMMENT '菜单路由层级',
    title      VARCHAR(200)                           NOT NULL COMMENT '菜单名称',
    redirect   VARCHAR(100)                           NULL COMMENT '默认子集路由',
    parent_id  BIGINT       DEFAULT 0                 NULL,
    icon       VARCHAR(64)                            NULL,
    order_num  INT          DEFAULT 0                 NULL,
    created_at DATETIME     DEFAULT CURRENT_TIMESTAMP NULL,
    updated_at DATETIME     DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted    INT          DEFAULT 0                 NULL COMMENT '逻辑未删除值默认为 0，逻辑已删除值默认为 1'
) COMMENT '菜单表';

-- 4. 创建角色表 x_role
CREATE TABLE x_role
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(64)                        NOT NULL,
    role_code   VARCHAR(20)                        NOT NULL COMMENT '角色编码',
    description VARCHAR(255)                       NULL,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
    updated_at  DATETIME DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted     INT      DEFAULT 0                 NULL COMMENT '逻辑未删除值默认为 0，逻辑已删除值默认为 1',
    CONSTRAINT uk_role_name UNIQUE (name)
) COMMENT '角色表';

-- 5. 创建用户表 x_user
CREATE TABLE x_user
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name  VARCHAR(64)                        NOT NULL,
    password   VARCHAR(128)                       NOT NULL,
    avatar     VARCHAR(255)                       NULL,
    email      VARCHAR(128)                       NULL,
    phone      VARCHAR(20)                        NULL,
    status     TINYINT  DEFAULT 1                 NULL,
    sex        TINYINT  DEFAULT 1                 NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted    INT      DEFAULT 0                 NULL COMMENT '逻辑未删除值默认为 0，逻辑已删除值默认为 1',
    CONSTRAINT uk_username UNIQUE (user_name)
) COMMENT '用户表';

-- 6. 创建用户-部门关联表
CREATE TABLE x_user_dept
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT                             NOT NULL,
    dept_id    BIGINT                             NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
    deleted    INT      DEFAULT 0                 NULL COMMENT '逻辑未删除值默认为 0，逻辑已删除值默认为 1'
) COMMENT '用户部门关联表';

-- 7. 创建用户-角色关联表
CREATE TABLE x_user_role
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT                             NOT NULL,
    role_id    BIGINT                             NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
    deleted    INT      DEFAULT 0                 NULL COMMENT '逻辑未删除值默认为 0，逻辑已删除值默认为 1'
) COMMENT '用户角色关联表';

-- 8. 创建角色-菜单关联表
CREATE TABLE x_role_menu
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_id    BIGINT                             NOT NULL,
    menu_id    BIGINT                             NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP NULL,
    deleted    INT      DEFAULT 0                 NULL COMMENT '逻辑未删除值默认为 0，逻辑已删除值默认为 1'
) COMMENT '角色菜单关联表';

-- 9. 插入部门数据
INSERT INTO x_dept (id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, deleted, create_by,
                    create_time, update_by, update_time)
VALUES (100, 0, '0', '若依科技', 0, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38', '', NULL),
       (101, 100, '0,100', '深圳总公司', 1, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38',
        '', NULL),
       (102, 100, '0,100', '长沙分公司', 2, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38',
        '', NULL),
       (103, 101, '0,100,101', '研发部门', 1, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38',
        '', NULL),
       (104, 101, '0,100,101', '市场部门', 2, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38',
        '', NULL),
       (105, 101, '0,100,101', '测试部门', 3, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38',
        '', NULL),
       (106, 101, '0,100,101', '财务部门', 4, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38',
        '', NULL),
       (107, 101, '0,100,101', '运维部门', 5, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38',
        '', NULL),
       (108, 102, '0,100,102', '市场部门', 1, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38',
        '', NULL),
       (109, 102, '0,100,102', '财务部门', 2, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38',
        '', NULL);

-- 10. 插入菜单数据
INSERT INTO x_menu (id, name, path, component, title, redirect, parent_id, icon, order_num, created_at, updated_at,
                    deleted)
VALUES (1, 'dashboard', '/', 'Layout', '首页', NULL, 0, 'el-icon-eleme', 1, '2025-06-12 18:25:24',
        '2025-08-11 15:28:58', 0),
       (3, 'sys', '/sys', 'Layout', '系统管理', '/sys/user', 0, 'el-icon-eleme', 2, '2025-06-12 18:25:24',
        '2025-08-11 09:41:56', 0),
       (2, 'users', 'users', 'sys/user', '用户管理', NULL, 3, 'el-icon-user', 3, '2025-06-12 18:25:24',
        '2025-08-11 11:16:19', 0),
       (4, 'role', 'role', 'sys/role', '角色管理', NULL, 3, 'el-icon-eleme', 4, '2025-06-12 18:25:24',
        '2025-08-11 11:16:19', 0),
       (5, 'dept', 'dept', 'sys/dept/index', '部门管理', '/sys/dept/post', 3, 'el-icon-eleme', 5, '2025-06-12 18:25:24',
        '2025-08-12 17:00:02', 0),
       (11, 'menu', 'menu', 'sys/menu', '菜单管理', NULL, 3, 'el-icon-eleme', 11, '2025-08-08 10:17:04',
        '2025-08-11 11:16:19', 0),
       (12, 'post', 'post', 'sys/dept/post', '组织结构', '', 5, 'el-icon-share', 11, '2025-08-12 15:19:41',
        '2025-08-12 15:19:41', 0),
       (7, 'test', '/test', 'Layout', '测试管理', '/test/test1', 0, 'el-icon-eleme', 7, '2025-08-08 10:09:27',
        '2025-08-11 09:41:56', 0),
       (8, 'test1', 'test1', 'test/test1', '测试1', NULL, 7, 'el-icon-eleme', 8, '2025-08-08 10:09:54',
        '2025-08-11 11:16:19', 0),
       (9, 'test2', 'test2', 'test/test2', '测试2', NULL, 7, 'el-icon-eleme', 9, '2025-08-08 10:10:17',
        '2025-08-11 11:16:19', 0),
       (10, 'test3', 'test3', 'test/test3', '测试3', NULL, 7, 'el-icon-eleme', 10, '2025-08-08 10:10:58',
        '2025-08-11 11:16:19', 0);

-- 11. 插入角色数据
INSERT INTO x_role (id, name, role_code, description, created_at, updated_at, deleted)
VALUES (1, 'admin', 'ADMIN', 'Administrator', '2025-06-12 18:25:23', '2025-06-12 18:25:23', 0),
       (2, 'editor', 'EDITOR', 'Content Editor', '2025-06-12 18:25:23', '2025-06-12 18:25:23', 0),
       (3, 'viewer', 'VIEWER', 'Read-only User', '2025-06-12 18:25:23', '2025-06-12 18:25:23', 0);

-- 12. 插入用户数据
INSERT INTO x_user (id, user_name, password, avatar, email, phone, status, sex, created_at, updated_at, deleted)
VALUES (1, 'alice', '$2a$10$8NkNLWv82CNwJ2p/qloaB.j7iH79scklHFfeDul7u4BfwvBduH95i',
        'https://avatars.githubusercontent.com/u/35021566?v=4', 'alice@example.com', '1234567890', 1, 1,
        '2025-06-12 18:25:23', '2025-06-12 18:25:23', 0),
       (2, 'bob', '$2a$10$8NkNLWv82CNwJ2p/qloaB.j7iH79scklHFfeDul7u4BfwvBduH95i',
        'https://avatars.githubusercontent.com/u/35021566?v=4', 'bob@example.com', '2345678901', 1, 1,
        '2025-06-12 18:25:23', '2025-06-12 18:25:23', 0),
       (3, 'charlie', '$2a$10$8NkNLWv82CNwJ2p/qloaB.j7iH79scklHFfeDul7u4BfwvBduH95i',
        'https://avatars.githubusercontent.com/u/35021566?v=4', 'charlie@example.com', '3456789012', 1, 1,
        '2025-06-12 18:25:23', '2025-06-12 18:25:23', 0);

-- 13. 插入用户-部门关联
INSERT INTO x_user_dept (id, user_id, dept_id, created_at, deleted)
VALUES (1, 1, 103, '2025-06-12 18:25:23', 0), -- alice -> 研发部门
       (2, 2, 104, '2025-06-12 18:25:23', 0), -- bob -> 市场部门（深圳）
       (3, 3, 108, '2025-06-12 18:25:23', 0);
-- charlie -> 市场部门（长沙）

-- 14. 插入用户-角色关联
INSERT INTO x_user_role (id, user_id, role_id, created_at, deleted)
VALUES (1, 1, 1, '2025-06-12 18:25:24', 0), -- alice -> admin
       (2, 2, 2, '2025-06-12 18:25:24', 0), -- bob -> editor
       (3, 3, 3, '2025-06-12 18:25:24', 0);
-- charlie -> viewer

-- 15. 插入角色-菜单权限
INSERT INTO x_role_menu (id, role_id, menu_id, created_at, deleted)
VALUES
-- admin 拥有全部菜单
(1, 1, 1, '2025-06-12 18:25:24', 0),
(2, 1, 3, '2025-06-12 18:25:24', 0),
(3, 1, 2, '2025-06-12 18:25:24', 0),
(4, 1, 4, '2025-06-12 18:25:24', 0),
(5, 1, 5, '2025-06-12 18:25:24', 0),
(6, 1, 11, '2025-06-12 18:25:24', 0),
(7, 1, 12, '2025-06-12 18:25:24', 0),
(8, 1, 7, '2025-06-12 18:25:24', 0),
(9, 1, 8, '2025-06-12 18:25:24', 0),
(10, 1, 9, '2025-06-12 18:25:24', 0),
(11, 1, 10, '2025-06-12 18:25:24', 0),
-- editor 拥有首页、系统管理、用户管理
(12, 2, 1, '2025-06-12 18:25:24', 0),
(13, 2, 3, '2025-06-12 18:25:24', 0),
(14, 2, 2, '2025-06-12 18:25:24', 0),
-- viewer 仅首页
(15, 3, 1, '2025-06-12 18:25:24', 0);