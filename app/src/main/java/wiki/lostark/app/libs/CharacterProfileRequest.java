package wiki.lostark.app.libs;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wiki.lostark.app.datas.CharacterProfile;

public class CharacterProfileRequest extends AsyncTask<String, String, CharacterProfile> {

    public static final String URL_CHARPROFILE = "http://lostark.game.onstove.com/Profile/Character/";

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
            // parse all of htmls.
            Document wholeDocument = Jsoup.connect(URL_CHARPROFILE + requestUsername).get();

            // get users basic info
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


            // contains name and level
            final Element basicProfile = baiscProfileElement.getElementsByClass("profile-character").select("h3").get(0);
            String[] basicinfo = basicProfile.text().split("\\s+");
            characterProfile.setNickname(basicinfo[1]);
            characterProfile.setLevel(basicinfo[0]);


       /*     Matcher match = Pattern.compile("$.Profile = ([^\"]+)").matcher(wholeDocument.html());
            while (match.find()) {
                Log.d("matchlog-json", match.group(1));
            }

            Elements testElements = wholeDocument.getElementsMatchingText("SkillBookInfo_00");*/

            // get all of skills info (parts of plain html)
            Elements allSkillsOfHtmlParts = wholeDocument.getElementsByClass("profile-skill__item--selected");
            mergeElements(allSkillsOfHtmlParts, wholeDocument.getElementsByClass("profile-skill__item  "));


            return characterProfile;
        } catch (IOException e) {
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
       /* List<MococoContinent> result = new Gson().fromJson(resultstr, new TypeToken<ArrayList<MococoContinent>>(){}.getType());
        mococoResponse.onResponse(result);*/
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
