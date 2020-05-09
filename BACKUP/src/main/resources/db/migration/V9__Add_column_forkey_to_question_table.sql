alter table QUESTION
	add task_id int;

alter table QUESTION
	add constraint QUESTION_TASK_ID_FK
		foreign key (task_id) references TASK;