package md.mgmt.dao.impl;

import com.alibaba.fastjson.JSON;
import md.mgmt.base.md.MdAttr;
import md.mgmt.dao.RdbDao;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr-yang on 16-1-14.
 */
@Component
public class RdbDaoImpl implements RdbDao {
    private static Logger logger = LoggerFactory.getLogger(RdbDaoImpl.class);

    private static final String DB_PATH = "/data/backend";
    private static Options options = new Options().setCreateIfMissing(true);
    private static RocksDB db = null;
    private static final String RDB_DECODE = "UTF8";

    static {
        RocksDB.loadLibrary();
        try {
            db = RocksDB.open(options, DB_PATH);
        } catch (RocksDBException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public boolean setOrCreateHashBucket(String distrCode, String fileCode) {
        List<String> fileCodeList;
        try {
            byte[] bytes = db.get(distrCode.getBytes(RDB_DECODE));
            if (bytes != null) {
                fileCodeList = JSON.parseObject(new String(bytes, RDB_DECODE), List.class);
            } else {
                fileCodeList = new ArrayList<String>();
            }
            fileCodeList.add(fileCode);
            db.put(String.valueOf(distrCode).getBytes(), JSON.toJSONString(fileCodeList).getBytes());
            return true;
        } catch (Exception e) {
            logger.error(String.format("[ERROR] caught the unexpected exception -- %s\n", e));
        }
        return false;
    }

    @Override
    public boolean putMdAttr(String fileCode, MdAttr mdAttr) {
        try {
            db.put(fileCode.getBytes(RDB_DECODE), JSON.toJSONString(mdAttr).getBytes(RDB_DECODE));
            return true;
        } catch (Exception e) {
            logger.error(String.format("[ERROR] caught the unexpected exception -- %s\n", e));
        }
        return false;
    }

    @Override
    public MdAttr getFileMdAttr(String fileCode) {
        try {
            byte[] attrBytes = db.get(fileCode.getBytes(RDB_DECODE));
            if (attrBytes != null) {
                return JSON.parseObject(new String(attrBytes, RDB_DECODE), MdAttr.class);
            }
        } catch (Exception e) {
            logger.error(String.format("[ERROR] caught the unexpected exception -- %s\n", e));
        }
        return null;
    }

    @Override
    public List<MdAttr> getDirMdAttrList(String distrCode) {
        List<MdAttr> mdAttrs = new ArrayList<MdAttr>();
        try {
            byte[] bytes = db.get(distrCode.getBytes(RDB_DECODE));
            if (bytes != null) {
                List<String> fileCodeList = JSON.parseObject(new String(bytes, RDB_DECODE), List.class);
                for (String fileCode : fileCodeList) {
                    byte[] attrBytes = db.get(fileCode.getBytes(RDB_DECODE));
                    if (attrBytes != null) {
                        mdAttrs.add(JSON.parseObject(new String(attrBytes, RDB_DECODE), MdAttr.class));
                    }
                }
                return mdAttrs;
            }
        } catch (Exception e) {
            logger.error(String.format("[ERROR] caught the unexpected exception -- %s\n", e));
        }
        return null;
    }
}
