
-- delete in reverse order of foreign key references

delete from relations where id >= 0;

delete from events where id >= 0;

delete from locations where id >= 0;

delete from users where id >= 0;
