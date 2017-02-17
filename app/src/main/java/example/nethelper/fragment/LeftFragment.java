package example.nethelper.fragment;

import example.nethelper.R;
import example.nethelper.dialog.SettingDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LeftFragment extends Fragment {

	private EditText et_msg;
	private Button btn_setting;
	private Button btn_send;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View leftView = inflater.inflate(R.layout.fragment_left, container, false);
		
		et_msg = (EditText) leftView.findViewById(R.id.et_msg);
		btn_setting = (Button) leftView.findViewById(R.id.btn_setting);
		btn_send = (Button) leftView.findViewById(R.id.btn_send);
		
		btn_send.setOnClickListener(listener);
		btn_setting.setOnClickListener(listener);
		return leftView;
	}
	
	OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_setting:
				SettingDialog settingDialog = new SettingDialog(null, false, null);
				settingDialog.show();
				break;
				
			case R.id.btn_send:
				break;

			default:
				break;
			}
			
		}
	};

}
