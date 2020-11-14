ALTER TABLE data DROP COLUMN IF EXISTS motor_status;
ALTER TABLE data ADD COLUMN motor_status integer default 0;