import exe.Sql_nakaa;

public class Main {
    public static void main(String[] args) {
        new Sql_nakaa().start();
    }
}
//todo gestion des alias
/*

todo GESTION PARANTHESES:
    - toerana misy parentheses "SELECT ...":
        - WHERE
        - FROM
    - verification: parentheses mety
    - get all parentheses
    - execute en fonction priorite parentheses
    exemple:
        * CASE from:
        ---
        aboay aby ame test x ( ( select * from tab1 ) x tab2 ) x tab3
 */