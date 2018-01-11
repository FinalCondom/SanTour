package ch.hes.santour;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import Models.Difficulty;

/**
 * Created by Cento on 12.12.2017.
 */

public class ListPodDifficulties extends ArrayAdapter<Difficulty> {
    private List<Difficulty> difficulties = new ArrayList<>();

    //Adpater to see a list of difficutlies in a POD

    public ListPodDifficulties(Context context, List<Difficulty> difficulties) {
        super(context, 0, difficulties);
        this.difficulties = difficulties;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.details_row, parent, false);

        }

        PodDifficutlyViewHolder viewHolder = (PodDifficutlyViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new PodDifficutlyViewHolder();
            viewHolder.checkboxPodDetails = convertView.findViewById(R.id.checkboxPodDetails);
            convertView.setTag(viewHolder);

        }

        //getItem(position) va récupérer l'item [position]
        Difficulty difficulty = getItem(position);
        viewHolder.checkboxPodDetails.setText(difficulty.getName());
        if(difficulty.getGradient()>0){
            viewHolder.checkboxPodDetails.setChecked(true);
            viewHolder.seekBarPodDetails = convertView.findViewById(R.id.sb_pod_details_horizontal);
            viewHolder.seekBarPodDetails.setProgress(difficulty.getGradient());
        }

        return convertView;
    }
}
