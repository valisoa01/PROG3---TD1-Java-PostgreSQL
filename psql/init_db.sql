CREATE DATABASE product_management_db;

CREATE USER product_manager_user WITH PASSWORD '123456';

GRANT CONNECT ON DATABASE product_management_db TO product_manager_user;


\c product_management_db

GRANT usage on SCHEMA PUBLIC to product_manager_user;
GRANT CREATE ON SCHEMA PUBLIC TO product_manager_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO product_manager_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO product_manager_user;

Alter DEFAULT privileges IN SCHEMA PUBLIC
GRANT ALL ON TABLES TO product_manager_user;

ALTER DEFAULT privileges IN SCHEMA PUBLIC
GRANT ALL ON SEQUENCES TO product_manager_user;