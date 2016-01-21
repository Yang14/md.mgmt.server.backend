package md.mgmt.connPool;

/**
 * Created by Mr-yang on 16-1-21.
 */
public class ConnectionFactory {

    private final ConnectionPool pool;
    private final String DB_PATH;

    public ConnectionFactory(String dbPath, ConnectionPool pool) {
        this.DB_PATH = dbPath;
        this.pool = pool;
    }

    public Connection getConnection(){
        return pool.getConnection();
    }

    public static void main(String[] args){

    }


}
