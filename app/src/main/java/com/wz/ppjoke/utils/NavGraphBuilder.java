package com.wz.ppjoke.utils;

import android.content.ComponentName;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.FragmentNavigator;

import com.wz.libcommon.global.AppGlobals;
import com.wz.ppjoke.FixFragmentNavigator;
import com.wz.ppjoke.model.Destination;

import java.util.HashMap;

/**
 * @author wangzhen
 * @date 2020/02/01
 */
public class NavGraphBuilder {

    public static void build(FragmentActivity activity, NavController controller, int containerId) {
        final NavigatorProvider provider = controller.getNavigatorProvider();

        //NavGraphNavigator也是页面路由导航器的一种，只不过他比较特殊。
        //它只为默认的展示页提供导航服务,但真正的跳转还是交给对应的navigator来完成的
        NavGraph navGraph = new NavGraph(new NavGraphNavigator(provider));

        //FragmentNavigator fragmentNavigator = provider.getNavigator(FragmentNavigator.class);
        //fragment的导航此处使用我们定制的FixFragmentNavigator，底部Tab切换时 使用hide()/show(),而不是replace()
        final FixFragmentNavigator fragmentNavigator = new FixFragmentNavigator(activity, activity.getSupportFragmentManager(), containerId);
        provider.addNavigator(fragmentNavigator);

        final ActivityNavigator activityNavigator = provider.getNavigator(ActivityNavigator.class);

        final HashMap<String, Destination> destConfig = AppConfig.getDestConfig();
        for (Destination value : destConfig.values()) {
            if (value.isFragment) {
                final FragmentNavigator.Destination destination = fragmentNavigator.createDestination();
                destination.setId(value.id);
                destination.setClassName(value.className);
                destination.addDeepLink(value.pageUrl);
                navGraph.addDestination(destination);
            } else {
                ActivityNavigator.Destination destination = activityNavigator.createDestination();
                destination.setId(value.id);
                destination.setComponentName(new ComponentName(AppGlobals.getApplication().getPackageName(), value.className));
                destination.addDeepLink(value.pageUrl);
                navGraph.addDestination(destination);
            }

            //给APP页面导航结果图 设置一个默认的展示页的id
            if (value.asStarter) {
                navGraph.setStartDestination(value.id);
            }
        }
        controller.setGraph(navGraph);
    }
}
