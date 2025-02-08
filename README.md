# Send & Solve: Server

The server-side component of the Send & Solve system, designed to help you organize your tasks. Whether for personal use or team collaboration, Send & Solve provides the support you need.

[[Русский](README-RU.md)| --> English]

## Development Goals

- Creation of tasks, subtasks, notes, and embedded resources.
- User creation, assignment of users as executors, access control, and the ability to group executors into teams.
- Setting priorities, statuses, tags, deadlines, and progress.
- Mechanisms for client-server synchronization after interruptions.
- Ensuring ease of deployment, even for users without technical expertise.

## Installation

The system requires:
- [**Install Java**](https://www.java.com/en/download/manual.jsp)


- PostgreSQL 17.2: Install PostgreSQL 17.2 and specify the database server port during installation.


- Database Creation: Execute the database and table creation script in pgAdmin: [tables.sql](tables-db-sendandsolve.sql)


- User Creation and Permissions: Execute the user creation and permission definition script: [user.sql](user-db-sendandsolve.sql)  Note: This script requires you to modify it with your chosen username and password (see below).


- Username and Password: Choose a username and password for the server user.  Do not hardcode these in your scripts.


- Modify user.sql: Replace app_server in the `CREATE USER app_server WITH LOGIN PASSWORD '';` line with your chosen username and replace the empty string with your chosen password within the single quotes.


- Set Environment Variables: Set the following environment variables:

> `DB_SAS_USERNAME`: This variable will store your database username.
>
> `DB_SAS_PASSWORD`: This variable will store your database password.
>
> `DB_SAS_URL`: This variable stores the database connection string: `jdbc:postgresql://[database server address]:[database server port]/[database name]`.
>
> Example: DB_SAS_URL=`jdbc:postgresql://localhost:5432/sendandsolvemain`