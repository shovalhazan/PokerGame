package com.example.pokergame;
import com.example.pokergame.utils.SP;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class TopTenListFragment extends Fragment {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<Record> records ;
    private String[] myStrings=new String[10];
    public MyCallBack callBack;
    private static final String TAG = "topTenFrag";
    private static final int LISTSIZE =10;

    public TopTenListFragment() {}
    public TopTenListFragment(MyCallBack callBack) {
        this.callBack=callBack;
    }


    @Override
    //when a fragment connect to activity
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_toplist, container, false);
        listView = (ListView)view.findViewById(R.id.LST);

        getRecordArray();
        setAdapter();

        return view;
    }

    public void setAdapter(){
        ArrayList<String> strListForAdapter = new ArrayList<>();
        for (int i =0;i<LISTSIZE;i++)
            strListForAdapter.add(i+1+") "+records.get(i).toString());
        adapter =  new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,strListForAdapter);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: pos"+records.get(position).getLocation()+"name "+records.get(position).getName());
               callBack.getLocation(records.get(position).getLocation(),records.get(position).getName());
            }
        });
    }
    public void getRecordArray(){
        String recStr=SP.getInstance().getString("arr",null);
        Gson gson = new Gson();
        Type scoreType = new TypeToken<ArrayList<Record>>() {
        }.getType();
        records = gson.fromJson(recStr, scoreType);
        if (records != null) { // If there is something to load
            System.out.println("Scores array: " + records.toString());
        } else { // If there is nothing to load, init array
            records = new ArrayList<Record>();
        }
    }

}