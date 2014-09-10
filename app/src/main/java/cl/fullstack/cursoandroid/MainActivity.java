package cl.fullstack.cursoandroid;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ListActivity {
    private ProgressDialog pDialog;
    List<Contact> contacts;
    ArrayList<HashMap<String, String>> distList;
    public static ArrayList<String> ArrayofName = new ArrayList<String>();

    private static final String id = "id";
    private static final String name = "name";
    private static final String phoneNumber = "phoneNumber";


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hashmap for ListView
        distList = new ArrayList<HashMap<String, String>>();

        // Loading in Background Thread
        new ListLoad().execute();

        // get listview
        ListView lv = getListView();

        //detail
        lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2,
                                    long arg3) {

                Intent i = new Intent(getApplicationContext(), ContactActivity.class);

                String c_id = ((TextView) view.findViewById(R.id.id)).getText().toString();
                i.putExtra("c_id", c_id);


                startActivity(i);
            }
        });
    }

    class ListLoad extends AsyncTask<String, String, String> {

        DbManager manager;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Cargando lista...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {

            DbManager db = new DbManager(MainActivity.this);

            /**
             * CRUD Operations
             * */
            // Inserting Contacts
            Log.d("Insert: ", "Inserting ..");
            db.addContact(new Contact("Ravi", "91"));
            db.addContact(new Contact("Srinivas", "99"));
            db.addContact(new Contact("Tommy", "95"));
            db.addContact(new Contact("Karthik", "93"));

            // Reading all contacts
            Log.d("Reading: ", "Reading all contacts..");
            contacts = db.getAllContacts();

            for (Contact cn : contacts) {
                String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
                // Writing Contacts to log
                Log.d("Name: ", log);

                HashMap<String, String> map = new HashMap<String, String>();

                // adding each child node to HashMap key => value
                map.put(id, String.valueOf(cn.getID()));
                map.put(name,cn.getName());
                map.put(phoneNumber,cn.getPhoneNumber());

                // adding HashList to ArrayList
                distList.add(map);

            }

            db.getAllContacts();

            return null;
        }
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all albums
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {

                    ListAdapter adapter = new SimpleAdapter(
                            MainActivity.this, distList,
                            R.layout.contact, new String[] {
                            id,name,phoneNumber }, new int[] {
                            R.id.id, R.id.name, R.id.phoneNumber });

                    // updating listview
                    setListAdapter(adapter);
                    //// Change Activity Title
                    setTitle("contactos");
                }
            });

        }

    }
}