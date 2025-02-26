--GROUPS
insert into groups (id, group_name, motto, logo)
values ('67f99ef6-5ad3-43a9-b116-e967f45ee6b4', 'Stormbreakers', 'Rise with the thunder, conquer with the storm.', 'https://media.wired.com/photos/593261cab8eb31692072f129/master/pass/85120553.jpg'),
('1564c438-a838-47a4-9515-93561d930d7d', 'Tigers', 'As strong as a tiger', 'https://www.colchesterzoologicalsociety.com/wp-content/uploads/2024/09/Tiger-600x380.jpg'),
('f0dd614f-649b-4207-a347-b5ba8c9085fb', 'Team Elephant', 'Steady like an elephant', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRyCO6sKbPcPuKicrudDPEsUpMmvOb1VAdfJg&s')
ON CONFLICT DO NOTHING;
--USERS
insert into users (id, email,first_name,last_name, password)
values ('ba804cb9-fa14-42a5-afaf-be488742fc54', 'admin@example.com', 'James','Bond', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6' ), -- Password: 1234
('0d8fa44c-54fd-4cd0-ace9-2a7da57992de', 'user@example.com', 'Tyler','Durden', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6') -- Password: 1234
 ON CONFLICT DO NOTHING;

--USERS WITH GROUP
insert into users (group_id, id, email, first_name, last_name, password)
values ('f0dd614f-649b-4207-a347-b5ba8c9085fb', '83ebe14b-61d2-41b9-bf61-21f2f1878ab2', 'user2@example.com', 'Forest', 'Gump', '$2a$10$TM3PAYG3b.H98cbRrHqWa.BM7YyCqV92e/kUTBfj85AjayxGZU7d6');

--ROLES
INSERT INTO role(id, name)
VALUES ('d29e709c-0ff1-4f4c-a7ef-09f656c390f1', 'DEFAULT'),
('ab505c92-7280-49fd-a7de-258e618df074', 'ADMIN'),
('c6aee32d-8c35-4481-8b3e-a876a39b0c02', 'USER')
ON CONFLICT DO NOTHING;

--AUTHORITIES
INSERT INTO authority(id, name)
VALUES ('2ebf301e-6c61-4076-98e3-2a38b31daf86', 'DEFAULT'),
('76d2cbf6-5845-470e-ad5f-2edb9e09a868', 'USER_MODIFY'),
('21c942db-a275-43f8-bdd6-d048c21bf5ab', 'USER_DELETE'),
('e0e8fdca-6f92-440c-ac82-da90c8f6eef8', 'GROUP_READ'),
('a1fc6328-28e2-4f94-9b2a-87b1a23cce74', 'GROUP_READ_ALL'),
('cf52ea92-f5d7-4f51-a439-d3315c01d4ce', 'GROUP_MODIFY')
ON CONFLICT DO NOTHING;

--assign roles to users
insert into users_role (users_id, role_id)
values ('ba804cb9-fa14-42a5-afaf-be488742fc54', 'd29e709c-0ff1-4f4c-a7ef-09f656c390f1'),
       ('0d8fa44c-54fd-4cd0-ace9-2a7da57992de', 'd29e709c-0ff1-4f4c-a7ef-09f656c390f1'),
       ('ba804cb9-fa14-42a5-afaf-be488742fc54', 'ab505c92-7280-49fd-a7de-258e618df074'),
       ('ba804cb9-fa14-42a5-afaf-be488742fc54', 'c6aee32d-8c35-4481-8b3e-a876a39b0c02')
 ON CONFLICT DO NOTHING;

--assign authorities to roles
INSERT INTO role_authority(role_id, authority_id)
VALUES ('d29e709c-0ff1-4f4c-a7ef-09f656c390f1', '2ebf301e-6c61-4076-98e3-2a38b31daf86'),
('ab505c92-7280-49fd-a7de-258e618df074', '76d2cbf6-5845-470e-ad5f-2edb9e09a868'),
('ab505c92-7280-49fd-a7de-258e618df074', 'e0e8fdca-6f92-440c-ac82-da90c8f6eef8'),
('ab505c92-7280-49fd-a7de-258e618df074', 'cf52ea92-f5d7-4f51-a439-d3315c01d4ce'),
('c6aee32d-8c35-4481-8b3e-a876a39b0c02', '21c942db-a275-43f8-bdd6-d048c21bf5ab'),
('c6aee32d-8c35-4481-8b3e-a876a39b0c02', 'e0e8fdca-6f92-440c-ac82-da90c8f6eef8'),
('ab505c92-7280-49fd-a7de-258e618df074', 'a1fc6328-28e2-4f94-9b2a-87b1a23cce74')
ON CONFLICT DO NOTHING;