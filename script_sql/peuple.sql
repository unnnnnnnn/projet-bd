DELETE FROM APourCatParent;
DELETE FROM PossedeAllergene;
DELETE FROM ProposeTypeCmd;
DELETE FROM FaitPartieCat;
DELETE FROM OuvrePendant;
DELETE FROM ContientPlats;
DELETE FROM APourEvaluation;
DELETE FROM Evaluations;
DELETE FROM Livraison;
DELETE FROM Sur_Place;
DELETE FROM Commande;
DELETE FROM Clients;
DELETE FROM Plat;
DELETE FROM Restaurant;
DELETE FROM Allergenes;
DELETE FROM Horaires;
DELETE FROM Client_Anonyme;
DELETE FROM Categorie;
DELETE FROM StatutCmd;
DELETE FROM TypeCmd;

-- Peuplement simple de la base de données
insert into TypeCmd values ('livraison');
insert into TypeCmd values ('sur place');
insert into TypeCmd values ('à emporter');

insert into StatutCmd values ('attente de confirmation');
insert into StatutCmd values ('validée');
insert into StatutCmd values ('disponible');
insert into StatutCmd values ('en livraison');
insert into StatutCmd values ('annulée par le client');
insert into StatutCmd values ('annulée par le restaurant');
insert into StatutCmd values ('terminée');

insert into Categorie values ('Cuisine régionale');
insert into Categorie values ('Cuisine des alpes');
insert into Categorie values ('Cuisine loraine');
insert into Categorie values ('Cuisine fromagère');
insert into Categorie values ('Cuisine boulangère');
insert into Categorie values ('Cuisine avec pain aux céréales');
insert into Categorie values ('Cuisine savoyarde');
insert into Categorie values ('Cuisine à base de viande');

insert into Client_Anonyme values (2);
insert into Client_Anonyme values (1);

insert into Horaires values ('Lundi',12,00,14,00);
insert into Horaires values ('Mardi',12,00,14,00);
insert into Horaires values ('Mercredi',12,00,14,00);
insert into Horaires values ('Jeudi',12,00,14,00);
insert into Horaires values ('Vendredi',12,00,14,00);
insert into Horaires values ('Samedi',12,00,14,00);
insert into Horaires values ('Dimanche',12,00,14,00);

insert into Horaires values ('Lundi',19,00,23,00);
insert into Horaires values ('Mardi',19,00,23,00);
insert into Horaires values ('Mercredi',19,00,23,00);
insert into Horaires values ('Jeudi',19,00,23,00);
insert into Horaires values ('Vendredi',19,00,23,00);
insert into Horaires values ('Samedi',19,00,23,00);
insert into Horaires values ('Dimanche',19,00,23,00);

insert into Horaires values ('Lundi',0,00,23,00);
insert into Horaires values ('Mardi',0,00,23,00);
insert into Horaires values ('Mercredi',0,00,23,00);
insert into Horaires values ('Jeudi',0,00,23,00);
insert into Horaires values ('Vendredi',0,00,23,00);
insert into Horaires values ('Samedi',0,00,23,00);
insert into Horaires values ('Dimanche',0,00,23,00);

insert into Horaires values ('Lundi',6,00,15,00);
insert into Horaires values ('Mardi',6,00,15,00);
insert into Horaires values ('Mercredi',6,00,15,00);
insert into Horaires values ('Jeudi',6,00,15,00);
insert into Horaires values ('Vendredi',6,00,15,00);
insert into Horaires values ('Samedi',6,00,15,00);
insert into Horaires values ('Dimanche',6,00,15,00);

insert into Allergenes values ('Fromage');
insert into Allergenes values ('Crème');

insert into Restaurant values ('aupetitplat@resto.com', 'Au petit plat', '04 48 25 64 12', '124 rue du chalet', 22,'Les petits plats dans les grands',3);
insert into Restaurant values ('cheztoto@resto.fr', 'Le resto de toto', '06 42 25 10 12', '102 place de la cuisine', 0, 'Pour être premier dans tous ce que vous faites',1);
insert into Restaurant values ('gillesbarbec@hotmail.fr', 'Le barbecue de Gilles', '09 32 56 48 69', '1 rue de la paix', 45, 'Propose sauscisses et grillades à volonté', 0);
insert into Restaurant values ('martine.aubrie@fromton.miam', 'Le fromton de martine', '01 45 78 16 99','18 chemin de la chèvre', 0, 'Pour ceux qui aiment le bon fromage', 2);
insert into Restaurant values ('jean.pierre@lepain.com', 'Au bon pain', '02 54 78 33 66', '16 place de l ancien moulin', 50, 'Le bon pain dans tous les coins',5);

