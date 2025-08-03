create table x_dept
(
    id         bigint auto_increment
        primary key,
    name       varchar(64)                        not null,
    parent_id  bigint                             null,
    created_at datetime default CURRENT_TIMESTAMP null,
    updated_at datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

INSERT INTO `x-admin-template`.x_dept (id, name, parent_id, created_at, updated_at) VALUES (1, 'Engineering', null, '2025-06-12 18:25:23', '2025-06-12 18:25:23');
INSERT INTO `x-admin-template`.x_dept (id, name, parent_id, created_at, updated_at) VALUES (2, 'HR', null, '2025-06-12 18:25:23', '2025-06-12 18:25:23');
INSERT INTO `x-admin-template`.x_dept (id, name, parent_id, created_at, updated_at) VALUES (3, 'IT Support', 1, '2025-06-12 18:25:23', '2025-06-12 18:25:23');


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

INSERT INTO `x-admin-template`.x_menu (id, name, path, parent_id, icon, order_num, created_at, updated_at) VALUES (1, 'Dashboard', '/dashboard', null, 'dashboard', 1, '2025-06-12 18:25:24', '2025-06-12 18:25:24');
INSERT INTO `x-admin-template`.x_menu (id, name, path, parent_id, icon, order_num, created_at, updated_at) VALUES (2, 'Users', '/users', null, 'users', 2, '2025-06-12 18:25:24', '2025-06-12 18:25:24');
INSERT INTO `x-admin-template`.x_menu (id, name, path, parent_id, icon, order_num, created_at, updated_at) VALUES (3, 'Settings', '/settings', null, 'settings', 3, '2025-06-12 18:25:24', '2025-06-12 18:25:24');


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

INSERT INTO `x-admin-template`.x_role (id, name, description, created_at, updated_at) VALUES (1, 'admin', 'Administrator', '2025-06-12 18:25:23', '2025-06-12 18:25:23');
INSERT INTO `x-admin-template`.x_role (id, name, description, created_at, updated_at) VALUES (2, 'editor', 'Content Editor', '2025-06-12 18:25:23', '2025-06-12 18:25:23');
INSERT INTO `x-admin-template`.x_role (id, name, description, created_at, updated_at) VALUES (3, 'viewer', 'Read-only User', '2025-06-12 18:25:23', '2025-06-12 18:25:23');


create table x_role_menu
(
    id         bigint auto_increment
        primary key,
    role_id    bigint                             not null,
    menu_id    bigint                             not null,
    created_at datetime default CURRENT_TIMESTAMP null
);
INSERT INTO `x-admin-template`.x_role_menu (id, role_id, menu_id, created_at) VALUES (1, 1, 1, '2025-06-12 18:25:24');
INSERT INTO `x-admin-template`.x_role_menu (id, role_id, menu_id, created_at) VALUES (2, 1, 2, '2025-06-12 18:25:24');
INSERT INTO `x-admin-template`.x_role_menu (id, role_id, menu_id, created_at) VALUES (3, 1, 3, '2025-06-12 18:25:24');
INSERT INTO `x-admin-template`.x_role_menu (id, role_id, menu_id, created_at) VALUES (4, 2, 1, '2025-06-12 18:25:24');
INSERT INTO `x-admin-template`.x_role_menu (id, role_id, menu_id, created_at) VALUES (5, 2, 2, '2025-06-12 18:25:24');
INSERT INTO `x-admin-template`.x_role_menu (id, role_id, menu_id, created_at) VALUES (6, 3, 1, '2025-06-12 18:25:24');



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
        unique (username)
);
INSERT INTO `x-admin-template`.x_user (id, user_name, password, avatar, email, phone, status,sex, created_at, updated_at) VALUES (1, 'alice', '$2a$10$8NkNLWv82CNwJ2p/qloaB.j7iH79scklHFfeDul7u4BfwvBduH95i', 'https://example.com/avatar/alice.png', 'alice@example.com', '1234567890', 1, 1,'2025-06-12 18:25:23', '2025-06-12 18:25:23');
INSERT INTO `x-admin-template`.x_user (id, user_name, password, avatar, email, phone, status,sex, created_at, updated_at) VALUES (2, 'bob', '$2a$10$8NkNLWv82CNwJ2p/qloaB.j7iH79scklHFfeDul7u4BfwvBduH95i', 'https://example.com/avatar/bob.png', 'bob@example.com', '2345678901', 1,1, '2025-06-12 18:25:23', '2025-06-12 18:25:23');
INSERT INTO `x-admin-template`.x_user (id, user_name, password, avatar, email, phone, status,sex, created_at, updated_at) VALUES (3, 'charlie', '$2a$10$8NkNLWv82CNwJ2p/qloaB.j7iH79scklHFfeDul7u4BfwvBduH95i', 'https://example.com/avatar/charlie.png', 'charlie@example.com', '3456789012', 1,1, '2025-06-12 18:25:23', '2025-06-12 18:25:23');


create table x_user_dept
(
    id         bigint auto_increment
        primary key,
    user_id    bigint                             not null,
    dept_id    bigint                             not null,
    created_at datetime default CURRENT_TIMESTAMP null
);
INSERT INTO `x-admin-template`.x_user_dept (id, user_id, dept_id, created_at) VALUES (1, 1, 1, '2025-06-12 18:25:23');
INSERT INTO `x-admin-template`.x_user_dept (id, user_id, dept_id, created_at) VALUES (2, 2, 2, '2025-06-12 18:25:23');
INSERT INTO `x-admin-template`.x_user_dept (id, user_id, dept_id, created_at) VALUES (3, 3, 3, '2025-06-12 18:25:23');

create table x_user_role
(
    id         bigint auto_increment
        primary key,
    user_id    bigint                             not null,
    role_id    bigint                             not null,
    created_at datetime default CURRENT_TIMESTAMP null
);
INSERT INTO `x-admin-template`.x_user_role (id, user_id, role_id, created_at) VALUES (1, 1, 1, '2025-06-12 18:25:24');
INSERT INTO `x-admin-template`.x_user_role (id, user_id, role_id, created_at) VALUES (2, 2, 2, '2025-06-12 18:25:24');
INSERT INTO `x-admin-template`.x_user_role (id, user_id, role_id, created_at) VALUES (3, 3, 3, '2025-06-12 18:25:24');

