INSERT INTO users (id, email, first_name, last_name, password, role) VALUES (1, 'customer@example.com', 'Bob', 'Alison', '$2a$10$9X1tL3P6nzkcPS1hzGXmvenxXLN9SJPjMYbxM8t/FsdoFF3Neu9CO', 'ROLE_CUSTOMER');

INSERT INTO users (id, email, first_name, last_name, password, role) VALUES (2, 'manager@example.com', 'Manager', 'Manager', '$2a$10$NF2dGFBdX/3n4CbRQq4gqe4IX1YUiEhoUvQUT3g4.45h79PDlqV0m', 'ROLE_MANAGER');

INSERT INTO amenities (id, name) VALUES (1, 'WIFI');

INSERT INTO amenities (id, name) VALUES (2, 'PARKING');

INSERT INTO addresses (id, address) VALUES (1, 'City, Street, 21');

INSERT INTO addresses (id, address) VALUES (2, 'City, Street, 18');

INSERT INTO accommodations (id, type, address_id, size, daily_rate, availability) VALUES (1, 'APARTMENT', 1, 'TWO_BEDROOM', 100.00, 1);

INSERT INTO accommodations (id, type, address_id, size, daily_rate, availability) VALUES (2, 'APARTMENT', 2, 'TWO_BEDROOM', 150.00, 2);

INSERT INTO accommodations_amenities (accommodation_id, amenity_id) VALUES (1, 1);

INSERT INTO accommodations_amenities (accommodation_id, amenity_id) VALUES (1, 2);

INSERT INTO accommodations_amenities (accommodation_id, amenity_id) VALUES (2, 1);

INSERT INTO accommodations_amenities (accommodation_id, amenity_id) VALUES (2, 2);