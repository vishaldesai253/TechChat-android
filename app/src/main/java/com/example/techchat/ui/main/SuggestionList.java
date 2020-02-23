package com.example.techchat.ui.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.techchat.ChatActivity;
import com.example.techchat.CurrentData;
import com.example.techchat.DatabaseHandler;
import com.example.techchat.Friend;
import com.example.techchat.MainActivity;
import com.example.techchat.MainWindow;
import com.example.techchat.MyCanvas;
import com.example.techchat.User;
import com.example.techchat.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.techchat.MainActivity;

import java.util.List;

public class SuggestionList extends Fragment {
    LinearLayout suggestionlist;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    DatabaseHandler db= MainActivity.db;

    LinearLayout friendlist;
    LinearLayout friendlistouter;


    public SuggestionList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_suggestion_list,container,false);
        suggestionlist=view.findViewById(R.id.suggestion_list);
        final LayoutInflater inside = getLayoutInflater();
        db=new DatabaseHandler(getContext());

        friendlistouter=(LinearLayout)inflater.inflate(R.layout.fragment_friend_list,container,false);
        friendlist=(LinearLayout)friendlistouter.findViewById(R.id.friend_list);
        myRef.addValueEventListener(new ValueEventListener() {
            List<String> emailList=db.getAllEmails();
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                   boolean flag=false;
                    Log.i("dataofcount",""+data.getChildrenCount());
                    if(data.getChildrenCount()==1)
                        flag=true;
                    for(DataSnapshot insidedata:data.getChildren()){

                        if(flag) {
                            User userdetails = insidedata.getValue(User.class);
                            Log.i("dataofcount user",""+userdetails.getEmail());
                            if((!emailList.contains(userdetails.getEmail())) && !(userdetails.getEmail().equals(CurrentData.getLoginEmail()))) {
                                final View friend = inside.inflate(R.layout.friend, suggestionlist, false);

                                final TextView friendName = (TextView) friend.findViewById(R.id.friendname);
                                final TextView friendEmail = friend.findViewById(R.id.friend_email);
                                Button addButton = friend.findViewById(R.id.addfriend);
                                addButton.setVisibility(View.VISIBLE);
                                addButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String[] name = friendName.getText().toString().split(" ");
                                        db.addFriend(new Friend(name[0], name[1], friendEmail.getText().toString()));
                                        friend.setVisibility(View.GONE);
                                        // MainWindow.viewPager.setAdapter(new SectionsPagerAdapter(MainWindow.class, MainWindow.getSupportFragmentManager()));
                                        TextView msgcount = friend.findViewById(R.id.messagecount);
                                        msgcount.setVisibility(View.VISIBLE);
                                        suggestionlist.removeView(friend);
                                        friend.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                startActivity(new ChatActivity().getIntent());
                                            }
                                        });

                                        friendlist.addView(friend);
                                    }
                                });
                                friendName.setText(userdetails.getFirstName() + " " + userdetails.getLastName());
                                friendEmail.setText(userdetails.getEmail());

                                View v = new MyCanvas(getContext());
                                Bitmap bitmap = Bitmap.createBitmap(70/*width*/, 70/*height*/, Bitmap.Config.ARGB_8888);
                                Canvas canvas = new Canvas(bitmap);
                                v.draw(canvas);
                                ImageView imageView=friend.findViewById(R.id.profileimage);
                                imageView.setImageBitmap(bitmap);
                                suggestionlist.addView(friend);
                            }
                        }
                        flag=true;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               // System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        return view;
    }
}
