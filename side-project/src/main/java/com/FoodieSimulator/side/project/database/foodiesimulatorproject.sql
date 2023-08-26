BEGIN TRANSACTION;

DROP TABLE IF EXISTS member, restaurant, rating, cuisine, restaurant_rating, restaurant_cuisine;

CREATE TABLE member(
    member_id serial,
    first_name varchar(50) NOT NULL,
    last_name (50) NOT NULL,
    email_address(50) NOT NULL,
    CONSTRAINT PK_member PRIMARY KEY(member_id)
);

CREATE TABLE restaurant (

    restaurant_id serial,
    name varchar(50) NOT NULL,
    address_1 varchar(50),
    address_2 varchar (50),
    city varchar (50) NOT NULL,
    state varchar (2) NOT NULL,
    zip_code varchar (5),
    cuisine_id int NOT NULL,
    CONSTRAINT PK_restaurant PRIMARY KEY(restaurant_id),
    CONSTRAINT FK_restaurant_cuisine FOREIGN KEY (cuisine_id) REFERENCES cuisine(cuisine_id)
);

CREATE TABLE rating (

    rating_id serial,
    is_thumbs_up boolean NOT NULL,
    is_thumbs_down boolean NOT NULL,
    CONSTRAINT PK_ratings PRIMARY KEY(rating_id)
);

CREATE TABLE cuisine (

    cuisine_id serial,
    name varchar (50),
    CONSTRAINT PK_cuisine PRIMARY KEY(cuisine_id),
    CONSTRAINT UQ_cuisine_name UNIQUE(name)
);

CREATE TABLE restaurant_rating (
    rating_id int NOT NULL,
    restaurant_id int NOT NULL,
    CONSTRAINT PK_restaurant_rating PRIMARY KEY (rating_id, restaurant_id),
    CONSTRAINT FK_restaurant_rating_rating FOREIGN KEY (rating_id) REFERENCES rating(rating_id),
    CONSTRAINT FK_restaurant_rating_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant(restaurant_id)
);

CREATE TABLE restaurant_cuisine (
    restaurant_id int NOT NULL,
    cuisine_id int NOT NULL;
    CONSTRAINT PK_restaurant_cuisine PRIMARY KEY (restaurant_id, cuisine_id),
    CONSTRAINT FK_restaurant_cuisine_cuisine FOREIGN KEY (cuisine_id) REFERENCES cuisine(cuisine_id),
    CONSTRAINT FK_restaurant_cuisine_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant(restaurant_id)
);


COMMIT TRANSACTION;



