package edu.neu.khoury.madsea.saiqihe.todolistversion2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

public class TodoNoteViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView detail;
    TodoModelView mv;
    public TodoNoteViewHolder(@NonNull View itemView) {
        super(itemView);
        //mv = new ViewModelProvider((ViewModelStoreOwner) this).get(TodoModelView.class);
        itemView.setOnClickListener(view -> {

        });
        title = itemView.findViewById(R.id.recycle_title);
        detail = itemView.findViewById(R.id.recycle_detail);
    }
    public void bind(String titles, String details){
        title.setText(titles);
        detail.setText(details);
    }
    public static TodoNoteViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view,parent,false);
        return new TodoNoteViewHolder(view);
    }
}
