/*

drop index idx_visit_type_name_upper;

*/

ALTER TABLE visit_type
    DROP CONSTRAINT IF EXISTS visit_type_name_key;

CREATE UNIQUE INDEX IF NOT EXISTS idx_visit_type_name_upper
    ON visit_type (UPPER(name));