package ch.hes.santour;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import Models.POD;

/**
 * Created by Cento on 04.12.2017.
 */

public class ListPodAdapter extends ArrayAdapter<POD> {
    private List<POD> pods ;

    public ListPodAdapter(Context context, List<POD> pods) {
        super(context, 0, pods);
        this.pods = pods;
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
        POD pod = getItem(position);
        viewHolder.nom.setText(pod.getName());
        viewHolder.description.setText(pod.getDescription());
        viewHolder.avatar.setImageDrawable(new BitmapDrawable(getContext().getResources(), pod.getPicture()));


        return convertView;
    }
}