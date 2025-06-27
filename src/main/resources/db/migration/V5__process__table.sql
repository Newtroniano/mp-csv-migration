CREATE TABLE processing_summary (
    id SERIAL PRIMARY KEY,
    file_name VARCHAR(255),
    total_records INT,
    female_count INT,
    male_count INT,
    average_age_female NUMERIC(5,2),
    average_age_male NUMERIC(5,2),
    processed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

