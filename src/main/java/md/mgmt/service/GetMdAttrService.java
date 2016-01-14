package md.mgmt.service;

import md.mgmt.base.md.MdAttr;

import java.util.List;

/**
 * Created by Mr-yang on 16-1-14.
 */
public interface GetMdAttrService {

    public MdAttr getFileMdAttr(String fileCode);

    public List<MdAttr> getDirMdAttrList(String distrCode);

}
