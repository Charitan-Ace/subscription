DROP TABLE IF EXISTS subscription CASCADE;
CREATE TABLE subscription (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    subscription_type VARCHAR(50),
    lookup_id VARCHAR(50),
    is_active BOOLEAN,
    donor_id VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
