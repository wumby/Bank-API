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
('Joe', 'Harris', 30),
('Goran', 'Dragic', 35),
('Lamarcus', 'Aldridge', 36),
('Ben','Simmons', 25),
('Patty', 'Mills', 33);

INSERT INTO accounts (account_name, balance, client_id)
VALUES
('Checking', 90, 1),
('Savings', 95, 2),
('Savings', 85, 3),
('Brokerage', 97, 4),
('Checking', 80, 5);