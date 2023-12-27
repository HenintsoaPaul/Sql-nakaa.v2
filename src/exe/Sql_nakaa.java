package exe;

import java.util.Scanner;

public class Sql_nakaa {
    DataStorageManager dataStorageManager;

    public DataStorageManager getDataStorageManager() {
        return dataStorageManager;
    }

    public void setDataStorageManager(DataStorageManager dataStorageManager) {
        this.dataStorageManager = dataStorageManager;
    }

    public Sql_nakaa() {
        setDataStorageManager(new DataStorageManager());
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void start() {
        DataStorageManager dataStorageManager = this.getDataStorageManager();
        Interpreter jean = new Interpreter(dataStorageManager);
        String header = "Bienvenu dans Sql-nakaa:\n"+
                "---------------------------------------------------------\n";
        System.out.print(header);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("SQL-nakaa ["+ dataStorageManager.getDbName()+ "] > ");
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
