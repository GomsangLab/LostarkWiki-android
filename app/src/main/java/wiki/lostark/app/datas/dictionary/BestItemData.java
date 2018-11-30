package wiki.lostark.app.datas.dictionary;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BestItemData {

    @SerializedName("totalCount")
    @Expose
    private Long totalCount;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
