-- changeset sardor:init-functions
CREATE OR REPLACE FUNCTION get_participation_count(
    event_id INTEGER
)
    RETURNS INTEGER AS
$$
BEGIN
    RETURN (SELECT COUNT(*) FROM participations WHERE participations.event_id = $1);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_average_rating(
    playground_id INTEGER
)
    RETURNS NUMERIC AS
$$
BEGIN
    RETURN (SELECT AVG(feedbacks.rating) FROM feedbacks WHERE feedbacks.playground_id = $1);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_playground_like_count(
    playground_id INTEGER
)
    RETURNS INTEGER AS
$$
BEGIN
    RETURN (SELECT COUNT(*) FROM playground_likes WHERE playground_likes.playground_id = $1);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_event_like_count(
    event_id INTEGER
)
    RETURNS INTEGER AS
$$
BEGIN
    RETURN (SELECT COUNT(*) FROM event_likes WHERE event_likes.event_id = $1);
END;
$$ LANGUAGE plpgsql;