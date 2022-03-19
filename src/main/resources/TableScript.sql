-- Table: visit_request

-- DROP TABLE visit_request;

CREATE TABLE visit_request
(
  id serial NOT NULL,
  visitor_talent_id character varying(20) NOT NULL,
  host_talent_id character varying(20) NOT NULL,
  visit_starttime timestamp without time zone,
  visit_endtime timestamp without time zone,
  visit_place character varying(20),
  purpose_of_visit character varying(20),
  parking_required character varying(5),
  vehical_no character varying(100),
  no_of_people numeric,
  baggage character varying(100),
  remarks character varying(1000),
  visitor_name1 character varying(20),
  visitor_name2 character varying(20),
  visitor_name3 character varying(20),
  visitor_name4 character varying(20),
  visitor_name5 character varying(20),
  visitor_name6 character varying(20),
  visitor_name7 character varying(20),
  visitor_name8 character varying(20),
  visitor_name9 character varying(20),
  visitor_name10 character varying(20),
  created_date timestamp without time zone,
  created_by character varying(20),
  updated_date timestamp without time zone,
  updated_by character varying(20),
  cancelled_date timestamp without time zone,
  cancelled_by character varying(20),
  approve_by character varying(20),
  approve_date timestamp without time zone,
  image character varying(200),
  CONSTRAINT visit_request_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE visit_request
  OWNER TO postgres;
