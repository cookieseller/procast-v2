package fix.de.procast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import fix.de.procast.Data.Podcast;
import fix.de.procast.R;

public class ImageListAdapter extends BaseAdapter {

    private Context context;
    private List<Podcast> entries;

    public ImageListAdapter(Context context, List<Podcast> entries) {
        this.context = context;
        this.entries = entries;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;

        if (listView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listView = layoutInflater.inflate(R.layout.podcastlist_row, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.firstLine = (TextView) listView.findViewById(R.id.firstLine);
            viewHolder.secondLine = (TextView) listView.findViewById(R.id.secondLine);
            viewHolder.image = (ImageView) listView.findViewById(R.id.icon);

            listView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) listView.getTag();
        holder.firstLine.setText(entries.get(position).title);
        holder.secondLine.setText("");
        Glide.with(context)
                .load(entries.get(position).remoteImage)
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.progress_spinner)
                .into(holder.image);

        return listView;
    }

    @Override
    public Object getItem(int position) {
        return entries.get(position);
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        public ImageView image;
        public TextView firstLine;
        public TextView secondLine;
    }
}
