package top.idoknow.ghost.slave.func;

import top.idoknow.ghost.slave.cmd.AbstractFunc;
import top.idoknow.ghost.slave.cmd.AbstractProcessor;
import top.idoknow.ghost.slave.conn.CheckAliveTimer;

/**
 * 接收服务器对心跳数据的回复
 * @author Rock Chin
 */
public class FuncSendAlive implements AbstractFunc {
    @Override
    public String getFuncName() {
        return "#alive#";
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
        CheckAliveTimer.setAlive(true);
    }
}
