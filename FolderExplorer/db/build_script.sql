create table folder_explorer.folders
(
    folder_id   int auto_increment
        primary key,
    name        varchar(100) not null,
    ancestor_id int          null,
    constraint folders_folders_folder_id_fk
        foreign key (ancestor_id) references folder_explorer.folders (folder_id)
);


