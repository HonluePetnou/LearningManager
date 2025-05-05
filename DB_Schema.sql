--
-- Fichier généré par SQLiteStudio v3.4.4 sur lun. mai 5 13:17:02 2025
--
-- Encodage texte utilisé : UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Tableau : books
CREATE TABLE IF NOT EXISTS books (
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

-- Tableau : category
CREATE TABLE IF NOT EXISTS category (
    category_id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT
);

-- Tableau : loans
CREATE TABLE IF NOT EXISTS loans (
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

-- Tableau : users
CREATE TABLE IF NOT EXISTS users (user_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, password_hash TEXT NOT NULL, role TEXT NOT NULL CHECK (role IN ('ADMIN', 'MEMBER')), created_at DATE DEFAULT (datetime('now')), phone INTEGER, birthdate TEXT, gender TEXT, address TEXT, email TEXT NOT NULL UNIQUE);


-- Index : idx_books_title
CREATE INDEX IF NOT EXISTS idx_books_title ON books(title);

-- Index : idx_category_name
CREATE INDEX IF NOT EXISTS idx_category_name ON category(name);

-- Index : idx_loans_book
CREATE INDEX IF NOT EXISTS idx_loans_book ON loans(book_id);

-- Index : idx_loans_user_status
CREATE INDEX IF NOT EXISTS idx_loans_user_status ON loans(user_id, status);

-- Index : idx_users_username
CREATE INDEX IF NOT EXISTS idx_users_username ON users (username);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
-- =========================================================
--  Seed data
-- =========================================================
-- BCrypt hash for password "admin123"
INSERT INTO users (user_id, username, password_hash, role, created_at, phone, birthdate, gender, address, email)
 VALUES (1, 'admin', '$2a$12$mSx4fNg1cqiXrHyCcSNqbO/vlv5k9yHp7jRTd/8x3y30eh3x9MJvS', 'ADMIN', '2025-04-25 20:22:30', NULL, NULL, NULL, NULL, 'admin123@gmail.com');