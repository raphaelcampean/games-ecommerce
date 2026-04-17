CREATE TABLE product_platforms (
    product_id UUID NOT NULL,
    platform_id UUID NOT NULL,
    PRIMARY KEY (product_id, platform_id),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (platform_id) REFERENCES platforms(id) ON DELETE CASCADE
);