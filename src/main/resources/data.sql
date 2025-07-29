
INSERT INTO member (email, password) VALUES
  ('test@example.com', '1234');



INSERT INTO product (name, price, image_url) VALUES
  ('초콜릿', 10000, 'https://mblogthumb-phinf.pstatic.net/MjAxODAyMTJfMTgy/MDAxNTE4NDI0NDc3NzQw...'),
  ('사탕', 15000, 'https://image.idus.com/image/files/...'),
  ('과자', 12000, 'https://i.namu.wiki/i/...');



INSERT INTO options (product_id, name, quantity) VALUES
  (1, '초콜릿 대용량', 50),
  (1, '초콜릿 소용량', 100),
  (2, '사탕 일반형', 30);



INSERT INTO wishlist (member_id, product_id, quantity) VALUES
  (1, 1, 1);

