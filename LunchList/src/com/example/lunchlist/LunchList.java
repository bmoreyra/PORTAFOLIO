package com.example.lunchlist;

import android.app.Activity;
import android.widget.*;
import android.view.*;
import android.app.TabActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class LunchList extends TabActivity {

	   List<Restaurant> model=new ArrayList<Restaurant>();//ahora tenemos varios restaurantes a escoger
	   RestaurantAdapter adapter=null;
	   
	   EditText name=null;
	   EditText address=null;
	   EditText phone=null;
	   EditText notes=null;//mis notitas
	   RadioGroup types=null;
	   Restaurant current=null;//esto servira para manejar las notas 
	   
		
		
	   @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_lunch_list);
	        
	        name=(EditText)findViewById(R.id.name);
	        address=(EditText)findViewById(R.id.addr);
	        phone=(EditText)findViewById(R.id.phone);
	        types=(RadioGroup)findViewById(R.id.types);
	        
	        
	                
	        Button save=(Button)findViewById(R.id.save);
	        save.setOnClickListener(onSave);
	        ListView list=(ListView)findViewById(R.id.restaurants);//la lista en la q se van a vaciar mis datos
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
	                                      .getDrawable(R.drawable.restaurant));
	        getTabHost().addTab(spec);
	        
	        getTabHost().setCurrentTab(0);
	        
	        list.setOnItemClickListener(onListClick);
	       }
	   @Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	   new MenuInflater(this).inflate(R.menu.option, menu);
	   return(super.onCreateOptionsMenu(menu));
	   }
	   
	   public boolean onOptionsItemSelected(MenuItem item)
	   {
		   if(item.getItemId()==R.id.toast){
			   String message="No restaurant selected";
			   if(current != null){
				   message=current.getNotes();
			   }
			   Toast.makeText(this,message, Toast.LENGTH_LONG).show();
			   return true; 
		   }
		return(super.onOptionsItemSelected(item));
	   }
	    
	    
/*Current es un nuevo restaurant para manejar el eventode lasnotas*/
		private View.OnClickListener onSave=new View.OnClickListener()
	    {
		public void onClick(View v)
	     {
			current=new Restaurant();
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
		
		/*me muestra los datos del usr y q opcion eligio*/
			private AdapterView.OnItemClickListener onListClick=new AdapterView.OnItemClickListener()
			{
				public void onItemClick(AdapterView<?>parent, View view, int position,
						long id) {
					Restaurant r=model.get(position);
					
				    name.setText(r.getName());
				    address.setText(r.getAddress());
				    phone.setText(r.getPhone());
				    

					if(r.getType().equals("sit_down"))
					{
						types.check(R.id.sit_down);
					}
					else if(r.getType().equals("take_out"))
					    {
						types.check(R.id.take_out);
						}
					else{
						types.check(R.id.delivery);
					    }
				getTabHost().setCurrentTab(1); //aqqui en este punto debe mosstrar la tabla
					
				}
			};
			
 static class RestaurantHolder
			{
				private TextView name=null;
				private TextView address=null;
				private TextView phone=null;
				private ImageView icon=null;
				
				RestaurantHolder(View row){
					name=(TextView)row.findViewById(R.id.title);
					address=(TextView)row.findViewById(R.id.address);
					phone=(TextView)row.findViewById(R.id.phone);
					icon=(ImageView)row.findViewById(R.id.icon);
					}
				
				void populateForm (Restaurant r){
					name.setText(r.getName());
					address.setText(r.getAddress());
					phone.setText(r.getPhone());
					
					if (r.getType().equals("sit_down")) {
						icon.setImageResource(R.drawable.ball_red);
					}
					else if(r.getType().equals("take_out"))
					    {
						icon.setImageResource(R.drawable.ball_yellow);
						}
					else{
						icon.setImageResource(R.drawable.ball_green);
					    }
	/*REVISAR EL CODIGO POR QUE NO HACE EL CAMBIO DE COLORES */					
				}
			}
			
			/*Esta clase va a "dibujar" renglones en una tabla para mostrar los datos*/
class RestaurantAdapter extends ArrayAdapter<Restaurant>{

				 RestaurantAdapter() 
				{
					super(LunchList.this,R.layout.row,model);
					// TODO Auto-generated constructor stub
				}
				public View getView(int position,View convertView,ViewGroup parent)
				{
				View row=convertView;
				RestaurantHolder holder=null;
				
				if(row==null)
				{
					LayoutInflater inflater=getLayoutInflater();
					row=inflater.inflate(R.layout.row,parent, false);
					holder=new RestaurantHolder(row);
					row.setTag(holder);
				}
				else{
					holder=(RestaurantHolder)row.getTag();
				}
				holder.populateForm(model.get(position));
				
					return(row);
			    }
			}
				
		}
