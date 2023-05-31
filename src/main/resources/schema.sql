CREATE TABLE EXPENSE(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    description varchar(100),
    date date,
    price decimal(10,2),
    category varchar(100)
);