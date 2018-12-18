package com.example.zhangruirui.lifetips.demo_learning;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;

import com.bugfree.zhangruirui.slideback.SlideBackHelper;
import com.bugfree.zhangruirui.slideback.SlideBackLayout;
import com.example.zhangruirui.lifetips.R;
import com.example.zhangruirui.lifetips.demo_learning.launchmode.BasicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuDemoActivity extends BasicActivity {

  // 菜单项ID
  // FIRST 为 Menu 类中的整型常量
  private static final int ITEM1 = Menu.FIRST;
  private static final int ITEM2 = Menu.FIRST + 1;
  private static final int ITEM3 = Menu.FIRST + 2;
  private static final int ITEM4 = Menu.FIRST + 3;
  private static final int ITEM5 = Menu.FIRST + 4;
  private static final int ITEM6 = Menu.FIRST + 5;
  private static final int ITEM7 = Menu.FIRST + 6;

  @BindView(R.id.menuDemo)
  TextView mMenuDemo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu_demo);
    ButterKnife.bind(this);
    registerForContextMenu(mMenuDemo);

    SlideBackLayout slideBackLayout = SlideBackHelper.attach(this, R.layout.swipe_back);
    slideBackLayout.setSwipeBackListener(this::finish);
  }

  /*
   * 看一看 menu.add 方法的参数：
   * 第一个 int 类型的 group ID 参数，代表的是组概念，你可以将几个菜单项归为一组，以便更好的以组的方式管理你的菜单按钮。
   * 第二个 int 类型的 item ID 参数，代表的是项目编号。这个参数非常重要，一个 item ID 对应一个 menu 中的选项。在后面使用菜单的时候，就靠这个 item ID
   * 来判断你使用的是哪个选项。
   * 第三个 int 类型的 order ID 参数，代表的是菜单项的显示顺序。默认是0，表示菜单的显示顺序就是按照 add 的显示顺序来显示。
   * 第四个 String 类型的 title 参数，表示选项中显示的文字。
   *
   */

  /**
   * 二、上下文菜单 ContextMenu
   * <p>
   * 当用户长按 Activity 页面时，弹出的菜单我们称为上下文菜单。我们经常在 Windows 中用鼠标右键单击弹出的菜单就是上下文菜单。
   * <p>
   * 1、重写 Activity 的 onCreateContextMenu() 方法，调用 Menu 的 add 方法添加菜单项 MenuItem
   * 2、重写 onContextItemSelected() 方法，响应菜单单击事件
   * 3、调用 registerForContextMenu() 方法，为视图注册上下文菜单
   */
  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    menu.add(0, ITEM1, 0, "红色背景");
    menu.add(0, ITEM2, 0, "蓝色背景");
    menu.add(1, ITEM3, 0, "绿色背景");
  }

  /**
   * 上下文菜单的点击事件
   */
  @Override
  public boolean onContextItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case ITEM1:
        mMenuDemo.setTextColor(Color.RED);
        break;
      case ITEM2:
        mMenuDemo.setTextColor(Color.BLUE);
        break;
      case ITEM3:
        mMenuDemo.setTextColor(Color.GREEN);
        break;
    }
    return true;
  }

  /**
   * 一．选项菜单 OptionsMenu
   * <p>
   * 当用户单击设备上的菜单按钮（Menu），触发事件弹出的菜单就是选项菜单。选项菜单最多只有六个，超过六个的话，第六个就会自动显示 更多 选项来显示。
   * <p>
   * 创建方法：
   * <p>
   * 1、重写 Activity 的 onCreateOptionsMenu(Menu menu) 方法，当我们第一次打开菜单时调用。
   * 2、调用 Menu 的 add() 方法添加菜单项 (MenuItem)，可以调用 MenuItem 的 setIcon() 方法为菜单项设置图标。
   * 3、当菜单项 (MenuItem) 被选中时，重写 Activity 的 onOptionsMenuSelected() 方法响应点击事件。
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    /* 子菜单
		SubMenu subFile = menu.addSubMenu("文件");
		SubMenu subEdit = menu.addSubMenu("编辑");
		subFile.add(0, ITEM1, 0, "新建");
		subFile.add(0, ITEM2, 0, "打开");
		*/
    //		menu.add(0, ITEM1, 0, "开始").setIcon(R.drawable.ic_launcher);
    //		menu.add(0, ITEM2, 0, "开始1");
    //		menu.add(1, ITEM3, 0, "开始2");
    //		menu.add(1, ITEM4, 0, "开始3");
    //		menu.add(1, ITEM5, 0, "开始4");
    //		menu.add(0, ITEM6, 0, "开始5");
    //		menu.add(0, ITEM7, 0, "开始6");

    SubMenu colorMenu = menu.addSubMenu(1, 1, 1, "选择画笔颜色");
    colorMenu.add(2, 200, 200, "红色");
    colorMenu.add(2, 210, 210, "绿色");
    colorMenu.add(2, 220, 220, "蓝色");
    colorMenu.add(2, 230, 230, "紫色");
    colorMenu.add(2, 240, 240, "黄色");
    colorMenu.add(2, 250, 250, "黑色");


    SubMenu widthSm = menu.addSubMenu(1, 3, 3, "设置画笔样式");
    widthSm.add(3, 300, 300, "线状画笔");
    widthSm.add(3, 301, 301, "填充画笔");

    menu.add(1, 2, 2, "设置画笔粗细");
    menu.add(1, 4, 4, "清空画布");
    menu.add(1, 5, 5, "保存画布");
    menu.add(1, 6, 6, "退出应用");

    return true; // 返回值为 “true” ,表示菜单可见，即显示菜单
  }

  /**
   * 选项菜单的点击事件
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // 在此说明一下，Menu 相当于一个容器，而 MenuItem 相当于容器中容纳的东西
		/*
		switch(item.getItemId()) {
		  case ITEM1:
			  setTitle("新建文件");
			  break;
		  case ITEM2:
			  setTitle("打开文件");
			  break;
		  }
		*/

    switch (item.getItemId()) {
      case ITEM1:
        setTitle("开始游戏1"); // 修改当前界面的 title
        break;
      case ITEM2:
        setTitle("开始游戏2");
        break;
      case ITEM3:
        setTitle("开始游戏3");
        break;
      case ITEM4:
        setTitle("开始游戏4");
        break;
      case ITEM5:
        setTitle("开始游戏5");
        break;
      case ITEM6:
        setTitle("开始游戏6");
        break;
      case ITEM7:
        setTitle("开始游戏7");
        break;
    }
    return true;
  }

}
