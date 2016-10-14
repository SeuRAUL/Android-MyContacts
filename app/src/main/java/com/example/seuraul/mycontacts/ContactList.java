package com.example.seuraul.mycontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ContactList extends AppCompatActivity {
    public static final int RESULT_DELETE = -1000;
    private List<Contact> contacts = new ArrayList<Contact>();
    private DatabaseManager dbManager;
    private ListView lstContacts;

    private void populateList() {
        contacts = dbManager.getAllContacts();
        List<String> contactFirstLastNames = new ArrayList<String>();
        for (Contact contact : contacts) {
            contactFirstLastNames.add(contact.getFirstName() + " " + contact.getLastName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, contactFirstLastNames);
        lstContacts.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager = new DatabaseManager(getApplicationContext());
        lstContacts = (ListView)findViewById(R.id.lstContacts);
        populateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_item_close_list:
                finish();
                break;
            case R.id.content_add_contact:
                Intent addContactIntent = new Intent(this, AddContact.class);
                startActivityForResult(addContactIntent, 1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
