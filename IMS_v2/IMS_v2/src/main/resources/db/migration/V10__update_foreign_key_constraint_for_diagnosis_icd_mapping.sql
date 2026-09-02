/*
-- Diagnosis ICD Mapping
drop index if exists fk_diagnosis_icd_mapping_diagnosis_mst_tran_id;

*/

alter table diagnosis_icd_mapping
drop constraint if exists fk_diagnosis_icd_mapping_diagnosis_mst_tran_id;

alter table diagnosis_icd_mapping
    add constraint fk_diagnosis_icd_mapping_diagnosis_mst_tran_id
    foreign key (diagnosis_id)
    references diagnosis_mst(tran_id)
    on delete cascade;
