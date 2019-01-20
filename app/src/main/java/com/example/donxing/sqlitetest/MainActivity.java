package com.example.donxing.sqlitetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    public void initView(){
        Button insert = (Button) findViewById(R.id.insert_button);
        Button insert_cleardata = (Button) findViewById(R.id.clear_insert_button);

        Button update = (Button) findViewById(R.id.update_button);
        Button update_cleardata = (Button)findViewById(R.id.clear_update_button);

        Button delete = (Button) findViewById(R.id.delete_button);
        Button delete_cleardata = (Button)findViewById(R.id.clear_delete_button);

        Button query = (Button) findViewById(R.id.query);
        Button clearquery = (Button)findViewById(R.id.clear_query);

        //为所有按钮对象设置监听器
        insert.setOnClickListener(this);
        insert_cleardata.setOnClickListener(this);

        update.setOnClickListener(this);
        update_cleardata.setOnClickListener(this);

        delete.setOnClickListener(this);
        delete_cleardata.setOnClickListener(this);

        query.setOnClickListener(this);
        clearquery.setOnClickListener(this);
    }

    public void onClick(View view){
        EditText insert_text = (EditText)findViewById(R.id.insert_text);
        String insert_data = insert_text.getText().toString();

        EditText delete_text = (EditText)findViewById(R.id.delete_text);
        String delete_data = delete_text.getText().toString();

        EditText update_before_text = (EditText)findViewById(R.id.update_before_text);
        String update_before_data = update_before_text.getText().toString();
        EditText update_after_text = (EditText)findViewById(R.id.update_after_text);
        String update_after_data = update_after_text.getText().toString();

        TextView textView = (TextView)findViewById(R.id.query_text);

        //新建了一个test_db的数据库
        DatabaseHelper databaseHelper = new DatabaseHelper(this,"test_db",null,1);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        switch (view.getId()){
            case R.id.insert_button:
                ContentValues values = new ContentValues();
                values.put("name",insert_data);
                db.insert("user",null,values);
                break;
            case R.id.clear_insert_button:
                insert_text.setText("");
                break;
            case R.id.delete_button:
                db.delete("user","name=?",new String[]{delete_data});
                break;
            case R.id.clear_delete_button:
                delete_text.setText("");
                break;
            //更新数据按钮
            case R.id.update_button:
                ContentValues values2 = new ContentValues();
                values2.put("name", update_after_data);
                db.update("user", values2, "name = ?", new String[]{update_before_data});
                break;
            //更新数据按钮后面的清除按钮
            case R.id.clear_update_button:
                update_before_text.setText("");
                update_after_text.setText("");
                break;

            //查询全部按钮
            case R.id.query:
                //创建游标对象
                Cursor cursor = db.query("user", new String[]{"name"}, null, null, null, null, null);
                //利用游标遍历所有数据对象
                //为了显示全部，把所有对象连接起来，放到TextView中
                String textview_data = "";
                while(cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    textview_data = textview_data + "\n" + name;
                }
                textView.setText(textview_data);
                break;
            //查询全部按钮下面的清除查询按钮
            case R.id.clear_query:
                textView.setText("");
                textView.setHint("查询内容为空");
                break;

            default:
                break;
        }
    }
}
