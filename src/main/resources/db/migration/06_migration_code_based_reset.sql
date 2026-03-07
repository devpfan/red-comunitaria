-- Migración: Cambiar de token UUID a código de 6 dígitos
-- Fecha: 2026-02-06
-- Descripción: Refactorización para mejorar UX con códigos cortos

-- Eliminar tabla anterior si existe
DROP TABLE IF EXISTS password_reset_token;

-- Crear nueva tabla con código de 6 dígitos
CREATE TABLE password_reset_token (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(6) NOT NULL,
    usuario_id BIGINT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    used BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_password_reset_usuario 
        FOREIGN KEY (usuario_id) 
        REFERENCES usuario(id) 
        ON DELETE CASCADE
);

-- Crear índices optimizados
CREATE INDEX idx_password_reset_code ON password_reset_token(code);
CREATE INDEX idx_password_reset_usuario_id ON password_reset_token(usuario_id);
CREATE INDEX idx_password_reset_expiry_date ON password_reset_token(expiry_date);
CREATE INDEX idx_password_reset_used ON password_reset_token(used);
CREATE INDEX idx_password_reset_valid ON password_reset_token(code, used, expiry_date);

-- Comentarios
COMMENT ON TABLE password_reset_token IS 'Tokens de reseteo de contraseña con código de 6 dígitos';
COMMENT ON COLUMN password_reset_token.code IS 'Código numérico de 6 dígitos para resetear contraseña';
COMMENT ON COLUMN password_reset_token.used IS 'Indica si el código ya fue utilizado';
COMMENT ON COLUMN password_reset_token.expiry_date IS 'Fecha de expiración del código (24 horas)';

-- Verificar creación
SELECT 
    table_name,
    column_name,
    data_type,
    character_maximum_length,
    is_nullable
FROM 
    information_schema.columns
WHERE 
    table_name = 'password_reset_token'
ORDER BY 
    ordinal_position;
