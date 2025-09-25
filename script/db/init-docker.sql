create table x_dept
(
    id         bigint auto_increment
        primary key,
    name       varchar(64)                        not null,
    parent_id  bigint                             null,
    created_at datetime default CURRENT_TIMESTAMP null,
    updated_at datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

INSERT INTO `x_admin_template`.x_dept (id, name, parent_id, created_at, updated_at) VALUES (1, 'Engineering', null, '2025-06-12 18:25:23', '2025-06-12 18:25:23');
INSERT INTO `x_admin_template`.x_dept (id, name, parent_id, created_at, updated_at) VALUES (2, 'HR', null, '2025-06-12 18:25:23', '2025-06-12 18:25:23');
INSERT INTO `x_admin_template`.x_dept (id, name, parent_id, created_at, updated_at) VALUES (3, 'IT Support', 1, '2025-06-12 18:25:23', '2025-06-12 18:25:23');


create table x_menu
(
    id         bigint auto_increment
        primary key,
    name       varchar(64)                        not null,
    path       varchar(255)                       null,
    parent_id  bigint                             null,
    icon       varchar(64)                        null,
    order_num  int      default 0                 null,
    created_at datetime default CURRENT_TIMESTAMP null,
    updated_at datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

INSERT INTO `x_admin_template`.x_menu (id, name, path, parent_id, icon, order_num, created_at, updated_at) VALUES (1, 'Dashboard', '/dashboard', null, 'dashboard', 1, '2025-06-12 18:25:24', '2025-06-12 18:25:24');
INSERT INTO `x_admin_template`.x_menu (id, name, path, parent_id, icon, order_num, created_at, updated_at) VALUES (2, 'Users', '/users', null, 'users', 2, '2025-06-12 18:25:24', '2025-06-12 18:25:24');
INSERT INTO `x_admin_template`.x_menu (id, name, path, parent_id, icon, order_num, created_at, updated_at) VALUES (3, 'Settings', '/settings', null, 'settings', 3, '2025-06-12 18:25:24', '2025-06-12 18:25:24');


create table x_role
(
    id          bigint auto_increment
        primary key,
    name        varchar(64)                        not null,
    description varchar(255)                       null,
    created_at  datetime default CURRENT_TIMESTAMP null,
    updated_at  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint name
        unique (name)
);

INSERT INTO `x_admin_template`.x_role (id, name, description, created_at, updated_at) VALUES (1, 'admin', 'Administrator', '2025-06-12 18:25:23', '2025-06-12 18:25:23');
INSERT INTO `x_admin_template`.x_role (id, name, description, created_at, updated_at) VALUES (2, 'editor', 'Content Editor', '2025-06-12 18:25:23', '2025-06-12 18:25:23');
INSERT INTO `x_admin_template`.x_role (id, name, description, created_at, updated_at) VALUES (3, 'viewer', 'Read-only User', '2025-06-12 18:25:23', '2025-06-12 18:25:23');

create table x_user
(
    id         bigint auto_increment
        primary key,
    user_name   varchar(64)                        not null,
    password   varchar(128)                       not null,
    avatar     varchar(255)                       null,
    email      varchar(128)                       null,
    phone      varchar(20)                        null,
    status     tinyint  default 1                 null,
    sex     tinyint  default 1                 null,
    created_at datetime default CURRENT_TIMESTAMP null,
    updated_at datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint username
        unique (user_name)
);
INSERT INTO `x_admin_template`.x_user (id, user_name, password, avatar, email, phone, status,sex, created_at, updated_at) VALUES (1, 'alice', '$2a$10$8NkNLWv82CNwJ2p/qloaB.j7iH79scklHFfeDul7u4BfwvBduH95i', 'https://avatars.githubusercontent.com/u/35021566?v=4', 'alice@example.com', '1234567890', 1, 1,'2025-06-12 18:25:23', '2025-06-12 18:25:23');
INSERT INTO `x_admin_template`.x_user (id, user_name, password, avatar, email, phone, status,sex, created_at, updated_at) VALUES (2, 'bob', '$2a$10$8NkNLWv82CNwJ2p/qloaB.j7iH79scklHFfeDul7u4BfwvBduH95i', 'https://avatars.githubusercontent.com/u/35021566?v=4', 'bob@example.com', '2345678901', 1,1, '2025-06-12 18:25:23', '2025-06-12 18:25:23');
INSERT INTO `x_admin_template`.x_user (id, user_name, password, avatar, email, phone, status,sex, created_at, updated_at) VALUES (3, 'charlie', '$2a$10$8NkNLWv82CNwJ2p/qloaB.j7iH79scklHFfeDul7u4BfwvBduH95i', 'https://avatars.githubusercontent.com/u/35021566?v=4', 'charlie@example.com', '3456789012', 1,1, '2025-06-12 18:25:23', '2025-06-12 18:25:23');


create table x_user_dept
(
    id         bigint auto_increment
        primary key,
    user_id    bigint                             not null,
    dept_id    bigint                             not null,
    created_at datetime default CURRENT_TIMESTAMP null
);
INSERT INTO `x_admin_template`.x_user_dept (id, user_id, dept_id, created_at) VALUES (1, 1, 1, '2025-06-12 18:25:23');
INSERT INTO `x_admin_template`.x_user_dept (id, user_id, dept_id, created_at) VALUES (2, 2, 2, '2025-06-12 18:25:23');
INSERT INTO `x_admin_template`.x_user_dept (id, user_id, dept_id, created_at) VALUES (3, 3, 3, '2025-06-12 18:25:23');

create table x_user_role
(
    id         bigint auto_increment
        primary key,
    user_id    bigint                             not null,
    role_id    bigint                             not null,
    created_at datetime default CURRENT_TIMESTAMP null
);
INSERT INTO `x_admin_template`.x_user_role (id, user_id, role_id, created_at) VALUES (1, 1, 1, '2025-06-12 18:25:24');
INSERT INTO `x_admin_template`.x_user_role (id, user_id, role_id, created_at) VALUES (2, 2, 2, '2025-06-12 18:25:24');
INSERT INTO `x_admin_template`.x_user_role (id, user_id, role_id, created_at) VALUES (3, 3, 3, '2025-06-12 18:25:24');


-- 补充逻辑删除字段
alter table x_dept    add deleted int default 0 null comment '逻辑未删除值默认为 0，逻辑已删除值默认为 1';
alter table x_menu    add deleted int default 0 null comment '逻辑未删除值默认为 0，逻辑已删除值默认为 1';
alter table x_role    add deleted int default 0 null comment '逻辑未删除值默认为 0，逻辑已删除值默认为 1';
alter table x_role_menu    add deleted int default 0 null comment '逻辑未删除值默认为 0，逻辑已删除值默认为 1';
alter table x_user    add deleted int default 0 null comment '逻辑未删除值默认为 0，逻辑已删除值默认为 1';
alter table x_user_dept    add deleted int default 0 null comment '逻辑未删除值默认为 0，逻辑已删除值默认为 1';
alter table x_user_role    add deleted int default 0 null comment '逻辑未删除值默认为 0，逻辑已删除值默认为 1';

alter table x_menu    add menu_code varchar(20) not null comment '菜单编码';
alter table x_role    add role_code varchar(20) not null comment '角色编码';

alter table x_menu    alter column parent_id set default 0;


alter table x_menu
    change menu_code component varchar(100) not null comment '布局路径' default 'Layout';

alter table x_menu
    add redirect varchar(50) null comment '首调菜单路径';

alter table x_menu
    add title varchar(100) not null comment '菜单名称';

alter table x_menu    modify name varchar(64) not null comment '菜单编码';
alter table x_menu    change menu_code component varchar(100) default 'Layout' not null comment '菜单路由层级';
alter table x_menu    add redirect varchar(100) null comment '默认子集路由';
alter table x_menu    add title varchar(200) not null comment '菜单名称';

delete from x_menu where deleted = 0;
INSERT INTO `x_admin_template`.x_menu (id, name, path, component, title, redirect, parent_id, icon, order_num, created_at, updated_at, deleted) VALUES (1, 'dashboard', '/', 'Layout', '首页', null, 0, 'el-icon-eleme', 1, '2025-06-12 18:25:24', '2025-08-11 15:28:58', 0);
INSERT INTO `x_admin_template`.x_menu (id, name, path, component, title, redirect, parent_id, icon, order_num, created_at, updated_at, deleted) VALUES (2, 'users', 'users', 'sys/user', '用户管理', null, 3, 'el-icon-user', 3, '2025-06-12 18:25:24', '2025-08-11 11:16:19', 0);
INSERT INTO `x_admin_template`.x_menu (id, name, path, component, title, redirect, parent_id, icon, order_num, created_at, updated_at, deleted) VALUES (3, 'sys', '/sys', 'Layout', '系统管理', '/sys/user', 0, 'el-icon-eleme', 2, '2025-06-12 18:25:24', '2025-08-11 09:41:56', 0);
INSERT INTO `x_admin_template`.x_menu (id, name, path, component, title, redirect, parent_id, icon, order_num, created_at, updated_at, deleted) VALUES (4, 'role', 'role', 'sys/role', '角色管理', null, 3, 'el-icon-eleme', 4, '2025-06-12 18:25:24', '2025-08-11 11:16:19', 0);
INSERT INTO `x_admin_template`.x_menu (id, name, path, component, title, redirect, parent_id, icon, order_num, created_at, updated_at, deleted) VALUES (5, 'dept', 'dept', 'sys/dept/index', '部门管理', '/sys/dept/post', 3, 'el-icon-eleme', 5, '2025-06-12 18:25:24', '2025-08-12 17:00:02', 0);
INSERT INTO `x_admin_template`.x_menu (id, name, path, component, title, redirect, parent_id, icon, order_num, created_at, updated_at, deleted) VALUES (7, 'test', '/test', 'Layout', '测试管理', '/test/test1', 0, 'el-icon-eleme', 7, '2025-08-08 10:09:27', '2025-08-11 09:41:56', 0);
INSERT INTO `x_admin_template`.x_menu (id, name, path, component, title, redirect, parent_id, icon, order_num, created_at, updated_at, deleted) VALUES (8, 'test1', 'test1', 'test/test1', '测试1', null, 7, 'el-icon-eleme', 8, '2025-08-08 10:09:54', '2025-08-11 11:16:19', 0);
INSERT INTO `x_admin_template`.x_menu (id, name, path, component, title, redirect, parent_id, icon, order_num, created_at, updated_at, deleted) VALUES (9, 'test2', 'test2', 'test/test2', '测试2', null, 7, 'el-icon-eleme', 9, '2025-08-08 10:10:17', '2025-08-11 11:16:19', 0);
INSERT INTO `x_admin_template`.x_menu (id, name, path, component, title, redirect, parent_id, icon, order_num, created_at, updated_at, deleted) VALUES (10, 'test3', 'test3', 'test/test3', '测试3', null, 7, 'el-icon-eleme', 10, '2025-08-08 10:10:58', '2025-08-11 11:16:19', 0);
INSERT INTO `x_admin_template`.x_menu (id, name, path, component, title, redirect, parent_id, icon, order_num, created_at, updated_at, deleted) VALUES (11, 'menu', 'menu', 'sys/menu', '菜单管理', null, 3, 'el-icon-eleme', 11, '2025-08-08 10:17:04', '2025-08-11 11:16:19', 0);
INSERT INTO `x_admin_template`.x_menu (id, name, path, component, title, redirect, parent_id, icon, order_num, created_at, updated_at, deleted) VALUES (12, 'post', 'post', 'sys/dept/post', '组织结构', '', 5, 'el-icon-share', 11, '2025-08-12 15:19:41', '2025-08-12 15:19:41', 0);




drop table if exists x_dept;
create table x_dept
(
    id     bigint auto_increment comment '部门id'
        primary key,
    parent_id   bigint      default 0   null comment '父部门id',
    ancestors   varchar(50) default ''  null comment '祖级列表',
    dept_name   varchar(30) default ''  null comment '部门名称',
    order_num   int(4)      default 0   null comment '显示顺序',
    leader      varchar(20)             null comment '负责人',
    phone       varchar(11)             null comment '联系电话',
    email       varchar(50)             null comment '邮箱',
    status      int      default 0  null comment '部门状态（0正常 1停用）',
    deleted     int      default 0  null comment '删除标志（0代表存在 2代表删除）',
    create_by   varchar(64) default ''  null comment '创建者',
    create_time datetime                null comment '创建时间',
    update_by   varchar(64) default ''  null comment '更新者',
    update_time datetime                null comment '更新时间'
)
    comment '部门表';

INSERT INTO `x_admin_template`.x_dept (id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, deleted, create_by, create_time, update_by, update_time) VALUES (100, 0, '0', '若依科技', 0, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38', '', null);
INSERT INTO `x_admin_template`.x_dept (id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, deleted, create_by, create_time, update_by, update_time) VALUES (101, 100, '0,100', '深圳总公司', 1, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38', '', null);
INSERT INTO `x_admin_template`.x_dept (id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, deleted, create_by, create_time, update_by, update_time) VALUES (102, 100, '0,100', '长沙分公司', 2, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38', '', null);
INSERT INTO `x_admin_template`.x_dept (id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, deleted, create_by, create_time, update_by, update_time) VALUES (103, 101, '0,100,101', '研发部门', 1, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38', '', null);
INSERT INTO `x_admin_template`.x_dept (id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, deleted, create_by, create_time, update_by, update_time) VALUES (104, 101, '0,100,101', '市场部门', 2, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38', '', null);
INSERT INTO `x_admin_template`.x_dept (id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, deleted, create_by, create_time, update_by, update_time) VALUES (105, 101, '0,100,101', '测试部门', 3, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38', '', null);
INSERT INTO `x_admin_template`.x_dept (id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, deleted, create_by, create_time, update_by, update_time) VALUES (106, 101, '0,100,101', '财务部门', 4, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38', '', null);
INSERT INTO `x_admin_template`.x_dept (id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, deleted, create_by, create_time, update_by, update_time) VALUES (107, 101, '0,100,101', '运维部门', 5, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38', '', null);
INSERT INTO `x_admin_template`.x_dept (id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, deleted, create_by, create_time, update_by, update_time) VALUES (108, 102, '0,100,102', '市场部门', 1, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38', '', null);
INSERT INTO `x_admin_template`.x_dept (id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status, deleted, create_by, create_time, update_by, update_time) VALUES (109, 102, '0,100,102', '财务部门', 2, '若依', '15888888888', 'ry@qq.com', 0, 0, 'admin', '2025-02-11 14:08:38', '', null);



create table x_role_menu
(
    id         bigint auto_increment
        primary key,
    role_id    bigint                             not null,
    menu_id    bigint                             not null,
    created_at datetime default CURRENT_TIMESTAMP null
);
INSERT INTO x_admin_template.x_role_menu (id, role_id, menu_id, created_at, deleted) VALUES (1, 1, 1, '2025-06-12 18:25:24', 0);
INSERT INTO x_admin_template.x_role_menu (id, role_id, menu_id, created_at, deleted) VALUES (2, 1, 2, '2025-06-12 18:25:24', 0);
INSERT INTO x_admin_template.x_role_menu (id, role_id, menu_id, created_at, deleted) VALUES (3, 1, 3, '2025-06-12 18:25:24', 0);
INSERT INTO x_admin_template.x_role_menu (id, role_id, menu_id, created_at, deleted) VALUES (4, 2, 1, '2025-06-12 18:25:24', 0);
INSERT INTO x_admin_template.x_role_menu (id, role_id, menu_id, created_at, deleted) VALUES (5, 2, 2, '2025-06-12 18:25:24', 0);
INSERT INTO x_admin_template.x_role_menu (id, role_id, menu_id, created_at, deleted) VALUES (6, 3, 1, '2025-06-12 18:25:24', 0);
INSERT INTO x_admin_template.x_role_menu (id, role_id, menu_id, created_at, deleted) VALUES (7, 1, 4, '2025-06-12 18:25:24', 0);
INSERT INTO x_admin_template.x_role_menu (id, role_id, menu_id, created_at, deleted) VALUES (8, 1, 5, '2025-06-12 18:25:24', 0);
INSERT INTO x_admin_template.x_role_menu (id, role_id, menu_id, created_at, deleted) VALUES (9, 1, 6, '2025-06-12 18:25:24', 0);
INSERT INTO x_admin_template.x_role_menu (id, role_id, menu_id, created_at, deleted) VALUES (10, 1, 7, '2025-06-12 18:25:24', 0);
INSERT INTO x_admin_template.x_role_menu (id, role_id, menu_id, created_at, deleted) VALUES (11, 1, 8, '2025-06-12 18:25:24', 0);
INSERT INTO x_admin_template.x_role_menu (id, role_id, menu_id, created_at, deleted) VALUES (12, 1, 9, '2025-06-12 18:25:24', 0);
INSERT INTO x_admin_template.x_role_menu (id, role_id, menu_id, created_at, deleted) VALUES (13, 1, 10, '2025-06-12 18:25:24', 0);
INSERT INTO x_admin_template.x_role_menu (id, role_id, menu_id, created_at, deleted) VALUES (14, 1, 11, '2025-06-12 18:25:24', 0);
INSERT INTO x_admin_template.x_role_menu (id, role_id, menu_id, created_at, deleted) VALUES (15, 1, 12, '2025-06-12 18:25:24', 0);

