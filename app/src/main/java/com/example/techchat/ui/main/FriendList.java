package com.example.techchat.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.techchat.ChatActivity;
import com.example.techchat.CurrentData;
import com.example.techchat.DatabaseHandler;
import com.example.techchat.DatabaseMessage;
import com.example.techchat.Friend;
import com.example.techchat.MainActivity;
import com.example.techchat.MainWindow;
import com.example.techchat.MyCanvas;
import com.example.techchat.R;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class FriendList extends Fragment {
    LinearLayout friendlist;
    LinearLayout friendlistouter;
    DatabaseHandler db= MainActivity.db;
    public FriendList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        friendlistouter=(LinearLayout)inflater.inflate(R.layout.fragment_friend_list,container,false);
        friendlist=(LinearLayout)friendlistouter.findViewById(R.id.friend_list);
        db= new DatabaseHandler(getContext());

        List<Friend> list=db.getAllFriends();
        ConstraintLayout allchats[]=new ConstraintLayout[list.size()];
        final LayoutInflater inside = getLayoutInflater();
        int i=0;
        for(Friend friend:list){
            allchats[i]=(ConstraintLayout) inflater.inflate(R.layout.activity_chat,null,false);
            TextView title=allchats[i].findViewById(R.id.title);

            List<DatabaseMessage> allMsg=db.getAllMessages(friend.getEmail());
            for(DatabaseMessage msg:allMsg){
                addMessageToChatArea(allchats[i],msg,inflater);
            }

            addFriend(friend,allchats[i],inflater,title);
            i++;

        }
        return friendlistouter;
    }
    public void addFriend(final Friend friend, final ConstraintLayout ca, final LayoutInflater inflater,final TextView title){

        final View friendlayout = inflater.inflate(R.layout.friend, friendlist, false);
        friendlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title.setText(friend.getFirstName()+" "+friend.getLastName());
                CurrentData.setSenderEmail(friend.getEmail());
               if(ca.getParent()!=null)
                {
                    Log.i("Error in ","get parent error");
                    ((ViewGroup)ca.getParent()).removeView(ca);
               }
                View v1 = new MyCanvas(getContext());
                Bitmap bitmap = Bitmap.createBitmap(70/*width*/, 70/*height*/, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                v1.draw(canvas);
                ImageView imageView=ca.findViewById(R.id.profileimage);
                imageView.setImageBitmap(bitmap);



                ChatActivity.contentView=ca;
                Intent intent=new Intent(getContext(),ChatActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(intent);

            }
        });

        MyCanvas.ch=friend.getFirstName().charAt(0)+"";

        View v = new MyCanvas(getContext());
        Bitmap bitmap = Bitmap.createBitmap(70/*width*/, 70/*height*/, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        ImageView imageView=friendlayout.findViewById(R.id.profileimage);
        imageView.setImageBitmap(bitmap);



        TextView friendName=friendlayout.findViewById(R.id.friendname);
        friendName.setText(friend.getFirstName()+" "+friend.getLastName());

        TextView friendEMail=friendlayout.findViewById(R.id.friend_email);
        friendEMail.setText(friend.getEmail());

        friendlist.addView(friendlayout);
    }
    public void addMessageToChatArea(ConstraintLayout chatcontent,DatabaseMessage message,LayoutInflater inflater){
        View myLayout;
       LinearLayout chat_area=chatcontent.findViewById(R.id.chat_area_layout);
        if(message.getInOut()==0)
            myLayout = inflater.inflate(R.layout.left_message,chat_area,false);
        else
            myLayout = inflater.inflate(R.layout.right_message,chat_area,false);

        TextView textView =myLayout.findViewById(R.id.text_message);
        textView.setText(message.getMessage());

        TextView textTime =myLayout.findViewById(R.id.msg_time);
        textView.setText(message.getTime());

        chat_area.addView(myLayout);
    }


}
