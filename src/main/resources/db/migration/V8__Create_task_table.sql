create table task
(
	ID int auto_increment,
	TITLE VARCHAR(100),
	DESCRIPTION VARCHAR(200),
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	constraint TASK_PK
		primary key (ID)
);