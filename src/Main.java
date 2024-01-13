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
            -


    select * from
        ( select * from tab1 where colA == 12 ) ,
        tab2
 */