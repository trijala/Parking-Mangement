package com.example.howlong;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.howlong.AddEmployee.LoadUrl;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class AddEmployee extends Activity {

	Button a, b;
	EditText n,u,p,l,e,m;
	Spinner sp;
	String na,us,pa,se,lo,em,mo,s;
	//String url="http://10.0.2.2/howlong/createuser.php";
	String url="http://ambesttechnologies.com/ParkingManagement/createemployee.php";
	ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_employee);
		 a = (Button) findViewById(R.id.savebutton1);
		 
		 n = (EditText) findViewById(R.id.nameeditText1);
		 u = (EditText) findViewById(R.id.usereditText2);
		 p = (EditText) findViewById(R.id.passeditText3);
		 
		 e = (EditText) findViewById(R.id.emaileditText5);
		 m = (EditText) findViewById(R.id.mobeditText6);
		
		
		
		a.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(n.equals("")||u.equals("")||p.equals("")||e.equals("")||m.equals("")){
					Toast.makeText(AddEmployee.this, "Pls fill the fields", Toast.LENGTH_LONG).show();
				}else{
					
					//na,us,pa,se,lo,em,mo
					/*na = n.getText().toString();
					us = u.getText().toString();
					pa = p.getText().toString();
					se = sp.getSelectedItem().toString();
					lo = l.getText().toString();
					em = e.getText().toString();
					mo = m.getText().toString();*/
					LoadUrl l=new LoadUrl();
					l.execute(url);
				}
			}
		});
	}
	public class LoadUrl extends AsyncTask<String, String, String>
	{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd=new ProgressDialog(AddEmployee.this);
			pd.setMessage("Loading...");
			pd.setMax(100);
			pd.setCancelable(false);
			pd.setIndeterminate(false);
			pd.show();
		}

		

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			//na,us,pa,se,lo,em,mo
			List<NameValuePair> param=new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("n",n.getText().toString()));
			param.add(new BasicNameValuePair("u",u.getText().toString()));
			param.add(new BasicNameValuePair("p",p.getText().toString()));
			param.add(new BasicNameValuePair("e",e.getText().toString()));
			param.add(new BasicNameValuePair("m",m.getText().toString()));
			
			
			JSONParser jp=new JSONParser();
			JSONObject obj=jp.makeHttpRequest(url, "POST", param);
			Log.d("JSON", obj.toString());
			try {
				final int s=obj.getInt("success");
				Log.d("int", ""+s);
				runOnUiThread( new Runnable() {
					public void run() {
						if(s==1)
						{
						Toast.makeText(AddEmployee.this, "Employee Registered Successfully", Toast.LENGTH_SHORT).show();
						Intent i=new Intent(AddEmployee.this, AdminHome.class);
						startActivity(i);
						}
						else
						{
							Toast.makeText(AddEmployee.this, "Failed to Created", Toast.LENGTH_SHORT).show();
							n.setText("");
							u.setText("");
							p.setText("");
							
							e.setText("");
							m.setText("");
						}
					}
				});
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(pd!=null && pd.isShowing())
				pd.dismiss();
		}
		
	}


}
