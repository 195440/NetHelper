package example.nethelper;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class CSChooseFragment extends Fragment {
	
	private View csChooseView;
	private Button clientButton;
	private Button serverButton;
	
	private MsgDetailFragment msgDetailFragment;
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		csChooseView = inflater.inflate(R.layout.cschoosefragment,container, false);
		clientButton = (Button)csChooseView.findViewById(R.id.clientButton);
		serverButton = (Button)csChooseView.findViewById(R.id.serverButton);
		
		clientButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.i("TAG_NET", "切换到客户端...");
				msgDetailFragment = (MsgDetailFragment)getFragmentManager().findFragmentById(R.id.msgDetail);
				msgDetailFragment.showClient();
			}
			
		});
		
		serverButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.i("TAG_NET", "切换到服务器...");
				msgDetailFragment = (MsgDetailFragment)getFragmentManager().findFragmentById(R.id.msgDetail);
				msgDetailFragment.showServer();
			}
			
		});
		
		return csChooseView;
		
		
	}
	
	

}
