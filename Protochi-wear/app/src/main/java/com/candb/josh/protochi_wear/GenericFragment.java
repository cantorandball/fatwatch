package com.candb.josh.protochi_wear;


import android.app.Activity;
import android.hardware.SensorEventListener;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenericFragment extends Fragment {


    public GenericFragment() {
        // Required empty public constructor
    }

    // Interface with main activity
    public interface MyFragmentCallback{
        public void readHeartRate();
    }

    private MyFragmentCallback callback;

    public void onAttach(Activity activity){
        callback = (MyFragmentCallback) activity;
        super.onAttach(activity);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item){
        String title = item.getTitle().toString();
        String resetTitle = getResources().getString(R.string.main_menu_reset);
        String readHeartTitle = getResources().getString(R.string.main_menu_heart_rate_reset);

        if (title.equals(resetTitle)){
            resetValues();
        }else if(title.equals(readHeartTitle)){
            callback.readHeartRate();
        }else{
            Toast.makeText(getActivity(),
                    "I don't know what to do when you press: " + item.getTitle() + ",",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    // These methods are overridden in individual fragments, thus empty here.
    public void resetValues(){
    }

}
