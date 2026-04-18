CREATE TABLE IF NOT EXISTS favorite_tasks (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    task_id UUID NOT NULL,

    CONSTRAINT uk_user_task UNIQUE (user_id, task_id),

    CONSTRAINT fk_favorite_task
    FOREIGN KEY(task_id)
    REFERENCES tasks(id)
    ON DELETE CASCADE
);

CREATE INDEX idx_favorites_user_id ON favorite_tasks(user_id);