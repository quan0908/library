create database lib;

use lib;

CREATE TABLE if not exists blacklist
(
    id          bigint                             NOT NULL COMMENT '黑名单id',
    blackUserId bigint                     NOT NULL COMMENT '黑名单用户id',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint                            NOT NULL DEFAULT '0' COMMENT '0-不删 1删',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE if not exists book
(
    id           bigint                             NOT NULL COMMENT '图书id',
    bookName     varchar(128)                       NOT NULL COMMENT '图书名',
    bookNumber   int                                NOT NULL COMMENT '图书数量',
    bookRemaining int                               NOT NULL COMMENT '图书可借数量',
    type         varchar(128)                       NOT NULL COMMENT '图书分类',
    bookLocation varchar(128)                       NOT NULL COMMENT '图书位置',
    bookAuthor   varchar(128)                       NOT NULL COMMENT '图书作者',
    bookCover    longtext                           NOT NULL COMMENT '图书封面',
    bookTra      text                               NOT NULL COMMENT '图书简介',
    publishTime  datetime                           comment '发布时间',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint                            NOT NULL DEFAULT '0' COMMENT '0-不删 1删',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE if not exists book_borrow_record
(
    id         bigint       NOT NULL COMMENT '图书借阅记录表',
    userId     bigint NOT NULL COMMENT '借阅用户id',
    bookId     bigint NOT NULL COMMENT '借阅图书id',
    borrowDays int          NOT NULL COMMENT '借阅天数',
    isReturned tinyint      NOT NULL DEFAULT '0' COMMENT '0-未归还 1-已归还',
    createTime datetime              default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime              default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint      NOT NULL DEFAULT '0' COMMENT '0-不删 1删',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE if not exists comments
(
    id          bigint       NOT NULL COMMENT '评论id',
    content     text         NOT NULL COMMENT '评论内容',
    userId      bigint       NOT NULL COMMENT '评论用户id',
    bookId      bigint       NOT NULL COMMENT '评论图书id',
    checkUserId varchar(128)  COMMENT '审核人id',
    isChecked   tinyint      DEFAULT '0' COMMENT '0-待审核 1-审核通过 2-审核未通过',
    createTime  datetime              default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime              default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint      NOT NULL DEFAULT '0' COMMENT '0-不删 1删',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE if not exists meeting_room
(
    id         bigint                             NOT NULL COMMENT '会议室id',
    name       varchar(128)                       NOT NULL COMMENT '会议室编号',
    capacity   int                                NOT NULL COMMENT '会议室容纳人数',
    isEmpty    int      default 0                 not null comment '会议室是否空 0-空 1-占用',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint                            NOT NULL DEFAULT '0' COMMENT '0-不删 1-删',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE if not exists meeting_room_borrow_record
(
    id            bigint       NOT NULL COMMENT '会议室id',
    userId        bigint NOT NULL COMMENT '租借用户id',
    meetingRoomId bigint NOT NULL COMMENT '租借会议室id',
    borrowTime    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '租借时间',
    createTime    datetime              default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime              default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint      NOT NULL DEFAULT '0' COMMENT '0-不删 1删',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE if not exists user
(
    id         bigint COMMENT '用户表id',
    account    varchar(128) NOT NULL COMMENT '账号',
    username   varchar(128) NOT NULL COMMENT '姓名',
    idCard     char(18)     NOT NULL COMMENT '身份证号',
    password   varchar(256) NOT NULL COMMENT '密码',
    userAvatar longtext COMMENT '用户头像',
    foulTimes  int          NOT NULL DEFAULT '0' COMMENT '违规次数',
    role       varchar(64)  NOT NULL DEFAULT '0' COMMENT 'BAN USER LIB_ADMIN MEETING_ROOM_ADMIN SUPPER_ADMIN',
    createTime datetime              default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime              default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint      NOT NULL DEFAULT '0' COMMENT '0-不删 1删',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE if not exists announcement(
    id          bigint      PRIMARY KEY   COMMENT '公告id',
    content     text        NOT NULL      COMMENT '公告内容',
    creatorId   bigint      NOT NULL      COMMENT '公告发布人',
    createTime datetime              default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime              default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint      NOT NULL DEFAULT '0' COMMENT '0-不删 1删'
);

CREATE TABLE if not exists meeting_record(
    id           bigint       primary key comment '会议记录id',
    meetingId   bigint        NOT NULL    comment '会议id',
    participantId  bigint     NOT NULL    comment '参与人id',
    status       int          default 0   NOT NULL comment '0-未审核 1-同意 2-不同意',
    createTime datetime       default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime       default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint        NOT NULL DEFAULT '0' COMMENT '0-不删 1删'
);

CREATE TABLE if not exists meeting(
    id          bigint      primary key       comment '会议id',
    creatorId     bigint      NOT NULL    comment '会议发起人id',
    startTime   datetime    NOT NULL comment '会议开始时间',
    endTime     datetime    NOT NULL comment '会议结束时间',
    meetingRoomId bigint    not null comment '会议室ID',
    createTime datetime              default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime              default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint      NOT NULL DEFAULT '0' COMMENT '0-不删 1删'
);



