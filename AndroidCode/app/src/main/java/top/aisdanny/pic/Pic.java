package top.aisdanny.pic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import top.aisdanny.pic.tool.Http;

public class Pic extends AppCompatActivity {
    LinearLayout imgView;
    TextView time;
    ImageView bigImg;
    Handler picHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bitmap bitmap = (Bitmap) msg.obj;
            imgView.addView(picView(bitmap));
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                JSONObject obj = new JSONObject(msg.obj.toString());
                JSONArray list = obj.getJSONArray("data");
                ((TextView)findViewById(R.id.total)).setText(obj.getString("flag"));
                imgView.removeAllViews();
                if (list.length() != 0) {
                    for (int i = 0; i < list.length(); i++)
                        Http.getPic("pic/" + list.get(i).toString(), picHandler);
                } else Toast.makeText(Pic.this, "此日期无图片记录", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    protected View picView(final Bitmap bitmap) {
        View view = LayoutInflater.from(Pic.this).inflate(R.layout.item_pic, null);
        ImageView img = view.findViewById(R.id.pic);
        img.setImageBitmap(bitmap);
        final int[] i = {0};
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bigImg.setImageBitmap(bitmap);
                if(i[0] ==0){
                    bigImg.setVisibility(View.VISIBLE);
                    i[0] =1;
                }else {
                    bigImg.setVisibility(View.INVISIBLE);
                    i[0]=0;
                }
            }
        });
        bigImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bigImg.setVisibility(View.INVISIBLE);
                i[0]=0;
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(Pic.this).setTitle("Save").setPositiveButton("保存图片", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                        String imgName=sp.format(new Date())+"_"+UUID.randomUUID();
                        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, imgName, "description");
                    }
                }).show();
                return true;
            }
        });
        bigImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(Pic.this).setTitle("Save").setPositiveButton("保存图片", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                        String imgName=sp.format(new Date())+"_"+UUID.randomUUID();
                        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, imgName, "description");
                    }
                }).show();
                return true;
            }
        });
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        imgView = findViewById(R.id.picView);
        time  = findViewById(R.id.time);
        bigImg=findViewById(R.id.bigImg);
        final SimpleDateFormat sp = new SimpleDateFormat("yyyy/MM/dd");
        time.setText(sp.format(new Date()));
        Http.get("pic/getPic?data=" + time.getText().toString(), handler);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(Pic.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        time.setText(year + "/" + (month + 1)+"/" + dayOfMonth);
                        updata();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        findViewById(R.id.all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.setText("");
                updata();
            }
        });
    }
    public void updata(){
        if(time.getText().toString().equals("")) Http.get("pic/getPic", handler);
        Http.get("pic/getPic?date=" + time.getText().toString(), handler);
    }
}