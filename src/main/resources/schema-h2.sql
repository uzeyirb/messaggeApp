CREATE TABLE IF NOT EXISTS CONTACT_MESSAGE (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    messageTime DATE NOT NULL,
    timeZone TIME NOT NULL
);



INSERT INTO Contact_Message (name, email, subject, message, messageTime, timeZone)
VALUES ('Govhar Doe', 'johndoe@example.com', 'Hello', 'This is a test message', '2023-10-27', '10:30:00');
INSERT INTO Contact_Message (name, email, subject, message, messageTime, timeZone)
VALUES ('Zuzik Doe', 'johndoe@example.com', 'Hello', 'This is a test message', '2023-10-27', '10:30:00');
INSERT INTO Contact_Message (name, email, subject, message, messageTime, timeZone)
VALUES ('Muzik Doe', 'johndoe@example.com', 'Hello', 'This is a test message', '2023-10-27', '10:30:00');
INSERT INTO Contact_Message (name, email, subject, message, messageTime, timeZone)
VALUES ('John Doe', 'johndoe@example.com', 'Hello', 'This is a test message', '2023-10-27', '10:30:00');
