package com.example.nueveactivity;

import android.app.TabActivity;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;



@SuppressWarnings("deprecation")
public class NueveActivity extends TabActivity {
  List<Lunch> model=new ArrayList<Lunch>();
  RestaurantAdapter adapter=null;
  EditText name=null;
  EditText address=null;
  EditText notes=null;
  RadioGroup types=null;
  Lunch current=null;
  int progress=0;//var para mi progress bar 
  

@Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
   //para q se vea como camina la progressBar
    //va ANTES DE Q SE VACIE SI NO NO JALA OJO !!!!
    requestWindowFeature(Window.FEATURE_PROGRESS);
    setContentView(R.layout.activity_nueve);
    
    name=(EditText)findViewById(R.id.name);
    address=(EditText)findViewById(R.id.addr);
    notes=(EditText)findViewById(R.id.notes);
    types=(RadioGroup)findViewById(R.id.types);
    
    Button save=(Button)findViewById(R.id.save);
    
    save.setOnClickListener(onSave);
    
    ListView list=(ListView)findViewById(R.id.restaurants);
    
    adapter=new RestaurantAdapter();
    list.setAdapter(adapter);
    
    TabHost.TabSpec spec=getTabHost().newTabSpec("tag1");
    
    spec.setContent(R.id.restaurants);
    spec.setIndicator("Booking", getResources()
                                .getDrawable(R.drawable.list));
    getTabHost().addTab(spec);
    
    spec=getTabHost().newTabSpec("tag2");
    spec.setContent(R.id.order);
    spec.setIndicator("Order", getResources()
                                  .getDrawable(R.drawable.resto));
    getTabHost().addTab(spec);
    
    getTabHost().setCurrentTab(0);
    
    list.setOnItemClickListener(onListClick);
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    new MenuInflater(this).inflate(R.menu.option, menu);

    return(super.onCreateOptionsMenu(menu));
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId()==R.id.toast) {
      String message="No restaurant selected";
      
      if (current!=null) {
        message=current.getNotes();
      }
      
      Toast.makeText(this, message, Toast.LENGTH_LONG).show();
      
      return(true);
      }
      else if (item.getItemId()==R.id.run)
      {
    	  setProgressBarVisibility(true);//hace q se vea mi barra
    	  progress=0;//ahi inicia y abajo se  incrementa 
    	  new Thread(longTask).start();
    	  return(true);
      }
    return(super.onOptionsItemSelected(item));
  }
  
  
  
  
  
  //Aca se va "actualizando " por q inicio en 0
  private void doSomeLongWork(final int incr) {
	    runOnUiThread(new Runnable()//aqui estoy declarando un thread donde lo unico q pasa es  la barra  
	    {
	      public void run() {
	        progress+=incr;
	        setProgress(progress);
	      }
	    });
	    
	    SystemClock.sleep(250); // se va a dormir 20mseg para hacer como q hace algo
	  }
  
  private View.OnClickListener onSave=new View.OnClickListener() {
    public void onClick(View v) {
      current=new Lunch();
      current.setName(name.getText().toString());
      current.setAddress(address.getText().toString());
      current.setNotes(notes.getText().toString());
      
      switch (types.getCheckedRadioButtonId()) {
        case R.id.sit_down:
          current.setType("sit_down");
          break;
          
        case R.id.take_out:
          current.setType("take_out");
          break;
          
        case R.id.delivery:
          current.setType("delivery");
          break;
      }
      
      adapter.add(current);
    }
  };
  
  private AdapterView.OnItemClickListener onListClick=new AdapterView.OnItemClickListener() {
    public void onItemClick(AdapterView<?> parent,
                             View view, int position,
                             long id) {
      current=model.get(position);
      
      name.setText(current.getName());
      address.setText(current.getAddress());
      notes.setText(current.getNotes());
      
      if (current.getType().equals("sit_down")) {
        types.check(R.id.sit_down);
      }
      else if (current.getType().equals("take_out")) {
        types.check(R.id.take_out);
      }
      else {
        types.check(R.id.delivery);
      }
      
      getTabHost().setCurrentTab(1);
    }
  };
  
  
  
  //hace un loop para q vaya avanzando mi barra
  private Runnable longTask=new Runnable() {
	    public void run() {
	      for (int i=0;i<20;i++) {
	        doSomeLongWork(500);
	      }
	      
	      runOnUiThread(new Runnable() {
	        public void run() {
	          setProgressBarVisibility(false);
	        }
	      });
	    }
	  };
  
 
	  
	  
	  class RestaurantAdapter extends ArrayAdapter<Lunch> {
    RestaurantAdapter() {
      super(NueveActivity.this, R.layout.row, model);
    }
    
    public View getView(int position, View convertView,
                        ViewGroup parent) {
      View row=convertView;
      RestaurantHolder holder=null;
      
      if (row==null) {                          
        LayoutInflater inflater=getLayoutInflater();
        
        row=inflater.inflate(R.layout.row, parent, false);
        holder=new RestaurantHolder(row);
        row.setTag(holder);
      }
      else {
        holder=(RestaurantHolder)row.getTag();
      }
      
      holder.populateFrom(model.get(position));
      
      return(row);
    }
  }
  
  static class RestaurantHolder {
    private TextView name=null;
    private TextView address=null;
    private ImageView icon=null;
    
    RestaurantHolder(View row) {
      name=(TextView)row.findViewById(R.id.title);
      address=(TextView)row.findViewById(R.id.address);
      icon=(ImageView)row.findViewById(R.id.icon);
    }
    
    void populateFrom(Lunch lu) {
      name.setText(lu.getName());
      address.setText(lu.getAddress());
  
      if (lu.getType().equals("sit_down")) {
        icon.setImageResource(R.drawable.ball_green);
      }
      else if (lu.getType().equals("take_out")) {
        icon.setImageResource(R.drawable.ball_red);
      }
      else {
        icon.setImageResource(R.drawable.ball_blue);
      }
    }
  }
}