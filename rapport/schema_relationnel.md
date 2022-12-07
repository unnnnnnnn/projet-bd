Restaurant(<u>mailR</u>, nomR, telR, adrR, nbPlacesR, presentationR, noteMoy)

Plat(<u>mailR, idP</u>, nomP, descP, prixP)  
  - (mailR) référence Restaurant

CLIENT(<u>mailCl</u>, mdpCl, nomCl, prenomCl, adrCl, idCl)  
  - (idCl) non nul et référence ClientAnonyme  
  - vérifier qu'un ClientAnonyme rend anonyme un seul client

ClientAnonyme(<u>idCl</u>)

COMMANDE(<u>mailR, idCl, dateCmd, heureCmd</u>, prixCmd, nomTypCmd, nomStatutCmd)  
  - (mailR) référence Restaurant  
  - (idCl) référence ClientAnonyme  
  - (nomTypCmd) non nul et référence TypeCmd  
  - (nomStatutCmd) non nul et référence StatutCmd

TYPECMD(nomTypCmd)

StatutCmd(nomTypCmd)

LIVRAISON(<u>(mailR, idCl, dateCmd, heureCmd)</u>, adrLiv, hLiv)  
  - (mailR, idCl, dateCmd, heureCmd) référence, Commande

SUR_PLACE(<u>(mailR, idCl, dateCmd, heureCmd)</u>, nbCouvertsSP, hArrivSP)  
  - (mailR, idCl, dateCmd, heureCmd) référence Commande

EVALUATION(dateEv, hEv, avisEv, noteEv)

CATEGORIE(nomCategorie)

HORAIRES(jour, debut, fin)

ALLERGENES(nomAllergene)

APourEvaluation(<u>mailR, idCl, dateCmd, heureCmd</u>, dateEv, hEv, avisEv, noteEv)  
  - (mailR, idCl, dateCmd, heureCmd) référence Commande  
  - (dateEv, hEv, avisEv, noteEv) référence Evaluation  
  - vérifier qu'une évaluation soit toujours utilisée

ContientPlats(<u>mailR, idCl, dateCmd, heureCmd, mailR, idP</u>, Quantite)  
  - (mailR, idCl, dateCmd, heureCmd) référence Commande  
  - (mailR, idP) référence Plat  
  - vérifier qu'une commande à au moins un plat

OuvrePendant(<u>mailR, jour, début, fin</u>)  
  - (mailR) référence Restaurant  
  - (jour, début, fin) référence Horaires

FaitPartieCat(<u>mailR, nomCategorie</u>)  
  - (mailR) référence Restaurant  
  - (nomCategorie) référence Categorie  
  - vérifier qu'un restaurant fait partie d'au moins une catégorie

ProposeTypeCmd(<u>mailR, nomTypCmd</u>)  
  - (mailR) référence Restaurant  
  - (nomTypCmd) référence TypeCmd

PossedeAllergene(<u>mailR, idP, nomAllergene</u>)  
  - (mailR, idP) référence Plat  
  - (nomAllergene) référence Allergenes

APourCatParent(<u>nomCategorieFils, nomCategorieParent</u>)  
  - (nomCategorieFils) référence Categorie  
  - (nomCategorieParent) référence Categorie  