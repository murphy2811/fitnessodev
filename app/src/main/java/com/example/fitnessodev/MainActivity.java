package com.example.fitnessodev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    Button giriss,kayitt;
    EditText email,sifre;
    String kullanici_adi,sifree;
    Intent i̇ntent;
    String gkullanici_adi,gsifre;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    private HashMap<String,Object> mData;
    private FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=findViewById(R.id.emaill);
        sifre=findViewById(R.id.password);
        giriss=findViewById(R.id.giris);
        kayitt=findViewById(R.id.kayit);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-fitness-c88d1-default-rtdb.firebaseio.com/");
        mAuth= FirebaseAuth.getInstance();

        kayitt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                kullanici_adi=email.getText().toString();
                sifree=sifre.getText().toString();

                if(!TextUtils.isEmpty(kullanici_adi) && !TextUtils.isEmpty(sifree))
                {
                    mAuth.createUserWithEmailAndPassword(kullanici_adi,sifree).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            databaseReference.child("Kullanicilar").addListenerForSingleValueEvent(new ValueEventListener()
                            {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot)
                                {
                                    if(snapshot.hasChild(kullanici_adi))
                                    {
                                        Toast.makeText(MainActivity.this, "Mevcut", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(MainActivity.this, "Kayıt yapıldı.", Toast.LENGTH_SHORT).show();

                                        Intent intent;
                                        intent=new Intent(MainActivity.this,MainActivity2.class);

                                        intent.putExtra("Key", kullanici_adi);

                                        firebaseUser=mAuth.getCurrentUser();

                                        mData=new HashMap<>();
                                        mData.put("Kullanici Adi",kullanici_adi);
                                        mData.put("Sifre",sifree);
                                        databaseReference.child("Kullanicilar").child(kullanici_adi).setValue(mData).addOnCompleteListener(new OnCompleteListener<Void>()
                                        {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                Toast.makeText(MainActivity.this, "veritabanı", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        startActivity(intent);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error)
                                {
                                }
                            });
                        }
                    });

                }
            }
        });


        giriss.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                kullanici_adi=email.getText().toString();
                sifree=sifre.getText().toString();

                if(!TextUtils.isEmpty(kullanici_adi) && !TextUtils.isEmpty(sifree))
                {
                    databaseReference.child("Kullanicilar").addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            if(snapshot.hasChild(kullanici_adi))
                            {
                                String sifre61=String.valueOf(snapshot.child(kullanici_adi).child("Sifre").getValue());

                                if(sifre61.equals(sifree))
                                {
                                    Intent intent1=new Intent(MainActivity.this,MainActivity2.class);

                                    intent1.putExtra("Key", kullanici_adi);
                                    startActivity(intent1);
                                    Toast.makeText(MainActivity.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Giriş Başarısız", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {

                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "Boş bırakma", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}