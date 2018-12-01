package wiki.lostark.app.datas.mococo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MococoRegion {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("total")
    @Expose
    private Integer total;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}