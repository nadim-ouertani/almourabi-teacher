package com.nadim.almourabim;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TEST REGISTER";
    private FirebaseAuth mAuth;
    Button clear, bad;
    TextView studentID;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clear = findViewById(R.id.clear);
        bad = findViewById(R.id.btnbad);
        studentID = findViewById(R.id.studentID);

        mAuth = FirebaseAuth.getInstance();

        ref.child("teachers").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Teacher teacher= dataSnapshot.getValue(Teacher.class);
                Log.w(TAG, "LGS : " + teacher.LGS + " ID: " + mAuth.getCurrentUser().getUid());
                getStudent(teacher.LGS);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Eva eva = new Eva(mAuth.getCurrentUser().getUid(), studentID.getText().toString(), "B","ARA");
                //eva.writeNewEva(mAuth.getCurrentUser().getUid(), studentID.getText().toString(), "B","ARA");

                Eva eva = new Eva(mAuth.getCurrentUser().getUid(), "MAsW1LKvotbilYulqRuLY2lzwBz2", "G","FRE");
                eva.writeNewEva(mAuth.getCurrentUser().getUid(), "MAsW1LKvotbilYulqRuLY2lzwBz2", "G","FRE");
            }
        });
    }


    void getStudent(String LGS) {
        int minisePos = LGS.indexOf("-");
        while (minisePos <= LGS.length() - 4 && minisePos > 0) {
            int start = minisePos - 3; int end = minisePos;int length = LGS.length(); int initPos = minisePos;
            final String lg = LGS.substring(start,end);

            System.out.println("LENGTH: " + length + " | MINISE POS: " + initPos + " | START POS: " + start + " | END POS: " + end + " | LG: " + lg );

            //fetch student
            ref.child("students").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    Student student = dataSnapshot.getValue(Student.class);
                    if (student.LG.equals(lg)) {
                        System.out.println(student.firstname);
                        studentID.setText(student.id);
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }


            });

            minisePos = LGS.indexOf("-", minisePos + 1);
        }

    }

}
