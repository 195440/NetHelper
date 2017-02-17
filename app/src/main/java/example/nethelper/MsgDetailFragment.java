package example.nethelper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MsgDetailFragment extends Fragment {

	private View msgDetailView;
	private Button configClientButton;
	private Button configServerButton;
	private TextView configClientDetail;
	private TextView configServerDetail;
	private TextView msgDetail;
	private EditText sendEdit;
	private Button sendButton;
	
	
	private Handler handler;
	private String serverPort;
	private String remotePort;
	private String remoteIP;
	
	private Socket cSocket;
	private Socket sSocket;
	
	private String sendData;
	
	//标记服务器和客户端，1为客户端，0为服务器
	private int flag = 0;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		msgDetailView = inflater.inflate(R.layout.msgdetailfragment, container,
				false);
		configClientButton = (Button) msgDetailView
				.findViewById(R.id.configClientButton);
		configClientButton.setOnClickListener(myClickListener);
		configServerButton = (Button) msgDetailView
				.findViewById(R.id.configServerButton);
		configServerButton.setOnClickListener(myClickListener);
		configClientDetail = (TextView) msgDetailView
				.findViewById(R.id.configClientDetail);
		configServerDetail = (TextView) msgDetailView
				.findViewById(R.id.configServerDetail);
		msgDetail = (TextView) msgDetailView.findViewById(R.id.msg);
		sendEdit = (EditText) msgDetailView.findViewById(R.id.editmsg);
		sendButton = (Button) msgDetailView.findViewById(R.id.sendButton);
		sendButton.setOnClickListener(myClickListener);

		return msgDetailView;
	}

	public void showServer() {
		Log.i("TAG_NET", "进入showdetail方法...");

		configServerButton.setVisibility(View.VISIBLE);
		configClientButton.setVisibility(View.GONE);
		configClientDetail.setText("");
		msgDetail.setText("");
		
	}

	public void showClient() {
		Log.i("TAG_NET", "进入showdetail方法...");

		configServerButton.setVisibility(View.GONE);
		configClientButton.setVisibility(View.VISIBLE);
		configServerDetail.setText("");
		msgDetail.setText("");
	}
	
	public void dataReceive(Socket socket){
		
		InputStream inputstream = null;
		DataInputStream dis = null;
		InputStreamReader isr = null;
		int num = 0;
		try {
			inputstream = socket.getInputStream();
			isr = new InputStreamReader(inputstream,"gbk");
			
			while((num=isr.read()) != -1){
				Log.i("TAG1", (char)num + "");
				Bundle bundle = new Bundle();
				bundle.putChar("xiaoxi", (char)num);
				Message message = new Message();
				message.setData(bundle);
				Log.i("TAG1", "message取到的内容：" + message.getData().getChar("xiaoxi"));
				handler.sendMessage(message);
				
			}
		} catch (IOException e) {
			Log.i("TAG_NET", e.toString());
		}finally{
			try {
				inputstream.close();
				isr.close();
			} catch (IOException e) {
				Log.i("TAG_NET", e.toString());
			}
			
		}
        
	}
	
	public void dataSend(final Socket socket,final String str){
		
		DataOutputStream dos = null;
		OutputStream os = null;
		OutputStreamWriter osw = null;
		try {
			os = socket.getOutputStream();
			osw = new OutputStreamWriter(os,"gbk");
			osw.write(str);
			osw.flush();
		} catch (IOException e) {
			Log.i("TAG_NET", e.toString());
		}
		
	}
	


	private OnClickListener myClickListener = new OnClickListener() {
		String remoteIP;
		String remotePort;
		View dialogClientView;
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.configClientButton:
				// 使用flag标记,客户端为1
				flag = 1;
				
				AlertDialog.Builder al = new AlertDialog.Builder(getActivity());
				al.setTitle("连接服务器：");
				//自定义alertDialog
				LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
				dialogClientView = layoutInflater.inflate(R.layout.dialogclient, null);
				al.setView(dialogClientView);
				al.setPositiveButton("连接服务器",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								EditText editText1 = (EditText)dialogClientView.findViewById(R.id.serverip);
								remoteIP = editText1.getText().toString();
								Log.i("TAG_NET", "服务器IP地址：" + remoteIP);
								EditText editText2 = (EditText)dialogClientView.findViewById(R.id.serverPort);
								remotePort = editText2.getText().toString();
								Log.i("TAG_NET", "服务器端口号：" + (remotePort.equals("")));
								configClientDetail.setVisibility(View.VISIBLE);
								if(remoteIP.equals("") || remotePort.equals("")){
									Toast.makeText(getActivity(), "请输入服务器ip地址和端口号", Toast.LENGTH_SHORT).show();
								}else{
									configClientDetail.setText("配置远端服务器ip地址为：" + remoteIP + "," + "端口为：" + remotePort);
								}
								
								
								
								handler = new Handler() {

									@Override
									public void handleMessage(Message message) {
										Log.i("TAG_NET", message.toString());
										char c = message.getData().getChar(
												"xiaoxi");
										Log.i("TAG_NET", c + "");
										msgDetail.setVisibility(View.VISIBLE);
										Log.d("TAG_NET", "得到的结果是：" + c);
										msgDetail.append(c + "");
									}
								};
								
								new Thread(new Runnable(){

									@Override
									public void run() {
										try {
											cSocket = new Socket(remoteIP,Integer.parseInt(remotePort));
											Log.i("TAG_NET", "已连接服务器...");
											//客户端接收服务器发来的数据
											dataReceive(cSocket);
											//客户端向服务器发消息
											//dataSend(cSocket,sendData);
											
											
										} catch (NumberFormatException e) {
											Log.i("TAG_NET", e.toString());
										} catch (UnknownHostException e) {
											Log.i("TAG_NET", e.toString());
										} catch (IOException e) {
											Log.i("TAG_NET", e.toString());
										}
									}
									
								}).start();
							}
						});
				al.setNegativeButton("取消", null);
				al.create();
				al.show();
				break;
			case R.id.configServerButton:
				//使用flag标记，服务器flag为0
				flag = 0;
				AlertDialog.Builder al2 = new AlertDialog.Builder(getActivity());
				al2.setTitle("输入端口号：");
				// al.setMessage("");
				final EditText editText = new EditText(getActivity());
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				al2.setView(editText);
				al2.setPositiveButton("激活",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								serverPort = editText.getText().toString();
								Log.i("TAG_NET", serverPort);
								configServerDetail.setVisibility(View.VISIBLE);
								configServerDetail.setText("服务器端口号："
										+ serverPort);

								handler = new Handler() {

									@Override
									public void handleMessage(Message message) {
										Log.i("TAG_NET", message.toString());
										char c = message.getData().getChar(
												"xiaoxi");
										Log.i("TAG_NET", c + "");
										msgDetail.setVisibility(View.VISIBLE);
										Log.d("TAG_NET", "得到的结果是：" + c);
										msgDetail.append(c + "");
									}
								};

								new Thread(new Runnable() {
									@Override
									public void run() {
										ServerSocket serverSocket;
										try {
											serverSocket = new ServerSocket(
													Integer.parseInt(serverPort));
											Log.i("TAG_NET", "服务器已经开启...");
											while(true){
												sSocket = serverSocket
														.accept();
												Log.i("TAG_NET", "服务器已连接...");
												dataReceive(sSocket);
											}
										} catch (IOException e) {
											e.printStackTrace();
										}
									}

								}).start();

							}
						});
				al2.setNegativeButton("取消", null);
				al2.create();
				al2.show();
				break;
			case R.id.sendButton:
				Log.i("TAG_NET", "发送数据！");
				final String sendData = sendEdit.getText().toString();
				if(!"".equals(sendData)||sendData != null){
					Log.i("TAG_NET", "发送的数据：" + sendData);
					new Thread(new Runnable(){
						@Override
						public void run() {
							try {
								if(flag == 1){
									cSocket = new Socket(remoteIP,Integer.parseInt(remotePort));
									dataSend(cSocket,sendData);
								}else if(flag == 0){
									dataSend(sSocket,sendData);
								}
							} catch (NumberFormatException e) {
								e.printStackTrace();
							} catch (UnknownHostException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
							
						}
					}).start();
				}else{
					Log.i("TAG_NET", "输入数据位空");
					Toast.makeText(getActivity(), "输入内容！", Toast.LENGTH_SHORT).show();
				}
			default:
				break;
			}

		}
	};
	
}
