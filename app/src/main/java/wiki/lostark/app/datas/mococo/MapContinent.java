package wiki.lostark.app.datas.mococo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MapContinent {

    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("datas")
    @Expose
    private List<MapRegion> datas = null;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<MapRegion> getDatas() {
        return datas;
    }

    public void setDatas(List<MapRegion> datas) {
        this.datas = datas;
    }
}