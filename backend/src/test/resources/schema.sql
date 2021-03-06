CREATE TABLE IF NOT EXISTS group_table (
    user_group INT AUTO_INCREMENT PRIMARY KEY,
    group_name VARCHAR(36) NOT NULL
);

CREATE TABLE IF NOT EXISTS category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(36) NOT NULL
);

CREATE TABLE IF NOT EXISTS manufacturer (
    manufacturer_id INT AUTO_INCREMENT PRIMARY KEY,
    manufacturer_name VARCHAR(36) NOT NULL,
    manufacturer_info VARCHAR(255),
    manufacturer_logo VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_group INT,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255),
    phone_number VARCHAR(255),
    FOREIGN KEY (user_group)
        REFERENCES group_table(user_group)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    category_id INT,
    manufacturer_id INT,
    short_info VARCHAR(255),
    cost INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (category_id)
        REFERENCES category(category_id)
        ON DELETE CASCADE,
    FOREIGN KEY (manufacturer_id)
        REFERENCES manufacturer(manufacturer_id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS product_image (
    product_id INT,
    product_image VARCHAR(255),
    FOREIGN KEY (product_id)
        REFERENCES product(product_id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS order_status (
    order_status_id INT AUTO_INCREMENT PRIMARY KEY,
    order_status_name VARCHAR(36)
);

CREATE TABLE IF NOT EXISTS delivery (
    delivery_id INT AUTO_INCREMENT PRIMARY KEY,
    delivery_name VARCHAR(36)
);

CREATE TABLE IF NOT EXISTS payment (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    payment_name VARCHAR(36)
);

CREATE TABLE IF NOT EXISTS payment_status (
    payment_status_id INT AUTO_INCREMENT PRIMARY KEY,
    payment_status_name VARCHAR(36)
);

CREATE TABLE IF NOT EXISTS order_table (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id_customer INT,
    user_id_manager INT,
    order_status_id INT,
    delivery_id INT,
    payment_id INT,
    payment_status_id INT,
    date_opened DATE NOT NULL,
    date_finished DATE,
    user_comment VARCHAR(255),
    total_cost INT NOT NULL,
    FOREIGN KEY (user_id_customer)
        REFERENCES user(user_id)
        ON DELETE CASCADE,
    FOREIGN KEY (user_id_manager)
        REFERENCES user(user_id)
        ON DELETE CASCADE,
    FOREIGN KEY (order_status_id)
        REFERENCES order_status(order_status_id)
        ON DELETE CASCADE,
    FOREIGN KEY (delivery_id)
        REFERENCES delivery(delivery_id)
        ON DELETE CASCADE,
    FOREIGN KEY (payment_id)
        REFERENCES payment(payment_id)
        ON DELETE CASCADE,
    FOREIGN KEY (payment_status_id)
        REFERENCES payment_status(payment_status_id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS order_info (
    order_id INT,
    product_id INT,
    amount INT NOT NULL,
    FOREIGN KEY (order_id)
        REFERENCES order_table(order_id)
        ON DELETE CASCADE,
    FOREIGN KEY (product_id)
        REFERENCES product(product_id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bucketItem (
    user_id INT,
    product_id INT,
    amount INT NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES user(user_id)
        ON DELETE CASCADE,
    FOREIGN KEY (product_id)
        REFERENCES product(product_id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS product_comment (
    product_id INT,
    user_id INT,
    product_comment VARCHAR(255) NOT NULL,
    comment_date DATE NOT NULL,
    FOREIGN KEY (product_id)
        REFERENCES product(product_id)
        ON DELETE CASCADE,
    FOREIGN KEY (user_id)
        REFERENCES user(user_id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS confirmation_token (
    user_id INT,
    token VARCHAR(36) NOT NULL,
    FOREIGN KEY (user_id)
            REFERENCES user(user_id)
            ON DELETE CASCADE
);

INSERT INTO group_table VALUES(NULL, 'Admin');
INSERT INTO category VALUES(NULL, 'Writing tools'), (NULL, 'Paper');
INSERT INTO manufacturer VALUES(NULL, 'RusTools', NULL, NULL), (NULL, 'EngTools', NULL, NULL);
INSERT INTO user VALUES(NULL, 'admin@mail.ru', 'qwerty', 1, 'admin', 'admin', NULL, NULL);
INSERT INTO user VALUES(NULL, 'admin2@mail.ru', 'qwerty', 1, 'admin', 'admin', NULL, NULL);
INSERT INTO product VALUES(NULL, 'Pen', 1, 1, NULL, 50, 10), (NULL, 'A4', 2, 2, NULL, 100, 5);
INSERT INTO bucketItem VALUES(1, 1, 10), (1, 2, 5);
INSERT INTO bucketItem VALUES(2, 1, 20), (2, 2, 100);