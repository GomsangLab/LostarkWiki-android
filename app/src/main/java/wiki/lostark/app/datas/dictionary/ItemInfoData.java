package wiki.lostark.app.datas.dictionary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemInfoData {

    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("categoryType")
    @Expose
    private String categoryType;
    @SerializedName("evolution")
    @Expose
    private String evolution;
    @SerializedName("itemId")
    @Expose
    private String itemId;
    @SerializedName("itemLevel")
    @Expose
    private String itemLevel;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("maxQualityIndex")
    @Expose
    private String maxQualityIndex;
    @SerializedName("minQualityIndex")
    @Expose
    private String minQualityIndex;
    @SerializedName("polishing")
    @Expose
    private String polishing;
    @SerializedName("quality")
    @Expose
    private String quality;
    @SerializedName("setItem")
    @Expose
    private String setItem;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getEvolution() {
        return evolution;
    }

    public void setEvolution(String evolution) {
        this.evolution = evolution;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(String itemLevel) {
        this.itemLevel = itemLevel;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getMaxQualityIndex() {
        return maxQualityIndex;
    }

    public void setMaxQualityIndex(String maxQualityIndex) {
        this.maxQualityIndex = maxQualityIndex;
    }

    public String getMinQualityIndex() {
        return minQualityIndex;
    }

    public void setMinQualityIndex(String minQualityIndex) {
        this.minQualityIndex = minQualityIndex;
    }

    public String getPolishing() {
        return polishing;
    }

    public void setPolishing(String polishing) {
        this.polishing = polishing;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getSetItem() {
        return setItem;
    }

    public void setSetItem(String setItem) {
        this.setItem = setItem;
    }

}
