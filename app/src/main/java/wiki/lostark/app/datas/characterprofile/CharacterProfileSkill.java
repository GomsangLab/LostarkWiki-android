package wiki.lostark.app.datas.characterprofile;

import java.util.ArrayList;

public class CharacterProfileSkill {
    private String category;                                            // 스킬 종류? (체인, 홀딩)
    private String name;                                                // 스킬 이름
    private String thumb;                                               // 스킬 썸네일

    private int level;                                                  // 스킬 레벨
    private int enableTier;                                             // 몇티어 까지 열었는지, (-1 일 경우 모두 열리지 않음)

    private int[] selectedTripodTier;                                   // 티어별 선택한 스킬 {1,1,2} (0인 경우 선택하지않음 (해금안한건가?))

    private ArrayList<ArrayList<Tripod>> tripods = new ArrayList<>();   // 모든 트리팟 리스트 (tripods 0번째 리스트의 0번째 => 첫번째 티어 첫번째 스킬)
    private ArrayList<TripodCustom> tripodCustoms = new ArrayList<>();  // 사용자 중심적 트리팟 리스트 (티어별 선택한 스킬만 표시, 비해금시 비해금 메세지 소지)

    private double masteratio;                                          // 마스터 비율 ""들의 ""마스터 비율 ~ 처럼 표시 필요, 0 보다 작을경우 masterRatio 파싱 오류

    private String cooltime;                                            // 쿨타임
    private String skillType;                                           // 스킬 타입 [스택트 스킬], [일반 스킬] 등
    private String middleText;                                          // 중간 텍스트, - 주로 최대 스택트가 들어감

    private ArrayList<String> detailDescs = new ArrayList<>(); // 이외의 모든 정보들이 html 로 순차적으로 들어있음. (비정형으로 분류 불가)

    public ArrayList<String> getDetailDescs() {
        return detailDescs;
    }

    public void setDetailDescs(ArrayList<String> detailDescs) {
        this.detailDescs = detailDescs;
    }

    public ArrayList<TripodCustom> getTripodCustoms() {
        return tripodCustoms;
    }

    public void setTripodCustoms(ArrayList<TripodCustom> tripodCustoms) {
        this.tripodCustoms = tripodCustoms;
    }

    public String getMiddleText() {
        return middleText;
    }

    public void setMiddleText(String middleText) {
        this.middleText = middleText;
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
        private String desc;    // 스킬 설명
        private String name;    // 스킬 이름
        private String icon;    // 스킬 아이콘 (티어 해금 안했을 경우 흑백처리 부탁)

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
        private String desc;    // 스킬 설명
        private boolean isLock; // 해금 여부

        // 비해금시 비어있는 값들입니다.
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
