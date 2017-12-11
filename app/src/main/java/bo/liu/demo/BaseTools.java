package bo.liu.demo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class BaseTools {
	/**
	 * ��ȡ��ǰӦ�ð汾��
	 * @param context
	 * @return version
	 * @throws Exception
	 */
	public static String getAppVersion(Context context) throws Exception {
		// ��ȡpackagemanager��ʵ��
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()���㵱ǰ��İ�����0�����ǻ�ȡ�汾��Ϣ
		PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
		String versionName = packInfo.versionName;
		return versionName;
	}
	
	/**
	 * ��ȡ��ǰϵͳSDK�汾��
	 */
	public static int getSystemVersion(){
		/*��ȡ��ǰϵͳ��android�汾��*/
		int version= android.os.Build.VERSION.SDK_INT;
		return version;
	}
}
