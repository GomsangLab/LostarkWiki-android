package wiki.lostark.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import wiki.lostark.app.databinding.ActivityCharacterProfileBinding;
import wiki.lostark.app.datas.characterprofile.BasicProfile;
import wiki.lostark.app.libs.CharacterProfileRequest;

import android.graphics.Color;
import android.os.Bundle;

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

            ArrayList<BasicProfile> basicProfiles = new ArrayList<>();
            basicProfiles.add(BasicProfile.init("서버", characterProfile.getServerName().replace("@", ""), Color.parseColor("#5866fd")));
            basicProfiles.add(BasicProfile.init("길드", characterProfile.getGuildName(), Color.parseColor("#ff4d54")));
            basicProfiles.add(BasicProfile.init("직업", characterProfile.getJobName(), Color.parseColor("#a559ff")));
            basicProfiles.add(BasicProfile.init("칭호", characterProfile.getTitle(), Color.parseColor("#fea259")));

            BasicProfileAdapter basicProfileAdapter = new BasicProfileAdapter(CharacterProfileActivity.this, basicProfiles);
            binding.basicProfileRecycler.setLayoutManager(new GridLayoutManager(this, 2));
            binding.basicProfileRecycler.setAdapter(basicProfileAdapter);

            ArrayList detailProfiles = characterProfile.getCharacterProfileEquipments();
            DetailProfileAdapter detailProfileAdapter = new DetailProfileAdapter(CharacterProfileActivity.this, detailProfiles);
            binding.detailProfileRecycler.setLayoutManager(new GridLayoutManager(this, 2));
            binding.detailProfileRecycler.setAdapter(detailProfileAdapter);
        }).execute();
    }
}