INSERT INTO campeonato(id_campeonato, descricao_campeonato, ano_campeonato)VALUES(1, 'Camp1', 2000);
INSERT INTO campeonato(id_campeonato, descricao_campeonato, ano_campeonato)VALUES(2, 'Camp2', 2005);
INSERT INTO campeonato(id_campeonato, descricao_campeonato, ano_campeonato)VALUES(3, 'Camp3', 2010);


INSERT INTO pais(id_pais, nome_pais)VALUES(1, 'Brasil');
INSERT INTO pais(id_pais, nome_pais)VALUES(2, 'Japão');
INSERT INTO pais(id_pais, nome_pais)VALUES(3, 'França');


INSERT INTO pista (id_pista, nome_pista, tamanho_pista, country_id_pais) VALUES(1 ,'Pista1', 5000, 1);
INSERT INTO pista (id_pista, nome_pista, tamanho_pista, country_id_pais) VALUES(2 ,'Pista2', 6000, 2);
INSERT INTO pista (id_pista, nome_pista, tamanho_pista, country_id_pais) VALUES(3 ,'Pista3', 7000, 2);


insert into equipe (id_equipe, nome_equipe) values (1, 'Redbull');
insert into equipe (id_equipe, nome_equipe) values (2, 'Ferrari');
insert into equipe (id_equipe, nome_equipe) values (3, 'BMW');


insert into piloto (id_piloto, nome_piloto, country_id_pais, equip_id_equipe) values (1, 'Piloto1', 1, 1);
insert into piloto (id_piloto, nome_piloto, country_id_pais, equip_id_equipe) values (2, 'Piloto2', 2, 2);
insert into piloto (id_piloto, nome_piloto, country_id_pais, equip_id_equipe) values (3, 'Piloto3', 1, 1);
insert into piloto (id_piloto, nome_piloto, country_id_pais, equip_id_equipe) values (4, 'Piloto4', 2, 2);
insert into piloto (id_piloto, nome_piloto, country_id_pais, equip_id_equipe) values (5, 'Piloto5', 1, 1);


insert into corrida (id_corrida, data_corrida, championship_id_campeonato, speedway_id_pista) values (1, '2000-01-01 10:00:00', 1, 1);
insert into corrida (id_corrida, data_corrida, championship_id_campeonato, speedway_id_pista) values (2, '2005-01-01 10:00:00', 2, 2);
insert into corrida (id_corrida, data_corrida, championship_id_campeonato, speedway_id_pista) values (3, '2010-01-01 10:00:00', 2, 1);


INSERT INTO usuario(id_usuario, nome_usuario, email_usuario, senha_usuario) VALUES(1, 'User 1', 'email1', 'senha1');
INSERT INTO usuario(id_usuario, nome_usuario, email_usuario, senha_usuario) VALUES(2, 'User 2', 'email2', 'senha2');