package edu.neu.khoury.madsea.saiqihe.todolistversion2;

import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import edu.neu.khoury.madsea.saiqihe.todolistversion2.RoomComponents.TodoNote;

public class TodoNoteAdapter extends ListAdapter<TodoNote,TodoNoteViewHolder> {
    private TodoModelView modelView;
    private final MainActivity mainActivity;
    public TodoNoteAdapter(@NonNull DiffUtil.ItemCallback<TodoNote> diffCallback, MainActivity mainActivity) {
        super(diffCallback);
        this.mainActivity=mainActivity;
        //modelView = new ViewModelProvider((ViewModelStoreOwner) this).get(TodoModelView.class);
    }


    @Override
    public TodoNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TodoNoteViewHolder holder =  TodoNoteViewHolder.create(parent);
        holder.itemView.findViewById(R.id.recycle_title).setBackgroundColor(Color.GRAY);
        holder.itemView.findViewById(R.id.recycle_title).setOnClickListener(view -> {
            TextView titleView = holder.itemView.findViewById(R.id.recycle_title);
            TextView detailView = holder.itemView.findViewById(R.id.recycle_detail);
            TextView idView = holder.itemView.findViewById(R.id.hidden_id);
            mainActivity.update(new TodoNote(Integer.parseInt(idView.getText().toString()),
                    titleView.getText().toString(),
                    detailView.getText().toString()));
            //Intent intent = new Intent(parent.getContext(),InsertActivity.class);
        });
        holder.itemView.findViewById(R.id.recycle_detail).setOnClickListener(view -> {
            TextView titleV = holder.itemView.findViewById(R.id.recycle_title);
            titleV.setBackgroundColor(Color.BLUE);
        });
        holder.itemView.findViewById(R.id.recycle_switch).setOnClickListener(view -> {
            TextView titleView = holder.itemView.findViewById(R.id.recycle_title);
            TextView detailView = holder.itemView.findViewById(R.id.recycle_detail);
            TextView idView = holder.itemView.findViewById(R.id.hidden_id);
            TodoNote t = new TodoNote(Integer.parseInt(idView.getText().toString()),
                    titleView.getText().toString(),
                    detailView.getText().toString());
            Switch aSwitch = holder.itemView.findViewById(R.id.recycle_switch);
            if(aSwitch.isChecked())t.setChecked("checked");
            else t.setChecked("unchecked");
            mainActivity.silentUpdate(t);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoNoteViewHolder holder, int position) {
        TodoNote note = getItem(position);
        holder.bind(note.getNoteId()+"",note.getTitle(),note.getDetail(),note.getChecked());
    }

    static class NoteDiff extends DiffUtil.ItemCallback<TodoNote>{

        @Override
        public boolean areItemsTheSame(@NonNull TodoNote oldItem, @NonNull TodoNote newItem) {
            return oldItem==newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull TodoNote oldItem, @NonNull TodoNote newItem) {
            return oldItem.equals(newItem);
        }
    }
}
