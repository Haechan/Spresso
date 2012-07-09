package com.Appeyroad.Spresso;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
//i think this class can be replaced by adding codes in Activities
	public DBHelper(Context context){
		super(context,"FavoriteCafes.s3db",null,1);
	}
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		//System.out.println("let there be table");
		arg0.execSQL("CREATE TABLE IF NOT EXISTS favorites(_id INTEGER PRIMARY KEY AUTOINCREMENT, number INTEGER, favorite INTEGER);");
		int numberOfCafes=23;//it is changerble
		for(int i=0; i<numberOfCafes+1; i++) arg0.execSQL("insert into favorites(number, favorite) values ("+i+", 0);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public void onOpen(){
		//System.out.println("there is already a table");
	}

}
