begin;

create user app_server with LOGIN password '';


create role app_read_insert_tags;
grant select, insert on Table public.tags to app_read_insert_tags;
create role app_update_delete_tags;
grant select, update, delete on Table public.tags to app_update_delete_tags;
create role app_edit_notes;
grant select, insert, update, delete on Table public.notes to app_edit_notes;
create role app_edit_resources;
grant select, insert, update, delete on Table public.resources to app_edit_resources;
create role app_edit_taskhierarchy;
grant select, insert, update, delete on Table public.taskhierarchy to app_edit_taskhierarchy;
create role app_edit_tasks;
grant select, insert, update, delete on Table public.tasks to app_edit_tasks;
create role app_edit_teammember;
grant select, insert, update, delete on Table public.teammembers to app_edit_teammember;
create role app_edit_teams;
grant select, insert, update, delete on Table public.teams to app_edit_teams;
create role app_read_insert_users;
grant select, insert on Table public.users to app_read_insert_users;
create role app_update_delete_users;
grant select, update, delete on Table public.users to app_update_delete_users;

grant app_read_insert_tags, app_update_delete_tags, app_edit_notes, app_edit_resources, app_edit_taskhierarchy, app_edit_tasks, app_edit_teammember, app_edit_teams, app_read_insert_users, app_update_delete_users  to app_server;
grant connect on database sendandsolvemain to app_server;
commit;