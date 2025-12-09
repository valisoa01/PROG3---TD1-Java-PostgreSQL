CREATE TABLE Product(
    id_product serial PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price NUMERIC NOT NULL,
    creation_datetime TIMESTAMP
);
CREATE TABLE product_category(
    id_category serial PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    id_product int REFERENCES Product(id_product)
);