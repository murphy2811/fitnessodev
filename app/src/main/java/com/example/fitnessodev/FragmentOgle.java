package com.example.fitnessodev;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FragmentOgle extends Fragment
{
    public TextView kalori;
    RecyclerView rv;
    List<Food> yiyecekListt;
    DatabaseReference databaseReference;
    ImageView imageView;
    Button gonder22;
    View view;
    private HashMap<String,Object> mData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_ogle, container, false);
        rv=view.findViewById(R.id.myrecycler);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-fitness-c88d1-default-rtdb.firebaseio.com/");

        kalori=view.findViewById(R.id.kalori);
        gonder22=view.findViewById(R.id.gonder2);

        yiyecekListt= new ArrayList<>();

        Query query=databaseReference.child("Öğle");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    String url=dataSnapshot.child("url").getValue().toString();
                    String ad=dataSnapshot.child("isim").getValue().toString();
                    String kalori=dataSnapshot.child("kalori").getValue().toString();

                    yiyecekListt.add(new Food(ad,kalori,url));
                }

                rv.setLayoutManager(new LinearLayoutManager(view.getContext()));

                rv.setAdapter(new MyAdapterFood(view.getContext(), yiyecekListt, new ClickDelegate(){

                    public void onClick(String text){

                        String ilkkalori=kalori.getText().toString();
                        int gelenkalori=Integer.parseInt(text);
                        int ilkkalorii=Integer.parseInt(ilkkalori);
                        int sonhal=gelenkalori+ilkkalorii;

                        String sonhals=String.valueOf(sonhal);
                        kalori.setText(sonhals);
                    }

                    @Override
                    public void onClickDelete(String text) {
                        String ilkkalori=kalori.getText().toString();
                        int gelenkalori=Integer.parseInt(text);
                        int ilkkalorii=Integer.parseInt(ilkkalori);
                        int sonhal=ilkkalorii-gelenkalori;

                        String sonhals=String.valueOf(sonhal);
                        kalori.setText(sonhals);
                    }
                }));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        gonder22.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String vakit = "Öğle";
                String gkalori = kalori.getText().toString();
                SimpleDateFormat bicim=new SimpleDateFormat("dd/M/yyyy");
                Date tarih=new Date();
                String tarih1 = bicim.format(tarih);
                Intent ii=getActivity().getIntent();
                String data = ii.getStringExtra("Key");

                Calendar calendar=Calendar.getInstance();
                String tarih12= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

                String a = tarih12 + "-"+ vakit;

                databaseReference.child("Kullanicilar").child(data).child("Bilgi").addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        mData=new HashMap<>();
                        mData.put("Tarih",tarih1);
                        mData.put("Kalori",kalori.getText().toString());
                        mData.put("Vakit",vakit);

                        databaseReference.child("Kullanicilar").child(data).child("Bilgi").child(a).setValue(mData).addOnCompleteListener(new OnCompleteListener<Void>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {

                    }
                });

            }
        });


        return view;
    }
}