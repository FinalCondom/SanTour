package ch.hes.santour;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import Models.POI;

/**
 * Created by Cento on 04.12.2017.
 */

public class ListPoiAdapter extends ArrayAdapter<POI> {
    private List<POI> pois;

    public ListPoiAdapter(Context context, List<POI> pois) {

        super(context, 0, pois);
        this.pois = pois ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pod_row, parent, false);

        }

        PodViewHolder viewHolder = (PodViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new PodViewHolder();
            viewHolder.nom = convertView.findViewById(R.id.nom);
            viewHolder.description = convertView.findViewById(R.id.description);
            viewHolder.avatar = convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);

        }

        //getItem(position) va récupérer l'item [position]
        POI poi = getItem(position);

        viewHolder.nom.setText(poi.getName());
        viewHolder.description.setText(poi.getDescription());
        viewHolder.avatar.setImageDrawable(new BitmapDrawable(getContext().getResources(), poi.getPicture()));

        return convertView;
    }
}