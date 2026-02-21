CREATE TABLE IF NOT EXISTS artists (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    bio TEXT,
    image_url TEXT,
    genre_id BIGINT,
    country VARCHAR(100),
    language VARCHAR(100),
    followers_count BIGINT DEFAULT 0,
    current_stars INTEGER DEFAULT 0,
    goal_stars INTEGER DEFAULT 100
);

CREATE TABLE IF NOT EXISTS genres (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);
