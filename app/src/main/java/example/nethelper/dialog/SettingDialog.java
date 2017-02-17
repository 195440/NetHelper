package example.nethelper.dialog;

import android.app.Dialog;
import android.content.Context;

public class SettingDialog extends Dialog {
	
	private Context context;
	
	

	public SettingDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

}
