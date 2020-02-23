package com.example.techchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {

    // Write a message to the database


    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users/");
    LinearLayout chat_area;
    LayoutInflater inflater;
    DatabaseHandler db;

    public static ConstraintLayout contentView ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db=new DatabaseHandler(getApplicationContext());

        chat_area=contentView.findViewById(R.id.chat_area_layout);
        contentView.findViewById(R.id.button_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v);
            }
        });
        inflater= getLayoutInflater();
        Log.i("ChatActivity Current :", "onCreate: "+CurrentData.getLoginEmail());
        myRef.child(CurrentData.getLoginEmail().split("@")[0]).child("message").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                try {

                    Message newMessage = dataSnapshot.getValue(Message.class);
                    String message = newMessage.getMessage();
                    String sender = newMessage.getSender();
                    Log.i("ChatActivity :", message+" from:"+sender);

                    View myLayout = inflater.inflate(R.layout.left_message,chat_area,false);
                    TextView textView =myLayout.findViewById(R.id.text_message);
                    textView.setText(message);
                    chat_area.addView(myLayout);
                    myRef.child(CurrentData.getLoginEmail().split("@")[0]).child("message").child(dataSnapshot.getKey()).removeValue();

                }

                catch(Exception e) {
                    Log.i("chatactivity Error", e.getMessage());
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
        setContentView(contentView);
       // title=findViewById(R.id.title);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("ChatActivty ONSTART",""+this.toString());
    }

    public void sendMessage(View v){

       // try {
            EditText editText = contentView.findViewById(R.id.message_area);
           Log.i("ERRORNOT", "error");

            //save in sqllite


            db.addMessage(new DatabaseMessage(CurrentData.senderEmail, editText.getText().toString(), 0, "10.10"));

            View myLayout = inflater.inflate(R.layout.right_message, chat_area, false);

            TextView textView = myLayout.findViewById(R.id.text_message);
            textView.setText(editText.getText().toString());
            chat_area.addView(myLayout);


            sendMessageToFirebase(CurrentData.loginEmail.split("@")[0], CurrentData.senderEmail.split("@")[0],editText.getText().toString());

            Log.i("ChatActivity COUNT", db.getMessageCount() + " ");

         //   ScrollView scrollView = contentView.findViewById(R.id.chat_area);
           // scrollView.fullScroll(View.FOCUS_DOWN);
            editText.setText("");

    }
    public void sendMessageToFirebase(String sender,String receiver,String message) {
        myRef.child(receiver).child("message").push().setValue(new Message(sender,message));
    }

}
