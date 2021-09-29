package edu.neu.khoury.madsea.saiqihe.todoapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import edu.neu.khoury.madsea.saiqihe.todoapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String ARG_PARAM1 = "new!";
    TextView tV;
    CheckBox cB;
    public boolean checked = false;


    public TestFragment() {
    }
    public TestFragment(String s){
        ARG_PARAM1 = s;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_test, null);
        tV = root.findViewById(R.id.textView2);
        cB = root.findViewById(R.id.checkBox);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tV = getView().findViewById(R.id.textView2);
        if(tV==null){
            Log.e("tag","tv is null1");
        }
        tV.setText(ARG_PARAM1);
        if(checked)cB.setBackgroundColor(Color.BLUE);
    }

}