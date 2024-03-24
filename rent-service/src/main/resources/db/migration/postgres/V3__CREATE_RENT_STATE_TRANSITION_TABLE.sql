CREATE TABLE IF NOT EXISTS rent_state_transitions (
    rent UUID,
    sequence_number SMALLINT,
    timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    state TEXT NOT NULL,
    PRIMARY KEY (rent, sequence_number),
    FOREIGN KEY (rent) REFERENCES rents(rent_id)
);
