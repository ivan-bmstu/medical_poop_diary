CREATE TABLE IF NOT EXISTS user_privacy (
  id integer NOT NULL PRIMARY KEY,
  chat_id integer NOT NULL,
  first_name varchar(30),
  language_code char(2),
  gmt varchar(15)
);

CREATE TABLE IF NOT EXISTS poop_record (
  id serial NOT NULL PRIMARY KEY,
  blood integer NOT NULL,
  mucus integer NOT NULL,
  stool integer NOT NULL,
  date timestamp NOT NULL,
  user_privacy_id integer REFERENCES user_privacy(id)
);

CREATE TABLE IF NOT EXISTS admin_user (
  id serial NOT NULL PRIMARY KEY,
  user_privacy_id integer REFERENCES user_privacy(id)
);

CREATE TABLE IF NOT EXISTS feedback(
  id serial NOT NULL PRIMARY KEY,
  message text,
  user_privacy_id integer REFERENCES user_privacy(id)
);
