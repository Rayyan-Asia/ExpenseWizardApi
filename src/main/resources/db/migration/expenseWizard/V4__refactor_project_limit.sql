-- Add a unique constraint for the compound key (user_id, project_name)
ALTER TABLE project
    CHANGE COLUMN `limit` project_limit FLOAT;