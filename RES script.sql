DROP TABLE employees;
DROP TABLE tickets;

CREATE TABLE employees (
	email VARCHAR(30) PRiMARY KEY,
	pass VARCHAR(30) NOT NULL,
	emp_role VARCHAR(30) NOT NULL
	
);

CREATE TABLE tickets(
	ticket_id SERIAL PRIMARY KEY CHECK(ticket_id>0),
	amount FLOAT NOT NULL  CHECK(amount>0) ,
	description VARCHAR(50) NOT NULL,
	status VARCHAR(20) NOT NULL DEFAULT 'Pending',
	email VARCHAR(30) NOT NULL,
	reinType VARCHAR(20),
	FOREIGN KEY(email) REFERENCES employees(email)
);


INSERT INTO employees  (email, pass, emp_role) VALUES ('123@gmail.com','Password1','manager');

INSERT INTO employees  (email, pass, emp_role) VALUES ('1234@gmail.com','Password1','employee');

INSERT INTO tickets (amount, description, status, email, reintype) VALUES (300, 'test1', 'pending', '1234@gmail.com', 'travel');
INSERT INTO tickets (amount, description, status, email, reintype) VALUES (400, 'test2', 'pending', '1234@gmail.com', 'travel');
INSERT INTO tickets (amount, description, status, email, reintype) VALUES (99, 'test3', 'pending', '1234@gmail.com', 'food');
