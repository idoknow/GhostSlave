package top.idoknow.ghost.slave.func;

import top.idoknow.ghost.slave.cmd.AbstractFunc;
import top.idoknow.ghost.slave.cmd.AbstractProcessor;
import top.idoknow.ghost.slave.conn.HandleConn;

public class FuncInfo implements AbstractFunc {
    @Override
    public String getFuncName() {
        return "!!info";
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
        Runtime rt=Runtime.getRuntime();
        HandleConn.writeToServerIgnoreException("Memory:\ntotal:"+rt.totalMemory()+"\tfree:"+rt.freeMemory()+"\tmax:"+rt.maxMemory()+
                "\n");
    }
}
