-------------------------------------------------------------------------------------------------
-- Drop

drop table if exists maintenance;
drop table if exists sites;
drop table if exists users;

drop type if exists user_role;
drop type if exists site_type;
drop type if exists site_status;

-------------------------------------------------------------------------------------------------
-- Create

create type site_type as enum('VILLA', 'APARTMENT', 'INDEPENDENT_HOUSE', 'OPEN_SITE');
create type site_status as enum('OPEN', 'OCCUPIED');
create type user_role as enum('ADMIN', 'SITE_OWNER');

create table users (
	uid int primary key,
	name varchar(20),
	role user_role
);

create table sites (
	sid int primary key,
	uid int,
	location varchar(255),
	type site_type,
	status site_status,
	length int,
	breadth int,
	constraint fk_owner_id foreign key (uid) references users(uid)
);

create table site_update_requests (
	sid int primary key,
	uid int,
	new_location varchar(255),
	new_type site_type,
	new_status site_status,
	new_length int,
	new_breadth int,
	approved boolean default false,
	constraint fk_site_id foreign key (sid) references sites(sid),
	constraint fk_owner_id foreign key (uid) references users(uid)
);

create table maintenance (
	mid int primary key generated always as identity,
	sid int,
	amount int,
	paid boolean default false,
	updated_uid int,
	constraint fk_site_id foreign key (sid) references sites(sid),
	constraint fk_updated_by_uid foreign key (updated_uid) references users(uid)
);

select * from users;
select * from sites;
select * from maintenance;
select * from site_update_requests;

delete from users;
delete from sites;
delete from maintenance;

-------------------------------------------------------------------------------------------------
-- Insert

insert into users (name, role) values
('admin', 'ADMIN'),
('ravi', 'SITE_OWNER'),
('anita', 'SITE_OWNER'),
('suresh', 'SITE_OWNER'),
('meena', 'SITE_OWNER'),
('kiran', 'SITE_OWNER'),
('pooja', 'SITE_OWNER');


insert into sites (sid, uid, location, type, status, length, breadth) values
-- first 10 sites (40x60)
(1, 2, 'block a - 1', 'VILLA', 'OCCUPIED', 40, 60),
(2, 3, 'block a - 2', 'APARTMENT', 'OCCUPIED', 40, 60),
(3, 4, 'block a - 3', 'INDEPENDENT_HOUSE', 'OPEN', 40, 60),
(4, 5, 'block a - 4', 'VILLA', 'OCCUPIED', 40, 60),
(5, 6, 'block a - 5', 'OPEN_SITE', 'OPEN', 40, 60),
(6, 7, 'block a - 6', 'APARTMENT', 'OCCUPIED', 40, 60),
(7, 2, 'block a - 7', 'VILLA', 'OCCUPIED', 40, 60),
(8, 3, 'block a - 8', 'OPEN_SITE', 'OPEN', 40, 60),
(9, 4, 'block a - 9', 'INDEPENDENT_HOUSE', 'OCCUPIED', 40, 60),
(10, 5, 'block a - 10', 'VILLA', 'OCCUPIED', 40, 60),

-- next 10 sites (30x50)
(11, 6, 'block b - 1', 'APARTMENT', 'OCCUPIED', 30, 50),
(12, 7, 'block b - 2', 'OPEN_SITE', 'OPEN', 30, 50),
(13, 2, 'block b - 3', 'VILLA', 'OCCUPIED', 30, 50),
(14, 3, 'block b - 4', 'INDEPENDENT_HOUSE', 'OCCUPIED', 30, 50),
(15, 4, 'block b - 5', 'OPEN_SITE', 'OPEN', 30, 50),
(16, 5, 'block b - 6', 'APARTMENT', 'OCCUPIED', 30, 50),
(17, 6, 'block b - 7', 'VILLA', 'OCCUPIED', 30, 50),
(18, 7, 'block b - 8', 'OPEN_SITE', 'OPEN', 30, 50),
(19, 2, 'block b - 9', 'INDEPENDENT_HOUSE', 'OCCUPIED', 30, 50),
(20, 3, 'block b - 10', 'VILLA', 'OCCUPIED', 30, 50),

-- last 15 sites (30x40)
(21, 4, 'block c - 1', 'OPEN_SITE', 'OPEN', 30, 40),
(22, 5, 'block c - 2', 'APARTMENT', 'OCCUPIED', 30, 40),
(23, 6, 'block c - 3', 'VILLA', 'OCCUPIED', 30, 40),
(24, 7, 'block c - 4', 'OPEN_SITE', 'OPEN', 30, 40),
(25, 2, 'block c - 5', 'INDEPENDENT_HOUSE', 'OCCUPIED', 30, 40),
(26, 3, 'block c - 6', 'OPEN_SITE', 'OPEN', 30, 40),
(27, 4, 'block c - 7', 'VILLA', 'OCCUPIED', 30, 40),
(28, 5, 'block c - 8', 'APARTMENT', 'OCCUPIED', 30, 40),
(29, 6, 'block c - 9', 'OPEN_SITE', 'OPEN', 30, 40),
(30, 7, 'block c - 10', 'VILLA', 'OCCUPIED', 30, 40),
(31, 2, 'block c - 11', 'OPEN_SITE', 'OPEN', 30, 40),
(32, 3, 'block c - 12', 'APARTMENT', 'OCCUPIED', 30, 40),
(33, 4, 'block c - 13', 'VILLA', 'OCCUPIED', 30, 40),
(34, 5, 'block c - 14', 'OPEN_SITE', 'OPEN', 30, 40),
(35, 6, 'block c - 15', 'INDEPENDENT_HOUSE', 'OCCUPIED', 30, 40);

insert into maintenance (sid, amount, paid, updated_uid) values
(1, 21600, true, 1),
(2, 21600, false, 1),
(3, 14400, false, 1),
(4, 21600, true, 1),
(5, 14400, false, 1),
(6, 21600, false, 1),
(7, 21600, true, 1),
(8, 14400, false, 1),
(9, 21600, false, 1),
(10, 21600, true, 1),

(11, 13500, false, 1),
(12, 9000, false, 1),
(13, 13500, true, 1),
(14, 13500, false, 1),
(15, 9000, false, 1),
(16, 13500, true, 1),
(17, 13500, false, 1),
(18, 9000, false, 1),
(19, 13500, false, 1),
(20, 13500, true, 1),

(21, 7200, false, 1),
(22, 10800, true, 1),
(23, 10800, false, 1),
(24, 7200, false, 1),
(25, 10800, true, 1),
(26, 7200, false, 1),
(27, 10800, false, 1),
(28, 10800, true, 1),
(29, 7200, false, 1),
(30, 10800, true, 1),
(31, 7200, false, 1),
(32, 10800, false, 1),
(33, 10800, true, 1),
(34, 7200, false, 1),
(35, 10800, false, 1);