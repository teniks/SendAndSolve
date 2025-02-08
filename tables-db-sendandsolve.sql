begin;
create database sendandsolvemain;


create table Users( 
	uuid UUID PRIMARY KEY,
	login VARCHAR(256) UNIQUE, 
	password_hash VARCHAR(256),
	nickname VARCHAR(256),
	is_deleted BOOLEAN DEFAULT FALSE,
	is_synced BOOLEAN DEFAULT FALSE
);

create table Tasks( 
	uuid UUID PRIMARY KEY,
	title TEXT, 
	description TEXT,
	status VARCHAR(64), 
	priority SMALLINT,
	startdate TIMESTAMP WITH TIME ZONE, 
	enddate TIMESTAMP WITH TIME ZONE,
	progress SMALLINT,
	id_creator UUID references Users(uuid), 
	date_creation TIMESTAMP WITH TIME ZONE,
	is_deleted BOOLEAN DEFAULT FALSE,
	is_synced BOOLEAN DEFAULT FALSE
);

create table Tags(
	uuid UUID PRIMARY KEY,
	tag VARCHAR(256) UNIQUE NOT NULL,
	is_deleted BOOLEAN DEFAULT FALSE,
	is_synced BOOLEAN DEFAULT FALSE
);

create table TaskTags(
	task_id UUID REFERENCES Tasks(uuid),
	tag_id UUID REFERENCES Tags(uuid),
	PRIMARY KEY (task_id, tag_id)
);

create table Notes(
	uuid UUID PRIMARY KEY,
	title TEXT, 
	description TEXT,
	date_creation TIMESTAMP WITH TIME ZONE,
	id_user UUID REFERENCES Users(uuid),
	is_deleted BOOLEAN DEFAULT FALSE,
	is_synced BOOLEAN DEFAULT FALSE
);

create table NoteTags(
	note_id UUID REFERENCES Notes(uuid),
	tag_id UUID REFERENCES Tags(uuid),
	PRIMARY KEY (note_id, tag_id)
);

create table Teams( 
	uuid UUID PRIMARY KEY,
	title VARCHAR(256), 
	id_creator UUID REFERENCES Users(uuid),
	is_deleted BOOLEAN DEFAULT FALSE,
	is_synced BOOLEAN DEFAULT FALSE
);

create table TeamMembers( 
	id_team UUID REFERENCES Teams(uuid), 
	id_user UUID REFERENCES Users(uuid),
	PRIMARY KEY (id_team, id_user)
);

create table TaskHierarchy(
	id_parent UUID REFERENCES Tasks(uuid), 
	id_child UUID REFERENCES Tasks(uuid),
	PRIMARY KEY (id_parent, id_child)
);

create table Resources( 
	uuid UUID PRIMARY KEY,
	id_creator UUID REFERENCES Users(uuid), 
	upload_timestamp TIMESTAMP WITH TIME ZONE,
	byte_size BIGINT, 
	hash VARCHAR(256),
	file_location TEXT, 
	metadata JSON,
	is_deleted BOOLEAN DEFAULT FALSE,
	is_synced BOOLEAN DEFAULT FALSE
);

create table TaskResource(
	id_task UUID REFERENCES Tasks(uuid), 
	id_resource UUID REFERENCES Resources(uuid),
	PRIMARY KEY (id_task, id_resource)
);

create table Sessions( 
	uuid UUID PRIMARY KEY,
	iduser UUID REFERENCES Users(uuid), 
	user_token VARCHAR(1024),
	expiry_date TIMESTAMP WITH TIME ZONE NOT NULL,
	date_activity TIMESTAMP WITH TIME ZONE,
	device VARCHAR(256) 
);

CREATE TABLE ChangeLog (
    uuid UUID PRIMARY KEY,
    user_id UUID REFERENCES Users(uuid) NOT NULL,  -- Кто внес изменение
    table_name VARCHAR(256) NOT NULL,              -- Имя таблицы (например, 'Tasks')
    operation VARCHAR(16) NOT NULL CHECK (operation IN ('INSERT', 'UPDATE', 'DELETE')),
    record_id UUID NOT NULL,                       -- ID измененной записи (например, uuid из Tasks)
    old_value JSONB,                               -- Данные до изменения (для UPDATE/DELETE)
    new_value JSONB,                               -- Данные после изменения (для INSERT/UPDATE)
    timestamp TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    device_id VARCHAR(256),                        -- Устройство, с которого внесено изменение
    is_synced BOOLEAN DEFAULT FALSE                -- Флаг синхронизации с сервером (для оффлайна)
);


CREATE INDEX idx_tasks_creator ON Tasks(id_creator);
CREATE INDEX idx_tasks_status ON Tasks(status);

CREATE INDEX idx_tags_name ON Tags(tag);

CREATE INDEX idx_changelog_record ON ChangeLog(record_id);
CREATE INDEX idx_changelog_timestamp ON ChangeLog(timestamp);

CREATE INDEX idx_teammembers_user ON TeamMembers(id_user);
CREATE INDEX idx_taskhierarchy_child ON TaskHierarchy(id_child);

CREATE INDEX idx_sessions_expiry ON Sessions(expiry_date);
CREATE INDEX idx_changelog_sync ON ChangeLog(is_synced);
commit;