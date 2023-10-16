package com.chivumarius.smartreply;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.smartreply.SmartReply;
import com.google.mlkit.nl.smartreply.SmartReplyGenerator;
import com.google.mlkit.nl.smartreply.SmartReplySuggestion;
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult;
import com.google.mlkit.nl.smartreply.TextMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // ▼ "DECLARATION" OF "UI ELEMENTS" ▼
    TextView resultTv;
    Button senderBtn,receivedBtn;
    EditText senderEd,receiverEd;


    // (STEP 1-1 - "SMART REPLY") "CREATING" A "CONVERSATION HISTORY" OBJECT
    //    → "DECLARATION":
    List<TextMessage> conversation;

    // (STEP 2-1 - "SMART REPLY") LOADING "SMART REPLY" MODEL
    //    → "DECLARATION" ▼
    SmartReplyGenerator smartReply;





    // ▬ "ON CREATE" METHOD ▬
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // ▼ "INITIALIZATION" OF "UI ELEMENTS" ▼
        resultTv = findViewById(R.id.textView);
        senderBtn =findViewById(R.id.button);
        receivedBtn = findViewById(R.id.button2);
        senderEd = findViewById(R.id.editTextTextPersonName);
        receiverEd = findViewById(R.id.editTextTextPersonName2);



        // (STEP 1-2 - "SMART REPLY") "CREATING" A "CONVERSATION HISTORY" OBJECT
        //    → "INITIALIZATION":
        conversation = new ArrayList<>();




        // ▼ EVENT LISTENERS → FOR "SENDER BUTTON" ▼
        senderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // (STEP 1-3 - "SMART REPLY") "CREATING" A "CONVERSATION HISTORY" OBJECT
                //    → "ADDING" THE "MESSAGE" AND ITS "TIMESTAMP"
                //    → TO THE "CONVERSATION HISTORY" OBJECT
                //    → FOR "LOCAL USER":
                conversation.add(
                        TextMessage.createForLocalUser(
                            senderEd.getText().toString(),
                                System.currentTimeMillis()
                        )
                );


                // ▼ "RESTING" THE "TEXT"
                //      → FOR "RESULT TEXTVIEW"
                //      → AND "SENDER EDIT TEXT" ▼
                resultTv.setText("");
                senderEd.setText("");



                // (STEP 2-3 - "SMART REPLY") LOADING "SMART REPLY" MODEL
                //    → SETTING "ADD ON SUCCESS LISTENER"  METHOD
                //    → AND "ADD ON FAILURE LISTENER" METHOD ▼
                smartReply.suggestReplies(conversation)
                        .addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                                // ▼ "RESETING" THE "TEXT" ▼
                                resultTv.setText("");


                                // ▼ CREATING "RESULT" VARIABLE ▼
                                SmartReplySuggestionResult result = (SmartReplySuggestionResult) o;


                                // ▼ CHECKING "RESULT" ▼
                                if (result.getStatus() == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                                    // The conversation's language isn't supported, so
                                    // the result doesn't contain any suggestions.
                                } else if (result.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS) {
                                    // (STEP 2-4 - "SMART REPLY") LOADING "SMART REPLY" MODEL
                                    //    → A "SMART REPLY SUGGESTION RESULT" OBJECT
                                    //    → IS "PASSED" TO THE "SUCCESS HANDLER" ▼
                                    for (SmartReplySuggestion suggestion : result.getSuggestions()) {
                                        String replyText = suggestion.getText();

                                        // ▼ SHOWING "REPLY TEXT" ▼
                                        resultTv.append(replyText + "\n");
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                // ...
                            }
                        });

            }
        });


        // ▼ EVENT LISTENERS → FOR "RECEIVED BUTTON" ▼
        receivedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // (STEP 1-4 - "SMART REPLY") "CREATING" A "CONVERSATION HISTORY" OBJECT
                //    → "ADDING" THE "MESSAGE" AND ITS "TIMESTAMP"
                //    → TO THE "CONVERSATION HISTORY" OBJECT
                //    → FOR "REMOTE USER":
                conversation.add(
                        TextMessage.createForRemoteUser(
                             receiverEd.getText().toString(),
                             System.currentTimeMillis(),
                            "1"
                        )
                );


                // ▼ "RESTING" THE "TEXT"
                //      → FOR "RESULT TEXTVIEW"
                //      → AND "SENDER EDIT TEXT" ▼
                resultTv.setText("");
                receiverEd.setText("");


                // (STEP 2-3 - "SMART REPLY") LOADING "SMART REPLY" MODEL
                //    → SETTING "ADD ON SUCCESS LISTENER"  METHOD
                //    → AND "ADD ON FAILURE LISTENER" METHOD ▼
                smartReply.suggestReplies(conversation)
                        .addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                                // ▼ "RESETING" THE "TEXT" ▼
                                resultTv.setText("");


                                // ▼ CREATING "RESULT" VARIABLE ▼
                                SmartReplySuggestionResult result = (SmartReplySuggestionResult) o;


                                // ▼ CHECKING "RESULT" ▼
                                if (result.getStatus() == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                                    // The conversation's language isn't supported, so
                                    // the result doesn't contain any suggestions.
                                } else if (result.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS) {
                                    // (STEP 2-4 - "SMART REPLY") LOADING "SMART REPLY" MODEL
                                    //    → A "SMART REPLY SUGGESTION RESULT" OBJECT
                                    //    → IS "PASSED" TO THE "SUCCESS HANDLER" ▼
                                    for (SmartReplySuggestion suggestion : result.getSuggestions()) {
                                        String replyText = suggestion.getText();

                                        // ▼ SHOWING "REPLY TEXT" ▼
                                        resultTv.append(replyText + "\n");
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                // ...
                            }
                        });
            }
        });



        // (STEP 2-2 - "SMART REPLY") LOADING "SMART REPLY" MODEL
        //    → "INITIALIZATION" ▼
        smartReply = SmartReply.getClient();



    }




    // ▬ "ON DESTROY()" METHOD ▬
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
    }

}
