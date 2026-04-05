CREATE TABLE IF NOT EXISTS tasks (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    due_date DATE,
    priority VARCHAR(50),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    completed BOOLEAN DEFAULT FALSE NOT NULL,
    tags JSONB DEFAULT '[]'::jsonb
);

CREATE INDEX idx_tasks_priority ON tasks(priority);
CREATE INDEX idx_tasks_tags ON tasks USING gin(tags);
CREATE INDEX idx_task_due_date ON tasks(due_date);