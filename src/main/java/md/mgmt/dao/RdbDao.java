package md.mgmt.dao;

import md.mgmt.base.md.MdAttr;
import md.mgmt.base.md.MdIndex;

import java.util.List;

/**
 * Created by Mr-yang on 16-1-14.
 */
public interface RdbDao {

    /**
     * 设置或创建哈希桶，与分布编码相关的所有文件编码
     */
    public boolean setOrCreateHashBucket(String distrCode, String fileCode);

    public boolean putMdAttr(String fileCode, MdAttr mdAttr);

    public MdAttr getFileMdAttr(String fileCode);

    public List<MdAttr> getDirMdAttrList(String distrCode);
}
