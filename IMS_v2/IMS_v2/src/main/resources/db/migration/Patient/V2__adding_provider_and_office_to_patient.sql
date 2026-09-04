/*

alter table if exists patient_mst_sb drop column office_id;
alter table if exists patient_mst_sb drop column provider_id;

*/

DO $$
DECLARE
    v_default_office_id BIGINT;
	v_default_provider_id BIGINT;
BEGIN

    SELECT
        tran_id,
        office_id
    into
        v_default_provider_id,
        v_default_office_id
    from
        doctor_mst_sb
    order by
        tran_id
        limit 1;

    RAISE NOTICE 'The fetched default office_Id is: %', v_default_office_id;
	RAISE NOTICE 'The fetched default provider_id is: %', v_default_provider_id;

	if not exists (select 1 from information_schema.columns where table_name like 'patient_mst_sb' and column_name = 'office_id' limit 1) then
		RAISE NOTICE 'Added office_id column in patient_mst_sb table';
        execute format(
            'ALTER TABLE IF EXISTS patient_mst_sb
                ADD COLUMN office_id bigint not null default %L
                constraint fk_patients_office references office_mst_sb(tran_id)',
        v_default_office_id);
    end if;

	if not exists (select 1 from information_schema.columns where table_name like 'patient_mst_sb' and column_name = 'provider_id' limit 1) then
		RAISE NOTICE 'Added provider_id column in patient_mst_sb table';
        execute format(
            'ALTER TABLE IF EXISTS patient_mst_sb
                ADD COLUMN provider_id bigint not null default %L
                constraint fk_patients_provider references doctor_mst_sb(tran_id);',
            v_default_provider_id);
        end if;

END $$;