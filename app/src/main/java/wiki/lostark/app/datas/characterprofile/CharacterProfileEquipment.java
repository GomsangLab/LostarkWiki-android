package wiki.lostark.app.datas.characterprofile;

import java.util.ArrayList;

public class CharacterProfileEquipment {
    private String name;                // 이름
    private String thumb;               // 이미지
    private String sort;                // 분류 (희귀 머리 방어구, 희귀 어깨 방어구 등)
    private int grindLevel;             // 연마단계
    private String equiped;             // 장착중

    private String itemLevel = "";           // 아이템 레벨
    private String requireLevel = "";        // 필요 레벨

    private boolean isAvailable;        // 정보가 없는 경우 (아이템이 없는 흑백 상태)

    private int iconGrade;              // 아이콘 그레이드 (이미지 배경 색깔이 이거 따라 바뀜)

    private boolean isLifeTool = false; // 생활 도구 여부

    private ArrayList<String> detailDescs = new ArrayList<>(); // 이외의 모든 정보들이 html 로 순차적으로 들어있음. (비정형으로 분류 불가)

    public boolean isLifeTool() {
        return isLifeTool;
    }

    public void setLifeTool(boolean lifeTool) {
        isLifeTool = lifeTool;
    }

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
