
CREATE TABLE Coins (
    coin_id INT AUTO_INCREMENT PRIMARY KEY,
    coin_value INT,
    quantity_available INT
);

CREATE TABLE Products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    price DECIMAL(10, 2),
    quantity_available INT,
    threshold INT
);

CREATE TABLE Reports (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    report_date TIMESTAMP,
    total_income DECIMAL(10, 2),
    items_sold INT,
    total_coins_income DECIMAL(10, 2)
);

CREATE TABLE Transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    report_id INT,
    product_id INT,
    coin_id INT,
    transaction_time TIMESTAMP,
    amount_paid DECIMAL(10, 2),
    change_returned DECIMAL(10, 2),
    canceled BOOLEAN,
    FOREIGN KEY (report_id) REFERENCES Reports(report_id),
    FOREIGN KEY (product_id) REFERENCES Products(product_id),
    FOREIGN KEY (coin_id) REFERENCES Coins(coin_id)
);

CREATE TABLE Income (
    income_id INT PRIMARY KEY AUTO_INCREMENT,
    transaction_id INT,
    income_amount DECIMAL(10, 2),
    FOREIGN KEY (transaction_id) REFERENCES Transactions(transaction_id)
);
