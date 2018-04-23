package com.yw.mvp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yw.mvp.adapter.TabFragmentAdapter;
import com.yw.mvp.base.BaseActivity;
import com.yw.mvp.db.bean.UserBean;
import com.yw.mvp.db.dao.UserBeanDao;
import com.yw.mvp.db.helper.GreenDbManager;
import com.yw.mvp.presenter.MainAtPresenter;
import com.yw.mvp.service.MyIntentService;
import com.yw.mvp.service.ServiceHandler;
import com.yw.mvp.utils.ReflectUtils;
import com.yw.mvp.iview.IMainAtView;
import com.yw.mvp.view.MainViewPager;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * 作者：create by YW
 * 日期：2018.01.03 16:12
 * 描述：
 */
public class MainActivity extends BaseActivity<IMainAtView, MainAtPresenter> implements IMainAtView {

    private static final String TAG = MainActivity.class.getName();

    public static final String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    TextView tv;
    ImageView iv_glide;

    NetWorkBroadcastReceiver mNetReceiver;

    MainViewPager mViewPager;
    TabLayout mTabLayout;
    FragmentManager mFragmentManager;
    TabFragmentAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetReceiver);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public MainAtPresenter createPresenter() {
        return new MainAtPresenter(this);
    }

    @Override
    public TextView getText() {
        return tv;
    }

    @Override
    protected void initView() {
        tv = findViewById(R.id.tv);
        tv.setOnClickListener(v -> mPresenter.userInfo("13250013344"));
        iv_glide = findViewById(R.id.glide_test);

        //viewpager & tabLayout
        mViewPager = findViewById(R.id.m_viewpager);
        mTabLayout = findViewById(R.id.tablayout);

    }
    @Override
    protected void initData() {
        //greendao 测试
        UserBean userBean = new UserBean(1L, "YW", 18);
        UserBeanDao userDao = GreenDbManager.instance(this).getUserDao();
        userDao.insertOrReplace(userBean);

        Query<UserBean> build = userDao.queryBuilder().build();
        List<UserBean> list = build.list();
        if (null != list && list.size() > 0) {
            System.out.println(list.get(0));
        }

        //glide();
        //intentService();
        //dynamicReceiver();
        //staticReceiver();
        //applyPermission();

        //绑定ViewPager与TabLayout
        initViewPager();
    }

    void initViewPager() {
        mFragmentManager = getSupportFragmentManager();
        mAdapter = new TabFragmentAdapter(mFragmentManager, this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            View view = mAdapter.getTabView(i);
            if (i == 0) {
                view.setSelected(true);
            }
            tab.setCustomView(view);
        }
    }

    /**
     * 8.0 静态注册系统的action大部分被禁止了
     */
    void staticReceiver() {
        //sendBroadcast(new Intent(CONNECTIVITY_ACTION));

        //Intent intent = new Intent(this, MvpReceiver.class);
        //intent.setAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //sendBroadcast(intent);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(CONNECTIVITY_ACTION));
    }

    // 动态注册
    void dynamicReceiver() {
        IntentFilter filter = new IntentFilter(CONNECTIVITY_ACTION);
        mNetReceiver = new NetWorkBroadcastReceiver();
        registerReceiver(mNetReceiver, filter);
    }

    private class NetWorkBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(CONNECTIVITY_ACTION)) {
                Log.e(TAG, "NetWorkBroadcastReceiver");
            }
        }
    }

    void intentService() {
        //IntentService 的耗时操作
        Intent intent = new Intent(this, MyIntentService.class);
        intent.setPackage("com.yw.mvp2");
        startService(intent);

        HandlerThread handlerThread = new HandlerThread("Handler-");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        //定义在与线程HandlerThread同一个Looper的Handler
        ServiceHandler serviceHandler = new ServiceHandler(looper);
        serviceHandler.sendEmptyMessage(1000);
    }

    void glide() {
        //glide test
        RequestOptions mOptions = new RequestOptions()
                .centerInside()
                .transforms(new RoundedCorners(1000))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .override(80, 80);
        Glide.with(this)
                .load(R.mipmap.ic_launcher)
                .apply(mOptions)
                .into(iv_glide);
    }

    void reflect() {

        UserBean userBean = new UserBean(1L, "YW", 18);
        int age = (int) ReflectUtils.getField(userBean, "age");
        System.out.println(age);

        //反射 test
        String aPackage = ReflectUtils.getPackage(userBean.getClass());
        System.out.println("类的包名：" + aPackage);

        String parentPackage = ReflectUtils.getSuperClassName(userBean.getClass());
        System.out.println("父类的包名：" + parentPackage);

        List<String> interfaces = ReflectUtils.getInterfaces(MainActivity.class);
        System.out.println("类的接口数量：" + interfaces.size());

        List<StringBuilder> fields = ReflectUtils.getFields(userBean.getClass());
        System.out.println("类的成员属性：" + fields.toString());

        try {
            ReflectUtils.setField(UserBean.class, "age", userBean,28);
            int a = (int) ReflectUtils.getField(userBean, "age");
            System.out.println("modify ffff: " + a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void applyPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Log.e("YW", "has permission");
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA},
                    1000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("YW", "onRequestPermissionsResult");
    }
}

