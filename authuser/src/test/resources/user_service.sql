DELETE FROM tb_users;

INSERT INTO tb_users(
    user_id, cpf, creation_date, email, full_name, last_update_date, password, phone_number, user_status, username)
VALUES ('456afc76-3221-4192-87bb-f1bc87c35c4a', '025.874.654-98', '2022-03-06 20:32:12.808075', 'ismadrade@gmail.com', 'Ismael da Silva de Andrade', '2022-03-06 20:32:12.808075', '123456', '48987456987', 'ACTIVE', 'ismadrade');

INSERT INTO tb_users(
    user_id, cpf, creation_date, email, full_name, last_update_date, password, phone_number, user_status, username)
VALUES ('9bbc8b99-7057-475b-81ca-6130e15bf030', '642.758.965-31', '2022-03-06 20:32:12.808075', 'rafys2000@gmail.com', 'João Rafael Cardoso De Medeiros', '2022-03-06 20:32:12.808075', '123456', '48965657474', 'ACTIVE', 'rafys2000');

INSERT INTO tb_users(
    user_id, cpf, creation_date, email, full_name, last_update_date, password, phone_number, user_status, username)
VALUES ('d1ce0dbe-1cdc-4305-8798-3a3fa29f950f', '985.569.632-85', '2022-03-06 20:32:12.808075', 'loreni@gmail.com', 'Loreni Faria da Silva', '2022-03-06 20:32:12.808075', '123456', '51963636398', 'BLOCKED', 'lorenif');

INSERT INTO tb_users(
    user_id, cpf, creation_date, email, full_name, last_update_date, password, phone_number, user_status, username)
VALUES ('a5986db1-e764-453f-87d8-5cdb688eb605', '658.452.214-95', '2022-03-06 20:32:12.808075', 'joao.silveira@gmail.com', 'João Silveira Das Neves', '2022-03-06 20:32:12.808075', '123456', '47963635454', 'INACTIVE', 'dasneves');
