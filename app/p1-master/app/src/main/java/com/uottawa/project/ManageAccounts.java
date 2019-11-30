package com.uottawa.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//the code for this Activity (this class and  AccountAdapter) was based on the code given as an
//example of how to use RecyclerView in the Android Documentation at:
//https://developer.android.com/guide/topics/ui/layout/recyclerview
//other activities that also use RecyclerView were based off this one

public class ManageAccounts extends AppCompatActivity implements DeleteAccountDialog.AccountDialogListener{

    private RecyclerView view;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layout;
    DatabaseReference database;
    List<Account> users;
    OnClick onClick;
    //for testing
    private ArrayList<Account> testAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_accounts);

        view = (RecyclerView) findViewById((R.id.accounts));

        layout = new LinearLayoutManager(this);
        view.setLayoutManager(layout);

        //create list of accounts from database here
        users = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference("users");

         onClick = new OnClick() {
            @Override
            public void clicked(Account account) {

                DeleteAccountDialog dialog = new DeleteAccountDialog(account);
                dialog.show(getSupportFragmentManager(), "delete account");
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                users.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Account user = postSnapshot.getValue(Account.class);
                    if (!user.getAccountType().equals( "Admin"))
                        users.add(user);
                }


                adapter = new AccountAdapter(users, onClick);
                view.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });


    }

    public void onDone(View view) {
        Intent returnIntent = new Intent();

        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onDelete(Account account) {
        //delete account from database


        String id = account.getID();
        DatabaseReference dR = database.child(id);
        dR.removeValue();

        Toast.makeText(getApplicationContext(), "Account  deleted", Toast.LENGTH_LONG).show();

        //int index = testAccounts.indexOf(account);
        //testAccounts.remove(index);
       // adapter.notifyItemRemoved(index);
    }
}
