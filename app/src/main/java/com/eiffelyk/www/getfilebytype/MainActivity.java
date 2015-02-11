package com.eiffelyk.www.getfilebytype;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private final static  String TAG ="馋猫";
    TextView textView;
    private ArrayList<String> listsd;
    private GetFileByTypeUtils getFolderImageUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        listsd = new ArrayList<>();
        getFolderImageUtils = new GetFileByTypeUtils();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //+"/Pictures/Screenshots/"
                getFolderImageUtils.getPicpath("/Pictures/Screenshots/");
                listsd=getFolderImageUtils.getList();
                textView.setText("个数"+listsd.size());
                for (int i=0;i <listsd.size();i++){
                    Log.d(TAG,listsd.get(i).toString());
                }
            }
        });
    }
}
