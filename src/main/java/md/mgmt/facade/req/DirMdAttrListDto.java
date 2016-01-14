package md.mgmt.facade.req;

/**
 * Created by Mr-yang on 16-1-14.
 */
@Deprecated
public class DirMdAttrListDto {

    private Long distrCode;

    public DirMdAttrListDto() {
    }

    public DirMdAttrListDto(Long distrCode) {
        this.distrCode = distrCode;
    }

    public Long getDistrCode() {
        return distrCode;
    }

    public void setDistrCode(Long distrCode) {
        this.distrCode = distrCode;
    }

    @Override
    public String toString() {
        return "DirMdAttrListDto{" +
                "distrCode=" + distrCode +
                '}';
    }
}
