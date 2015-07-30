package fix.de.procast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

import java.util.List;

import fix.de.procast.R;

public class ImageListAdapter extends BaseAdapter {

    private Context context;
    private List<SyndEntry> entries;

    public ImageListAdapter(Context context, List<SyndEntry> entries) {
        this.context = context;
        this.entries = entries;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;

        if (listView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listView = layoutInflater.inflate(R.layout.subscribe_row, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.firstLine = (TextView) listView.findViewById(R.id.firstLine);
            viewHolder.secondLine = (TextView) listView.findViewById(R.id.secondLine);
            viewHolder.image = (ImageView) listView.findViewById(R.id.icon);

            listView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) listView.getTag();
        holder.firstLine.setText(entries.get(position).getTitle());
        holder.secondLine.setText(entries.get(position).getDescription().getValue());
//        holder.image.setImageBitmap();

        return listView;
    }

    @Override
    public Object getItem(int position) {
        return null;
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
