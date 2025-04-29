-- changeset sardor:init-seed-data-events
INSERT INTO events(id, title, city_id, description, address, status, starts_at, max_participants)
VALUES
    (1, 'Крутое событие', 1, 'Реально крутое событие', 'улица', 'OPEN', '2025-04-29 13:00:00+00', 20),
    (2, 'Плохое событие', 1, 'Говно событие', 'улиц', 'OPEN', '2025-04-29 15:00:00+00', 25),
    (3, 'Закрытоо', 1, 'событие кончилось', 'улиц', 'FINISHED', '2025-04-29 17:00:00+00', 25);

INSERT INTO events_stations(event_id, station_id)
values (1, 2),
       (1, 4),
       (2, 4),
       (2, 5);