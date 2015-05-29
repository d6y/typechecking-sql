# Typechecking SQL in doobie and Slick

This project contains an example of type-checking SQL using Slick 3 and doobie.

## PostgreSQL

Both projects make use of a PostgreSQL database.  You'll need to create this by hand:

```
$ psql
richard=# create database chat;
CREATE DATABASE

richard=#\c chat
You are now connected to database "chat" as user "richard".

chat=# create table "message" (
  id serial primary key,
  content varchar(255) not null
);
CREATE TABLE
```

If you need to change connection details, take a look in the following files:

- _src/main/resources/application.conf_
- _src/main/scala/doobie.scala_
- _src/test/scala/query-specs.scala_

## Slick 3

Run the example with:

```
$ sbt
sbt> runMain PlainSqlExample
```

## doobie

Run the example with:

```
$ sbt
sbt> test
sbt> runMain DoobieExample
```