DROP TABLE APourCatParent;
DROP TABLE PossedeAllergene;
DROP TABLE ProposeTypeCmd;
DROP TABLE FaitPartieCat;
DROP TABLE OuvrePendant;
DROP TABLE ContientPlats;
DROP TABLE APourEvaluation;
DROP TABLE Evaluations;
DROP TABLE Livraison;
DROP TABLE Sur_Place;
DROP TABLE Commande;
DROP TABLE Clients;
DROP TABLE Plat;
DROP TABLE Restaurant;
DROP TABLE Allergenes;
DROP TABLE Horaires;
DROP TABLE Client_Anonyme;
DROP TABLE Categorie;
DROP TABLE StatutCmd;
DROP TABLE TypeCmd;

CREATE TABLE TypeCmd (
	nomTypeCmd VARCHAR(30) CONSTRAINT Ktc PRIMARY KEY,
	CONSTRAINT typec CHECK(nomTypeCmd IN ('livraison', 'sur place', 'à emporter'))
);

CREATE TABLE StatutCmd (
	nomStatutCmd VARCHAR(30) CONSTRAINT Ksc PRIMARY KEY,
	CONSTRAINT typecmd CHECK(nomStatutCmd IN ('attente de confirmation', 'validée', 'disponible', 'en livraison', 'annulée par le client', 'annulée par le restaurant', 'terminée'))
);

CREATE TABLE Categorie (
	nomCategorie VARCHAR(100) CONSTRAINT Kcat PRIMARY KEY
);

CREATE TABLE Client_Anonyme (
	idCl INT CONSTRAINT Kid PRIMARY KEY
);

CREATE TABLE Horaires (
	jour VARCHAR(10) NOT NULL CONSTRAINT Valjour CHECK(jour IN ('Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche')),
	hDebut INT NOT NULL CONSTRAINT ValHDeb CHECK(hDebut BETWEEN 00 and 23),
	minDebut INT NOT NULL CONSTRAINT ValMinDeb CHECK(minDebut BETWEEN 00 and 59),
	hFin INT NOT NULL CONSTRAINT ValHFin CHECK(hFin BETWEEN 00 and 23),
	minFin INT NOT NULL CONSTRAINT ValMinFin CHECK(minFin BETWEEN 00 and 59),
	CONSTRAINT Kh PRIMARY KEY (jour, hdebut, minDebut, hFin, minFin)
);

CREATE TABLE Allergenes (
	allergene VARCHAR(30) CONSTRAINT Kal PRIMARY KEY
);

CREATE TABLE Restaurant (
	mailR VARCHAR(30) CONSTRAINT Kmail PRIMARY KEY,
	nomR VARCHAR(30) NOT NULL,
	telR VARCHAR(20) NOT NULL,
	adrR VARCHAR(30) NOT NULL,
	nbPlacesR INT,
	presentationR VARCHAR(100),
	noteMoy FLOAT NOT NULL CONSTRAINT val CHECK(noteMoy BETWEEN 0.0 AND 5.0)
);

CREATE TABLE Plat (
	mailR VARCHAR(30) NOT NULL,
	idP INT NOT NULL,
	nomP VARCHAR(30) NOT NULL,
	descP VARCHAR(100) NOT NULL,
	prixP FLOAT NOT NULL CONSTRAINT Prix CHECK (prixP > 0),
	CONSTRAINT Rmail FOREIGN KEY (mailR) REFERENCES Restaurant (mailR),
	CONSTRAINT Kplat PRIMARY KEY (mailR, idP)
);

CREATE TABLE Clients (
	mailCl VARCHAR(30) CONSTRAINT Kmailc PRIMARY KEY,
	mdpCl VARCHAR(30) NOT NULL,
	nomCl VARCHAR(30) NOT NULL,
	prenomCl VARCHAR(30) NOT NULL,
	adrCl VARCHAR(30) NOT NULL,
	idCl INT NOT NULL,
	CONSTRAINT RidCl FOREIGN KEY (idCl) REFERENCES Client_Anonyme (idCl)
);

