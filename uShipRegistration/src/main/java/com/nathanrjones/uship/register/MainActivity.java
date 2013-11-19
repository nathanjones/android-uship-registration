package com.nathanrjones.uship.register;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends Activity
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Shipper mShipper;

    private Switch mShipperType;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmailAddress;
    private EditText mCompanyName;
    private EditText mPassword;
    private Button mJoinButton;

    private String URL_CREATE_USER = "http://www.uship.com/mvc/register/CreateUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public void onStart(){
        super.onStart();

        mShipper = new Shipper();

        mShipperType = (Switch) findViewById(R.id.shipper_type);
        mFirstName = (EditText) findViewById(R.id.first_name);
        mLastName = (EditText) findViewById(R.id.last_name);
        mEmailAddress = (EditText) findViewById(R.id.email_address);
        mCompanyName = (EditText) findViewById(R.id.company_name);
        mPassword = (EditText) findViewById(R.id.password);
        mJoinButton = (Button) findViewById(R.id.join_button);

        mCompanyName.setVisibility(View.GONE);

        mJoinButton.setOnClickListener(this);
        mShipperType.setOnCheckedChangeListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.join_button:

                mShipper.setFirstName(mFirstName.getText().toString());
                mShipper.setLastName(mLastName.getText().toString());
                mShipper.setEmailAddress(mEmailAddress.getText().toString());
                mShipper.setCompanyName(mCompanyName.getText().toString());
                mShipper.setPassword(mPassword.getText().toString());

                CreateUserTask task = new CreateUserTask();
                task.execute(URL_CREATE_USER);
                break;
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            mShipper.setShipperType("business");
            mCompanyName.setVisibility(View.VISIBLE);
        } else {
            mShipper.setShipperType("personal");
            mCompanyName.setVisibility(View.GONE);
        }
    }

    private class CreateUserTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            String url = urls[0];

            JSONObject params = new JSONObject();
            try {
                params.put("siteId", "UnitedStates");
                params.put("userType","shipper");
                params.put("shipperType", mShipper.getShipperType());
                params.put("firstName", mShipper.getFirstName());
                params.put("lastName", mShipper.getLastName());
                params.put("emailAddress", mShipper.getEmailAddress());
                params.put("companyName", mShipper.getCompanyName());
                params.put("password", mShipper.getPassword());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            StringEntity entity = null;

            try {
                entity = new StringEntity(params.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");

            AsyncHttpClient client = new AsyncHttpClient();

            client.post(MainActivity.this, url, entity, "application/json", new AsyncHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(MainActivity.this, "Account Created!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://uship.com/signin.aspx"));
                    startActivity(intent);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
                {
                    Toast.makeText(MainActivity.this, "Well that didn't work", Toast.LENGTH_SHORT).show();
                }
            });

            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }
    }

}
