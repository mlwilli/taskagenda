INSERT INTO tasks (title, description, status, priority, due_date, created_at, updated_at)
VALUES ('Review pull requests',
        'Check open pull requests in the main repository.',
        'OPEN',
        'HIGH',
        CURRENT_DATE + 1,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

INSERT INTO tasks (title, description, status, priority, due_date, created_at, updated_at)
VALUES ('Refactor task service',
        'Improve transaction boundaries and error handling.',
        'IN_PROGRESS',
        'MEDIUM',
        CURRENT_DATE + 7,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

INSERT INTO tasks (title, description, status, priority, due_date, created_at, updated_at)
VALUES ('Prepare release notes',
        'Summarize changes for the next minor release.',
        'COMPLETED',
        'LOW',
        CURRENT_DATE - 1,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);
