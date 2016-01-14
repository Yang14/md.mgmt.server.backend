package md.mgmt.facade.req;

/**
 * Created by Mr-yang on 16-1-14.
 */
@Deprecated
public class FileMdAttrDto {

    private String fileCode;

    public FileMdAttrDto() {
    }

    public FileMdAttrDto(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    @Override
    public String toString() {
        return "FileMdAttrDto{" +
                "fileCode='" + fileCode + '\'' +
                '}';
    }
}
