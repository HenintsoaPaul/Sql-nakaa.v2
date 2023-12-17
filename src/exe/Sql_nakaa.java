package exe;

import java.util.Scanner;

public class Sql_nakaa {

    String storagePath = "/home/tsoa/Documents/semestre_3/Mr Tsinjo/Sql-nakaa/Sql-nakaa/data/";           // path to data storage
    String dbName = "first_db";             // name of the db
    String dbPath;                          // storagePath + dbName



    // Constructor
    public Sql_nakaa( String dbName, String storagePath )
            throws Exception {

        setDbName(dbName);
        setStoragePath(storagePath);
        setDbPath();
    }
    public Sql_nakaa() {
        setDbPath();
    }



    // Getters
    public String getStoragePath() {
        return this.storagePath;
    }
    public String getDbName() {
        return this.dbName;
    }
    public String getDbPath() {
        return this.dbPath;
    }




    // Setters
    private void setDbName(String dbName) throws Exception {
        if ( dbName == null )
            throw new Exception("Tsy azo atao NULL ny Base ampesaina nama!");
        this.dbName = dbName;
    }
    private void setStoragePath(String storagePath) throws Exception {
        if ( storagePath == null )
            throw new Exception("Tsy azo atao NULL ny storagePath anle Base nama!");
        this.storagePath = storagePath;
    }
    private void setDbPath() {
        this.dbPath = this.storagePath + this.dbName;
    }

    public void changeDb(String baseName) throws Exception {
        if ( baseName == null )
            throw new Exception("Tsy afaky miova base NULL anao nama!");

        this.dbPath = this.storagePath + baseName;
    }



    // USEFUL FUNCTIONS
    @SuppressWarnings("CallToPrintStackTrace")
    public void start() {
        Interpreter jean = new Interpreter(this);
        String header = "Bienvenu dans Sql-nakaa:\n"+
                "---------------------------------------------------------\n";
        System.out.print(header);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("SQL-nakaa ["+ this.getDbName()+ "] > ");
                String commande = scanner.nextLine();

                boolean toContinue = jean.translate( commande );

                if (!toContinue) break;
            }
            catch( Exception e ) {
                System.out.println("***Exception***");
                e.printStackTrace();
            }
        }

        String bye = "(Closing the program...)\n"+
                "---------------------------------------------------------\n";
        System.out.println( bye );
    }
}
