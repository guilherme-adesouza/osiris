ALTER TABLE embedded DROP COLUMN IF EXISTS motor_status;
ALTER TABLE embedded ADD COLUMN motor_status boolean default false;