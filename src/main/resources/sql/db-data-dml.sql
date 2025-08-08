-- Добавление пользователей
INSERT INTO tracker_schema.users (email, name, status, salary)
VALUES
('alex@mail.ru', 'Alex', 'WORKING', 40000.50),
('egor@mail.ru', 'Egor', 'CHILLING', 5000.49),
('maria@mail.ru', 'Maria', 'WORKING', 45000.00),
('ivan@mail.ru', 'Ivan', 'CHILLING', 3000.00),
('olga@mail.ru', 'Olga', 'WORKING', 38000.75);

-- Добавление проектов
INSERT INTO tracker_schema.projects (name, status)
VALUES
('Cloud', 'PROGRESS'),
('Infra', 'PROGRESS'),
('Music', 'STOPPED'),
('AI', 'PROGRESS'),
('Web', 'STOPPED'),
('Mobile', 'PROGRESS');

-- Добавление интеграций
INSERT INTO tracker_schema.integrations (name, url, project_id)
VALUES
('Google Cloud', 'api.cloud.google.com', 1),
('Yandex Cloud', 'api.cloud.yandex.com', 1),
('AWS', 'api.aws.amazon.com', 2),
('Docker Hub', 'api.hub.docker.com', 2),
('Spotify', 'api.spotify.com', 3),
('OpenAI', 'api.openai.com', 4);

-- Добавление задач
INSERT INTO tracker_schema.tasks (name, description, status, deadline, project_id, implementer)
VALUES
('Рефакторинг кнопки', 'Переписать код кнопки на React', 'UNDONE', '2026-07-28', 1, 1),
('Настройка Docker', 'Развернуть Docker-контейнеры', 'DONE', '2026-07-15', 2, 2),
('Дизайн логотипа', 'Создать новый логотип проекта', 'UNDONE', '2026-08-10', 3, 3),
('Обучение модели', 'Обучить нейросеть на новых данных', 'UNDONE', '2026-09-01', 4, 4),
('Тестирование API', 'Протестировать все endpoints', 'DONE', '2026-07-20', 1, 5),
('Оптимизация БД', 'Переиндексировать таблицы', 'UNDONE', '2026-08-05', 2, 1),
('Документация', 'Написать документацию к API', 'UNDONE', '2026-07-30', 4, 2);