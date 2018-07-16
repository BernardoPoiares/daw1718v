INSERT INTO users (username,password,token) values 	('dog2','im still not a dog muahahahaha','ZG9nMjppbSBzdGlsbCBub3QgYSBkb2cgbXVhaGFoYWhhaGE=')
INSERT INTO users (username,password,token) values 	('cat','cat2','Y2F0OmNhdDI=')

INSERT INTO checkitem_template (name,description,itemtemplate_user) values ('do Daw work','not so ezzzz',1)
INSERT INTO checkitem_template (name,description,itemtemplate_user) values ('do Daw work2','not so ezzzz',2)
INSERT INTO checkitem_template (name,description,itemtemplate_user) values ('showme','pleaseeee',2)

INSERT INTO checkitem (state,checkitem_itemtemplate) values ('uncompleted',1)
INSERT INTO checkitem (state,checkitem_itemtemplate) values ('uncompleted',2)
INSERT INTO checkitem (state,checkitem_itemtemplate) values ('uncompleted',2)
INSERT INTO checkitem (state,checkitem_itemtemplate) values ('uncompleted',3)


INSERT INTO checklist (name,completion_date,list_user) values ('lis1','2013-04-23T18:25:43.511Z',2)
INSERT INTO checklist (name,completion_date,list_user) values ('list2','2013-04-23T18:25:43.511Z',2)

INSERT INTO checklist_template (name,listtemplate_user) values ('listtemp1',2)

INSERT INTO checklist (name,completion_date,checklist_checklisttemplate,list_user) values ('listbytemplate','2013-04-23T18:25:43.511Z',1,2)

