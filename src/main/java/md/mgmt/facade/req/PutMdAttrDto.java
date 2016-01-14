package md.mgmt.facade.req;

import md.mgmt.base.md.ExactCode;
import md.mgmt.base.md.MdAttr;

/**
 * Created by Mr-yang on 16-1-14.
 */
public class PutMdAttrDto {

    private ExactCode exactCode;

    private MdAttr mdAttr;

    public PutMdAttrDto() {
    }

    public PutMdAttrDto(ExactCode exactCode, MdAttr mdAttr) {
        this.exactCode = exactCode;
        this.mdAttr = mdAttr;
    }

    public ExactCode getExactCode() {
        return exactCode;
    }

    public void setExactCode(ExactCode exactCode) {
        this.exactCode = exactCode;
    }

    public MdAttr getMdAttr() {
        return mdAttr;
    }

    public void setMdAttr(MdAttr mdAttr) {
        this.mdAttr = mdAttr;
    }

    @Override
    public String toString() {
        return "PutMdAttrDto{" +
                "exactCode=" + exactCode +
                ", mdAttr=" + mdAttr +
                '}';
    }
}
