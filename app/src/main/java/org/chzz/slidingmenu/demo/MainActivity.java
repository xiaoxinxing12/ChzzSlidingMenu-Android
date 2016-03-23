package org.chzz.slidingmenu.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.chzz.slidingmenu.SlidingMenu;

public class MainActivity extends Activity implements View.OnClickListener {
    private SlidingMenu menu;
    //再按一次退出程序
    private long exitTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // configure the SlidingMenu
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);

        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        //把滑动菜单添加进所有的Activity中，可选值SLIDING_CONTENT ， SLIDING_WINDOW
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.leftmenu);
        menu.showMenu(true);
        TextView test = (TextView) findViewById(R.id.test);
        test.setText("2222");
        test.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(this,"我是左边",Toast.LENGTH_LONG).show();
    }
    /**
     * 记录返回事件
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再次点击即可退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
                return true;
            } else if ((System.currentTimeMillis() - exitTime) <= 2000) {
                /**
                 *  1. 任务管理器方法
                 首先要说明该方法运行在Android 1.5 API Level为3以上才可以，同时需要权限
                 <uses-permission android:name=\"android.permission.RESTART_PACKAGES\"></uses-permission>
                 */
                // ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                //ActivityManager am= (ActivityManager) getApplication().getSystemService(Context.ACTIVITY_SERVICE);
                //am.restartPackage(getPackageName());

                /**
                 * 1. Dalvik VM的本地方法
                 * 获取PID
                 */
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
