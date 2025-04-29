-- changeset sardor:init-triggers
CREATE OR REPLACE FUNCTION update_event_status_to_filled()
    RETURNS TRIGGER AS
$$
BEGIN
    IF (SELECT COUNT(*) FROM participations WHERE participations.event_id = NEW.event_id) >=
       (SELECT events.max_participants FROM events WHERE events.id = NEW.event_id) THEN
        UPDATE events SET status = 'FILLED' WHERE events.id = NEW.event_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_event_status_to_filled_trigger
    AFTER INSERT
    ON participations
    FOR EACH ROW
EXECUTE PROCEDURE update_event_status_to_filled();

CREATE OR REPLACE FUNCTION update_event_status_to_open()
    RETURNS TRIGGER AS
$$
BEGIN
    IF (SELECT events.status FROM events WHERE events.id = OLD.event_id) = 'FILLED' THEN
        UPDATE events SET status = 'OPEN' WHERE events.id = OLD.event_id;
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_event_status_to_open_trigger
    AFTER DELETE
    ON participations
    FOR EACH ROW
EXECUTE PROCEDURE update_event_status_to_open();

CREATE OR REPLACE FUNCTION update_session_status_to_booked()
    RETURNS TRIGGER AS
$$
BEGIN
    UPDATE sessions SET status = 'BOOKED' WHERE sessions.id = NEW.session_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_session_status_to_booked_trigger
    AFTER INSERT
    ON bookings
    FOR EACH ROW
EXECUTE PROCEDURE update_session_status_to_booked();

CREATE OR REPLACE FUNCTION check_session_booked_status()
    RETURNS TRIGGER AS
$$
BEGIN
    IF (SELECT sessions.status FROM sessions WHERE sessions.id = NEW.session_id) = 'BOOKED' THEN
        RAISE EXCEPTION 'Невозможно забронировать данный сеанс, так как он уже забронирован';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_session_booked_status_trigger
    BEFORE INSERT
    ON bookings
    FOR EACH ROW
EXECUTE PROCEDURE check_session_booked_status();

CREATE OR REPLACE FUNCTION check_event_filled_status()
    RETURNS TRIGGER AS
$$
BEGIN
    IF (SELECT events.status FROM events WHERE events.id = NEW.event_id) = 'FILLED' THEN
        RAISE EXCEPTION 'Невозможно записаться на данное событие, так как оно уже заполнено';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_event_filled_status_trigger
    BEFORE INSERT
    ON participations
    FOR EACH ROW
EXECUTE PROCEDURE check_event_filled_status();

CREATE OR REPLACE FUNCTION check_event_finished_status()
    RETURNS TRIGGER AS
$$
BEGIN
    IF (SELECT events.status FROM events WHERE events.id = NEW.event_id) = 'FINISHED' THEN
        RAISE EXCEPTION 'Невозможно записаться на событие в прошлом';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_event_finished_status_trigger
    BEFORE INSERT
    ON participations
    FOR EACH ROW
EXECUTE PROCEDURE check_event_finished_status();

CREATE OR REPLACE FUNCTION check_session_closed_status()
    RETURNS TRIGGER AS
$$
BEGIN
    IF (SELECT sessions.status FROM sessions WHERE sessions.id = NEW.session_id) = 'CLOSED' THEN
        RAISE EXCEPTION 'Невозможно забронировать закрытый сеанс';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_session_closed_status_trigger
    BEFORE INSERT
    ON bookings
    FOR EACH ROW
EXECUTE PROCEDURE check_session_closed_status();

CREATE OR REPLACE FUNCTION deduct_booking_price()
    RETURNS TRIGGER AS
$$
BEGIN
    IF (SELECT users.balance FROM users WHERE users.id = NEW.user_id) <
       (SELECT sessions.booking_price FROM sessions WHERE sessions.id = NEW.session_id)
    THEN
        RAISE EXCEPTION 'Недостаточно средств для бронирования';
    ELSE
        UPDATE users
        SET balance = balance - (SELECT booking_price FROM sessions WHERE id = NEW.session_id)
        WHERE id = NEW.user_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER deduct_booking_price_trigger
    AFTER INSERT
    ON bookings
    FOR EACH ROW
EXECUTE PROCEDURE deduct_booking_price();

CREATE OR REPLACE FUNCTION check_session_start()
    RETURNS TRIGGER AS
$$
BEGIN

    IF (EXTRACT(MIN FROM NEW.starts_at) != 0 OR EXTRACT(MILLISECONDS FROM NEW.starts_at) != 0) THEN
        RAISE EXCEPTION 'Время начала сессии должно быть целым часом';
    END IF;
    IF EXISTS (SELECT 1
               FROM sessions AS s
               WHERE s.playground_id = NEW.playground_id
                 AND (NEW.starts_at = s.starts_at)
                 AND s.id != NEW.id
                 AND s.status != 'CLOSED') THEN
        RAISE EXCEPTION 'Невозможно создать сеанс, так как время его начала совпадает с другим сеансом.';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER check_session_start_trigger
    BEFORE INSERT
    ON sessions
    FOR EACH ROW
EXECUTE FUNCTION check_session_start();