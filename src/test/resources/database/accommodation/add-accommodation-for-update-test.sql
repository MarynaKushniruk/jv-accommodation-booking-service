INSERT INTO addresses (id, address) VALUES (1, 'City, Street, 21');
INSERT INTO addresses (id, address) VALUES (2, 'City, Street, 18');

INSERT INTO amenities (id, name) VALUES (1, 'BIG WINDOW');
INSERT INTO amenities (id, name) VALUES (2, 'PARKING');

INSERT INTO accommodations (id, type, address_id, size, daily_rate, availability) VALUES (1, 'APARTMENT', 2, 'TWO_BEDROOM', 100.00, 1);
INSERT INTO accommodations_amenities (accommodation_id, amenity_id) VALUES (1, 2);


