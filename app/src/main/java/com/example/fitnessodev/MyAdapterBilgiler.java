package com.example.fitnessodev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapterBilgiler extends RecyclerView.Adapter<MyAdapterBilgiler.MyViewHolder>
{

    Context context;
    List<Deneme> denemelist;

    public MyAdapterBilgiler(Context context, List<Deneme> denemelist)
    {
        this.context = context;
        this.denemelist = denemelist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new MyAdapterBilgiler.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.bilgiler,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        Deneme deneme = denemelist.get(position);

        String tarih=deneme.getTarih();
        String kalori=deneme.getKalori();
        String vakit=deneme.getVakit();

        holder.texttarih.setText(tarih);
        holder.textkalori.setText(kalori);
        holder.textvakit.setText(vakit);

    }

    @Override
    public int getItemCount()
    {
        return denemelist.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView texttarih,textvakit,textkalori;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            texttarih = itemView.findViewById(R.id.textViewtarih);
            textkalori = itemView.findViewById(R.id.textViewkalori);
            textvakit = itemView.findViewById(R.id.textViewvakit);
        }
    }

}