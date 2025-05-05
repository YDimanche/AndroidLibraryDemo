package com.lybl.permission.native_demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lybl.permission.R;

import java.util.ArrayList;
import java.util.List;

public class NativePermissionActivity extends AppCompatActivity {

    private int PERMISSION_REQUEST_CODE=0;

    ScrollView root;
    Button haveCamera;
    Button requestCamera;
    Button openCamera;
    Button requestGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_native_permission);
        initView();
        initClick();
    }

    private void initView(){
        root=findViewById(R.id.root);
        haveCamera=findViewById(R.id.have_camera);
        requestCamera=findViewById(R.id.request_camera);
        openCamera=findViewById(R.id.open_camera);
        requestGroup=findViewById(R.id.request_group);
    }

    private void initClick(){
        //监听系统窗口变化，动态调整视图内边距，以避免内容被系统栏遮挡
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //判断是否已经获取了相机权限
        haveCamera.setOnClickListener(v -> {
            checkPermission();
        });
        //获取相机权限
        requestCamera.setOnClickListener(v->{
            requestCamera();
        });
    }

    /**
     * 判断权限
     */
    private void checkPermission(){
        int flag=checkSelfPermission(Manifest.permission.CAMERA);
        if (flag== PackageManager.PERMISSION_DENIED){
            //权限未授予
            toast("权限未授予");
            //权限是否被永久拒绝
            boolean foreverDenied=shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
            if (foreverDenied){
                toast("程序被永久拒绝，需要用户到设置中手动开启");
            }else {
                toast("程序被拒绝，可以再次申请");
            }
        }else {
            //权限已授予
            toast("权限已授予");
        }
    }

    /**
     * 获取相机权限
     */
    private void requestCamera(){
        //获取权限有两种写法,1、直接使用requestPermissions，然后在onRequestPermissionsResult中处理
        requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERMISSION_REQUEST_CODE){
            List<String>deniedList=new ArrayList<>();
            for (int i=0;i<grantResults.length;i++){
                int result=grantResults[i];
                if (result==PackageManager.PERMISSION_DENIED){
                    deniedList.add(permissions[i]);
                }
            }
            if (deniedList.size()==0){
                toast("权限都被授予");
            }else {
                toast("有"+deniedList.size()+"个权限被拒绝");
                //此时和弹出对话框，对用户说明该权限的重要性，并且在此申请所需的权限
            }
        }
    }

    private void toast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
