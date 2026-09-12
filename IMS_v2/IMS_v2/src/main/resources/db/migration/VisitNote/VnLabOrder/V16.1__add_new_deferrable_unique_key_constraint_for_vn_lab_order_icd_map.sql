/*
-- VnLabOrder Icd Map
drop constraint if exists idx_vn_lab_order_icd_map_vn_lab_order_id_desc;
drop constraint if exists idx_vn_lab_order_icd_map_vn_lab_order_id_desc_deferrable;
*/

ALTER TABLE vn_lab_order_icd_map
    DROP CONSTRAINT IF EXISTS uk_vn_lab_order_icd_map_vn_lab_order_id_seq;

ALTER TABLE vn_lab_order_icd_map
    DROP CONSTRAINT IF EXISTS uk_vn_lab_order_icd_map_vn_lab_order_id_seq_deferrable;

ALTER TABLE vn_lab_order_icd_map
    ADD CONSTRAINT uk_vn_lab_order_icd_map_vn_lab_order_id_seq_deferrable UNIQUE (vn_lab_order_id, seq)
    DEFERRABLE INITIALLY DEFERRED;
