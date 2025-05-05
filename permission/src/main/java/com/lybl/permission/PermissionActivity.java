package com.lybl.permission;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lybl.permission.native_demo.NativePermissionActivity;

public class PermissionActivity extends AppCompatActivity {
    private LinearLayout root;
    private Button nativeDemo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置沉浸式状态栏
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_permission);
        initView();
        initClick();
    }

    private void initView(){
        root=findViewById(R.id.root);
        nativeDemo=findViewById(R.id.native_demo);
    }

    private void initClick(){
        //监听系统窗口变化，动态调整视图内边距，以避免内容被系统栏遮挡
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nativeDemo.setOnClickListener(o->{
            startActivity(new Intent(this, NativePermissionActivity.class));
        });
    }
}
