package wiki.lostark.app.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import wiki.lostark.app.databinding.ItemDictionaryMainBinding;
import wiki.lostark.app.datas.dictionary.Datum;
import wiki.lostark.app.ui.activities.ItemDetailActivity;

public class ItemDictionaryMainAdapter extends RecyclerView.Adapter<ItemDictionaryViewHolder> {
    private Context context;
    private List<Datum> list;

    public ItemDictionaryMainAdapter(Context context, List<Datum> list) {
        this.context = context;
        this.list = list;
    }

    public void clear() {
        list.clear();
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemChanged(position);
        notifyItemRangeChanged(position, list.size());
    }

    @NonNull
    @Override
    public ItemDictionaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemDictionaryMainBinding binding = ItemDictionaryMainBinding.inflate(layoutInflater, parent, false);
        return new ItemDictionaryViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ItemDictionaryViewHolder holder, int position) {
        Datum model = list.get(position);
        holder.binding.setItemData(model);

        if (model.getGrade() == 1) {
            holder.binding.itemTitleText.setTextColor(Color.parseColor("#00FF00"));
        } else if (model.getGrade() ==2) {
            holder.binding.itemTitleText.setTextColor(Color.parseColor("#00BFFF"));
        } else if (model.getGrade() == 3) {
            holder.binding.itemTitleText.setTextColor(Color.parseColor("#BF00FF"));
        } else if (model.getGrade() == 4) {
            holder.binding.itemTitleText.setTextColor(Color.parseColor("#FE9A2E"));
        } else if (model.getGrade() == 5) {
            holder.binding.itemTitleText.setTextColor(Color.parseColor("#FE2E2E"));
        }

        holder.itemView.setOnClickListener(view -> {
            Log.v("SELECTED ITEM KEY", list.get(position).getText() + list.get(position).getKey());

            Intent intent = new Intent(view.getContext(), ItemDetailActivity.class);
            intent.putExtra("key", list.get(position).getKey());
            view.getContext().startActivity(intent);
        });
    }
}

class ItemDictionaryViewHolder extends RecyclerView.ViewHolder {
    public ItemDictionaryMainBinding binding;

    public ItemDictionaryViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }
}