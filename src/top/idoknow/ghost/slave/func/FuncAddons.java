package top.idoknow.ghost.slave.func;

import top.idoknow.ghost.slave.cmd.AbstractFunc;
import top.idoknow.ghost.slave.cmd.AbstractProcessor;

public class FuncAddons implements AbstractFunc {
	@Override
	public String getFuncName() {
		return "!!addons";
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
		try{
			switch (params[0]){
				case "install":{

				}
			}
		}catch (Exception ignored){}
	}
}
