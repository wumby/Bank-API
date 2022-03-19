DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS clients;

CREATE TABLE clients (
	id SERIAL PRIMARY KEY,
	first_name VARCHAR(200) NOT NULL,
	last_name VARCHAR(200) NOT NULL,
	age INTEGER NOT NULL
);

CREATE TABLE accounts (
	id SERIAL PRIMARY KEY, -- PRIMARY KEY (implicitly UNIQUE and NOT NULL)
	account_name VARCHAR(9) NOT NULL,
	balance decimal(10,2), -- NOT NULL, DEFAULT, CHECK
	client_id INTEGER NOT NULL,

	CONSTRAINT fk_client FOREIGN KEY(client_id) REFERENCES clients(id) ON DELETE CASCADE -- FOREIGN KEY
);


-- DML queries
INSERT INTO clients (first_name, last_name, age)
VALUES
('Dwight', 'Schrute', 30),
('Toby', 'Flenderson', 35),
('Kevin', 'Malone', 36),
('Angela','Martin', 25),
('Pam', 'Beesly', 33);

INSERT INTO accounts (account_name, balance, client_id)
VALUES
('Checking', 90567, 1),
('Savings', 955, 1),
('Savings', 854, 2),
('Brokerage', 9767, 2),
('Checking', 80, 3),
('Savings', 9567, 3),
('Checking', 9556, 4),
('Savings', 85756, 4),
('Brokerage', 97087, 5),
('Checking', 80567, 5);
select clients.id,first_name,last_name,age,account_name ,balance
from clients
join accounts on (clients.id = client_id)
where clients.id = 2