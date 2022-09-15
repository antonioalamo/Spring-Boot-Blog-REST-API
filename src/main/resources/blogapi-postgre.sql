-- SET SCHEMA 'blogapi';

-- UNLOCK TABLES;

-- DROP TABLE IF EXISTS post_tag;
-- DROP TABLE IF EXISTS tags;
-- DROP TABLE IF EXISTS user_role;
-- DROP TABLE IF EXISTS roles;
-- DROP TABLE IF EXISTS comments;
-- DROP TABLE IF EXISTS posts;
-- DROP TABLE IF EXISTS photos;
-- DROP TABLE IF EXISTS albums;
-- DROP TABLE IF EXISTS todos;
-- DROP TABLE IF EXISTS users;
-- DROP TABLE IF EXISTS address;
-- DROP TABLE IF EXISTS company;
-- DROP TABLE IF EXISTS geo;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE tags_seq;

CREATE TABLE tags (
  id bigint check (id > 0) NOT NULL DEFAULT NEXTVAL ('tags_seq'),
  name varchar(255) NOT NULL,
  created_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by bigint check (created_by > 0) NOT NULL,
  updated_by bigint check (updated_by > 0) NOT NULL,
  PRIMARY KEY (id)
) ;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE geo_seq;

CREATE TABLE geo (
  id bigint check (id > 0) NOT NULL DEFAULT NEXTVAL ('geo_seq'),
  lat varchar(255),
  lng varchar(255),
  created_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by bigint check (created_by > 0) DEFAULT NULL,
  updated_by bigint check (updated_by > 0) DEFAULT NULL,
  PRIMARY KEY (id)
)  ;

ALTER SEQUENCE geo_seq RESTART WITH 1;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE company_seq;

CREATE TABLE company (
  id bigint check (id > 0) NOT NULL DEFAULT NEXTVAL ('company_seq'),
  name varchar(255),
  catch_phrase varchar(255),
  bs varchar(255),
  created_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by bigint check (created_by > 0) DEFAULT NULL,
  updated_by bigint check (updated_by > 0) DEFAULT NULL,
  PRIMARY KEY (id)
)  ;

ALTER SEQUENCE company_seq RESTART WITH 1;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE address_seq;

CREATE TABLE address (
  id bigint check (id > 0) NOT NULL DEFAULT NEXTVAL ('address_seq'),
  street varchar(255),
  suite varchar(255),
  city varchar(255),
  zipcode varchar(255),
  geo_id bigint check (geo_id > 0) DEFAULT NULL,
  created_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by bigint check (created_by > 0) DEFAULT NULL,
  updated_by bigint check (updated_by > 0) DEFAULT NULL,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_geo FOREIGN KEY (geo_id) REFERENCES geo (id) ON DELETE CASCADE ON UPDATE CASCADE
)  ;

ALTER SEQUENCE address_seq RESTART WITH 1;

CREATE INDEX fk_geo ON address (geo_id);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE users_seq;

