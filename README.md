Sql-nakaa : SGBD perso

# A propos:
* chaque commande doit se terminer par un point virgule ";".
(pas encore operationnel-actuellement les commandes se terminent par la touche ENTREE)
* les commandes suivantes sont "Case Sensitive".
    → dans le futur les commandes ne devront plus etre case sensitive.
* les parantheses doivent toujours etre separees des mots par un espace
* les commandes sont en langue "Malagasy" dialecte "Antesaka".

# Commandes:
* ## MAMOROOGNA ~ CREATE:
        MAMOROOGNA TABLE t1 ( etu int, nom String )

* ## DIVEK ~ DIVISION EUCLIDIENNE
        DIVEK tab1 / tab2
  
* ## ABOAY ~ SELECT:
        ABOAY ... AME t1
    - ### columns:
          ABOAY aby AME t1
          ABOAY etu, nom AME t1
          ABOAY etu AME t1
    - ### lines -- WHERE:
      N.B. : Seuls les conditions numerics(==, <>, <, >, <=, >=) sont encore possibles.
      Les conditions(cond) dans les illustrations suivantes sont toutes des conditions numerics.
    
          ABOAY aby AME t1 REFA <conditionNumeric>
      - #### operators:
            ABOAY aby AME t1 REFA <cond1> ARY <cond2>
            ABOAY aby AME t1 REFA <cond1> NA <cond2>
            ABOAY aby AME t1 REFA <cond1> NA <cond2> ARY <cond3> ...
        - #### not:
              ABOAY aby AME t1 REFA tsy <cond>
              ABOAY aby AME t1 REFA tsy <cond1> NA <cond2>
              ABOAY aby AME t1 REFA tsy <cond1> ARY tsy <cond2> ...
          - ### relations -- JOIN:
              - #### crossJoin:
                      ABOAY aby AME t1 x t2 
                      ABOAY aby AME t1 x t2 x t3 ...
              - #### naturalJoin:
                      ABOAY aby AME t1 , t2
                      ABOAY aby AME t1 , t2 , t3
              - #### tetaJoin:
              N.B. : tetaCond [valueCol1 operator valueCol2] will be a numerical operation:
              - maybe simple: 
                - columnA == columnB
                - columnA < columnB
                - ...
              - or not very simple: 
                - columnA + 1 == columnB
                - columnA < columnB * 12
                - 2 + columnA - 15 / 5 <> 1 - columnB * 2 

                          ABOAY aby AME t1 teta[t1.columnA <tetaCond> t2.columnB] t2