package com.io2020.PodzielSieKsiazka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.io2020.PodzielSieKsiazka.adapters.MessageAdapter;
import com.io2020.PodzielSieKsiazka.retrofit.RetrofitInstance;
import com.io2020.PodzielSieKsiazka.schemas.MemberData;
import com.io2020.PodzielSieKsiazka.schemas.Message;
import com.io2020.PodzielSieKsiazka.schemas.Transaction;
import com.io2020.PodzielSieKsiazka.schemas.TransactionChange;
import com.io2020.PodzielSieKsiazka.schemas.TransactionStatus;
import com.scaledrone.lib.AuthenticationListener;
import com.scaledrone.lib.HistoryRoomListener;
import com.scaledrone.lib.Listener;
import com.scaledrone.lib.Member;
import com.scaledrone.lib.ObservableRoomListener;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Scaledrone;
import com.scaledrone.lib.SubscribeOptions;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionDetailsActivity extends AppCompatActivity implements RoomListener {

    private int transactionId;
    private boolean isOwner;
    private String status;

    private String name;

    private String channelID ="sTdWiVeudkHxFmVg" ;
    private String roomName;
    private EditText editText;
    private Scaledrone scaledrone;
    private MessageAdapter messageAdapter;
    private ListView messagesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        Intent intent = getIntent();

        status = intent.getStringExtra("status");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView bookTitle = findViewById(R.id.transactionBookTitle);
        bookTitle.setText(intent.getStringExtra("title"));

        TextView rentalTime = findViewById(R.id.rentalTime);
        rentalTime.setText(getString(R.string.transactionTime) + " " + intent.getStringExtra("endDate"));

        roomName = "observable-" + intent.getStringExtra("roomId");

        isOwner = intent.getBooleanExtra("isOwner",false);

        if(isOwner) {
            getSupportActionBar().setTitle(getString(R.string.transactionDetailsTitleOwner) + " " + intent.getStringExtra("customer"));
            name = intent.getStringExtra("owner");
        } else {
            getSupportActionBar().setTitle(getString(R.string.transactionDetailsTitleCustomer) + " " + intent.getStringExtra("owner"));
            name = intent.getStringExtra("customer");
        }

        if(!isOwner){
            View view = findViewById(R.id.view4);
            view.setVisibility(View.GONE);
            LinearLayout buttons = findViewById(R.id.ownerButtons);
            buttons.setVisibility(View.GONE);
        }

        if(status.equals(TransactionStatus.Declined.toString())){
            View view = findViewById(R.id.view4);
            view.setVisibility(View.GONE);
            LinearLayout buttons = findViewById(R.id.ownerButtons);
            buttons.setVisibility(View.GONE);
        }

        if(isOwner && status.equals(TransactionStatus.Accepted.toString())){
            Button cancelButton = findViewById(R.id.ownerButtons).findViewById(R.id.buttonDecline);
            cancelButton.setVisibility(View.GONE);
            Button acceptButton = findViewById(R.id.ownerButtons).findViewById(R.id.buttonAccept);
            acceptButton.setText(R.string.transactionRent);
        }

        if(status.equals(TransactionStatus.Rented.toString()) && isOwner){
            Button cancelButton = findViewById(R.id.ownerButtons).findViewById(R.id.buttonDecline);
            cancelButton.setVisibility(View.GONE);
            Button acceptButton = findViewById(R.id.ownerButtons).findViewById(R.id.buttonAccept);
            acceptButton.setText(R.string.transactionEnd);
        }

        if(status.equals(TransactionStatus.Finished.toString())){
            View view = findViewById(R.id.view4);
            view.setVisibility(View.GONE);
            LinearLayout buttons = findViewById(R.id.ownerButtons);
            buttons.setVisibility(View.GONE);
        }

        displayNotice(status);

        transactionId = intent.getIntExtra("transactionId", -1);

        editText = (EditText) findViewById(R.id.editText);

        messageAdapter = new MessageAdapter(this);
        messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);

        MemberData data = new MemberData(getRandomName(), getRandomColor());

        scaledrone = new Scaledrone(channelID, data);
        scaledrone.connect(new Listener() {

            @Override
            public void onOpen() {
                System.out.println("Scaledrone connection open");

                Room room = scaledrone.subscribe(roomName, TransactionDetailsActivity.this, new SubscribeOptions(100));
                room.listenToObservableEvents(new ObservableRoomListener() {
                    @Override
                    public void onMembers(Room room, ArrayList<Member> members) {
                        // Emits an array of members that have joined the room. This event is only triggered once, right after the user has successfully connected to the observable room.
                        // Keep in mind that the session user will also be part of this array, so the minimum size of the array is 1

                        Member member = members.get(0);
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            ContactsContract.Contacts.Data d = mapper.treeToValue(member.getClientData(), ContactsContract.Contacts.Data.class);
                        } catch (JsonProcessingException e) {

                        }
                    }

                    @Override
                    public void onMemberJoin(Room room, Member member) {
                        // A new member joined the room.
                    }

                    @Override
                    public void onMemberLeave(Room room, Member member) {
                        // A member left the room (or disconnected)
                    }
                });
                room.listenToHistoryEvents(new HistoryRoomListener() {
                    @Override
                    public void onHistoryMessage(Room room, com.scaledrone.lib.Message receivedMessage) {

                            final MemberData data = new MemberData();
                            final Message message = new Message(receivedMessage.getData().asText(), data);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    messageAdapter.add(message);
                                    messagesView.setSelection(messagesView.getCount() - 1);
                                }
                            });

                        System.out.println(receivedMessage.getData().asText());
                        System.out.println(receivedMessage.getClientID());
                    }
                });
            }

            @Override
            public void onOpenFailure(Exception ex) {
                System.err.println(ex);
            }

            @Override
            public void onFailure(Exception ex) {
                System.err.println(ex);
            }

            @Override
            public void onClosed(String reason) {
                System.err.println(reason);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void acceptOffer(View view){
        if(status.equals(TransactionStatus.Pending.toString())){
            TransactionUpdater.UpdateTransactionStatus(transactionId, TransactionStatus.Accepted);
            finish();
            return;
        }
        if(status.equals(TransactionStatus.Accepted.toString())){
            TransactionUpdater.UpdateTransactionStatus(transactionId, TransactionStatus.Rented);
            finish();
            return;
        }
        if(status.equals(TransactionStatus.Rented.toString())){
            TransactionUpdater.UpdateTransactionStatus(transactionId, TransactionStatus.Finished);
            finish();
        }

    }

    public void declineOffer(View view){
        TransactionUpdater.UpdateTransactionStatus(transactionId, TransactionStatus.Declined);
        finish();
    }

    private void displayNotice(String status){
        ConstraintLayout noticeLayout = findViewById(R.id.noticeLayout);
        noticeLayout.setVisibility(View.VISIBLE);
        TextView notice = noticeLayout.findViewById(R.id.notice);
        TextView noticeOk = noticeLayout.findViewById(R.id.noticeOK);

        switch (status){
            case "Pending":{
                notice.setText(R.string.noticePending);
                break;
            }
            case "Declined":{
                notice.setText(R.string.noticeDeclined);
                break;
            }
            case "Accepted":{
                notice.setText(R.string.noticeAccepted);
                break;
            }
            case "Rented":{
                notice.setText(R.string.noticeRented);
                break;
            }
            case "Finished":{
                notice.setText(R.string.noticeFinished);
                break;
            }
        }

        noticeOk.setOnClickListener(l -> {
            noticeLayout.setVisibility(View.GONE);
        });
    }

    public void sendMessage(View view) {
        String message = name + ": " + editText.getText().toString();
        if (message.length() > 0) {
            scaledrone.publish(roomName, message);
            editText.getText().clear();
        }
    }

    @Override
    public void onOpen(Room room) {
        System.out.println("Conneted to room");
    }

    @Override
    public void onOpenFailure(Room room, Exception ex) {
        System.err.println(ex);
    }

    @Override
    public void onMessage(Room room, com.scaledrone.lib.Message receivedMessage) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final MemberData data = mapper.treeToValue(receivedMessage.getMember().getClientData(), MemberData.class);
            final Message message = new Message(receivedMessage.getData().asText(), data);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    messageAdapter.add(message);
                    messagesView.setSelection(messagesView.getCount() - 1);
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String getRandomName() {
        String[] adjs = {"autumn", "hidden", "bitter", "misty", "silent", "empty", "dry", "dark", "summer", "icy", "delicate", "quiet", "white", "cool", "spring", "winter", "patient", "twilight", "dawn", "crimson", "wispy", "weathered", "blue", "billowing", "broken", "cold", "damp", "falling", "frosty", "green", "long", "late", "lingering", "bold", "little", "morning", "muddy", "old", "red", "rough", "still", "small", "sparkling", "throbbing", "shy", "wandering", "withered", "wild", "black", "young", "holy", "solitary", "fragrant", "aged", "snowy", "proud", "floral", "restless", "divine", "polished", "ancient", "purple", "lively", "nameless"};
        String[] nouns = {"waterfall", "river", "breeze", "moon", "rain", "wind", "sea", "morning", "snow", "lake", "sunset", "pine", "shadow", "leaf", "dawn", "glitter", "forest", "hill", "cloud", "meadow", "sun", "glade", "bird", "brook", "butterfly", "bush", "dew", "dust", "field", "fire", "flower", "firefly", "feather", "grass", "haze", "mountain", "night", "pond", "darkness", "snowflake", "silence", "sound", "sky", "shape", "surf", "thunder", "violet", "water", "wildflower", "wave", "water", "resonance", "sun", "wood", "dream", "cherry", "tree", "fog", "frost", "voice", "paper", "frog", "smoke", "star"};
        return (
                adjs[(int) Math.floor(Math.random() * adjs.length)] +
                        "_" +
                        nouns[(int) Math.floor(Math.random() * nouns.length)]
        );
    }

    private String getRandomColor() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer("#");
        while(sb.length() < 7){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }
}