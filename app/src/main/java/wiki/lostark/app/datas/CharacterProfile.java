package wiki.lostark.app.datas;

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

    public ArrayList<CharacterProfileEquipment> getCharacterProfileEquipments() {
        return characterProfileEquipments;
    }

    public void setCharacterProfileEquipments(ArrayList<CharacterProfileEquipment> characterProfileEquipments) {
        this.characterProfileEquipments = characterProfileEquipments;
    }

    public String getNickname() {
        return nickname;
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
