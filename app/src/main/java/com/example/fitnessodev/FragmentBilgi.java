package com.example.fitnessodev;

import android.content.Context;
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
import java.util.List;
import java.util.zip.DataFormatException;

public class FragmentBilgi extends Fragment
{
    View view;
    RecyclerView rv;
    Button del;
    List<Deneme> bilgilist;
    DatabaseReference databaseReference;

    Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_bilgi, container, false);
        del=view.findViewById(R.id.deletebutton);
        pull();
        return view;
    }

    private void pull()
    {
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-fitness-c88d1-default-rtdb.firebaseio.com/");
        rv=view.findViewById(R.id.myrecycler);

        bilgilist= new ArrayList<>();
        Intent ii=getActivity().getIntent();
        String data = ii.getStringExtra("Key");

        Query query=databaseReference.child("Kullanicilar").child(data).child("Bilgi");

        query.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {

                    String kalori2=dataSnapshot.child("Kalori").getValue().toString();
                    String tarih2=dataSnapshot.child("Tarih").getValue().toString();
                    String vakit2=dataSnapshot.child("Vakit").getValue().toString();

                    bilgilist.add(new Deneme(tarih2,kalori2,vakit2));
                }
                rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
                rv.setAdapter(new MyAdapterBilgiler(view.getContext(),bilgilist));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

    }
}