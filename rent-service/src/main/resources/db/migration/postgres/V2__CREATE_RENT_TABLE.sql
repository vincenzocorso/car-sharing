CREATE TABLE IF NOT EXISTS rents (
    rent_id uuid primary key,
    version bigint not null,
    customer_id text not null,
    vehicle_id text not null,
    current_state text not null
);
