package wiki.lostark.app.datas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MococoCategory {

    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("datas")
    @Expose
    private List<MococoRegion> datas = null;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<MococoRegion> getDatas() {
        return datas;
    }

    public void setDatas(List<MococoRegion> datas) {
        this.datas = datas;
    }
}
