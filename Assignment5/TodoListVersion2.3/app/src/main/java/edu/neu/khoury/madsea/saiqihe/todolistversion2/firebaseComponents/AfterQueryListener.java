package edu.neu.khoury.madsea.saiqihe.todolistversion2.firebaseComponents;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AfterQueryListener implements OnCompleteListener<QuerySnapshot> {
    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if(task.isSuccessful()){
            for(QueryDocumentSnapshot q : task.getResult()){
                Log.d("query result", q.getId() + q.getData());//auto to string for hashmap
            }
        }
        else Log.w("query result", "query failed");
    }
}
