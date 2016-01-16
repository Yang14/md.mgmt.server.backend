package md.mgmt.facade.req;

/**
 * Created by Mr-yang on 16-1-16.
 */
public class RenameMdAttrDto {

    private String fileCode;
    private String newName;

    public RenameMdAttrDto() {
    }

    public RenameMdAttrDto(String fileCode, String newName) {
        this.fileCode = fileCode;
        this.newName = newName;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    @Override
    public String toString() {
        return "RenameMdAttrDto{" +
                "fileCode='" + fileCode + '\'' +
                ", newName='" + newName + '\'' +
                '}';
    }
}

