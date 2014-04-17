package apt.tutorial;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

class RestaurantHelper extends SQLiteOpenHelper {
  //LA BD SE CREA AL CREARSE EL PROGRAMA 
  private static final String DATABASE_NAME="lunchlistBD.db";
  //private static final int SCHEMA_VERSION=1;
  private static final int SCHEMA_VERSION=2;
  
  public RestaurantHelper(Context context) {
    super(context, DATABASE_NAME, null, SCHEMA_VERSION);
  }
  
  @Override
  public void onCreate(SQLiteDatabase db) {
   // db.execSQL("CREATE TABLE restaurants (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, phone TEXT,type TEXT, notes TEXT);");
	  db.execSQL("CREATE TABLE restaurants " +
	  		"(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, phone TEXT,type TEXT, notes TEXT,feed TEXT);");
  }

  //para modificar mi bd cree una version 2 y aqui añado una columna
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("ALTER TABLE restaurants ADD COLUMN feed TEXT");
  }

  public Cursor getAll(String orderBy) {
    return(getReadableDatabase()
            .rawQuery("SELECT _id, name, address,phone, type, notes,feed FROM restaurants ORDER BY "+orderBy,
                      null));
  }
  
  public Cursor getById(String id) {
    String[] args={id};

    return(getReadableDatabase()
            .rawQuery("SELECT _id, name, address, phone,type, notes ,feed FROM restaurants WHERE _ID=?",
                      args));
  }
  
  public void insert(String name, String address,String phone,
                     String type, String notes,String feed) {
    ContentValues cv=new ContentValues();
          
    cv.put("name", name);
    cv.put("phone", phone);
    cv.put("address", address);
    cv.put("type", type);
    cv.put("notes", notes);
    cv.put("feed", feed);
    
    getWritableDatabase().insert("restaurants", "name", cv);
  }
  
  public void update(String id, String name, String address,String phone,
                     String type, String notes,String feed) {
    ContentValues cv=new ContentValues();
    String[] args={id};
    
    cv.put("name", name);
    cv.put("address", address);
    cv.put("phone", phone);
    cv.put("type", type);
    cv.put("notes", notes);
    cv.put("feed", feed);
    
    getWritableDatabase().update("restaurants", cv, "_ID=?",
                                 args);
  }
 
  
  public String getName(Cursor c) {
    return(c.getString(1));
  }
  
  public String getAddress(Cursor c) {
    return(c.getString(2));
  }
  public String getPhone(Cursor c) {
	    return(c.getString(3));
	  }
	  
  
  public String getType(Cursor c) {
    return(c.getString(4));
  }
  
  public String getNotes(Cursor c) {
    return(c.getString(5));
  }
  public String getFeed(Cursor c) {
	    return(c.getString(6));
	  }
}