CREATE TABLE Commande (
	mailR VARCHAR(30) NOT NULL,
	idCl INT NOT NULL,
	dateCmd DATE NOT NULL,
	hCmd INT NOT NULL,
	minCmd INT NOT NULL,
	prixCmd FLOAT NOT NULL CONSTRAINT Price CHECK(prixCmd > 0),
	nomTypeCmd VARCHAR(30) NOT NULL, /* Vérifier cohérence typeCmd de la commande et typeCmd du restaurant */
	nomStatutCmd VARCHAR(30) NOT NULL,
	CONSTRAINT Rntpc FOREIGN KEY (nomTypeCmd) REFERENCES TypeCmd (nomTypeCmd),
	CONSTRAINT Rstc FOREIGN KEY (nomStatutCmd) REFERENCES StatutCmd (nomStatutCmd),
	CONSTRAINT RmailCmd FOREIGN KEY (mailR) REFERENCES Restaurant (mailR),
	CONSTRAINT Rid FOREIGN KEY (idCl) REFERENCES Client_Anonyme (idCl),
	CONSTRAINT KCom PRIMARY KEY (mailR, idCl, dateCmd, hCmd, minCmd)
);

CREATE TABLE Sur_Place (
	mailR VARCHAR(30) NOT NULL,
	idCl INT NOT NULL,
	dateCmd DATE NOT NULL,
	hCmd INT NOT NULL,
	minCmd INT NOT NULL,
	nbCouvertsSP INT NOT NULL CONSTRAINT nbCouv CHECK(nbCouvertsSP > 0), /* Vérifier nbCouvertsSP <= nbPlacesR de la commande */
	hArrivSP INT NOT NULL, /* Vérifier hArrivSP après hCmd */
	minArrivSP INT NOT NULL,
	CONSTRAINT RCmdSp FOREIGN KEY (mailR, idCl, dateCmd, hCmd, minCmd) REFERENCES Commande (mailR, idCl, dateCmd, hCmd, minCmd) /* Vérifier typeCmd de la commande est sur place */
);

CREATE TABLE Livraison (
	dateCmd DATE NOT NULL,
	hCmd INT NOT NULL,
	minCmd INT NOT NULL,
	idCl INT NOT NULL,
	mailR VARCHAR(30) NOT NULL,
	adrLiv VARCHAR(30) NOT NULL,
	hLiv INT, /* Update hLiv quand statut passe en terminée uniquement et vérifier hLiv après hCmd */
	minLiv INT,
	CONSTRAINT RCmdLiv FOREIGN KEY (mailR, idCl, dateCmd, hCmd, minCmd) REFERENCES Commande (mailR, idCl, dateCmd, hCmd, minCmd) /* Vérifier typeCmd de la commande est livraison */
);

CREATE TABLE Evaluations (
	dateEv DATE NOT NULL,
	hEv INT NOT NULL,
	minEv INT NOT NULL,
	avisEv VARCHAR(100) DEFAULT 'Aucun avis écrit.',
	noteEv INT NOT NULL CONSTRAINT valNoteEv CHECK(noteEv IN (0, 1, 2, 3, 4, 5)),
	CONSTRAINT Kev PRIMARY KEY (dateEv, hEv, minEv,avisEv, noteEv)
);

CREATE TABLE APourEvaluation (
	dateEv DATE NOT NULL,
	hEv INT NOT NULL,
	minEv INT NOT NULL,
	avisEv VARCHAR(100) DEFAULT 'Aucun avis écrit.',
	noteEv INT NOT NULL CONSTRAINT valNote CHECK(noteEv IN (0, 1, 2, 3, 4, 5)),
	dateCmd DATE NOT NULL,
	hCmd INT NOT NULL,
	minCmd INT NOT NULL,
	idCl INT NOT NULL,
	mailR VARCHAR(30) NOT NULL,
	CONSTRAINT RCmdEval FOREIGN KEY (dateCmd, hCmd, minCmd, idCl, mailR) REFERENCES Commande (dateCmd, hCmd, minCmd, idCl, mailR), /* Vérifier statut de la commande pas annulée */
	CONSTRAINT REval FOREIGN KEY (dateEv, hEv, minEv,avisEv, noteEv) REFERENCES Evaluations (dateEv, hEv, minEv,avisEv, noteEv),
	CONSTRAINT KPape PRIMARY KEY (dateCmd, hCmd, minCmd, idCl, mailR)
);

