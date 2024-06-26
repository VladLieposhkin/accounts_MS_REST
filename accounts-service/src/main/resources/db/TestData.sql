INSERT INTO t_coin (c_code, c_name, c_created_at, c_updated_at, c_status) VALUES ('90', 'BITCOIN', '2024-06-01', '2024-06-01', 'ACTIVE');
INSERT INTO t_coin (c_code, c_name, c_created_at, c_updated_at, c_status) VALUES ('80', 'ETHEREUM', '2024-06-01', '2024-06-01', 'ACTIVE');
INSERT INTO t_coin (c_code, c_name, c_created_at, c_updated_at, c_status) VALUES ('89', 'STELLAR', '2024-06-01', '2024-06-01', 'ACTIVE');
INSERT INTO t_coin (c_code, c_name, c_created_at, c_updated_at, c_status) VALUES ('1', 'LITECOIN', '2024-06-01', '2024-06-01', 'ACTIVE');
INSERT INTO t_coin (c_code, c_name, c_created_at, c_updated_at, c_status) VALUES ('2713', 'TRON', '2024-06-01', '2024-06-01', 'ACTIVE');

INSERT INTO t_client (c_name, c_email, c_created_at, c_updated_at, c_status) VALUES ('Client_1', 'c1@mail.com', '2024-06-01', '2024-06-01', 'ACTIVE');
INSERT INTO t_client (c_name, c_email, c_created_at, c_updated_at, c_status) VALUES ('Client_2', 'c2@mail.com', '2024-06-01', '2024-06-01', 'ACTIVE');
INSERT INTO t_client (c_name, c_email, c_created_at, c_updated_at, c_status) VALUES ('Client_3', 'c3@mail.com', '2024-06-01', '2024-06-01', 'ACTIVE');

INSERT INTO t_account (client_id, coin_id, c_number, c_quantity, c_price, c_amount, c_created_at, c_updated_at, c_status)
VALUES (1, 1, '1_1', 10, 0, 0, '2024-06-01', '2024-06-01', 'ACTIVE');
INSERT INTO t_account (client_id, coin_id, c_number, c_quantity, c_price, c_amount, c_created_at, c_updated_at, c_status)
VALUES (1, 2, '1_2', 11, 0, 0, '2024-06-01', '2024-06-01', 'ACTIVE');
INSERT INTO t_account (client_id, coin_id, c_number, c_quantity, c_price, c_amount, c_created_at, c_updated_at, c_status)
VALUES (1, 3, '1_3', 12, 0, 0, '2024-06-01', '2024-06-01', 'ACTIVE');

INSERT INTO t_account (client_id, coin_id, c_number, c_quantity, c_price, c_amount, c_created_at, c_updated_at, c_status)
VALUES (2, 4, '2_1', 21, 0, 0, '2024-06-01', '2024-06-01', 'ACTIVE');
INSERT INTO t_account (client_id, coin_id, c_number, c_quantity, c_price, c_amount, c_created_at, c_updated_at, c_status)
VALUES (2, 5, '2_2', 22, 0, 0, '2024-06-01', '2024-06-01', 'ACTIVE');

INSERT INTO t_account (client_id, coin_id, c_number, c_quantity, c_price, c_amount, c_created_at, c_updated_at, c_status)
VALUES (3, 1, '3_1', 31, 0, 0, '2024-06-01', '2024-06-01', 'ACTIVE');
INSERT INTO t_account (client_id, coin_id, c_number, c_quantity, c_price, c_amount, c_created_at, c_updated_at, c_status)
VALUES (3, 5, '3_2', 32, 0, 0, '2024-06-01', '2024-06-01', 'ACTIVE');