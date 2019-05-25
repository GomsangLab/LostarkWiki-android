package wiki.lostark.app.libs.gomsang;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wiki.lostark.app.datas.mococo.MapContinent;

public class MapRequest extends AsyncTask<String, String, String> {

    public static final String URL_MOCOCO = "http://lab-seoul-mococo.gomsang.com/index.json";

    private MococoResponse mococoResponse = null;


    public MapRequest(MococoResponse mococoResponse) {
        this.mococoResponse = mococoResponse;
        if (mococoResponse == null) {
            throw new RuntimeException("Response callback should be not null.");
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            String json = Jsoup.connect(URL_MOCOCO).ignoreContentType(true).execute().body();
            return json;
        } catch (IOException e) {
            Log.d("parse-log-mococo", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String resultstr) {
        super.onPostExecute(resultstr);
        if (resultstr == null){
            mococoResponse.onResponse(null);
            return;
        }
        List<MapContinent> result = new Gson().fromJson(resultstr, new TypeToken<ArrayList<MapContinent>>(){}.getType());
        mococoResponse.onResponse(result);
    }

    public interface MococoResponse {
        void onResponse(List<MapContinent> result);
    }
}
