package com.example.seuraul.mycontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    private DatabaseManager dbManager;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case AddContact.RESULT_CONTACT_SAVED:
                Serializable extra = data.getSerializableExtra("Contact");
                if (extra!=null) {
                    Contact contact = (Contact)extra;
                    dbManager.createContact(contact);
                    Toast.makeText(getApplicationContext(), contact.getFirstName() + " " + contact.getLastName() + " " + contact.getPhone() + " " + contact.getAddress() + " " + contact.getCity() + " " + contact.getState() + " " + contact.getZipcode(), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager = new DatabaseManager(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.menu_item_contact_list:
                Intent contactListIntent = new Intent(this, ContactList.class);
                startActivity(contactListIntent);
                break;
            case R.id.menu_item_add_contact:
                Intent addContectIntent = new Intent(this, AddContact.class);
                startActivityForResult(addContectIntent, 1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
