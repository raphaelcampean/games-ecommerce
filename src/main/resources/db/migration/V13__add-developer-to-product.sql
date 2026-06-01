ALTER TABLE products ADD COLUMN developer_id UUID;
ALTER TABLE products ADD CONSTRAINT fk_products_developer FOREIGN KEY (developer_id) REFERENCES developers(id);