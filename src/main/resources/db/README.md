# Database Scripts

This directory contains SQL scripts for database management and initialization.

## Structure

```
db/
├── migration/   # DDL scripts (CREATE, ALTER, DROP)
└── seed/        # DML scripts (INSERT initial data)
```

## Migration Scripts

These scripts handle database schema creation and modifications:

- `01_create_tables.sql` - Creates all database tables
- `02_create_indexes.sql` - Creates indexes for query optimization
- `06_migration_code_based_reset.sql` - Password reset token migration
- `09_archivo_emprendimiento.sql` - File management tables
- `10_create_dato_historico.sql` - Historical data tracking
- `11_create_sector.sql` - Economic sectors table
- `12_migrate_emprendimiento_sector.sql` - Sector relationship migration

## Seed Scripts

These scripts populate initial data:

- `03_seed_data.sql` - Sample data for development and testing
- `07_import_divipola.sql` - Colombian geographical divisions (DIVIPOLA codes)

## Execution Order

For a fresh database setup, execute in this order:

1. Migration scripts (01-12)
2. Seed scripts (03, 07)

## Usage with Docker

Scripts in the `migration/` directory are automatically executed by Docker Compose on first container startup when mounted to `/docker-entrypoint-initdb.d/`.

## Manual Execution

```bash
# Connect to PostgreSQL
psql -h localhost -U admin -d red_comunitaria

# Run a script
\i /path/to/script.sql
```

## Notes

- Always backup before running migration scripts in production
- Seed data is for development/testing purposes only
- Scripts are idempotent where possible (use IF NOT EXISTS)
