package wiki.lostark.app.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import wiki.lostark.app.ui.adapters.BasicProfileAdapter;
import wiki.lostark.app.ui.adapters.UserEquipmentAdapter;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.ActivityCharacterProfileBinding;
import wiki.lostark.app.datas.characterprofile.BasicProfile;
import wiki.lostark.app.libs.CharacterProfileRequest;
import wiki.lostark.app.ui.adapters.UserSkillAdapter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class CharacterProfileActivity extends AppCompatActivity {

    private ActivityCharacterProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_character_profile);

        new CharacterProfileRequest("샨구스", characterProfile -> {
            binding.nickname.setText(characterProfile.getNickname());
            binding.level.setText(characterProfile.getLevel());
            binding.itemlevel.setText(characterProfile.getItemLevel());
            binding.expeditionlevel.setText(characterProfile.getExpeditionLevel());
            binding.pvplevel.setText(characterProfile.getPvpLevel());

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
        }).execute();

        tapAdjustment(1);

        binding.btnEquipment.setOnClickListener(v -> {
            tapAdjustment(1);
        });

        binding.btnAbility.setOnClickListener(v -> {
            tapAdjustment(2);
        });

        binding.btnSkill.setOnClickListener(v -> {
            tapAdjustment(3);
        });
    }

    void tapAdjustment(int clicked) {
        if (clicked == 1) {
            binding.viewEquipment.setVisibility(View.VISIBLE);
            binding.tvEquipment.setTextColor(Color.parseColor("#5966fe"));
            binding.viewAbility.setVisibility(View.INVISIBLE);
            binding.tvAbility.setTextColor(Color.parseColor("#ffffff"));
            binding.viewSkill.setVisibility(View.INVISIBLE);
            binding.tvSkill.setTextColor(Color.parseColor("#ffffff"));

            binding.userEquipmentRecycler.setVisibility(View.VISIBLE);
            binding.userSkillRecycler.setVisibility(View.INVISIBLE);
        }

        if (clicked == 2) {
            binding.viewEquipment.setVisibility(View.INVISIBLE);
            binding.tvEquipment.setTextColor(Color.parseColor("#ffffff"));
            binding.viewAbility.setVisibility(View.VISIBLE);
            binding.tvAbility.setTextColor(Color.parseColor("#5966fe"));
            binding.viewSkill.setVisibility(View.INVISIBLE);
            binding.tvSkill.setTextColor(Color.parseColor("#ffffff"));

            binding.userEquipmentRecycler.setVisibility(View.INVISIBLE);
            binding.userSkillRecycler.setVisibility(View.INVISIBLE);
        }

        if (clicked == 3) {
            binding.viewEquipment.setVisibility(View.INVISIBLE);
            binding.tvEquipment.setTextColor(Color.parseColor("#ffffff"));
            binding.viewAbility.setVisibility(View.INVISIBLE);
            binding.tvAbility.setTextColor(Color.parseColor("#ffffff"));
            binding.viewSkill.setVisibility(View.VISIBLE);
            binding.tvSkill.setTextColor(Color.parseColor("#5966fe"));

            binding.userEquipmentRecycler.setVisibility(View.INVISIBLE);
            binding.userSkillRecycler.setVisibility(View.VISIBLE);
        }
    }
}