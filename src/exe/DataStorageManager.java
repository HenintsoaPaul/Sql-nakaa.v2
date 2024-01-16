package exe;

public class DataStorageManager {
    // path to data storage
    private String storagePath = "/home/tsoa/Documents/semestre_3/Mr Tsinjo/Sql-nakaa/Sql-nakaa/data/";
    // name of the db
    private String dbName = "first_db";
    // storagePath + dbName
    private String dbPath;



    // CONSTRUCTOR
    public DataStorageManager() {
        this.setDbPath();
    }



    // GETTERS AND SETTERS
    public String getStoragePath() {
        return storagePath;
    }

    @SuppressWarnings("unused")
    private void setStoragePath(String storagePath) throws Exception {
        if ( storagePath == null )
            throw new Exception("Tsy azo atao NULL ny storagePath anle Base nama!");
        this.storagePath = storagePath;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) throws Exception {
        if ( dbName == null )
            throw new Exception("Tsy azo atao NULL ny Base ampesaina nama!");
        this.dbName = dbName;
    }

    public String getDbPath() {
        return dbPath;
    }

    public void setDbPath() {
        this.dbPath = this.storagePath + this.dbName;
    }

    public void changeDb(String dbName) throws Exception {
        if ( dbName == null )
            throw new Exception("Tsy afaky miova base NULL anao nama!");

        String oldDbStoragePath = this.getStoragePath();
        String newDbName = oldDbStoragePath + dbName;
        this.setDbName(newDbName);
        this.setDbPath();

        System.out.println("Yesss, nifindra ame Base '"
                + dbName + "' itsika zao nama!");
    }
}
