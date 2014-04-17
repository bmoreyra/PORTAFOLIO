package com.example.lunchlist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

public class LunchList extends Activity {
	//Restaurant r=new Restaurant();//ACA ESTA EL DICHOSO OBJETO 
	List<Restaurant> model=new ArrayList<Restaurant>();
	//ahora no sera un solo objeto sino un conjunto de restaurantes en un array  
    ArrayAdapter<Restaurant> adapter=null;//para q me muestre todos los registros en una lista
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_list);
        
        Button save=(Button)findViewById(R.id.save);
        //con esta instruccion digamos q conecto mi boton para aca
        save.setOnClickListener(onSave);
        //save se llama mi boton, set on click q cuando lo toque, ejecute el metodo on Save
        ListView list=(ListView)findViewById(R.id.restaurants);
        adapter=new ArrayAdapter<Restaurant>
        (this,android.R.layout.simple_expandable_list_item_1,model);
        list.setAdapter(adapter);
    }
	//ok este es mi metodo q guardara todo lo delas "cajitas" asi q 
	//como mandaba llamar las cajitas?
    private View.OnClickListener  onSave=new View.OnClickListener() {
		public void onClick(View v) {
    		Restaurant r= new Restaurant();
			// TODO Auto-generated method stub
			EditText name=(EditText)findViewById(R.id.name);
			EditText address=(EditText)findViewById(R.id.addr);
			//addr es el id de mi elemento en el layout
			EditText phone=(EditText)findViewById(R.id.phone);
			
		//aja ya los conectamos ahora q . . ps a manejarlos con mi objeto r
			r.setName(name.getText().toString());
			r.setAddress(address.getText().toString());
			r.setPhone(phone.getText().toString());//para jalar el string de las cajitas
			/*Ahora q mas hay en mi escenrio unos radio button*/
			RadioGroup types=(RadioGroup)findViewById(R.id.type);
			switch(types.getCheckedRadioButtonId())
			{case R.id.sit_down:
				  r.setType("SitDown");
				  break;
			case R.id.take_out:
				r.setType("TakeOut");
				break;
			case R.id.delivery:
				r.setType("Delivery");
				break;
			}adapter.add(r);
		}
	};
}
/*Vale hasta aqui compila, habra q hacer los widgets personalizados para q se vea lindo */
