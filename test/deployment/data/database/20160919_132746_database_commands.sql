ALTER TABLE "realprogress$customer" RENAME TO "demo$customer";
UPDATE "mendixsystem$entity"
 SET "entity_name" = 'Demo.Customer', 
"table_name" = 'demo$customer', 
"superentity_id" = NULL
 WHERE "id" = '1042ca31-4ddf-43f0-b257-ec99e84695b0';
UPDATE "mendixsystem$version"
 SET "versionnumber" = '4.0.7', 
"lastsyncdate" = '20160919 13:27:44';
