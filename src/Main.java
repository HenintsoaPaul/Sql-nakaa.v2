import exe.Sql_nakaa;

public class Main {
    public static void main(String[] args) {
        new Sql_nakaa().start();
    }
}
//todo gestion des alias
/*

todo GESTION PARANTHESES:
    - toerana misy parantheses "SELECT ...":
        - WHERE
        - FROM
    - verification: parantheses mety
    - get all parantheses
    - execute en fonction priorite paranthese
    exemple:
        * CASE from:
        ---
        select * from
            tab1 ,
            ( select * from t2 where t2.A < 25 ) ,
            t3 ,
            (
                ( select * from t5 where t5.A < 25 ) x
                (
                    ( select * from t4 ) teta[ t4.colN == t6.colK ]
                    t6
            )
                    ---
        -> ALGO:
            1- on prend la requete
            2- on split
            3- verification parentheses correctes
            4-1- En presence de parenth dans la requete
                a- on la divise en plusieurs sous-requete
                b- on les ranges par priorites en fonction des parenth
                c- on les executes dans l'ordre
                d- le resultat de chq sous-requete devient un parametre du requete parent
            4-2- Sinon
                execution de la requete en une seule fois

             < mety afaka atao recurssive io zvt io ngambany >


    select * from
        ( select * from tab1 where colA == 12 ) ,
        tab2 ;
 */