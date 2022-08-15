CREATE TABLE IF NOT EXISTS item(
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created TIMESTAMP,
    done BOOLEAN
);

CREATE TABLE IF NOT EXISTS account(
    id SERIAL PRIMARY KEY,
    name TEXT,
    login TEXT,
    password TEXT
);

ALTER TABLE item
    ADD COLUMN account_id INT NOT NULL REFERENCES account(id);

CREATE TABLE IF NOT EXISTS category(
    id SERIAL PRIMARY KEY,
    name TEXT
);

INSERT INTO category(name)
VALUES
    ('Домашние дела'),
    ('Еда'),
    ('Здоровье'),
    ('Спорт'),
    ('рабочие дела');

create table item_category (
    Item_id int4 not null,
    categories_id int4 not null
);

alter table if exists item_category
    add foreign key (categories_id)
            references category;

alter table if exists item_category
    add foreign key (Item_id)
            references item;


