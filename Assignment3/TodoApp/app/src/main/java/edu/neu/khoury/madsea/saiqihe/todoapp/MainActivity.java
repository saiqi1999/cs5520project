package edu.neu.khoury.madsea.saiqihe.todoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import edu.neu.khoury.madsea.saiqihe.todoapp.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 1, create MainActivity, have some slot, an add button
 * 2, create Fragment, which is abbreviation of note
 * 3, create FragmentActivity, which is detailed note
 * 4, record all slot id, put them in a Queue.
 * 5, implement add function, pop some id from the queue, get the view, pass it to waitResult intent->FragmentActivity
 * 6, FragmentActivity gets intent, recognize create, open layout and get info, return info and id
 * 7, onResult function, gets id and info, give it to bundle and create a new fragment in view(id)
 * 8, implement edit function, get view.getId() from onclick(view) event, pack info, saved in Main or viewModel, get to the Fragment Activity
 * 9, FragmentActivity recognized the info, this is edit and put them into form
 * 10, intent result and set, like create
 * 11, color change for checkbox, checkbox is in fragment
 */
public class MainActivity extends AppCompatActivity {
    ArrayList<Integer> slotIds;
    ArrayList<Integer> filledSlotIds;
    HashMap<Integer,ArrayList<String>> slotData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slotIds = new ArrayList<>();
        filledSlotIds = new ArrayList<>();
        slotData = new HashMap<>();
        if(savedInstanceState==null){
            TestFragment f = new TestFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.slot_1,f).commit();
            TestFragment f2 = new TestFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.slot_2,f2).commit();
            TestFragment f3 =new TestFragment();
            TestFragment f4 =new TestFragment();
            TestFragment f5 =new TestFragment();
            TestFragment f6 =new TestFragment();
            TestFragment f7 =new TestFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.slot_3,f3).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.slot_4,f4).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.slot_5,f5).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.slot_6,f6).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.slot_7,f7).commit();
            //getSupportFragmentManager().findFragmentById(R.id.slot_2).getView().setVisibility(View.VISIBLE);
            slotIds.add(R.id.slot_7);
            slotIds.add(R.id.slot_6);
            slotIds.add(R.id.slot_5);
            slotIds.add(R.id.slot_4);
            slotIds.add(R.id.slot_3);
            slotIds.add(R.id.slot_2);
            slotIds.add(R.id.slot_1);
            //getSupportFragmentManager().beginTransaction().remove(f).commit();
            /*getSupportFragmentManager().beginTransaction().remove(f).commit();
            f = new TestFragment("new");
            getSupportFragmentManager().beginTransaction().add(R.id.slot_1,f).commit();*/
        }
        //Toast.makeText(this,slotIds.toString(),Toast.LENGTH_SHORT);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList("filled",filledSlotIds);
        outState.putIntegerArrayList("slotIds",slotIds);
        for(int i:slotData.keySet()){
            outState.putStringArrayList(i+"",slotData.get(i));
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<Integer> filled = savedInstanceState.getIntegerArrayList("filled");
        for(int i : filled){
            findViewById(i).setVisibility(View.VISIBLE);
            filledSlotIds.add(i);
            slotData.put(i,savedInstanceState.getStringArrayList(i+""));
        }
        ArrayList<Integer> slots = savedInstanceState.getIntegerArrayList("slotIds");
        slotIds.addAll(slots);
        //System.out.println(slotIds.toString());
    }

    public void addNote(View view) {
        if(!slotIds.isEmpty()){
            Integer id = slotIds.get(slotIds.size()-1);
            Intent intent = new Intent(this,AddActivity.class);
            intent.putExtra("frameId",id.toString());
            startActivityForResult(intent,0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        String s = data.getStringExtra("title1");
        int frameId = Integer.parseInt(data.getStringExtra("frameId"));
        TestFragment temp = new TestFragment(s);
        getSupportFragmentManager().beginTransaction().add(frameId,temp).commit();
        if(slotIds.get(slotIds.size()-1)==frameId){
            slotIds.remove(slotIds.size()-1);
            filledSlotIds.add(frameId);
            findViewById(frameId).setVisibility(View.VISIBLE);
        }
        ArrayList<String> info = new ArrayList<>();
        info.add(data.getStringExtra("title1"));
        info.add(data.getStringExtra("detail"));
        info.add(data.getStringExtra("date"));
        slotData.put(frameId,info);
    }

    public void changeColor(View view) {
        view.setBackgroundColor(Color.parseColor("#77ff77"));

    }

    public void editNote(View view) {
        Integer id = view.getId();
        ArrayList<String> info = slotData.get(id);
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("frameId",id.toString());
        intent.putExtra("title1",info.get(0));
        intent.putExtra("detail",info.get(1));
        intent.putExtra("date",info.get(2));
        startActivityForResult(intent,1);


        //Toast.makeText(this, view.getId()+""+slotIds.toString(), Toast.LENGTH_LONG).show();
        //view.setBackgroundColor(Color.GREEN);
        //findViewById(R.id.checkBox).setBackgroundColor(Color.GREEN);

    }
}