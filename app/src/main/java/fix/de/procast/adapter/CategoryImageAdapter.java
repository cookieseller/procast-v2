package fix.de.procast.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import fix.de.procast.Data.Category;
import fix.de.procast.R;
import fix.de.procast.misc.BitmapUtils;
import fix.de.procast.misc.Constants;

public class CategoryImageAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Category> category;

    public CategoryImageAdapter(Context context, ArrayList<Category> category) {
        this.context = context;
        this.category = category;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;

        if (gridView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = layoutInflater.inflate(R.layout.category_item, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) gridView.findViewById(R.id.grid_item_label);
            viewHolder.image = (ImageView) gridView.findViewById(R.id.grid_item_image);

            gridView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) gridView.getTag();
        holder.text.setText(category.get(position).title);
        Glide.with(context)
                .load(category.get(position).remoteImage)
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.progress_spinner)
                .into(holder.image);
//        holder.text.setAlpha(0.6f);

        return gridView;
    }

    @Override
    public Object getItem(int position) {
        return category.get(position);
    }

    @Override
    public int getCount() {
        return category.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }
}