CREATE TABLE users (
  id bigint check (id > 0) NOT NULL DEFAULT NEXTVAL ('users_seq'),
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  address_id bigint check (address_id > 0) DEFAULT NULL,
  phone varchar(255),
  website varchar(255),
  company_id bigint check (company_id > 0) DEFAULT NULL,
  created_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES address (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_company FOREIGN KEY (company_id) REFERENCES company (id) ON DELETE CASCADE ON UPDATE CASCADE
)  ;

ALTER SEQUENCE users_seq RESTART WITH 1;

CREATE INDEX fk_address ON users (address_id);
CREATE INDEX fk_company ON users (company_id);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE todos_seq;

CREATE TABLE todos (
  id bigint check (id > 0) NOT NULL DEFAULT NEXTVAL ('todos_seq'),
  title varchar(255) NOT NULL,
  completed boolean default false,
  user_id bigint check (user_id > 0) DEFAULT NULL,
  created_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by bigint check (created_by > 0) DEFAULT NULL,
  updated_by bigint check (updated_by > 0) DEFAULT NULL,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_user_todos FOREIGN KEY (user_id) REFERENCES users (id)
)  ;

ALTER SEQUENCE todos_seq RESTART WITH 1;

CREATE INDEX fk_user_todos ON todos (user_id);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE albums_seq;

CREATE TABLE albums (
  id bigint check (id > 0) NOT NULL DEFAULT NEXTVAL ('albums_seq'),
  title varchar(255) NOT NULL,
  user_id bigint check (user_id > 0) DEFAULT NULL,
  created_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by bigint check (created_by > 0) DEFAULT NULL,
  updated_by bigint check (updated_by > 0) DEFAULT NULL,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_user_album FOREIGN KEY (user_id) REFERENCES users (id)
)  ;

ALTER SEQUENCE albums_seq RESTART WITH 1;

CREATE INDEX fk_user_album ON albums (user_id);


-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE book_seq;

CREATE TABLE books (
    id bigint check (id > 0) NOT NULL DEFAULT NEXTVAL ('book_seq'),
    title varchar(255) NOT NULL,
    isbn13 VARCHAR(13) NOT NULL,
    language_id bigint NOT NULL,
    num_pages bigint NOT NULL,
    -- publication_date timestamp(0) NOT NULL,
    publication_date varchar(255) NOT NULL,
    publisher_id bigint NOT NULL,
    created_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by bigint check (created_by > 0) DEFAULT NULL,
    updated_by bigint check (updated_by > 0) DEFAULT NULL,
    PRIMARY KEY (id)
)  ;

ALTER SEQUENCE book_seq RESTART WITH 1;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE photos_seq;

CREATE TABLE photos (
  id bigint check (id > 0) NOT NULL DEFAULT NEXTVAL ('photos_seq'),
  title varchar(255) NOT NULL,
  url varchar(255) NOT NULL,
  thumbnail_url varchar(255) NOT NULL,
  album_id bigint check (album_id > 0) DEFAULT NULL,
  created_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by bigint check (created_by > 0) DEFAULT NULL,
  updated_by bigint check (updated_by > 0) DEFAULT NULL,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_album FOREIGN KEY (album_id) REFERENCES albums (id)
)  ;

ALTER SEQUENCE photos_seq RESTART WITH 1;

CREATE INDEX fk_album ON photos (album_id);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE posts_seq;

CREATE TABLE posts (
  id bigint check (id > 0) NOT NULL DEFAULT NEXTVAL ('posts_seq'),
  title varchar(255) NOT NULL,
  body text NOT NULL,
  user_id bigint check (user_id > 0) DEFAULT NULL,
  category_id bigint check (category_id > 0) DEFAULT NULL,
  created_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by bigint check (created_by > 0) DEFAULT NULL,
  updated_by bigint check (updated_by > 0) DEFAULT NULL,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_user_post FOREIGN KEY (user_id) REFERENCES users (id)
)  ;

ALTER SEQUENCE posts_seq RESTART WITH 1;

CREATE INDEX fk_user_post ON posts (user_id);


-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE categories_seq;

CREATE TABLE categories (
  id bigint check (id > 0) NOT NULL DEFAULT NEXTVAL ('categories_seq'),
  name varchar(255) NOT NULL,
  created_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by bigint check (created_by > 0) DEFAULT NULL,
  updated_by bigint check (updated_by > 0) DEFAULT NULL,
  PRIMARY KEY (id)
)  ;

ALTER SEQUENCE categories_seq RESTART WITH 1;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE post_tag_seq;

CREATE TABLE post_tag (
  id bigint check (id > 0) NOT NULL DEFAULT NEXTVAL ('post_tag_seq'),
  post_id bigint check (post_id > 0) NOT NULL,
  tag_id bigint check (tag_id > 0) NOT NULL,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_posttag_post_id FOREIGN KEY (post_id) REFERENCES posts (id),
  CONSTRAINT fk_posttag_tag_id FOREIGN KEY (tag_id) REFERENCES tags (id)
) ;

CREATE INDEX fk_posttag_post_id ON post_tag (post_id);
CREATE INDEX fk_posttag_tag_id ON post_tag (tag_id);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE comments_seq;

CREATE TABLE comments (
  id bigint check (id > 0) NOT NULL DEFAULT NEXTVAL ('comments_seq'),
  name varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  body text NOT NULL,
  post_id bigint check (post_id > 0) DEFAULT NULL,
  user_id bigint check (user_id > 0) DEFAULT NULL,
  created_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by bigint check (created_by > 0) NOT NULL,
  updated_by bigint check (updated_by > 0) NOT NULL,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_comment_post FOREIGN KEY (post_id) REFERENCES posts (id),
  CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES users (id)
)  ;

ALTER SEQUENCE comments_seq RESTART WITH 1;

CREATE INDEX fk_comment_post ON comments (post_id);
CREATE INDEX fk_comment_user ON comments (user_id);

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE roles_seq;

CREATE TABLE roles (
  id bigint check (id > 0) NOT NULL DEFAULT NEXTVAL ('roles_seq'),
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
)  ;

ALTER SEQUENCE roles_seq RESTART WITH 1;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE SEQUENCE user_role_seq;

CREATE TABLE user_role (
  id bigint check (id > 0) NOT NULL DEFAULT NEXTVAL ('user_role_seq'),
  user_id bigint check (user_id > 0) NOT NULL,
  role_id bigint check (role_id > 0) NOT NULL,
  PRIMARY KEY (id)
 ,
  CONSTRAINT fk_security_user_id FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_security_role_id FOREIGN KEY (role_id) REFERENCES roles (id)
)  ;

ALTER SEQUENCE user_role_seq RESTART WITH 1;

CREATE INDEX fk_security_user_id ON user_role (user_id);
CREATE INDEX fk_security_role_id ON user_role (role_id);

LOCK TABLES roles WRITE;
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO roles VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
UNLOCK TABLES;

-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO categories (name) VALUES (1, 'Category 1');