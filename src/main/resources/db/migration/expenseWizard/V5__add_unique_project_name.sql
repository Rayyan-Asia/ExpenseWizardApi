ALTER TABLE project
    ADD CONSTRAINT uc_project_name UNIQUE (project_name);
