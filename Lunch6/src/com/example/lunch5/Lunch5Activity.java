package com.example.lunch5;
import android.app.Activity;
import android.widget.*;
import android.view.*;
import android.app.TabActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("deprecation")
public class Lunch5Activity extends TabActivity {

   List<Lunch> model=new ArrayList<Lunch>();//ahora tenemos varios restaurantes a escoger
   RestaurantAdapter adapter=null;
   
   EditText name=null;
   EditText address=null;
   EditText phone=null;
   EditText notes=null;//mis notitas
   RadioGroup types=null;
   Lunch current=null;//esto servira para manejar las notas 
   
	
	
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch5);
        
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
    
    

	private View.OnClickListener onSave=new View.OnClickListener()
    {
		
		@Override
		public void onClick(View v)
{
			Lunch lu=new Lunch();//defino uno de mis restaurantes del array restaurantes
			//estas instrucciones son para conectar lo de mi pantalla con el programa
			//IMPORTANTE el id, es el id  de mi objeto
			
			lu.setName(name.getText().toString());
			lu.setAddress(address.getText().toString());
			lu.setPhone(phone.getText().toString());
//hay q observar como removieron el metodo de aqui 			
			switch(types.getCheckedRadioButtonId())
			{
			case R.id.sit_down:
				lu.setType("Reservation");
				break;
				case R.id.take_out:
				lu.setType("TakeOut");
				break;
				case R.id.delivery:
					lu.setType("Delivery");
					break;
				}
			adapter.add(lu);	
		}
	};  
	
	/*me muestra los datos del usr y q opcion eligio*/
		private AdapterView.OnItemClickListener onListClick=new AdapterView.OnItemClickListener()
		{
			public void onItemClick(AdapterView<?>parent, View view, int position,
					long id) {
				Lunch lu=model.get(position);
				
			    name.setText(lu.getName());
			    address.setText(lu.getAddress());
			    phone.setText(lu.getPhone());
			    

				if(lu.getType().equals("sit_down"))
				{
					types.check(R.id.sit_down);
				}
				else if(lu.getType().equals("take_out"))
				    {
					types.check(R.id.take_out);
					}
				else{
					types.check(R.id.delivery);
				    }
			getTabHost().setCurrentTab(1); //aqqui en este punto debe mosstrar la tabla
				
			}
		};
		
		static class LunchHolder
		{
			private TextView name=null;
			private TextView address=null;
			private TextView phone=null;
			private ImageView icon=null;
			
			LunchHolder(View row){
				name=(TextView)row.findViewById(R.id.title);
				address=(TextView)row.findViewById(R.id.address);
				phone=(TextView)row.findViewById(R.id.phone);
				icon=(ImageView)row.findViewById(R.id.icon);
				}
			
			void populateForm (Lunch lu){
				name.setText(lu.getName());
				address.setText(lu.getAddress());
				phone.setText(lu.getPhone());
				
				if(lu.getType().equals("sit_down")){
					icon.setImageResource(R.drawable.ball_red);
				}
				else if(lu.getType().equals("take_out"))
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
		class RestaurantAdapter extends ArrayAdapter<Lunch>{

			public RestaurantAdapter() 
			{
				super(Lunch5Activity.this,R.layout.row,model);
				// TODO Auto-generated constructor stub
			}
			public View getView(int position,View convertView,ViewGroup parent){
			View row=convertView;
			LunchHolder holder=null;
			
			if(row==null)
			{
				LayoutInflater inflater=getLayoutInflater();
				row=inflater.inflate(R.layout.row,parent, false);
				holder=new LunchHolder(row);
				row.setTag(holder);
			}
			else{
				holder=(LunchHolder)row.getTag();
			}
			holder.populateForm(model.get(position));
			
				return(row);
		    }
		}
			
	}
