package wiki.lostark.app.datas.characterprofile;

import java.util.ArrayList;

public class CharacterProfileEquipment {

    private String name;
    private String thumb;
    private String sort;
    private int grindLevel;
    private String equiped;

    private String itemLevel = "";
    private String requireLevel = "";

    private boolean isAvailable;

    private int iconGrade;

    private boolean isLifeTool = false;

    public boolean isLifeTool() {
        return isLifeTool;
    }

    public void setLifeTool(boolean lifeTool) {
        isLifeTool = lifeTool;
    }

    private ArrayList<String> detailDescs = new ArrayList<>();

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getGrindLevel() {
        return grindLevel;
    }

    public void setGrindLevel(int grindLevel) {
        this.grindLevel = grindLevel;
    }

    public String getEquiped() {
        return equiped;
    }

    public void setEquiped(String equiped) {
        this.equiped = equiped;
    }

    public int getIconGrade() {
        return iconGrade;
    }

    public void setIconGrade(int iconGrade) {
        this.iconGrade = iconGrade;
    }

    public String getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(String itemLevel) {
        this.itemLevel = itemLevel;
    }

    public String getRequireLevel() {
        return requireLevel;
    }

    public void setRequireLevel(String requireLevel) {
        this.requireLevel = requireLevel;
    }

    public ArrayList<String> getDetailDescs() {
        return detailDescs;
    }

    public void setDetailDescs(ArrayList<String> detailDescs) {
        this.detailDescs = detailDescs;
    }
}