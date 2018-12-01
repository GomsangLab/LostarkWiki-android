package wiki.lostark.app.datas.characterprofile;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CharacterProfileSkill {
    private String category;
    private String name;
    private String thumb;

    private int level;
    private int enableTier;

    private int[] selectedTripodTier;

    private ArrayList<ArrayList<Tripod>> tripods = new ArrayList<>();

    private double masteratio;

    private String cooltime;
    private String skillType;
    private String maxStact;

    private ArrayList<String> detailDescs = new ArrayList<>(); // 이외의 모든 정보들이 html 로 순차적으로 들어있음. (비정형으로 분류 불가)

    public ArrayList<String> getDetailDescs() {
        return detailDescs;
    }

    public void setDetailDescs(ArrayList<String> detailDescs) {
        this.detailDescs = detailDescs;
    }

    public String getMaxStact() {
        return maxStact;
    }

    public void setMaxStact(String maxStact) {
        this.maxStact = maxStact;
    }

    public String getSkillType() {
        return skillType;
    }

    public void setSkillType(String skillType) {
        this.skillType = skillType;
    }

    public String getCooltime() {
        return cooltime;
    }

    public void setCooltime(String cooltime) {
        this.cooltime = cooltime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getEnableTier() {
        return enableTier;
    }

    public void setEnableTier(int enableTier) {
        this.enableTier = enableTier;
    }

    public int[] getSelectedTripodTier() {
        return selectedTripodTier;
    }

    public void setSelectedTripodTier(int[] selectedTripodTier) {
        this.selectedTripodTier = selectedTripodTier;
    }


    public ArrayList<ArrayList<Tripod>> getTripods() {
        return tripods;
    }

    public void setTripods(ArrayList<ArrayList<Tripod>> tripods) {
        this.tripods = tripods;
    }

    public double getMasteratio() {
        return masteratio;
    }

    public void setMasteratio(double masteratio) {
        this.masteratio = masteratio;
    }

    public static class Tripod {
        private String desc;
        private String name;
        private String icon;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public static class TripodCustom {
        private String desc;
        private boolean isLock;
        private String name;
        private String tier;
        private int iconGrade;
        private String thumb;


        public int getIconGrade() {
            return iconGrade;
        }

        public void setIconGrade(int iconGrade) {
            this.iconGrade = iconGrade;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public boolean isLock() {
            return isLock;
        }

        public void setLock(boolean lock) {
            isLock = lock;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTier() {
            return tier;
        }

        public void setTier(String tier) {
            this.tier = tier;
        }
    }


}
