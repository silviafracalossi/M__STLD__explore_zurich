CREATE TABLE IF NOT EXISTS "train_station" (
    "stations_id" INT,
    "stations_name" TEXT,
    "stations_score" INT,
    "stations_coordinate_type" TEXT,
    "stations_coordinate_x" NUMERIC(8, 6),
    "stations_coordinate_y" NUMERIC(7, 6),
    "stations_distance" INT,
    "stations_icon" TEXT,
    "stations_geometry" TEXT,
    "stations_district_id" INT
);
INSERT INTO "train_station" VALUES
    (8503000,'Zürich HB',NULL,'WGS84',47.377847,8.540502,NULL,'train','POINT (47.377847 8.540502)',1),
    (8503006,'Zürich Oerlikon',NULL,'WGS84',47.411526,8.54414,NULL,'train','POINT (47.411526 8.544140000000001)',11),
    (8503020,'Zürich Hardbrücke',NULL,'WGS84',47.385087,8.517686,NULL,NULL,'POINT (47.385087 8.517685999999999)',4),
    (8503003,'Zürich Stadelhofen',NULL,'WGS84',47.366607,8.548492,NULL,NULL,'POINT (47.366607 8.548492)',1),
    (8503001,'Zürich Altstetten',NULL,'WGS84',47.391478,8.488966,NULL,'train','POINT (47.391478 8.488966)',9),
    (8576193,'Zürich, Bellevue',NULL,'WGS84',47.367089,8.545112,NULL,'tram','POINT (47.367089 8.545112)',1),
    (8591299,'Zürich, Paradeplatz',NULL,'WGS84',47.36973,8.538918,NULL,'tram','POINT (47.36973 8.538918000000001)',1),
    (8588078,'Zürich, Central',NULL,'WGS84',47.376842,8.543937,NULL,'tram','POINT (47.376842 8.543937)',1),
    (8580522,'Zürich, Escher-Wyss-Platz',NULL,'WGS84',47.390791,8.522398,NULL,'tram','POINT (47.390791 8.522398000000001)',5);
