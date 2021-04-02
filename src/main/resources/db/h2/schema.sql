DROP TABLE comments IF EXISTS;
DROP INDEX comments_game_id IF EXISTS;

CREATE TABLE comments (
   id VARCHAR(255) NOT NULL PRIMARY KEY,
   text VARCHAR(500) NOT NULL,
   modified_date TIMESTAMP WITH TIME ZONE NOT NULL,
   game_id BIGINT NOT NULL
);

CREATE INDEX comments_game_id ON comments (game_id);
