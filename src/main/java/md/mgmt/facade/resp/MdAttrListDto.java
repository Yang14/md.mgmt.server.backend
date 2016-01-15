package md.mgmt.facade.resp;

import md.mgmt.base.md.MdAttr;

import java.util.List;

/**
 * Created by Mr-yang on 16-1-15.
 */
public class MdAttrListDto {

    private List<MdAttr> mdAttrList;

    public MdAttrListDto() {
    }

    public MdAttrListDto(List<MdAttr> mdAttrList) {
        this.mdAttrList = mdAttrList;
    }

    public List<MdAttr> getMdAttrList() {
        return mdAttrList;
    }

    public void setMdAttrList(List<MdAttr> mdAttrList) {
        this.mdAttrList = mdAttrList;
    }

    @Override
    public String toString() {
        return "MdAttrListDto{" +
                "mdAttrList=" + mdAttrList +
                '}';
    }
}
