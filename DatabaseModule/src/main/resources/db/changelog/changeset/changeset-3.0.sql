-- changeset sardor:init-indexes
CREATE INDEX sessions_starts_at_idx ON sessions USING btree (starts_at);
CREATE INDEX sessions_playground_id_idx ON sessions USING hash (playground_id);

CREATE INDEX bookings_user_id_idx ON bookings USING hash (user_id);
CREATE INDEX bookings_session_id_idx ON bookings USING hash (session_id);

CREATE INDEX participations_user_id_idx ON participations USING hash (user_id);
CREATE INDEX participations_event_id_idx ON participations USING hash (event_id);

CREATE INDEX feedbacks_playground_id_idx ON feedbacks USING HASH (playground_id);

CREATE INDEX playground_likes_playground_id_idx ON playground_likes USING HASH (playground_id);
CREATE INDEX event_likes_event_id_idx ON event_likes USING HASH (event_id);