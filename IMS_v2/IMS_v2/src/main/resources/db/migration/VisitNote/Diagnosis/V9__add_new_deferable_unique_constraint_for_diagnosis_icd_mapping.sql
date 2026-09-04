/*
-- Diagnosis ICD Mapping
drop index if exists idx_diagnosis_icd_mapping_diagnosis_id_icd_id;

*/

ALTER TABLE diagnosis_icd_mapping
DROP CONSTRAINT IF EXISTS uk_diagnosis_icd_mapping_diagnosis_id_seq;

ALTER TABLE diagnosis_icd_mapping
DROP CONSTRAINT IF EXISTS uk_diagnosis_icd_mapping_diagnosis_id_seq_deferrable;

ALTER TABLE diagnosis_icd_mapping
    ADD CONSTRAINT uk_diagnosis_icd_mapping_diagnosis_id_seq_deferrable UNIQUE(diagnosis_id, seq)
    DEFERRABLE INITIALLY DEFERRED;