CREATE TABLE ContientPlats (
	dateCmd DATE NOT NULL,
	hCmd INT NOT NULL,
	minCmd INT NOT NULL,
	idP INT NOT NULL,
	idCl INT NOT NULL,
	mailR VARCHAR(30) NOT NULL,
	quantite INT NOT NULL,
	CONSTRAINT RCmdContient FOREIGN KEY (dateCmd, hCmd, minCmd, idCl, mailR) REFERENCES Commande (dateCmd, hCmd, minCmd, idCl, mailR),
	CONSTRAINT RidP FOREIGN KEY (mailR, idP) REFERENCES Plat (mailR, idP),
	CONSTRAINT Kcp PRIMARY KEY (dateCmd, hCmd, minCmd, idCl, mailR, idP)
);

CREATE TABLE OuvrePendant (
	mailR VARCHAR(30) NOT NULL,
	jour VARCHAR(10) NOT NULL,
	hDebut INT NOT NULL,
	minDebut INT NOT NULL,
	hFin INT NOT NULL,
	minFin INT NOT NULL,
	CONSTRAINT Rhor FOREIGN KEY (jour, hDebut, minDebut, hFin, minFin) REFERENCES Horaires (jour, hDebut, minDebut, hFin, minFin),
	CONSTRAINT Kop PRIMARY KEY (mailR, jour, hDebut, minDebut, hFin, minFin)
);

CREATE TABLE FaitPartieCat (
	mailR VARCHAR(30) NOT NULL,
	nomCategorie VARCHAR(100) NOT NULL,
	CONSTRAINT Rmailrest FOREIGN KEY (mailR) REFERENCES Restaurant (mailR),
	CONSTRAINT Rnomcat FOREIGN KEY (nomCategorie) REFERENCES Categorie (nomCategorie),
	CONSTRAINT Kfpc PRIMARY KEY (mailR, nomCategorie)
);

CREATE TABLE ProposeTypeCmd (
	mailR VARCHAR(30) NOT NULL,
	nomTypeCmd VARCHAR(30) NOT NULL,
	CONSTRAINT Rmailr FOREIGN KEY (mailR) REFERENCES Restaurant (mailR),
	CONSTRAINT Rtype FOREIGN KEY (nomTypeCmd) REFERENCES TypeCmd (nomTypeCmd),
	CONSTRAINT Kptc PRIMARY KEY (mailR, nomTypeCmd)
);

CREATE TABLE PossedeAllergene (
	mailR VARCHAR(30) NOT NULL,
	idP INT NOT NULL,
	allergene VARCHAR(30) NOT NULL,
	CONSTRAINT Ridplat FOREIGN KEY (mailR, idP) REFERENCES Plat (mailR, idP),
	CONSTRAINT Rallergene FOREIGN KEY (allergene) REFERENCES Allergenes (allergene),
	CONSTRAINT Kpa PRIMARY KEY (mailR, idP, allergene)
);

CREATE TABLE APourCatParent(
	nomCategorieFils VARCHAR(100),
	nomCategorieParent VARCHAR(100),
	CONSTRAINT Rnomcatf FOREIGN KEY (nomCategorieFils) REFERENCES Categorie (nomCategorie),
	CONSTRAINT Rnomcatp FOREIGN KEY (nomCategorieParent) REFERENCES Categorie (nomCategorie),
	CONSTRAINT Kapcp PRIMARY KEY (nomCategorieFils, nomCategorieParent)
);

COMMIT;