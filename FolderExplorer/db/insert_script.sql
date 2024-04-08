insert into MY_TABLE (folder_id, name)
values  (1, 'Root folder'),
        (2, 'A nested folder'),
        (3, 'Child folder 1'),
        (4, 'Child folder 2');

insert into MY_TABLE (folder_id, ancestor_id)
values  (2, 1),
        (3, 2),
        (4, 2);