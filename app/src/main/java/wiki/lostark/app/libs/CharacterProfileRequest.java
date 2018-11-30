package wiki.lostark.app.libs;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import wiki.lostark.app.datas.characterprofile.CharacterProfile;
import wiki.lostark.app.datas.characterprofile.CharacterProfileEquipment;

public class CharacterProfileRequest extends AsyncTask<String, String, CharacterProfile> {

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
    protected CharacterProfile doInBackground(String... strings) {
        try {
            CharacterProfile characterProfile = new CharacterProfile();
            // 모든 html 파싱
            Document wholeDocument = Jsoup.connect(URL_CHARPROFILE + requestUsername).get();

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

            // 스킬부분
            Elements allSkillsOfHtmlParts = wholeDocument.getElementsByClass("profile-skill__item--selected");
            mergeElements(allSkillsOfHtmlParts, wholeDocument.getElementsByClass("profile-skill__item  "));

            // html json 단 가져오기
            JSONObject profilePartJSON = getProfilePart(wholeDocument.toString());

            final ArrayList<CharacterProfileEquipment> characterProfileEquipments = new ArrayList<>();
            // 장비와 아바타 장비 모두 검색하기 위해 1회 반복합니다.
            for (String slotsClassName : new String[]{"profile-equipment__slot", "profile-avatar__slot"}) {
                // 총 equip 슬롯을 분석합니다.
                final Element slotsElement = wholeDocument.getElementsByClass(slotsClassName).get(0);
                final int totalEquipSlot = slotsElement.select("div").size() - 1; // 부모 div 개수를 제외하기 위해 -1
                characterProfile.setTotalEquipSlot(totalEquipSlot);
                // 총 equip 개수 만큼 돌며, 소지 중인 equip 은 상세 정보를 입력합니다.
                for (int ei = 1; ei <= totalEquipSlot; ei++) {
                    final Element element = slotsElement.getElementsByClass("slot" + ei).get(0);
                    CharacterProfileEquipment characterProfileEquipment = new CharacterProfileEquipment();

                    // data-item 을 소지하고 있는지 (data-item attribute 가 존재하는 경우는 아이템을 소지하고 있음을 뜻합니다.) data-item 의 value 는 json 단의 상세 아이템 설명 key 임.
                    if (element.attributes().hasKey("data-item")) {
                        characterProfileEquipment.setAvailable(true);
                        JSONObject eachEquipJSON = profilePartJSON.getJSONObject(element.attributes().get("data-item"));
                        if (element.attributes().get("data-item").contains("LifeTool")) characterProfileEquipment.setLifeTool(true);
                        characterProfileEquipment = analyzeEquipment(characterProfileEquipment, eachEquipJSON, slotsClassName.equals("profile-avatar__slot"));
                    } else {
                        characterProfileEquipment.setAvailable(false);
                        characterProfileEquipment.setThumb("http://cdn-lostark.game.onstove.com/2018/obt/assets/images/common/game/" +
                                "bg_" + (slotsClassName.equals("profile-equipment__slot") ? "equipment" : "avatar") + "_slot+" + ei + "+.png");
                    }
                    characterProfileEquipments.add(characterProfileEquipment);
                }
            }


            return characterProfile;
        } catch (Exception e) {
            Log.d("parse-log-profile", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(CharacterProfile characterProfile) {
        super.onPostExecute(characterProfile);
        if (characterProfile == null) {
            characterProfileResponse.onResponse(null);
            return;
        }
        characterProfileResponse.onResponse(characterProfile);
    }

    private CharacterProfileEquipment analyzeEquipment(CharacterProfileEquipment characterProfileEquipment, JSONObject eachEquipJSON, boolean isAvatar) throws JSONException {
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

                    if (partkey.equals("Element_02")) {
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
        return characterProfileEquipment;
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
        void onResponse(CharacterProfile characterProfile);
    }

    public void mergeElements(Elements prev, Elements add) {
        for (Element ae : add) {
            prev.add(ae);
        }
    }
}
