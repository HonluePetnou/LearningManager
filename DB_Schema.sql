-- =========================================================
--  Library Management ‑ SQLite schema
-- =========================================================

PRAGMA foreign_keys = ON;             -- enforce FK constraints

/* ---------- USERS ---------- */
CREATE TABLE users (
    user_id        INTEGER PRIMARY KEY AUTOINCREMENT,
    username       TEXT    NOT NULL UNIQUE,
    password_hash  TEXT    NOT NULL,
    role           TEXT    NOT NULL CHECK(role IN ('ADMIN','MEMBER')),
    created_at     DATE    DEFAULT (datetime('now'))
);

CREATE INDEX idx_users_username ON users(username);

/* ---------- BOOKS ---------- */
CREATE TABLE books (
    book_id           INTEGER PRIMARY KEY AUTOINCREMENT,
    title             TEXT    NOT NULL,
    author            TEXT    NOT NULL,
    isbn              TEXT    UNIQUE,
    category_id       INTEGER,
    published_year    INTEGER,
    copies_total      INTEGER NOT NULL DEFAULT 1 CHECK (copies_total > 0),
    copies_available  INTEGER NOT NULL DEFAULT 1 CHECK (copies_available >= 0),
    created_at        DATE    DEFAULT (datetime('now')),
    FOREIGN KEY (category_id) REFERENCES category(category_id) ON UPDATE CASCADE ON DELETE RESTRICT
);
CREATE INDEX idx_books_title ON books(title);

/* ---------- LOANS ---------- */
CREATE TABLE loans (
    loan_id      INTEGER PRIMARY KEY AUTOINCREMENT,
    book_id      INTEGER NOT NULL,
    user_id      INTEGER NOT NULL,
    borrowed_at  DATE    DEFAULT (datetime('now')),
    due_at       DATE,
    returned_at  DATE,
    status       TEXT    NOT NULL DEFAULT 'ONGOING'
                 CHECK(status IN ('ONGOING','RETURNED','OVERDUE')),
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE INDEX idx_loans_user_status ON loans(user_id, status);
CREATE INDEX idx_loans_book ON loans(book_id);

/* ---------- CATEGORY ---------- */
CREATE TABLE category (
    category_id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT
);
CREATE INDEX idx_category_name ON category(name);

-- =========================================================
--  Seed data
-- =========================================================
-- BCrypt hash for password "admin123"
INSERT INTO users (username, password_hash, role)
VALUES ('admin',
        '$2a$12$mSx4fNg1cqiXrHyCcSNqbO/vlv5k9yHp7jRTd/8x3y30eh3x9MJvS',
        'ADMIN');
