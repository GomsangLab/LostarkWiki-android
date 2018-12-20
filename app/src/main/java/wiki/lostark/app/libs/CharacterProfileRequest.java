package wiki.lostark.app.libs;

import android.os.AsyncTask;
import android.provider.FontRequest;
import android.util.Log;

import com.bumptech.glide.util.CachedHashCodeArrayMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import wiki.lostark.app.datas.characterprofile.CharacterProfile;
import wiki.lostark.app.datas.characterprofile.CharacterProfileEquipment;
import wiki.lostark.app.datas.characterprofile.CharacterProfileSkill;
import wiki.lostark.app.datas.characterprofile.CharacterProfileStat;

public class CharacterProfileRequest extends AsyncTask<String, String, CharacterProfileRequest.RequestResult> {

    public static final String URL_CHARPROFILE = "http://lostark.game.onstove.com/Profile/Character/";
    public static final String URL_CDN_LOSTARK = "http://cdn-lostark.game.onstove.com/";

    private String requestUsername;
    private CharacterProfileResponse characterProfileResponse = null;

    public CharacterProfileRequest(String requestUsername, CharacterProfileResponse characterProfileResponse) {
        this.requestUsername = requestUsername;
        this.characterProfileResponse = characterProfileResponse;
        if (characterProfileResponse == null) {
            throw new RuntimeException("Response callback should be not null.");
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected RequestResult doInBackground(String... strings) {
        RequestResult requestResult = new RequestResult();
        try {
            CharacterProfile characterProfile = new CharacterProfile();
            // 모든 html 파싱
            Document wholeDocument = Jsoup.connect(URL_CHARPROFILE + requestUsername)
                    .header("Accept-Encoding", "gzip, deflate")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                    .maxBodySize(0)
                    .timeout(600000)
                    .get();
            String wholeHtmlStr = wholeDocument.toString();

            // 캐릭터 정보가 없다는 문자열 포함시 캐릭터 정보가 없는것으로 판단.
            if (wholeHtmlStr.contains("캐릭터 정보가 없습니다") && wholeHtmlStr.contains("캐릭터명을 확인해주세요.")) {
                requestResult.setMsg("캐릭터 정보가 없습니다. 캐릭터명을 확인해주세요.");
                requestResult.setSuccessful(false);
                return requestResult;
            }

            // 예상 점검시간이 포함되어 있을경우, 점검중인것을 ㅗ판단
            if (wholeHtmlStr.contains("예상 점검시간")) {
                requestResult.setMsg("현재 홈페이지 점검중입니다. 잠시후에 다시 시도해주세요.");
                requestResult.setSuccessful(false);
                return requestResult;
            }

            // 유저의 기본적인 정보들을 가져옴
            final Element baiscProfileElement = wholeDocument.getElementsByClass("profile-character").get(0);
            final Element serverNameElement = baiscProfileElement.getElementsByClass("game-info__server").get(0).getAllElements().get(2);
            characterProfile.setServerName(serverNameElement.text());
            final Element guildNameElement = baiscProfileElement.getElementsByClass("game-info__guild").get(0).getAllElements().get(2);
            characterProfile.setGuildName(guildNameElement.text());
            final Element jobNameElement = baiscProfileElement.getElementsByClass("game-info__class").get(0).getAllElements().get(2);
            characterProfile.setJobName(jobNameElement.text());
            final Element designationElement = baiscProfileElement.getElementsByClass("game-info__title").get(0).getAllElements().get(2);
            characterProfile.setTitle(designationElement.text());

            final Element itemLevelElement = baiscProfileElement.getElementsByClass("level-info__item").get(0).getAllElements().get(2);
            characterProfile.setItemLevel(itemLevelElement.text());
            final Element expeditionLevelElement = baiscProfileElement.getElementsByClass("level-info__expedition").get(0).getAllElements().get(2);
            characterProfile.setExpeditionLevel(expeditionLevelElement.text());
            final Element pvpLevelElement = baiscProfileElement.getElementsByClass("level-info__pvp").get(0).getAllElements().get(2);
            characterProfile.setPvpLevel(pvpLevelElement.text());

            // 이름과 레벨을 한 문자열로 가져왔기 때문에 나누어 저장합니다.
            final Element basicProfile = baiscProfileElement.getElementsByClass("profile-character").select("h3").get(0);
            String[] basicinfo = basicProfile.text().split("\\s+");
            characterProfile.setNickname(basicinfo[1]);
            characterProfile.setLevel(basicinfo[0]);

            // html json 단 가져오기 ($.Profile)
            JSONObject profilePartJSON = getProfilePart(wholeHtmlStr);

            // html 단 모든  스탯정보들을 가져옴.
            final Elements basicStatsElements = wholeDocument.getElementsByClass("profile-ability-basic").get(0).selectFirst("ul").children();
            final Elements battleStatsElements = wholeDocument.getElementsByClass("profile-ability-battle").get(0).selectFirst("ul").children();

            final ArrayList<CharacterProfileStat> basicStats = new ArrayList<>();
            final ArrayList<CharacterProfileStat> battleStats = new ArrayList<>();

            for (Element basicStat : basicStatsElements)
                basicStats.add(analyzeStat(new CharacterProfileStat(), basicStat));
            for (Element battleStat : battleStatsElements)
                battleStats.add(analyzeStat(new CharacterProfileStat(), battleStat));

            characterProfile.setBasicStats(basicStats);
            characterProfile.setBattleStats(battleStats);

            // html 단 모든 스킬 DIV 들을 가져옴.
            final Elements skillsOfHtmlPart = wholeDocument.getElementsByClass("profile-skill__list").get(0).children();
            final ArrayList<CharacterProfileSkill> characterProfileSkills = new ArrayList<>();

            // skill 하나씩 돌며 정보 가공
            for (Element skillElement : skillsOfHtmlPart) {
                final CharacterProfileSkill characterProfileSkill = new CharacterProfileSkill();
                characterProfileSkill.setName(skillElement.getElementsByClass("profile-skill__title").get(0).text());
                characterProfileSkill.setCategory(skillElement.getElementsByClass("profile-skill__category").get(0).html());
                characterProfileSkill.setJobname(characterProfile.getJobName());

                try {
                    JSONObject skillJSONPartOfHtml = new JSONObject((skillElement
                            .getElementsByClass("button button--profile-skill")
                            .get(0)
                            .attributes()
                            .get("data-skill")
                            .replaceAll("&uot;", "\"") // hard-coded exception handling
                            .replaceAll("&ot;", "\"")));

                    characterProfileSkill.setMasteratio(skillJSONPartOfHtml.getDouble("masterRatio"));
                } catch (Exception e) {
                    characterProfileSkill.setMasteratio(-1);
                }

                final String jsonPartId = skillElement.getElementsByClass("profile-skill__slot").get(0).attributes().get("data-item");
                analyzeSkill(characterProfileSkill, profilePartJSON.getJSONObject(jsonPartId), profilePartJSON.getJSONObject(jsonPartId.replace("Tooltip_Skill_", "SkillBookInfo_")));
                characterProfileSkills.add(characterProfileSkill);
            }
            characterProfile.setCharacterProfileSkills(characterProfileSkills);


            final ArrayList<CharacterProfileEquipment> characterProfileEquipments = new ArrayList<>();
            // 장비와 아바타 장비 모두 검색하기 위해 1회 반복합니다.
            for (String slotsClassName : new String[]{"profile-equipment__slot", "profile-avatar__slot"}) {
                // 총 equip 슬롯을 분석합니다.
                final Element slotsElement = wholeDocument.getElementsByClass(slotsClassName).get(0);
                final int totalEquipSlot = slotsElement.select("div").size() - 1; // 부모 div 개수를 제외하기 위해 -1
                characterProfile.setTotalEquipSlot(totalEquipSlot);
                // 총 equip 개수 만큼 돌며, 소지 중인 equip 은 상세 정보를 입력합니다.
                for (int ei = 1; ei <= totalEquipSlot; ei++) {
                    // 아이템 처리 예외 처리, 상하의 공용 의복일때 마지막 번째 아이템이 비는 오류 있음.
                    try {
                        final Element element = slotsElement.getElementsByClass("slot" + ei).get(0);
                        CharacterProfileEquipment characterProfileEquipment = new CharacterProfileEquipment();

                        // data-item 을 소지하고 있는지 (data-item attribute 가 존재하는 경우는 아이템을 소지하고 있음을 뜻합니다.) data-item 의 value 는 json 단의 상세 아이템 설명 key 임.
                        if (element.attributes().hasKey("data-item")) {
                            characterProfileEquipment.setAvailable(true);
                            JSONObject eachEquipJSON = profilePartJSON.getJSONObject(element.attributes().get("data-item"));
                            if (element.attributes().get("data-item").contains("LifeTool"))
                                characterProfileEquipment.setLifeTool(true);
                            analyzeEquipment(characterProfileEquipment, eachEquipJSON, slotsClassName.equals("profile-avatar__slot"));
                        } else {
                            characterProfileEquipment.setAvailable(false);
                            characterProfileEquipment.setThumb("http://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/" +
                                    "bg_" + (slotsClassName.equals("profile-equipment__slot") ? "equipment" : "avatar") + "_slot" + ei + ".png");
                        }
                        characterProfileEquipments.add(characterProfileEquipment);
                    }catch (Exception e){

                    }
                }
            }

            characterProfile.setCharacterProfileEquipments(characterProfileEquipments);

            requestResult.setCharacterProfile(characterProfile);
            requestResult.setSuccessful(true);
            return requestResult;
        } catch (Exception e) {
            e.printStackTrace();
            requestResult.setMsg(e.getLocalizedMessage());
            requestResult.setSuccessful(false);
        }
        return requestResult;
    }

    @Override
    protected void onPostExecute(RequestResult requestResult) {
        super.onPostExecute(requestResult);
        characterProfileResponse.onResponse(requestResult);
    }

    private CharacterProfileStat analyzeStat(CharacterProfileStat characterProfileStat, Element element) {
        Elements basicInfos = element.select("span");
        characterProfileStat.setName(basicInfos.get(0).text());
        characterProfileStat.setValue(basicInfos.get(1).text());
        characterProfileStat.setDescription(element.selectFirst("ul").html());
        return characterProfileStat;
    }

    private void analyzeSkill(CharacterProfileSkill characterProfileSkill, JSONObject eachSkillJSON /*Tooltip_Skill*/, JSONObject skillBookJSON /*SkillBookInfo*/) throws JSONException {
        characterProfileSkill.setEnableTier(skillBookJSON.getInt("EnableTier"));
        characterProfileSkill.setLevel(skillBookJSON.getInt("Level"));
        JSONArray selectedTripod = skillBookJSON.getJSONArray("SelectedTripodTier");
        characterProfileSkill.setSelectedTripodTier(new int[]{selectedTripod.getInt(0),
                selectedTripod.getInt(1), selectedTripod.getInt(2)});

        characterProfileSkill.setThumb(URL_CDN_LOSTARK + skillBookJSON.getString("SlotIcon"));

        if (skillBookJSON.has("TripodList")) {
            JSONObject tripodJSON = skillBookJSON.getJSONObject("TripodList");

            // 스킬 트라이포드 가져오는 부분
            for (int ti = 0; ; ti++) {
                if (!tripodJSON.has("Tripod_" + ti + "_" + 1)) break;
                ArrayList<CharacterProfileSkill.Tripod> skillsOfTripod = new ArrayList<>();
                for (int subi = 1; ; subi++) {
                    if (tripodJSON.has("Tripod_" + ti + "_" + subi)) {
                        JSONObject eachTripod = tripodJSON.getJSONObject("Tripod_" + ti + "_" + subi);
                        CharacterProfileSkill.Tripod tripod = new CharacterProfileSkill.Tripod();
                        tripod.setName(eachTripod.getString("Name"));
                        tripod.setDesc(eachTripod.getString("Desc"));
                        tripod.setIcon(URL_CDN_LOSTARK + eachTripod.getString("SlotIcon"));
                        skillsOfTripod.add(tripod);
                    } else {
                        characterProfileSkill.getTripods().add(skillsOfTripod);
                        break;
                    }
                }
            }
        }

        for (int pi = 0; pi < eachSkillJSON.length(); pi++) {
            final String partkey = "Element_" + String.format("%02d", pi); // Like Element_00
            if (eachSkillJSON.has(partkey)) {
                final JSONObject partOfSkillJSON = eachSkillJSON.getJSONObject(partkey);
                final String typeStr = partOfSkillJSON.getString("type"); // NameTagBox, MultiTextBox.. etc..

                if (typeStr.equals("NameTagBox")) {
                    characterProfileSkill.setName(partOfSkillJSON.getString("value"));
                }

                if (typeStr.equals("CommonSkillTitle")) {
                    final JSONObject commonSkillValueJSON = partOfSkillJSON.getJSONObject("value");
                    if (commonSkillValueJSON.has("leftText"))
                        characterProfileSkill.setCooltime(commonSkillValueJSON.getString("leftText"));
                    if (commonSkillValueJSON.has("level"))
                        characterProfileSkill.setSkillType(commonSkillValueJSON.getString("level"));
                    if (commonSkillValueJSON.has("middleText"))
                        characterProfileSkill.setMiddleText(commonSkillValueJSON.getString("middleText"));
                }

                if (typeStr.equals("MultiTextBox")) {
                    final String[] multiTextParts = partOfSkillJSON.getString("value").split("\\|", Integer.MAX_VALUE);

                    for (String str : multiTextParts) {
                        if (str != null && str.length() > 0)
                            characterProfileSkill.getDetailDescs().add(str);
                    }

                }

                if (typeStr.equals("SingleTextBox")) {
                    characterProfileSkill.getDetailDescs().add(partOfSkillJSON.getString("value"));
                }

                if (typeStr.equals("TripodSkillCustom")) {
                    JSONObject tripodSkillJSON = partOfSkillJSON.getJSONObject("value");

                    for (int tsc = 0; tsc < tripodSkillJSON.length(); tsc++) {
                        final String tsckey = "Element_" + String.format("%02d", pi); // Like Element_00
                        if (tripodSkillJSON.has(tsckey)) {
                            final JSONObject eachTripodSkillCustomJSON = tripodSkillJSON.getJSONObject(tsckey);
                            final CharacterProfileSkill.TripodCustom tripodCustom = new CharacterProfileSkill.TripodCustom();

                            if (eachTripodSkillCustomJSON.has("desc"))
                                tripodCustom.setDesc(eachTripodSkillCustomJSON.getString("desc"));
                            if (eachTripodSkillCustomJSON.has("lock"))
                                tripodCustom.setLock(eachTripodSkillCustomJSON.getBoolean("lock"));
                            if (eachTripodSkillCustomJSON.has("name"))
                                tripodCustom.setName(eachTripodSkillCustomJSON.getString("name"));
                            if (eachTripodSkillCustomJSON.has("tier"))
                                tripodCustom.setDesc(eachTripodSkillCustomJSON.getString("tier"));

                            if (!tripodCustom.isLock()) {
                                final JSONObject iconTripodSkillCustomJSON = eachTripodSkillCustomJSON.getJSONObject("slotData");
                                if (iconTripodSkillCustomJSON.has("iconGrade"))
                                    tripodCustom.setIconGrade(iconTripodSkillCustomJSON.getInt("iconGrade"));
                                if (eachTripodSkillCustomJSON.has("tier"))
                                    tripodCustom.setThumb(iconTripodSkillCustomJSON.getString(URL_CDN_LOSTARK + "iconPath"));
                            }
                            characterProfileSkill.getTripodCustoms().add(tripodCustom);
                        }
                    }
                }
            }
        }
    }

    private void analyzeEquipment(CharacterProfileEquipment characterProfileEquipment, JSONObject eachEquipJSON, boolean isAvatar) throws JSONException {
        for (int pi = 0; pi < eachEquipJSON.length(); pi++) {
            final String partkey = "Element_" + String.format("%02d", pi); // Like Element_00

            if (eachEquipJSON.has(partkey)) {
                final JSONObject partOfEquipJSON = eachEquipJSON.getJSONObject(partkey);
                final String typeStr = partOfEquipJSON.getString("type"); // NameTagBox, MultiTextBox.. etc..
                if (typeStr.equals("NameTagBox")) {
                    characterProfileEquipment.setName(partOfEquipJSON.getString("value"));
                }
                if (typeStr.equals("ItemTitle") && partkey.equals("Element_01")) {
                    characterProfileEquipment.setSort(partOfEquipJSON.getJSONObject("value").getString("leftStr0"));

                    if (partOfEquipJSON.getJSONObject("value").getJSONObject("leftStr1").has("enableCnt"))
                        characterProfileEquipment.setGrindLevel(partOfEquipJSON.getJSONObject("value").getJSONObject("leftStr1").getInt("enableCnt"));

                    characterProfileEquipment.setEquiped(partOfEquipJSON.getJSONObject("value").getString("rightStr0"));

                    characterProfileEquipment.setIconGrade(partOfEquipJSON.getJSONObject("value").getJSONObject("slotData").getInt("iconGrade"));
                    characterProfileEquipment.setThumb(URL_CDN_LOSTARK + partOfEquipJSON.getJSONObject("value").getJSONObject("slotData").getString("iconPath"));
                }
                if (typeStr.equals("MultiTextBox")) {
                    final String[] multiTextParts = partOfEquipJSON.getString("value").split("\\|", Integer.MAX_VALUE);

                    if (partkey.equals("Element_02") && partOfEquipJSON.getString("value").contains("레벨")) {
                        Log.d("multiTextBoxLog", partOfEquipJSON.getString("value") + multiTextParts.length);
                        characterProfileEquipment.setItemLevel(multiTextParts[0]);
                        characterProfileEquipment.setRequireLevel(multiTextParts[1]);
                    } else {
                        for (String str : multiTextParts) {
                            if (str != null && str.length() > 0)
                                characterProfileEquipment.getDetailDescs().add(str);
                        }
                    }
                }
                if (typeStr.equals("SingleTextBox")) {
                    characterProfileEquipment.getDetailDescs().add(partOfEquipJSON.getString("value"));
                }

                if (typeStr.equals("ItemUniqueOption")) {
                    characterProfileEquipment.getDetailDescs().add(partOfEquipJSON.getJSONObject("value").getString("title"));
                }
                if (typeStr.equals("ItemPartBox")) {
                    final JSONObject itemPartBoxValueJSON = partOfEquipJSON.getJSONObject("value");
                    for (int pbvi = 0; pbvi < itemPartBoxValueJSON.length(); pbvi++) {
                        final String pbvipartkey = "Element_" + String.format("%02d", pbvi); // Like Element_00
                        if (itemPartBoxValueJSON.has(pbvipartkey)) {
                            characterProfileEquipment.getDetailDescs().add(partOfEquipJSON.getJSONObject("value").getString(pbvipartkey));
                        }
                    }
                }
                if (typeStr.equals("ShowMeTheMoney")) {
                    characterProfileEquipment.getDetailDescs().add(partOfEquipJSON.getString("value"));
                }
            }
        }
    }

    private JSONObject getProfilePart(String wholeHtmlStr) {
        final int startIndex = wholeHtmlStr.lastIndexOf("$.Profile = ") + "$.Profile = ".length();
        final int endIndex = wholeHtmlStr.indexOf("};", startIndex) + 1;
        final String profilePartStr = wholeHtmlStr.substring(startIndex, endIndex);
        try {
            final JSONObject profilePartJSON = new JSONObject(profilePartStr.trim());
            return profilePartJSON;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface CharacterProfileResponse {
        void onResponse(RequestResult requestResult);
    }

    public void mergeElements(Elements prev, Elements add) {
        for (Element ae : add) {
            prev.add(ae);
        }
    }

    public class RequestResult {
        private CharacterProfile characterProfile;
        private boolean isSuccessful;
        private String msg;

        public CharacterProfile getCharacterProfile() {
            return characterProfile;
        }

        public void setCharacterProfile(CharacterProfile characterProfile) {
            this.characterProfile = characterProfile;
        }

        public boolean isSuccessful() {
            return isSuccessful;
        }

        public void setSuccessful(boolean successful) {
            isSuccessful = successful;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