insert into Plat values ('gillesbarbec@hotmail.fr',1,'Diot','Grosse sauscisse fumée avec son gratin dauphinois',15);
insert into Plat values ('gillesbarbec@hotmail.fr', 2, 'Saucisson', 'Gros saucisson d Ardèche', 10);
insert into Plat values ('aupetitplat@resto.com',1,'Quiche loraine', 'Tout le monde sait ce que c est',6);
insert into Plat values ('cheztoto@resto.fr',1,'Croziflette', 'Gratin à base de crozets', 10);
insert into Plat values ('martine.aubrie@fromton.miam',1,'Le brie','Un fromage à pâte molle',12);

insert into Clients values ('toto.tutu@tata.fr','12345','tutu','toto', '1 rue de l idiot',1);
insert into Clients values ('dodo@sommeil.sleep', 'zzzzz', 'dormeur', 'nain', '132 avenue de la forêt',2);


insert into Commande values ('aupetitplat@resto.com',2,TO_DATE('2022-05-10', 'YYYY-MM-DD'), 10, 59, 12,'sur place','validée');
insert into Commande values ('cheztoto@resto.fr', 1,TO_DATE('2022-05-02', 'YYYY-MM-DD'), 16, 22, 10,'livraison','attente de confirmation');
insert into Commande values ('gillesbarbec@hotmail.fr',1,TO_DATE('2022-05-15', 'YYYY-MM-DD'),12, 00, 25,'sur place','disponible');

insert into Sur_Place values ('aupetitplat@resto.com',2, TO_DATE('2022-05-10', 'YYYY-MM-DD'), 10, 59, 10, 15, 00);
insert into Sur_Place values ('gillesbarbec@hotmail.fr',1, TO_DATE('2022-05-15', 'YYYY-MM-DD'), 12, 00, 2, 12, 00);
insert into Livraison values (TO_DATE('2022-05-02', 'YYYY-MM-DD'),16,22,1,'cheztoto@resto.fr','1 rue de la paix',17,00);

insert into Evaluations values (TO_DATE('2022-05-10', 'YYYY-MM-DD'),13,30,'Je m en souviens encore tellement c était goûtu',5);
insert into Evaluations values (TO_DATE('2022-05-02', 'YYYY-MM-DD'),18,48,'Pas terrible, l attente était insupportable',2);

insert into APourEvaluation values (TO_DATE('2022-05-10', 'YYYY-MM-DD'),13,30,'Je m en souviens encore tellement c était goûtu',5,TO_DATE('2022-05-10', 'YYYY-MM-DD'),10,59,2,'aupetitplat@resto.com');
insert into APourEvaluation values (TO_DATE('2022-05-02', 'YYYY-MM-DD'),18,48,'Pas terrible, l attente était insupportable',2,TO_DATE('2022-05-02', 'YYYY-MM-DD'),16,22,1,'cheztoto@resto.fr');

insert into ContientPlats values (TO_DATE('2022-05-10', 'YYYY-MM-DD'), 10, 59, 1,2,'aupetitplat@resto.com',2);
insert into ContientPlats values (TO_DATE('2022-05-02', 'YYYY-MM-DD'),16,22,1,1,'cheztoto@resto.fr',2);
insert into ContientPlats values (TO_DATE('2022-05-15', 'YYYY-MM-DD'),12,00,1,1,'gillesbarbec@hotmail.fr',1);
insert into ContientPlats values (TO_DATE('2022-05-15', 'YYYY-MM-DD'),12,00,2,1,'gillesbarbec@hotmail.fr',1);

insert into OuvrePendant values ('aupetitplat@resto.com','Lundi',12,00,14,00);
insert into OuvrePendant values ('aupetitplat@resto.com','Mardi',12,00,14,00);
insert into OuvrePendant values ('aupetitplat@resto.com','Mercredi',12,00,14,00);
insert into OuvrePendant values ('aupetitplat@resto.com','Jeudi',12,00,14,00);
insert into OuvrePendant values ('aupetitplat@resto.com','Vendredi',12,00,14,00);

