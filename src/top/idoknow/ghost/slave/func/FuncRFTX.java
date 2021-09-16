package top.idoknow.ghost.slave.func;

import top.idoknow.ghost.slave.cmd.AbstractFunc;
import top.idoknow.ghost.slave.cmd.AbstractProcessor;

/**
 * RFTX文件传送协议支持
 * @author Rock Chin
 */
public class FuncRFTX implements AbstractFunc {
	@Override
	public String getFuncName() {
		return "rftx";
	}

	@Override
	public String[] getParamsModel() {
		return new String[0];
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public int getMinParamsAmount() {
		return 0;
	}

	@Override
	public void run(String[] params, String cmd, AbstractProcessor processor) {

	}
}
