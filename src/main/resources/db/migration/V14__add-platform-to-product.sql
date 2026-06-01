ALTER TABLE products ADD COLUMN platform_id UUID;
ALTER TABLE products ADD CONSTRAINT fk_products_platform FOREIGN KEY (platform_id) REFERENCES platforms(id);