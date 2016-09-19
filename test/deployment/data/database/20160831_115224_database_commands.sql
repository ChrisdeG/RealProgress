ALTER TABLE "realprogress$customer"
	ADD "message" VARCHAR_IGNORECASE(200) NULL;
INSERT INTO "mendixsystem$attribute" ("id", 
"entity_id", 
"attribute_name", 
"column_name", 
"type", 
"length", 
"default_value", 
"is_auto_number")
 VALUES ('c7e269a9-3a07-47db-8b63-ffc833cc060f', 
'1042ca31-4ddf-43f0-b257-ec99e84695b0', 
'Message', 
'message', 
30, 
200, 
'', 
false);
UPDATE "mendixsystem$version"
 SET "versionnumber" = '4.0.7', 
"lastsyncdate" = '20160831 11:52:07';
