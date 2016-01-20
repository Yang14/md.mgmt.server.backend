package md.mgmt.dao;

import org.rocksdb.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang on 16-1-20.
 */
public class RocksDBColumnTest {

    private static String db_path = "/data/rocksdb/test4";

    static {
        RocksDB.loadLibrary();
    }

    public static void testColumn() throws RocksDBException {
        System.out.println("RocksDBColumnFamilySample");
        RocksDB db = null;
        Options options = null;
        ColumnFamilyHandle columnFamilyHandle = null;
        WriteBatch wb = null;
        try {
            options = new Options().setCreateIfMissing(true);
            db = RocksDB.open(options, db_path);
            assert(db != null);

            // create column family
            columnFamilyHandle = db.createColumnFamily(
                    new ColumnFamilyDescriptor("new_cf".getBytes(),
                            new ColumnFamilyOptions()));
            assert(columnFamilyHandle != null);

        } finally {
            if (columnFamilyHandle != null) {
                columnFamilyHandle.dispose();
            }
            if (db != null) {
                db.close();
                db = null;
            }
            if (options != null) {
                options.dispose();
            }
        }

        // open DB with two column families
        List<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<ColumnFamilyDescriptor>();
        // have to open default column family
        columnFamilyDescriptors.add(new ColumnFamilyDescriptor(
                RocksDB.DEFAULT_COLUMN_FAMILY, new ColumnFamilyOptions()));
        // open the new one, too
        columnFamilyDescriptors.add(new ColumnFamilyDescriptor(
                "new_cf".getBytes(), new ColumnFamilyOptions()));
        List<ColumnFamilyHandle> columnFamilyHandles = new ArrayList<ColumnFamilyHandle>();
        try {
            db = RocksDB.open(new DBOptions(), db_path,
                    columnFamilyDescriptors, columnFamilyHandles);
            assert(db != null);

            // put and get from non-default column family
            db.put(columnFamilyHandles.get(0), new WriteOptions(),
                    "key".getBytes(), "value".getBytes());
            String value = new String(db.get(columnFamilyHandles.get(0),
                    "key".getBytes()));
            System.out.println("key:" +value);
            // atomic write
            wb = new WriteBatch();
            wb.put(columnFamilyHandles.get(0), "key2".getBytes(), "value2".getBytes());
            wb.put(columnFamilyHandles.get(1), "key3".getBytes(), "value3".getBytes());
            wb.remove(columnFamilyHandles.get(0), "key".getBytes());
            db.write(new WriteOptions(), wb);

            // drop column family
            db.dropColumnFamily(columnFamilyHandles.get(1));

        } finally {
            for (ColumnFamilyHandle handle : columnFamilyHandles){
                handle.dispose();
            }
            if (db != null) {
                db.close();
            }
            if (wb != null) {
                wb.dispose();
            }
        }
    }
    public static void main(String[] args) throws RocksDBException {
        testColumn();
    }
}
