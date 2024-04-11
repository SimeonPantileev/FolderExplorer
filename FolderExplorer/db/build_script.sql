create table folder_explorer.folders
(
    folder_id int auto_increment
        primary key,
    name      varchar(100) not null
);

create table folder_explorer.folders_structure
(
    folder_id   int null,
    ancestor_id int null,
    constraint structure_folders_folder_id_fk
        foreign key (folder_id) references folder_explorer.folders (folder_id),
    constraint structure_folders_folder_id_fk2
        foreign key (ancestor_id) references folder_explorer.folders (folder_id)
);


