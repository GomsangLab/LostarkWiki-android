package wiki.lostark.app.datas.characterprofile;

import java.util.ArrayList;

public class CharacterProfile {

    private String nickname;
    private String level;
    private String serverName;
    private String guildName;
    private String jobName;
    private String title;
    private String itemLevel;
    private String expeditionLevel;
    private String pvpLevel;
    private ArrayList<CharacterProfileEquipment> characterProfileEquipments;
    private ArrayList<CharacterProfileSkill> characterProfileSkills;

    private int totalEquipSlot;

    private ArrayList<CharacterProfileStat> basicStats = new ArrayList<>();
    private ArrayList<CharacterProfileStat> battleStats = new ArrayList<>();


    public ArrayList<CharacterProfileStat> getBasicStats() {
        return basicStats;
    }

    public void setBasicStats(ArrayList<CharacterProfileStat> basicStats) {
        this.basicStats = basicStats;
    }

    public ArrayList<CharacterProfileStat> getBattleStats() {
        return battleStats;
    }

    public void setBattleStats(ArrayList<CharacterProfileStat> battleStats) {
        this.battleStats = battleStats;
    }

    public ArrayList<CharacterProfileSkill> getCharacterProfileSkills() {
        return characterProfileSkills;
    }

    public void setCharacterProfileSkills(ArrayList<CharacterProfileSkill> characterProfileSkills) {
        this.characterProfileSkills = characterProfileSkills;
    }

    public ArrayList<CharacterProfileEquipment> getCharacterProfileEquipments() {
        return characterProfileEquipments;
    }

    public void setCharacterProfileEquipments(ArrayList<CharacterProfileEquipment> characterProfileEquipments) {
        this.characterProfileEquipments = characterProfileEquipments;
    }

    public String getNickname() {
        return nickname;
    }

    public int getTotalEquipSlot() {
        return totalEquipSlot;
    }

    public void setTotalEquipSlot(int totalEquipSlot) {
        this.totalEquipSlot = totalEquipSlot;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(String itemLevel) {
        this.itemLevel = itemLevel;
    }

    public String getExpeditionLevel() {
        return expeditionLevel;
    }

    public void setExpeditionLevel(String expeditionLevel) {
        this.expeditionLevel = expeditionLevel;
    }

    public String getPvpLevel() {
        return pvpLevel;
    }

    public void setPvpLevel(String pvpLevel) {
        this.pvpLevel = pvpLevel;
    }
}
