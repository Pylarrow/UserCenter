package com.idthk.usercenter.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * @ClassName: AppManager 
 * @Description: TODO( 应用程序Activity管理类，用于Activity管理和应用程序的退出  ) 
 * @author DZS dzsdevelop@163.com
 * @date 2015-1-24 下午4:12:13 
 * @version  1.1
 *
 */
public class AppManager {
	
	private Stack<Activity> activityStack;
	private static class AppManagerFactory {
		private static AppManager appManager=new AppManager();
	}
	/**
	 * @Description: TODO( 单一实例 ) 
	 * @return
	 * @throws
	 */
	public static AppManager getAppManager(){
		return AppManagerFactory.appManager;
	}
	/**
	 * @Description: TODO( 添加Activity到堆栈  ) 
	 * @param activity
	 * @throws
	 */
	public void addActivity(Activity activity){
		if (activityStack==null) {
			activityStack=new Stack<Activity>();
		}
		if (activity!=null) {
			activityStack.add(activity);
		}
		
	}
	
	/**
	 * @Description: TODO( 获取当前Activity（堆栈中最后一个压入 ）) 
	 * @return
	 * @throws
	 */
	public Activity currentActivity(){
		Activity activity=activityStack.lastElement();
		return activity;
	}
	/**
	 * @Description: TODO( 获取指定的Activity ) 
	 * @param cls
	 * @return 
	 * @throws
	 */
	public Activity getActivity(Class<?> cls) {
        if (activityStack != null)
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

	/**
	 * @Description: TODO( 结束指定的Activity  ) 
	 * @param activity
	 * @throws
	 */
	public void removeActivity(Activity activity){
		if (activity!=null) {
			activityStack.remove(activity);
		}
	}
	

	/**
	 * @Description: TODO( 结束所有Activity ) 
	 * @throws
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null!=activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}
	
	/**
	 * @Description: TODO( 退出应用程序  ) 
	 * @throws
	 */
	public void AppExit(){
		try {
			finishAllActivity();
			//杀死应用进程
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
