package org.meowcat.iaphooker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static String SIMStyle = "有卡模式";
    public static String freeNum = "10086";

    public static String readFile(String paramString)
            throws Throwable
    {
        FileInputStream localFileInputStream = new FileInputStream(Environment.getExternalStorageDirectory() + "/Android/IAPHelper.json");
        Object localObject = new byte[100];
        localObject = new String((byte[])localObject, 0, localFileInputStream.read((byte[])localObject), "utf-8");
        localFileInputStream.close();
        return new JSONObject((String)localObject).getString(paramString);
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        final TextView localTextView = (TextView)findViewById(R.id.tvResult);
        Button localButton = (Button)findViewById(R.id.button1);
        RadioButton localRadioButton = (RadioButton)findViewById(R.id.radioNoSIM);
        RadioGroup localRadioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        if (new File(Environment.getExternalStorageDirectory() + "/Android/IAPHelper.json").exists()) {
            paramBundle = null;
        }
        try
        {
            String str = readFile("SIMStyle");
            paramBundle = str;
        }
        catch (Throwable localThrowable)
        {
            for (;;)
            {
                localThrowable.printStackTrace();
            }
        }
        if (paramBundle.equals("无卡模式"))
        {
            localRadioButton.setChecked(true);
            localTextView.setText("当前的运行模式为：无卡模式");
        }
        CheckIMSI.getOperators();
        localRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup paramAnonymousRadioGroup, int paramAnonymousInt)
            {
                paramAnonymousInt = paramAnonymousRadioGroup.getCheckedRadioButtonId();
                paramAnonymousRadioGroup = (RadioButton)MainActivity.this.findViewById(paramAnonymousInt);
                if (paramAnonymousRadioGroup.getText().toString().equals("无卡模式")) {}
                for (MainActivity.SIMStyle = "无卡模式";; MainActivity.SIMStyle = "有卡模式")
                {
                    localTextView.setText("当前的运行模式为：" + paramAnonymousRadioGroup.getText());
                    return;
                }
            }
        });
        localButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                try
                {
                    MainActivity.this.writeFile(MainActivity.SIMStyle);
                    return;
                }
                catch (Throwable paramAnonymousView)
                {
                    paramAnonymousView.printStackTrace();
                }
            }
        });
    }

    public void writeFile(String paramString)
            throws Throwable
    {
        Object localObject = new JSONObject();
        if (paramString.equals("有卡模式"))
        {
            ((JSONObject)localObject).put("SIMStyle", paramString);
            ((JSONObject)localObject).put("freeNum", freeNum);
        }
        for (;;)
        {
            paramString = ((JSONObject)localObject).toString();
            localObject = new File(Environment.getExternalStorageDirectory() + "/Android/IAPHelper.json");
            if (((File)localObject).exists()) {
                ((File)localObject).delete();
            }
            ((File)localObject).createNewFile();
            localObject = new FileOutputStream((File)localObject);
            ((FileOutputStream)localObject).write(paramString.getBytes());
            ((FileOutputStream)localObject).close();
            Toast.makeText(this, "写入成功", 1).show();
            return;
            ((JSONObject)localObject).put("SIMStyle", paramString);
            ((JSONObject)localObject).put("freeNum", "10086");
        }
    }
}
