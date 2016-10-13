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
import android.widget.EditText;

import java.io.Serializable;

public class AddContact extends AppCompatActivity {

    public static final int RESULT_CONTACT_SAVED =1000;
    public static final int RESULT_CONTACT_UPDATED = 2000;
    Contact editingContact;
    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtPhone;
    private EditText txtAddress;
    private EditText txtCity;
    private EditText txtState;
    private EditText txtZipcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editingContact = null;
        txtFirstName = (EditText)findViewById(R.id.txtFirstName);
        txtLastName = (EditText)findViewById(R.id.txtLastName);
        txtPhone = (EditText)findViewById(R.id.txtPhone);
        txtAddress = (EditText)findViewById(R.id.txtAddress);
        txtCity = (EditText)findViewById(R.id.txtCity);
        txtState = (EditText)findViewById(R.id.txtState);
        txtZipcode = (EditText)findViewById(R.id.txtZipcode);

        Serializable extra = getIntent().getSerializableExtra("Contact");
        if (extra!=null) {
            editingContact = (Contact)extra;
            txtFirstName.setText(editingContact.getFirstName());
            txtLastName.setText(editingContact.getLastName());
            txtPhone.setText(editingContact.getPhone());
            txtAddress.setText(editingContact.getAddress());
            txtCity.setText(editingContact.getCity());
            txtState.setText(editingContact.getState());
            txtZipcode.setText(editingContact.getZipcode());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_item_save_contact:
                handleSave();
                break;
            case R.id.menu_item_cancel_contact:
                handleCancel();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleCancel() {
        setResult(RESULT_CANCELED, new Intent());
        finish();
    }

    private void handleSave() {
        Intent saveIntent = new Intent();
        if (editingContact==null) {
            editingContact = new Contact(-1, txtFirstName.getText().toString(), txtLastName.getText().toString(), txtPhone.getText().toString(), txtAddress.getText().toString(), txtCity.getText().toString(), txtState.getText().toString(), txtZipcode.getText().toString());
            saveIntent.putExtra("Contact", editingContact);
            setResult(RESULT_CONTACT_SAVED, saveIntent);
        } else {
            editingContact = new Contact(editingContact.getId(), editingContact.getFirstName().toString(), editingContact.getLastName().toString(), editingContact.getPhone().toString(), editingContact.getAddress().toString(), editingContact.getCity().toString(), editingContact.getState().toString(), editingContact.getZipcode().toString());
            saveIntent.putExtra("Contact", editingContact);
            setResult(RESULT_CONTACT_UPDATED, saveIntent);
        }
        finish();
    }
}
