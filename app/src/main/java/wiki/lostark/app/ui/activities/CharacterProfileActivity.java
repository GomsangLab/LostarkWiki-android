package wiki.lostark.app.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import io.paperdb.Paper;
import wiki.lostark.app.datas.characterprofile.CharacterProfile;
import wiki.lostark.app.libs.AdvertisementManager;
import wiki.lostark.app.ui.adapters.BasicProfileAdapter;
import wiki.lostark.app.ui.adapters.UserStatsAdapter;
import wiki.lostark.app.ui.adapters.UserEquipmentAdapter;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.ActivityCharacterProfileBinding;
import wiki.lostark.app.datas.characterprofile.BasicProfile;
import wiki.lostark.app.libs.CharacterProfileRequest;
import wiki.lostark.app.ui.adapters.UserSkillAdapter;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * 캐릭터 정보를 보여주는 Activity
 */

public class CharacterProfileActivity extends AppCompatActivity {

    private ActivityCharacterProfileBinding binding;
    private String searchNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_character_profile);

        if (getIntent() != null) {
            searchNick = getIntent().getStringExtra("nickname");
        }

        ProgressDialog progressDialog = new ProgressDialog(this , R.style.DarkDialogTheme);
        progressDialog.setTitle("캐릭터 정보 로드 중");
        progressDialog.setMessage("조금만 기다려주세요!");
        progressDialog.show();

        new CharacterProfileRequest(searchNick, requestResult -> {
            progressDialog.dismiss();
            if (!requestResult.isSuccessful()) {
                Toast.makeText(CharacterProfileActivity.this, "[오류발생]" + "\n" + requestResult.getMsg(), Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            final CharacterProfile characterProfile = requestResult.getCharacterProfile();
            final ArrayList<String> histories = Paper.book().read("histories", new ArrayList<>());
            if (histories.contains(searchNick)) histories.remove(searchNick);
            histories.add(0, searchNick);
            Paper.book().write("histories", histories);

            binding.nickname.setText(characterProfile.getNickname());
            binding.level.setText(characterProfile.getLevel());
            binding.itemLevel.setText(characterProfile.getItemLevel());
            binding.expeditionLevel.setText(characterProfile.getExpeditionLevel());
            binding.pvpLevel.setText(characterProfile.getPvpLevel());

            ArrayList<BasicProfile> basicProfiles = new ArrayList<>();
            basicProfiles.add(BasicProfile.init("서버", characterProfile.getServerName().replace("@", ""), Color.parseColor("#5866fd")));
            basicProfiles.add(BasicProfile.init("길드", characterProfile.getGuildName(), Color.parseColor("#ff4d54")));
            basicProfiles.add(BasicProfile.init("직업", characterProfile.getJobName(), Color.parseColor("#a559ff")));
            basicProfiles.add(BasicProfile.init("칭호", characterProfile.getTitle(), Color.parseColor("#fea259")));

            BasicProfileAdapter basicProfileAdapter = new BasicProfileAdapter(CharacterProfileActivity.this, basicProfiles);
            binding.basicProfileRecycler.setLayoutManager(new GridLayoutManager(this, 2));
            binding.basicProfileRecycler.setAdapter(basicProfileAdapter);

            ArrayList userEquipments = characterProfile.getCharacterProfileEquipments();
            UserEquipmentAdapter userEquipmentAdapter = new UserEquipmentAdapter(CharacterProfileActivity.this, userEquipments);
            binding.userEquipmentRecycler.setLayoutManager(new GridLayoutManager(this, 2));
            binding.userEquipmentRecycler.setAdapter(userEquipmentAdapter);

            ArrayList userSkill = characterProfile.getCharacterProfileSkills();
            UserSkillAdapter userSkillAdapter = new UserSkillAdapter(CharacterProfileActivity.this, userSkill);
            binding.userSkillRecycler.setLayoutManager(new GridLayoutManager(this, 1));
            binding.userSkillRecycler.setAdapter(userSkillAdapter);

            ArrayList userBasicStats = characterProfile.getBasicStats();
            UserStatsAdapter userBasicStatsAdapter = new UserStatsAdapter(CharacterProfileActivity.this, userBasicStats);
            binding.userBasicStatsRecycler.setLayoutManager(new GridLayoutManager(this, 1));
            binding.userBasicStatsRecycler.setAdapter(userBasicStatsAdapter);

            ArrayList userBattleStats = characterProfile.getBattleStats();
            UserStatsAdapter userBattleStatsAdapter = new UserStatsAdapter(CharacterProfileActivity.this, userBattleStats);
            binding.userBattleStatsRecycler.setLayoutManager(new GridLayoutManager(this, 1));
            binding.userBattleStatsRecycler.setAdapter(userBattleStatsAdapter);

            AdvertisementManager.getInstance().showAdFront(0.5f);
        }).execute();

        tapAdjustment(1);

        binding.btnEquipment.setOnClickListener(v -> tapAdjustment(1));

        binding.btnStat.setOnClickListener(v -> tapAdjustment(2));

        binding.btnSkill.setOnClickListener(v -> tapAdjustment(3));
    }

    void tapAdjustment(int clicked) {
        if (clicked == 1) {
            binding.viewEquipment.setVisibility(View.VISIBLE);
            binding.tvEquipment.setTextColor(Color.parseColor("#5966fe"));
            binding.viewStat.setVisibility(View.INVISIBLE);
            binding.tvStat.setTextColor(Color.parseColor("#ffffff"));
            binding.viewSkill.setVisibility(View.INVISIBLE);
            binding.tvSkill.setTextColor(Color.parseColor("#ffffff"));

            binding.userEquipmentRecycler.setVisibility(View.VISIBLE);
            binding.layoutUserStats.setVisibility(View.INVISIBLE);
            binding.userSkillRecycler.setVisibility(View.INVISIBLE);
        }

        if (clicked == 2) {
            binding.viewEquipment.setVisibility(View.INVISIBLE);
            binding.tvEquipment.setTextColor(Color.parseColor("#ffffff"));
            binding.viewStat.setVisibility(View.VISIBLE);
            binding.tvStat.setTextColor(Color.parseColor("#5966fe"));
            binding.viewSkill.setVisibility(View.INVISIBLE);
            binding.tvSkill.setTextColor(Color.parseColor("#ffffff"));

            binding.userEquipmentRecycler.setVisibility(View.INVISIBLE);
            binding.layoutUserStats.setVisibility(View.VISIBLE);
            binding.userSkillRecycler.setVisibility(View.INVISIBLE);
        }

        if (clicked == 3) {
            binding.viewEquipment.setVisibility(View.INVISIBLE);
            binding.tvEquipment.setTextColor(Color.parseColor("#ffffff"));
            binding.viewStat.setVisibility(View.INVISIBLE);
            binding.tvStat.setTextColor(Color.parseColor("#ffffff"));
            binding.viewSkill.setVisibility(View.VISIBLE);
            binding.tvSkill.setTextColor(Color.parseColor("#5966fe"));

            binding.userEquipmentRecycler.setVisibility(View.INVISIBLE);
            binding.layoutUserStats.setVisibility(View.INVISIBLE);
            binding.userSkillRecycler.setVisibility(View.VISIBLE);
        }
    }
}