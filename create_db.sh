#!/bin/sh

psql -d template1 -U postgres
psql --command "CREATE USER ticket_user WITH PASSWORD 'tickets2025';"
psql --command "CREATE DATABASE db_tickets;"
psql --command "GRANT ALL PRIVILEGES ON DATABASE db_tickets to ticket_user;"
psql --command "\q;"
psql -U user -d userdb -f /var/lib/postgresql/backup.sql

exit