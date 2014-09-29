package cl.fullstack.cursoandroid;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddContact extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Button BtAdd = (Button) findViewById(R.id.botonLista);
        BtAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                EditText listText = (EditText) findViewById(R.id.listText);
                EditText idText = (EditText) findViewById(R.id.idText);

                String lista_id = listText.getText().toString();
                String user = idText.getText().toString();
                Log.d("id lista", lista_id);
                Log.d("user", user);
                DbManager db = new DbManager(AddContact.this);

                /**
                 * CRUD Operations
                 * */
                // Inserting Contacts
                Log.d("Insert: ", "Inserting ..");
                db.addContact(new Contact(lista_id, user));


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
