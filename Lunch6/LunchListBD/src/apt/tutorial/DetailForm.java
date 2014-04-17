package apt.tutorial;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import android.net.*;
import android.view.*;

/*COMENTAR TODO EL CODIGO PA SABER Q HICE :)*/

public class DetailForm extends Activity {
  EditText feed=null;
  EditText name=null;
  EditText address=null;
  EditText phone=null;
  EditText notes=null;
  RadioGroup types=null;
  RestaurantHelper helper=null;
  String restaurantId=null;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.detail_form);
    
    helper=new RestaurantHelper(this);
    
    feed=(EditText)findViewById(R.id.feed);
    name=(EditText)findViewById(R.id.name);
    address=(EditText)findViewById(R.id.addr);
    phone=(EditText)findViewById(R.id.phone);
    notes=(EditText)findViewById(R.id.notes);
    types=(RadioGroup)findViewById(R.id.types);
    
    Button save=(Button)findViewById(R.id.save);
    
    save.setOnClickListener(onSave);
    
    restaurantId=getIntent().getStringExtra(LunchList.ID_EXTRA);
    
    if (restaurantId!=null) {
      load();
    }
  }
  
  @Override                                                                   
  public void onDestroy() {
    super.onDestroy();
  
    helper.close();
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
  new MenuInflater(this).inflate(R.menu.details_option, menu);
  return(super.onCreateOptionsMenu(menu));
  }
  
  public boolean onOptionsItemSelected(MenuItem item) {
	  if (item.getItemId()==R.id.feed) {
	  if (isNetworkAvailable()) {
	  Intent i=new Intent(this, FeedActivity.class);
	  i.putExtra(FeedActivity.FEED_URL, feed.getText().toString());
	  startActivity(i);
	  }
	  else {
	  Toast
	  .makeText(this, "Sorry, the Internet is not available",
	  Toast.LENGTH_LONG)
	  .show();
	  }
	  return(true);
	  }
	  return(super.onOptionsItemSelected(item));
	  }
	  private boolean isNetworkAvailable() {
	  ConnectivityManager
	  cm=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
	  NetworkInfo info=cm.getActiveNetworkInfo();
	  return(info!=null);
}
	  private void load() {
		    Cursor c=helper.getById(restaurantId);

		    c.moveToFirst();    
		    name.setText(helper.getName(c));
		    address.setText(helper.getAddress(c));
		    phone.setText(helper.getPhone(c));
		    notes.setText(helper.getNotes(c));
		    feed.setText(helper.getFeed(c));
		    
		    if (helper.getType(c).equals("sit_down")) {
		      types.check(R.id.sit_down);
		    }
		    else if (helper.getType(c).equals("take_out")) {
		      types.check(R.id.take_out);
		    }
		    else {
		      types.check(R.id.delivery);
		    }
		    
		    c.close();
		  }

	  private View.OnClickListener onSave=new View.OnClickListener() {
	    public void onClick(View v) {
	      String type=null;
	      
	      switch (types.getCheckedRadioButtonId()) {
	        case R.id.sit_down:
	          type="sit_down";
	          break;
	        case R.id.take_out:
	          type="take_out";
	          break;
	        case R.id.delivery:
	          type="delivery";
	          break;
	      }

	      if (restaurantId==null) {
	        helper.insert(name.getText().toString(),
	                      address.getText().toString(), phone.getText().toString(),
	                      type,notes.getText().toString(),feed.getText().toString());
	      }
	      else {
	        helper.update(restaurantId, name.getText().toString(),
	        		      phone.getText().toString(),
	                      address.getText().toString(), type,
	                      notes.getText().toString(),feed.getText().toString());
	      }
	      
	      finish();
	    }
	  };
	/*  @Override
  public void onSaveInstanceState(Bundle state) {
    super.onSaveInstanceState(state);
    
    state.putString("feed", feed.getText().toString());
    state.putString("name", name.getText().toString());
    state.putString("address", address.getText().toString());
    state.putString("phone", phone.getText().toString());
    state.putString("notes", notes.getText().toString());
    state.putInt("type", types.getCheckedRadioButtonId());
  }

  @Override
  public void onRestoreInstanceState(Bundle state) {
    super.onRestoreInstanceState(state);
    
    feed.setText(state.getString("feed"));
    name.setText(state.getString("name"));
    address.setText(state.getString("address"));
    phone.setText(state.getString("phone"));
    notes.setText(state.getString("notes"));
    types.check(state.getInt("type"));
  }*/
  
 
  
  }