# Use official MySQL Server image
FROM mysql/mysql-server:latest

# Set the working directory
WORKDIR /tmp

# Copy database schema and dump files
COPY test_db/*.sql /tmp/
COPY test_db/*.dump /tmp/

# Copy entrypoint initialization script
COPY test_db/employees.sql /docker-entrypoint-initdb.d

# Set environment variables
ENV MYSQL_ROOT_PASSWORD=example
ENV MYSQL_ROOT_HOST=%