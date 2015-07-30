package fix.de.procast.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fix.de.procast.Data.Podcast;
import fix.de.procast.R;
import fix.de.procast.misc.BitmapUtils;
import fix.de.procast.misc.Constants;

public class CategoryImageAdapter extends BaseAdapter {

    private Context context;
    private int requiredImageSize;
    private ArrayList<Podcast> podcasts;

    public CategoryImageAdapter(Context context, int requiredImageSize, ArrayList<Podcast> podcasts) {
        this.context = context;
        this.podcasts = podcasts;
        this.requiredImageSize = requiredImageSize / 4;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;

        if (gridView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = layoutInflater.inflate(R.layout.category_item, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) gridView.findViewById(R.id.grid_item_label);
            viewHolder.image = (ImageView) gridView.findViewById(R.id.grid_item_image);

            gridView.setTag(viewHolder);
        }

        Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromFile(Constants.ROOT_DIR_EXTERNAL_STORAGE + podcasts.get(position).title + ".jpg", requiredImageSize, requiredImageSize);

        ViewHolder holder = (ViewHolder) gridView.getTag();
        holder.text.setText(podcasts.get(position).title);
        holder.text.setAlpha(0.6f);
        holder.image.setImageBitmap(bitmap);

        return gridView;
    }

    @Override
    public Object getItem(int position) {
        return podcasts.get(position);
    }

    @Override
    public int getCount() {
        return podcasts.size();
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
