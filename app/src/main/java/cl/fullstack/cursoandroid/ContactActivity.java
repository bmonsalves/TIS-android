package cl.fullstack.cursoandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;


public class ContactActivity extends ActionBarActivity {
    private ProgressDialog pDialog;
    private int contactID;
    private String contactName;
    private String contactPhone;
    private String contact_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        //get notification data
        Intent i = getIntent();

        contact_id = i.getStringExtra("c_id");

        new DetailLoad().execute();
    }

    class DetailLoad extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ContactActivity.this);
            pDialog.setMessage("Cargando Contacto...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            DbManager db = new DbManager(ContactActivity.this);

            Contact contacts = db.getContact(Integer.parseInt(contact_id));

            contactID = contacts.getID();
            contactName = contacts.getName();
            contactPhone = contacts.getPhoneNumber();

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all albums
            pDialog.dismiss();
            // updating UI from Background Thread
            TextView txt_id = (TextView) findViewById(R.id.id);
            TextView txt_name = (TextView) findViewById(R.id.name);
            TextView txt_phone = (TextView) findViewById(R.id.phoneNumber);

            // displaying song data in view
            txt_id.setText(String.valueOf(contactID));
            txt_name.setText(contactName);
            txt_phone.setText(contactPhone);

        }

    }
}