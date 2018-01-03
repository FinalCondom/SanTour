package ch.hes.santour;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;


public class SettingsFragment extends Fragment {

    Fragment fragment;
    private int time;
    private int precision;

    public SettingsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        //to see the menu on the top
        setHasOptionsMenu(true);

        //set the title on the app
        getActivity().setTitle(R.string.settings);



        RadioGroup rb_group1 =  rootView.findViewById(R.id.rb_group1);
        rb_group1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                precision =2;
                // Check which radio button was clicked
                switch(i) {
                    case R.id.rb_precision_low:
                            precision = 1;
                            break;
                    case R.id.rb_precision_middle:
                            precision = 2;
                            break;
                    case R.id.rb_precision_high:
                            precision = 3;
                            break;
                }

            }
        });

        RadioGroup rb_group2 =  rootView.findViewById(R.id.rb_group2);
        rb_group2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()  {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                 time =2;
                // Check which radio button was clicked
                switch(i) {
                    case R.id.rb_syncro_5:
                        time = 1;
                        break;
                    case R.id.rb_syncro_10:
                        time = 2;
                        break;
                    case R.id.rb_syncro_15:
                        time = 3;
                        break;
                }


            }

        });


        int precisionChecked = R.id.rb_precision_middle;
        int timeChecked = R.id.rb_syncro_10;


        switch(((MainActivity)getActivity()).getIntPrecision())
        {
            case 1:
                precisionChecked = R.id.rb_precision_low;
                break;
            case 2:
                precisionChecked = R.id.rb_precision_middle;
                break;
            case 3:
                precisionChecked = R.id.rb_precision_high;
                break;


        }
        switch(((MainActivity)getActivity()).getIntChoosedTime())
        {
            case 1:
                timeChecked = R.id.rb_syncro_5;
                break;
            case 2:
                timeChecked = R.id.rb_syncro_10;
                break;
            case 3:
                timeChecked = R.id.rb_syncro_15;
                break;

        }

        rb_group1.check(precisionChecked);
        rb_group2.check(timeChecked);


        Button bt_save =  rootView.findViewById(R.id.bt_save);
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //méthode pour changer la précision et vitesse
                ((MainActivity)getActivity()).DefineLocalisation(precision,time);
            }
        });



        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.language, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
