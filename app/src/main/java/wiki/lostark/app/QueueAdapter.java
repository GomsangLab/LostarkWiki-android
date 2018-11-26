package wiki.lostark.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import wiki.lostark.app.databinding.RowQueueBinding;

public class QueueAdapter extends BaseAdapter {

    private List<QueueViewModel> listQueueViewModels;
    private Activity activity;

    public QueueAdapter(Activity activity, List<QueueViewModel> listQueueViewModels) {
        this.listQueueViewModels = listQueueViewModels;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listQueueViewModels.size();
    }

    @Override
    public Object getItem(int position) {
        return listQueueViewModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RowQueueBinding binding;
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.row_queue, null);
            binding = DataBindingUtil.bind(convertView);
            convertView.setTag(binding);
        } else {
            binding = (RowQueueBinding) convertView.getTag();
        }
        binding.setPerson(listQueueViewModels.get(position));
        return binding.getRoot();
    }
}