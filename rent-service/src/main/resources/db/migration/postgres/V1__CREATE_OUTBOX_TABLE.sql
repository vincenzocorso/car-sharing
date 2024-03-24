CREATE TABLE IF NOT EXISTS outbox (
    message_id text primary key,
    channel text not null,
    message_key text not null,
    payload text not null,
    headers text not null
);
