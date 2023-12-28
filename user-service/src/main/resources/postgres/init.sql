CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(50),
  balance INT
);

CREATE TABLE user_transaction (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
  amount INT,
  transaction_date TIMESTAMP
);

INSERT INTO users (name, balance) VALUES
  ('sam', 1000),
  ('mike', 1200),
  ('jake', 800),
  ('marshal', 2000);
