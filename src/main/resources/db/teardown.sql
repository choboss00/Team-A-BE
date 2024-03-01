-- 더미 데이터 작성 파일
SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE fundings;
TRUNCATE TABLE banners;
TRUNCATE TABLE users;
TRUNCATE TABLE funding_images;
SET REFERENTIAL_INTEGRITY TRUE;

INSERT INTO fundings (created_at, funding_title, funding_summary, funding_description, category, price, start_date, end_date, funding_enum)
VALUES
    (NOW(), 'Product 1', 'Summary for product 1', 'Description for product 1', '가전', 1000, '2024-03-01 00:00:00', '2024-03-15 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 'Product 2', 'Summary for product 2', 'Description for product 2', '가전', 2000, '2024-03-02 00:00:00', '2024-03-16 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 'Product 3', 'Summary for product 3', 'Description for product 3', '가전', 3000, '2024-03-03 00:00:00', '2024-03-17 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 'Product 4', 'Summary for product 4', 'Description for product 4', '가전', 4000, '2024-03-04 00:00:00', '2024-03-18 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 'Product 5', 'Summary for product 5', 'Description for product 5', '가전', 5000, '2024-03-05 00:00:00', '2024-03-19 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 'Product 6', 'Summary for product 6', 'Description for product 6', '가전', 1000, '2024-03-01 00:00:00', '2024-03-15 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 'Product 7', 'Summary for product 7', 'Description for product 7', '가전', 2000, '2024-03-02 00:00:00', '2024-03-16 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 'Product 8', 'Summary for product 8', 'Description for product 8', '가전', 3000, '2024-03-03 00:00:00', '2024-03-17 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 'Product 9', 'Summary for product 9', 'Description for product 9', '가전', 4000, '2024-03-04 00:00:00', '2024-03-18 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 'Product 10', 'Summary for product 10', 'Description for product 10', '가전', 5000, '2024-03-05 00:00:00', '2024-03-02 00:00:00', 'OPEN_SCHEDULED'),
    (NOW(), 'Product 11', 'Summary for product 11', 'Description for product 11', '가전', 2000, '2024-02-28 00:00:00', '2024-03-02 00:00:00', 'CLOSE_IMMINENT'),
    (NOW(), 'Product 12', 'Summary for product 12', 'Description for product 12', '가전', 3000, '2024-02-28 00:00:00', '2024-03-02 00:00:00', 'CLOSE_IMMINENT'),
    (NOW(), 'Product 13', 'Summary for product 13', 'Description for product 13', '가전', 4000, '2024-02-28 00:00:00', '2024-03-02 00:00:00', 'CLOSE_IMMINENT'),
    (NOW(), 'Product 14', 'Summary for product 14', 'Description for product 14', '가전', 5000, '2024-02-28 00:00:00', '2024-03-02 00:00:00', 'CLOSE_IMMINENT');

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
