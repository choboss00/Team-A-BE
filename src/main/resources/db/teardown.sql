-- 더미 데이터 작성 파일
SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE fundings;
TRUNCATE TABLE banners;
TRUNCATE TABLE users;
TRUNCATE TABLE funding_images;
TRUNCATE TABLE funding_hearts;
TRUNCATE TABLE users;
TRUNCATE TABLE participating_fundings;
SET REFERENTIAL_INTEGRITY TRUE;

INSERT INTO users (created_at, email, password, nickname, role)
VALUES (NOW(), 'user1@example.com', 'hashed_password1', 'nickname1', 'USER'),
       (NOW(), 'user2@example.com', 'hashed_password2', 'nickname2', 'USER'),
       (NOW(), 'user3@example.com', 'hashed_password3', 'nickname3', 'USER'),
       (NOW(), 'user4@example.com', 'hashed_password4', 'nickname4', 'USER'),
       (NOW(), 'user5@example.com', 'hashed_password5', 'nickname5', 'USER'),
       (NOW(), 'user6@example.com', 'hashed_password6', 'nickname6', 'USER');

INSERT INTO fundings (created_at, user_id, funding_title, funding_summary, funding_description, category, individual_price, total_price, start_date, end_date, funding_enum)
VALUES
    (NOW(), 1, 'Product 1', 'Summary for product 1', 'Description for product 1', '테크가전', 1000, 10000, '2024-03-01 00:00:00', '2024-03-15 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 1, 'Product 2', 'Summary for product 2', 'Description for product 2', '테크가전', 2000, 20000, '2024-03-02 00:00:00', '2024-03-16 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 1, 'Product 3', 'Summary for product 3', 'Description for product 3', '테크가전', 3000, 30000, '2024-03-03 00:00:00', '2024-03-17 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 1, 'fashion 1', 'Summary for fashion 1', 'Description for fashion 1', '패션', 4000, 40000, '2024-03-04 00:00:00', '2024-03-18 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 1, 'fashion 2', 'Summary for fashion 2', 'Description for fashion 2', '패션', 5000, 50000, '2024-03-05 00:00:00', '2024-03-19 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 2, 'fashion 3', 'Summary for fashion 3', 'Description for fashion 3', '패션', 1000, 10000, '2024-03-01 00:00:00', '2024-03-15 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 2, 'beauty 1', 'Summary for beauty 1', 'Description for beauty 1', '뷰티', 2000, 20000, '2024-03-02 00:00:00', '2024-03-16 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 2, 'beauty 2', 'Summary for beauty 2', 'Description for beauty 2', '뷰티', 3000, 30000, '2024-03-03 00:00:00', '2024-03-17 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 2, 'beauty 3', 'Summary for beauty 3', 'Description for beauty 3', '뷰티', 4000, 40000, '2024-03-04 00:00:00', '2024-03-18 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 2, 'food 1', 'Summary for food 1', 'Description for food 1', '푸드', 5000, 50000, '2024-03-03 00:00:00', '2024-03-05 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 2, 'food 2', 'Summary for food 2', 'Description for food 2', '푸드', 2000, 20000, '2024-03-03 00:00:00', '2024-03-05 00:00:00', 'CLOSE_IMMINENT'),
    (NOW(), 3, 'book 1', 'Summary for book 1', 'Description for book 1', '도서', 3000, 30000, '2024-03-03 00:00:00', '2024-03-05 00:00:00', 'CLOSE_IMMINENT'),
    (NOW(), 3, 'goods 1', 'Summary for goods 1', 'Description for goods 1', '굿즈', 4000, 40000, '2024-03-03 00:00:00', '2024-03-05 00:00:00', 'CLOSE_IMMINENT'),
    (NOW(), 3, 'stuff 1', 'Summary for stuff 1', 'Description for stuff 1', '잡화', 5000, 50000, '2024-03-03 00:00:00', '2024-03-05 00:00:00', 'CLOSE_IMMINENT');

INSERT INTO participating_fundings (funding_id, user_id, created_at)
VALUES
    (11, 1, NOW()),
    (11, 3, NOW());

INSERT INTO funding_hearts (funding_id, user_id, created_at)
VALUES
    (1, 2, NOW()),
    (1, 3, NOW()),
    (1, 4, NOW()),
    (1, 5, NOW()),
    (2, 2, NOW()),
    (2, 3, NOW()),
    (2, 4, NOW()),
    (2, 5, NOW()),
    (2, 6, NOW()),
    (3, 2, NOW()),
    (3, 3, NOW()),
    (3, 4, NOW()),
    (4, 2, NOW()),
    (4, 3, NOW()),
    (4, 4, NOW()),
    (5, 2, NOW()),
    (5, 3, NOW()),
    (5, 4, NOW()),
    (6, 1, NOW()),
    (6, 3, NOW()),
    (6, 4, NOW());



INSERT INTO banners (funding_id, image, created_at)
VALUES
    (1, 'image_path_1.jpg', NOW()),
    (2, 'image_path_2.jpg', NOW()),
    (3, 'image_path_3.jpg', NOW()),
    (4, 'image_path_4.jpg', NOW()),
    (5, 'image_path_5.jpg', NOW()),
    (7, 'image_path_7.jpg', NOW()),
    (8, 'image_path_8.jpg', NOW()),
    (9, 'image_path_9.jpg', NOW()),
    (10, 'image_path_10.jpg', NOW());


INSERT INTO funding_images (funding_id, funding_image, created_at)
VALUES
    (1, 'image_path_1.jpg', NOW()),
    (2, 'image_path_2.jpg', NOW()),
    (3, 'image_path_3.jpg', NOW()),
    (4, 'image_path_4.jpg', NOW()),
    (5, 'image_path_5.jpg', NOW()),
    (6, 'image_path_6.jpg', NOW()),
    (7, 'image_path_7.jpg', NOW()),
    (8, 'image_path_8.jpg', NOW()),
    (9, 'image_path_9.jpg', NOW()),
    (10, 'image_path_10.jpg', NOW()),
    (11, 'image_path_11-1.jpg', NOW()),
    (11, 'image_path_11-2.jpg', NOW()),
    (11, 'image_path_11-3.jpg', NOW()),
    (12, 'image_path_12-1.jpg', NOW()),
    (12, 'image_path_12-2.jpg', NOW()),
    (12, 'image_path_12-3.jpg', NOW()),
    (13, 'image_path_13-1.jpg', NOW()),
    (13, 'image_path_13-2.jpg', NOW()),
    (14, 'image_path_14-1.jpg', NOW()),
    (14, 'image_path_14-2.jpg', NOW()),
    (14, 'image_path_14-3.jpg', NOW());
