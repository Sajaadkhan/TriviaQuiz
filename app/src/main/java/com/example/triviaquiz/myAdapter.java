package com.example.triviaquiz;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myviewholder> {

    ArrayList<QuizScore> dataholder;
    Context context1;

    public myAdapter(ArrayList<QuizScore> dataholder, Context context) {
        this.dataholder = dataholder;
        this.context1=context;
    }


    @NonNull
    @NotNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item_layout,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull myviewholder holder, int position) {

        final QuizScore temp=dataholder.get(position);

        holder.name.setText(dataholder.get(position).getName());
        holder.category.setText(dataholder.get(position).getCategoryName());
        holder.defficulty.setText(dataholder.get(position).getLevel());
        holder.score.setText("Score : "+String.valueOf(dataholder.get(position).getScore()));




    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView name,category,score,defficulty;
        CardView cardView;
        public myviewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            //widget reference
            cardView=(CardView) itemView.findViewById(R.id.cardViewL);
            name=(TextView) itemView.findViewById(R.id.recName);
            category=(TextView) itemView.findViewById(R.id.recCat);
            score=(TextView) itemView.findViewById(R.id.recScore);
            defficulty=(TextView) itemView.findViewById(R.id.recDef);

        }
    }
}
