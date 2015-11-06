package com.candb.josh.protochi_wear;


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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        Toast.makeText(getActivity(),
                "Budgie: " + item.getTitle() + ",",
                Toast.LENGTH_SHORT).show();
        return super.onContextItemSelected(item);
    }

}
