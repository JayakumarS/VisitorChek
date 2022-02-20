-- Table: roles

-- DROP TABLE roles;

CREATE TABLE roles
(
  id serial NOT NULL,
  name character varying(20),
  CONSTRAINT roles_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE roles
  OWNER TO postgres;

INSERT INTO roles (id, name) values(1,'ROLE_ADMIN');
INSERT INTO roles (id, name) values(1,'ROLE_USER');
  -- Table: user_roles

-- DROP TABLE user_roles;

CREATE TABLE user_roles
(
  user_id bigint NOT NULL,
  role_id integer NOT NULL,
  CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
  CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id)
      REFERENCES roles (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_roles
  OWNER TO postgres;
  
  -- Table: users

-- DROP TABLE users;

CREATE TABLE users
(
  id bigserial NOT NULL,
  email character varying(50),
  password character varying(120),
  username character varying(20),
  mobilenumber character varying(12),
  usertype character varying(100),
  image bytea,
  CONSTRAINT users_pkey PRIMARY KEY (id),
  CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
  CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE users
  OWNER TO postgres;
  
  -- Table: visit_request

-- DROP TABLE visit_request;

CREATE TABLE visit_request
(
  id serial NOT NULL,
  talentid character varying(20) NOT NULL,
  visitor_id character varying(20),
  host_id character varying(20),
  visit_starttime timestamp without time zone,
  visit_endtime timestamp without time zone,
  visit_place character varying(20),
  purpose character varying(20),
  parking_required character varying(5),
  vehical_no character varying(100),
  no_of_people numeric,
  baggage character varying(100),
  created_date timestamp without time zone,
  created_by character varying(20),
  updated_date timestamp without time zone,
  updated_by character varying(20),
  cancelled_date timestamp without time zone,
  cancelled_by character varying(20),
  approve_by character varying(20),
  approve_date timestamp without time zone,
  remarks character varying(1000),
  CONSTRAINT visit_request_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE visit_request
  OWNER TO postgres;
  