insert into OuvrePendant values ('cheztoto@resto.fr','Lundi',0,00,23,00);
insert into OuvrePendant values ('cheztoto@resto.fr','Mardi',0,00,23,00);
insert into OuvrePendant values ('cheztoto@resto.fr','Mercredi',0,00,23,00);
insert into OuvrePendant values ('cheztoto@resto.fr','Jeudi',0,00,23,00);
insert into OuvrePendant values ('cheztoto@resto.fr','Vendredi',0,00,23,00);
insert into OuvrePendant values ('cheztoto@resto.fr','Samedi',0,00,23,00);
insert into OuvrePendant values ('cheztoto@resto.fr','Dimanche',0,00,23,00);

insert into OuvrePendant values ('gillesbarbec@hotmail.fr','Lundi',12,00,14,00);
insert into OuvrePendant values ('gillesbarbec@hotmail.fr','Mardi',12,00,14,00);
insert into OuvrePendant values ('gillesbarbec@hotmail.fr','Mercredi',12,00,14,00);
insert into OuvrePendant values ('gillesbarbec@hotmail.fr','Jeudi',12,00,14,00);
insert into OuvrePendant values ('gillesbarbec@hotmail.fr','Vendredi',12,00,14,00);

insert into OuvrePendant values ('martine.aubrie@fromton.miam','Lundi',19,00,23,00);
insert into OuvrePendant values ('martine.aubrie@fromton.miam','Mardi',19,00,23,00);
insert into OuvrePendant values ('martine.aubrie@fromton.miam','Mercredi',19,00,23,00);
insert into OuvrePendant values ('martine.aubrie@fromton.miam','Jeudi',19,00,23,00);
insert into OuvrePendant values ('martine.aubrie@fromton.miam','Vendredi',19,00,23,00);

insert into OuvrePendant values ('jean.pierre@lepain.com','Lundi',6,00,15,00);
insert into OuvrePendant values ('jean.pierre@lepain.com','Mardi',6,00,15,00);
insert into OuvrePendant values ('jean.pierre@lepain.com','Mercredi',6,00,15,00);
insert into OuvrePendant values ('jean.pierre@lepain.com','Jeudi',6,00,15,00);
insert into OuvrePendant values ('jean.pierre@lepain.com','Vendredi',6,00,15,00);

insert into FaitPartieCat values ('aupetitplat@resto.com','Cuisine loraine');
insert into FaitPartieCat values ('cheztoto@resto.fr','Cuisine savoyarde');
insert into FaitPartieCat values ('gillesbarbec@hotmail.fr', 'Cuisine à base de viande');
insert into FaitPartieCat values ('martine.aubrie@fromton.miam', 'Cuisine fromagère');
insert into FaitPartieCat values ('jean.pierre@lepain.com', 'Cuisine avec pain aux céréales');

insert into ProposeTypeCmd values ('aupetitplat@resto.com','sur place');
insert into ProposeTypeCmd values ('aupetitplat@resto.com', 'livraison');
insert into ProposeTypeCmd values ('cheztoto@resto.fr', 'livraison');
insert into ProposeTypeCmd values ('gillesbarbec@hotmail.fr', 'sur place');
insert into ProposeTypeCmd values ('martine.aubrie@fromton.miam', 'à emporter');
insert into ProposeTypeCmd values ('jean.pierre@lepain.com', 'sur place');
insert into ProposeTypeCmd values ('jean.pierre@lepain.com', 'à emporter');

insert into PossedeAllergene values ('martine.aubrie@fromton.miam',1,'Fromage');
insert into PossedeAllergene values ('aupetitplat@resto.com',1,'Fromage');
insert into PossedeAllergene values ('aupetitplat@resto.com',1,'Crème');
insert into PossedeAllergene values ('cheztoto@resto.fr',1,'Fromage');
insert into PossedeAllergene values ('cheztoto@resto.fr',1,'Crème');

insert into APourCatParent values ('Cuisine loraine', 'Cuisine régionale');
insert into APourCatParent values ('Cuisine savoyarde', 'Cuisine des alpes');
insert into APourCatParent values ('Cuisine des alpes', 'Cuisine régionale');
insert into APourCatParent values ('Cuisine avec pain aux céréales', 'Cuisine boulangère');

COMMIT;
