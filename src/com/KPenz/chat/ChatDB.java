package com.KPenz.chat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDB extends SQLiteOpenHelper{
	public static final String FIELD_ID="id";
	public static final String FIELD_NAME="name";
	public ChatDB(Context context){
		super(context, "chat",null,1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS Messages( INT PRIMARY KEY,time LONG,text_msg TEXT, sender_id INT, room_id INT);");
		db.execSQL("CREATE TABLE IF NOT EXISTS Rooms ("+FIELD_ID+" INT PRIMARY KEY,"+FIELD_NAME+" VARCHAR(255) );");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int old, int newVer) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS Messages;");
		db.execSQL("DROP TABLE IF EXISTS Rooms;");
		onCreate(db);
		
	}
	public void insertMessage(int id,String msg, int room_id,int sender_id){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("INSERT INTO Messages(id, time, text_msg, sender_id, room_id) " +
		"VALUES("+id+", "+System.currentTimeMillis()+",\""+msg+"\","+sender_id+","+room_id+");");
	}
	public void selectAll(){
		Cursor c = getReadableDatabase().rawQuery("SELECT id, time, text_msg, sender_id, room_id FROM Messages;", new String[0]);
		while (c.moveToNext()) {
			int id = c.getInt(0);
			long time = c.getLong(1);
			String text = c.getString(2);
			int senderId = c.getInt(3);
			int roomId = c.getInt(4);
			Log.i("Penzykov","id= "+id+", time="+time+", text: "+text+", Sender ID="+senderId+", Room ID="+roomId);
			
			
		}
	}
	public Cursor fetchRooms(){
		return getReadableDatabase().rawQuery("SELECT"+FIELD_ID+", "+FIELD_NAME+"FROM Rooms", new String[0]);
	}
}
