CREATE TABLE employees (
    id BIGSERIAL  PRIMARY KEY,  -- id, otomatik artan birincil anahtar
    employee_name VARCHAR(255) NOT NULL,   -- Çalışan adı
    employee_salary VARCHAR(255) NOT NULL, -- Çalışan maaşı
    employee_age BIGINT NOT NULL,         -- Çalışan yaşını temsil eden alan
    profile_image VARCHAR(255)            -- Çalışan profil resmi (isteğe bağlı)
);