package top.idoknow.ghost.slave.func;

import top.idoknow.ghost.slave.cmd.AbstractFunc;
import top.idoknow.ghost.slave.cmd.AbstractProcessor;
import top.idoknow.ghost.slave.conn.HandleConn;
import top.idoknow.ghost.slave.core.ClientMain;

/**
 * 关闭客户端的指令
 * @author Rock Chin
 */
public class FuncExit implements AbstractFunc {
    @Override
    public String getFuncName() {
        return "!!exit";
    }

    @Override
    public String[] getParamsModel() {
        return new String[]{"<fullName>"};
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getMinParamsAmount() {
        return 1;
    }

    @Override
    public void run(String[] params, String cmd, AbstractProcessor processor) {
        if(params[0].equals(ClientMain.name)){
            HandleConn.sendFinishToServer();
            System.exit(0);
        }else {
            HandleConn.writeToServerIgnoreException("名称不正确\n");
            HandleConn.sendFinishToServer();
        }
    }
}
