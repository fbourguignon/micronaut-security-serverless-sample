CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE public.tb_user (
	id INT8 NOT NULL,
	email VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	uuid UUID NOT NULL,
	CONSTRAINT tb_user_pkey PRIMARY KEY (id ASC),
	UNIQUE INDEX uk_4vih17mube9j7cqyjlfbcrk4m (email ASC)
);

CREATE TABLE public.tb_role (
	id INT8 NOT NULL,
	type VARCHAR(60) NULL,
	CONSTRAINT tb_role_pkey PRIMARY KEY (id ASC),
	UNIQUE INDEX uk_of3cwwmbes1eollg68rx6h22p (type ASC)
);

CREATE TABLE public.tb_user_role (
	tb_user_id INT8 NOT NULL,
	tb_role_id INT8 NOT NULL,
	CONSTRAINT tb_user_role_pkey PRIMARY KEY (tb_user_id ASC, tb_role_id ASC),
	CONSTRAINT fkmew46kle22413jg5fwgsrqf8m FOREIGN KEY (tb_role_id) REFERENCES public.tb_role(id),
	CONSTRAINT fkbb47wlqy20p9ibpd15dhlgi83 FOREIGN KEY (tb_user_id) REFERENCES public.tb_user(id)
);

INSERT INTO public.tb_role (id, "type") VALUES(1, 'ROLE_USER');
INSERT INTO public.tb_role(id, "type")VALUES(2, 'ROLE_ADMIN');
