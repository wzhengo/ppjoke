package com.wz.ppjoke.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.wz.ppjoke.R;
import com.wz.ppjoke.model.BottomBar;
import com.wz.ppjoke.model.Destination;
import com.wz.ppjoke.utils.AppConfig;

import java.util.List;

/**
 * @author wangzhen
 * @date 2020/02/01
 */
public class AppBottomBar extends BottomNavigationView {
    private static int[] sIcons = new int[]{
            R.drawable.icon_tab_home,
            R.drawable.icon_tab_sofa,
            R.drawable.icon_tab_publish,
            R.drawable.icon_tab_find,
            R.drawable.icon_tab_mine};


    public AppBottomBar(Context context) {
        this(context, null);
    }

    public AppBottomBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    @SuppressLint("RestrictedApi")
    public AppBottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final BottomBar bottomBar = AppConfig.getBottomBarConfig();
        final List<BottomBar.Tab> tabs = bottomBar.tabs;

        final int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{};

        int[] colors = new int[]{Color.parseColor(bottomBar.activeColor), Color.parseColor(bottomBar.inActiveColor)};
        final ColorStateList colorStateList = new ColorStateList(states, colors);
        setItemIconTintList(colorStateList);
        setItemTextColor(colorStateList);
        //LABEL_VISIBILITY_LABELED:设置按钮的文本为一直显示模式
        //LABEL_VISIBILITY_AUTO:当按钮个数小于三个时一直显示，或者当按钮个数大于3个且小于5个时，被选中的那个按钮文本才会显示
        //LABEL_VISIBILITY_SELECTED：只有被选中的那个按钮的文本才会显示
        //LABEL_VISIBILITY_UNLABELED:所有的按钮文本都不显示
        setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        setSelectedItemId(bottomBar.selectTab);

        for (BottomBar.Tab tab : tabs) {
            if (!tab.enable) {
                continue;
            }
            int itemId = getItemId(tab.pageUrl);
            if (itemId < 0) {
                continue;
            }
            final MenuItem menuItem = getMenu().add(0, itemId, tab.index, tab.title);
            menuItem.setIcon(sIcons[tab.index]);
        }

        //此处给按钮icon设置大小
        int index = 0;
        for (BottomBar.Tab tab : tabs) {
            if (!tab.enable) {
                continue;
            }
            int itemId = getItemId(tab.pageUrl);
            if (itemId < 0) {
                continue;
            }
            final int iconSize = dp2Px(tab.size);
            final BottomNavigationMenuView menuView = (BottomNavigationMenuView) getChildAt(0);
            final BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(index);
            itemView.setIconSize(iconSize);

            if (TextUtils.isEmpty(tab.title)) {
                itemView.setIconTintList(ColorStateList.valueOf(Color.parseColor(tab.tintColor)));
                //禁止掉点按时 上下浮动的效果
                itemView.setShifting(false);
                /**
                 * 如果想要禁止掉所有按钮的点击浮动效果。
                 * 那么还需要给选中和未选中的按钮配置一样大小的字号。
                 *
                 *  在MainActivity布局的AppBottomBar标签增加如下配置，
                 *  @style/active，@style/inActive 在style.xml中
                 *  app:itemTextAppearanceActive="@style/active"
                 *  app:itemTextAppearanceInactive="@style/inActive"
                 */
            }
            index++;
        }

    }

    private int dp2Px(int dpValue) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return (int) (metrics.density * dpValue + 0.5f);
    }

    private int getItemId(String pageUrl) {
        final Destination destination = AppConfig.getDestConfig().get(pageUrl);
        if (destination == null) {
            return -1;
        }
        return destination.id;
    }
}
