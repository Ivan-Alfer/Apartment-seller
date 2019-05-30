delete
from user_role;
delete
from usr;

insert into usr(id, active, password, username, enable)
values (1, true, 'q', 'q', true),
       (2, true, 'a', 'a', true),
       (3,true,'g','g',true),
       (4, true, 'z', 'z', false);

insert into user_role(user_id, roles)
values (1, 'ADMIN'),
       (1, 'USER'),
       (2, 'USER'),
       (3, 'USER'),
       (4,'USER');