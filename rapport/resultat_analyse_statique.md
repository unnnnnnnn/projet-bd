## Propriétés:

\{mailR, nomR, telR, adrR, catR, nbPlacesR, presentationR, typCmdR, horaires, noteMoy,
idP, nomP, descP, prixP, allergènesP,
idCl, mailCl, mdpCl, nomCl, prenomCl, adrCl,
idCmd, dateCmd, hCmd, typCmd, prixCmd, statutCmd,
idLiv, adrLiv, textLiv, hLiv,
idSP, nbCouvertsSP, hSP,
dateEv, hEv, avisEv, noteEv\}

## Contraintes

### DF

mailR --> nomR, telR, adrR, nbPlacesR, presentationR, noteMoy \
mailR, idP --> nomP, descP, prixP \
idCl --> mailCl \
mailCl --> mdpCl, nomCl, prenomCl, adrCl \
idCmd --> dateCmd, hCmd, idCl, mailR, typCmd, prixCmd, statutCmd \
idLiv --> adrLiv, hLiv \
idSP --> nbCouvertsSP, hArrivSP\
idCmd, dateEv --> hEv, avisEv, noteEv

### Contraintes de valeur

typCmd $\in \text{\{sur place, à emporter, livraison\}}$ \
statutCmd $\in \text{\{attente de confirmation, validée, disponible, en livraison, annulée client, annulée restaurant, terminée\}}$\
Ext(idLiv) $\subseteq$ Ext(idCmd)\
Ext(idSP) $\subseteq$ Ext(idCmd)\
Ext(idSP) $\cap$ Ext(idLiv) $ = \emptyset$\
nbCouvertsSP > 0 \
$0 \leq {\tt noteEv} \leq 5$\
$0 \leq {\tt noteMoy} \leq 5$

### Contraintes de Multiplicité

mailR -->> catR, idP, typCmd \
mailR -/->> horaires, idCmd \
mailR, idP -/->> allergènesP \
idCl -/-> mailCl \
idCmd -->> idP \
idLiv -/-> textLiv, hLiv \
idCmd -/-> dateEv

### Autres contraintes

catR associé ou non à une autre catR parente. \
pour chaque restaurant somme des nbCouvertsSP dans chaque créneau inférieure à nbPlacesR. \
hSp compatible avec horaires du restaurant. \
horaires d'un restaturant ne se superposent pas. \
si statutCmd == "annulée client" alors pas possible d'associer eval à idCmd.
