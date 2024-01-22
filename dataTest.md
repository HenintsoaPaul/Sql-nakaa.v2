DATA TEST
* CREATE:

  *          MAMOROOGNA TABLE test ( etu String, vola double )
  *          MAMOROOGNA TABLE test2 ( etu String, age int )
  *          MAMOROOGNA TABLE test3 ( nom String, age int )
  *          MAMOROOGNA TABLE hopital ( id double, dtn dateHeure, doctor boolean )
  *          MAMOROOGNA TABLE biblio ( numero int, debutEmprunt date )
  *          MAMOROOGNA TABLE etudiants ( etu int, nom String, isCool boolean )
  *          MAMOROOGNA TABLE test4 ( etu String )
    MAMOROOGNA TABLE banque ( idClient int, nom String, vola double, age int )

      * * DROP:
      *          FAFAY TABLE test
      *          FAFAY TABLE test2
      *          FAFAY TABLE test3
      *          FAFAY TABLE hopital
      *          FAFAY TABLE biblio
      *          FAFAY TABLE etudiants
      * 
      * * INSERT:
  - vers test:
    APIDIRO ( etu, vola ) AGNATY test VALUES ( Jean, 150 )
    APIDIRO ( etu, vola ) AGNATY test VALUES ( Atlas, 32 )
    APIDIRO ( etu, vola ) AGNATY test VALUES ( Sharon, 45 )
    APIDIRO ( etu, vola ) AGNATY test VALUES ( Tsoa, 450 )
    APIDIRO ( etu, vola ) AGNATY test VALUES ( Kabisa, 12 )
    APIDIRO ( vola ) AGNATY test VALUES ( 12 )
    APIDIRO ( etu ) AGNATY test VALUES ( 226 )

  - vers biblio:
  APIDIRO ( numero, debutEmprunt ) AGNATY biblio VALUES ( 2, 12-05-2015 )
  APIDIRO ( numero, debutEmprunt ) AGNATY biblio VALUES ( 1, 20-03-2015 )

  - vers test4 :
  APIDIRO ( etu ) AGNATY test4 VALUES ( Atlas )
  APIDIRO ( etu ) AGNATY test4 VALUES ( Georges )
  APIDIRO ( etu ) AGNATY test4 VALUES ( Ckay )
  APIDIRO ( etu ) AGNATY test4 VALUES ( Jean )

  - vers banque :
  APIDIRO ( idClient, nom, vola, age ) AGNATY banque VALUES ( 1, Atlas, 250, 19 )
  APIDIRO ( idClient, nom, vola, age ) AGNATY banque VALUES ( 2, Tsoa, 150, 19 )
  APIDIRO ( idClient, nom, vola, age ) AGNATY banque VALUES ( 3, Hawks, 450, 24 )

* teta join:
  aboay aby ame test teta[ colA == colB ] test2 x test4

-- TEST PARENTHESES:

-> query:
aboay aby ame test
aboay aby ame itu

aboay aby ame itu refa ( ( age == 18 ) na ( age >= 50 ) )
aboay aby ame itu refa ( ( ( nom == Atlas ) na ( nom == Jean ) ) ary ( age < 30 ) )

aboay aby ame ( ( aboay aby ame test4 ) x itu ) , test
aboay aby ame ( aboay aby ame itu refa ( age < 19 ) ) x ( aboay aby ame test refa ( vola == 150 ) )
aboay aby ame ( aboay aby ame itu refa ( age < 19 ) ) x ( aboay aby ame test refa ( vola <= 150 ) ) refa vola == 150

-> itu:
APIDIRO ( nom, age ) AGNATY itu VALUES ( Atlas, 18 )
APIDIRO ( nom, age ) AGNATY itu VALUES ( Jean, 35 )
APIDIRO ( nom, age ) AGNATY itu VALUES ( Jeanne, 26 )
APIDIRO ( nom, age ) AGNATY itu VALUES ( Malko, 51 )
APIDIRO ( nom, age ) AGNATY itu VALUES ( Kevin, 19 )

APIDIRO ( etu, age ) AGNATY test2 VALUES ( Jean, 19 )
APIDIRO ( etu, age ) AGNATY test2 VALUES ( Atlas, 19 )