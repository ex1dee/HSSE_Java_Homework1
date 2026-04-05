CREATE TABLE IF NOT EXISTS task_attachments (
    id UUID PRIMARY KEY,
    task_id UUID NOT NULL,
    filename VARCHAR(255) NOT NULL,
    stored_filename VARCHAR(255) NOT NULL,
    content_type VARCHAR(100),
    uploaded_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    size BIGINT,

    CONSTRAINT fk_task
        FOREIGN KEY(task_id)
        REFERENCES tasks(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_attachments_task_id ON task_attachments(task_id);