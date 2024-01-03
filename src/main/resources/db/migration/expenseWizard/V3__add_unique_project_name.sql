-- Add a unique constraint for the compound key (user_id, project_name)
ALTER TABLE project
    ADD CONSTRAINT uc_user_project UNIQUE (user_id, project_name);