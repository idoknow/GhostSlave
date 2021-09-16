package top.idoknow.ghost.slave.func;

import top.idoknow.ghost.slave.cmd.AbstractFunc;
import top.idoknow.ghost.slave.cmd.AbstractProcessor;
import top.idoknow.ghost.slave.conn.HandleConn;

/**
 * 接收到服务器的心跳数据并返回
 * @author Rock Chin
 */
public class FuncRespAlive implements AbstractFunc {
    @Override
    public String getFuncName() {
        return "#alives#";
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
        HandleConn.writeToServerIgnoreException("!alives!");
    }
}
