DATA TEST
* CREATE:

  *          MAMOROOGNA TABLE test ( etu String, vola double )
  *          MAMOROOGNA TABLE test2 ( etu String, age int )
  *          MAMOROOGNA TABLE test3 ( nom String, age int )
  *          MAMOROOGNA TABLE hopital ( id double, dtn dateHeure, doctor boolean )
  *          MAMOROOGNA TABLE biblio ( numero int, debutEmprunt date )
  *          MAMOROOGNA TABLE etudiants ( etu int, nom String, isCool boolean )

    * * DROP:
    *          FAFAY TABLE test
    *          FAFAY TABLE test2
    *          FAFAY TABLE test3
    *          FAFAY TABLE hopital
    *          FAFAY TABLE biblio
    *          FAFAY TABLE etudiants
    * <p>
    * * INSERT:
    *      - vers test:
    *          APIDIRO ( etu, vola ) AGNATY test VALUES ( Jean, 150 )
    *          APIDIRO ( etu, vola ) AGNATY test VALUES ( Atlas, 32 )
    *          APIDIRO ( etu, vola ) AGNATY test VALUES ( Sharon, 45 )
    *          APIDIRO ( etu, vola ) AGNATY test VALUES ( Kabisa, 12 )
    *          APIDIRO ( vola ) AGNATY test VALUES ( 12 )
    *          APIDIRO ( etu ) AGNATY test VALUES ( 226 )
    *     - vers biblio:
    *          APIDIRO ( numero, debutEmprunt ) AGNATY biblio VALUES ( 2, 12-05-2015 )
    *          APIDIRO ( numero, debutEmprunt ) AGNATY biblio VALUES ( 1, 20-03-2015 )