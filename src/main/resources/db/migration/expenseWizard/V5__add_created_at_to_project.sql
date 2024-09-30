alter table expensewizard.project add column if not exists
    created_at  TIMESTAMP  NOT NULL default now();