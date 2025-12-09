INSERT INTO Product(name, price, creation_datetime)
VALUES('Laptop Dell XPS', 4500.00, NOW()),
('Smartphone Samsung', 499.50, NOW()),
('Casque Sony WH1000', 890.50, NOW()),
('Clavier Logitech', 180.00, NOW()),
('Ecran Samsung27"', 1200.00, NOW());

INSERT INTO Product_category (name, id_product)
VALUES
    ('Informatique', 1),
    ('Téléphonie', 2),
    ('Audio', 3),
    ('Accessoires', 4),
    ('Informatique', 5),
    ('Bureau', 5),
    ('Mobile', 2);