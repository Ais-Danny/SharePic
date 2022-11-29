package top.aisdanny.pic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import top.aisdanny.pic.tool.Http;


public class MainActivity extends AppCompatActivity {
    List<ImageView> imageViewList = new ArrayList<>();
    LinearLayout picView;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Toast.makeText(MainActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JSONObject obj=new JSONObject();
        picView=findViewById(R.id.picView);
        findViewById(R.id.upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK,null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent,1);
            }
        });
        findViewById(R.id.see).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Pic.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,About.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                JSONObject jsonObject=new JSONObject();
                String src=data.getData().toString();
                jsonObject.put("type", "."+MimeTypeMap.getFileExtensionFromUrl(src));
                jsonObject.put("name",minStr(src));
                jsonObject.put("img",imageString);
                Http.post("pic/saveImg",jsonObject.toString(),handler);
            } catch (Exception e) {
            }
        }
    }

    private String minStr(String string){
        String temp=string;
        String str1=temp.substring(temp.lastIndexOf("%2F")+3,temp.length()-MimeTypeMap.getFileExtensionFromUrl(temp).length()-1);
        String str2=string.substring(string.lastIndexOf("/")+1,string.length()-MimeTypeMap.getFileExtensionFromUrl(string).length()-1);
        if(str1.length()<str2.length()) return str1;
        else return str2;
    }
}