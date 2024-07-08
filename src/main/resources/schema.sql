-- Drop existing tables and sequence if they exist
DROP TABLE IF EXISTS "books";
DROP TABLE IF EXISTS "authors";
DROP SEQUENCE IF EXISTS authors_id_seq;

-- Create sequence for author IDs
CREATE SEQUENCE authors_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

-- Create authors table with the new sequence
CREATE TABLE "authors" (
                           "id" bigint DEFAULT nextval('authors_id_seq') NOT NULL,
                           "name" text,
                           "age" integer,
                           CONSTRAINT "authors_pkey" PRIMARY KEY ("id")
);

-- Create books table
CREATE TABLE "books" (
                         "isbn" text NOT NULL,
                         "title" text,
                         "author_id" bigint,
                         CONSTRAINT "books_pkey" PRIMARY KEY ("isbn"),
                         CONSTRAINT "books_author_id_fkey" FOREIGN KEY ("author_id") REFERENCES "authors" ("id") NOT DEFERRABLE
);
