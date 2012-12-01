CREATE TABLE "archivo" (
"id_arc" int4 NOT NULL,
"nombre" text NOT NULL,
"id_cat" int4 NOT NULL,
"id_des" int4 NOT NULL,
CONSTRAINT "archivo_pkey" PRIMARY KEY ("id_arc") 
);

CREATE TABLE "categoria" (
"id_cat" int4 NOT NULL,
"nombre" text NOT NULL,
CONSTRAINT "categoria_pkey" PRIMARY KEY ("id_cat") 
);

CREATE TABLE "desarrollo" (
"id_des" int4 NOT NULL,
"descrip" text NOT NULL,
"id_eve" int4 NOT NULL,
CONSTRAINT "desarrollo_pkey" PRIMARY KEY ("id_des") 
);

CREATE TABLE "evento" (
"id_eve" int4 NOT NULL,
"descrip" text NOT NULL,
"reg_hora" time(6),
"reg_fecha" date,
"fecha" date,
"id_top" int4 NOT NULL,
"id_us" int4 NOT NULL,
CONSTRAINT "evento_pkey" PRIMARY KEY ("id_eve") 
);

CREATE TABLE "men_url" (
"id_men" int4 NOT NULL,
"id_url" int4 NOT NULL,
CONSTRAINT "men_url_pkey" PRIMARY KEY ("id_men", "id_url") 
);

CREATE TABLE "menu" (
"id_men" int4 NOT NULL,
"nombre" text NOT NULL,
CONSTRAINT "menu_pkey" PRIMARY KEY ("id_men") 
);

CREATE TABLE "poligono" (
"gid" int4 NOT NULL,
"the_geom" "public"."geometry",
"id_eve" int4,
CONSTRAINT "poligono_pkey" PRIMARY KEY ("gid") 
);

CREATE TABLE "punto" (
"gid" int4 NOT NULL,
"the_geom" "public"."geometry",
"id_eve" int4,
CONSTRAINT "punto_pkey" PRIMARY KEY ("gid") 
);

CREATE TABLE "rol" (
"id_rol" int4 NOT NULL,
"nombre" text NOT NULL,
CONSTRAINT "rol_pkey" PRIMARY KEY ("id_rol") 
);

CREATE TABLE "rol_men" (
"id_rol" int4 NOT NULL,
"id_men" int4 NOT NULL,
CONSTRAINT "rol_men_pkey" PRIMARY KEY ("id_rol", "id_men") 
);

CREATE TABLE "spatial_ref_sys" (
"srid" int4 NOT NULL,
"auth_name" varchar(256),
"auth_srid" int4,
"srtext" varchar(2048),
"proj4text" varchar(2048),
CONSTRAINT "spatial_ref_sys_pkey" PRIMARY KEY ("srid") 
);

CREATE TABLE "topico" (
"id_top" int4 NOT NULL,
"nombre" text NOT NULL,
CONSTRAINT "topico_pkey" PRIMARY KEY ("id_top") 
);

CREATE TABLE "url" (
"id_url" int4 NOT NULL,
"nombre" text NOT NULL,
"enlace" text NOT NULL,
CONSTRAINT "url_pkey" PRIMARY KEY ("id_url") 
);

CREATE TABLE "usuario" (
"id_us" int4 NOT NULL,
"ci" int4,
"nombre" text NOT NULL,
"ap" text,
"am" text,
"id_rol" int4 NOT NULL,
"direcc" text,
"telf" int4,
CONSTRAINT "usuario_pkey" PRIMARY KEY ("id_us") 
);


ALTER TABLE "archivo" ADD CONSTRAINT "archivo_id_arc_fkey1" FOREIGN KEY ("id_arc") REFERENCES "evento" ("id_eve");
ALTER TABLE "archivo" ADD CONSTRAINT "archivo_id_arc_fkey" FOREIGN KEY ("id_arc") REFERENCES "categoria" ("id_cat");
ALTER TABLE "desarrollo" ADD CONSTRAINT "desarrollo_id_eve_fkey" FOREIGN KEY ("id_eve") REFERENCES "evento" ("id_eve");
ALTER TABLE "evento" ADD CONSTRAINT "evento_id_us_fkey" FOREIGN KEY ("id_us") REFERENCES "usuario" ("id_us");
ALTER TABLE "evento" ADD CONSTRAINT "evento_id_top_fkey" FOREIGN KEY ("id_top") REFERENCES "topico" ("id_top");
ALTER TABLE "men_url" ADD CONSTRAINT "men_url_id_url_fkey" FOREIGN KEY ("id_url") REFERENCES "url" ("id_url");
ALTER TABLE "men_url" ADD CONSTRAINT "men_url_id_men_fkey" FOREIGN KEY ("id_men") REFERENCES "menu" ("id_men");
ALTER TABLE "poligono" ADD CONSTRAINT "poligono_id_eve_fkey" FOREIGN KEY ("id_eve") REFERENCES "evento" ("id_eve");
ALTER TABLE "punto" ADD CONSTRAINT "punto_id_eve_fkey" FOREIGN KEY ("id_eve") REFERENCES "evento" ("id_eve");
ALTER TABLE "rol_men" ADD CONSTRAINT "rol_men_id_rol_fkey" FOREIGN KEY ("id_rol") REFERENCES "rol" ("id_rol");
ALTER TABLE "rol_men" ADD CONSTRAINT "rol_men_id_men_fkey" FOREIGN KEY ("id_men") REFERENCES "menu" ("id_men");
ALTER TABLE "usuario" ADD CONSTRAINT "usuario_id_rol_fkey" FOREIGN KEY ("id_rol") REFERENCES "rol" ("id_rol");

