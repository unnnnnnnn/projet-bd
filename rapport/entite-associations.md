## Schema Entité / Associations

```plantuml

 @startuml
 allow_mixing

    object RESTAURANT {
        mailR {pk}
        nomR
        telR
        adrR
        nbPlacesR
        presentationR
        noteMoy
    }

    RESTAURANT "1..1" *--- "0..*" PLAT : propose

    object PLAT {
        idP {pk}
        nomP
        descP
        prixP
    }

    object CLIENT {
        mailCl {pk}
        mdpCl
        nomCl
        prenomCl
        adrCl
    }


    object CLIENT_ANONYME {
        idCl {pk}
    }

    CLIENT "0..1" -down- "1" CLIENT_ANONYME : rend anonyme

    CLIENT_ANONYME "1..1" *-right- "0..*" COMMANDE : commande
    RESTAURANT "1..1" *--- "0..*" COMMANDE : prépare

    object COMMANDE {
        dateCmd {pk}
        heureCmd {pk}
        prixCmd
    }

    COMMANDE "0..*" -up- "1" TYPECMD : est de type
    COMMANDE "0..*" -- "1" STATUTCMD : a pour statut

    object TYPECMD {
        nomTypCmd
    }
    object STATUTCMD {
        nomStatutCmd
    }

    object LIVRAISON {
        adrLiv
        hLiv
    }
    
    object SUR_PLACE {
        nbCouvertsSP
        hArrivSP        
    }

    COMMANDE <|-up- LIVRAISON
    COMMANDE <|-up- SUR_PLACE : {Facultatif, OU}

    object EVALUATION {
        dateEv
        hEv
        avisEv
        noteEv
    }

    COMMANDE "1..*" --- "0..1" EVALUATION : a pour évaluation

    object CATEGORIE {
        nomCategorie
    }

    CATEGORIE "0..1" -- "0..*" CATEGORIE : a pour parent

    RESTAURANT "0..*" -up- "1..*" CATEGORIE : fait partie
    RESTAURANT "0..*" -- "1..*" TYPECMD : propose

    object HORAIRES {
        jour
        debut
        fin
    }

    RESTAURANT "0..*" -up- "0..*" HORAIRES : ouvre pendant

    object ALLERGENES {
        nomAllergene
    }

    PLAT "0..*" -- "0..*" ALLERGENES : possède

    object " " as _ {
        Quantite
    }

    <> diamond
    _ .up. diamond : contient
    diamond -left-"0..*" COMMANDE
    diamond -right-"1..*" PLAT
@enduml
```

## Contraintes non listées : 

Pour chaque restaurant, la somme des NbCouvertsSp liés ne doit pas dépasser nbPlacesR.\
hSp doit être compatible avec les horaires d'ouverture du restaurant.\
Les horaires d'un restaurant ne se superposent pas.\
Si le champ statutCmd d'une commande est à "annulé client" alors il n'est pas possible d'avoir une évaluation associée à la commande.\

## Explications des choix :

L'intégralité des données personnelles des clients sont enrigstrées dans une entité client qui est dissocié de l'entité clientAnonyme qui ne contient qu'un identifiant. Le reste de l'application interagit exclusivement avec l'entité clientAnonyme. Cette séparation devrait permettre d'oublier un client en toute simplicité.

Une commande est associée à au mois un plat, on considère que les commandes sur places ne sont pas simplement des réservation de table.

Un restaurant peut ne pas proposer de plats, il ne sera alors pas possible d'y effectuer des commandes. Cela simplifie la procédure d'enregistrement d'un restaurant.