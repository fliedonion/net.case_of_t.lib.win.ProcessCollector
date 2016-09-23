package net.case_of_t.lib.win;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ProcessCollectorTest {

    ProcessCollector target;

    @Before
    public void before(){
        target = new ProcessCollector();
    }

    private Process runCmdExe() throws IOException {
        return Runtime.getRuntime().exec("cmd.exe /K");
    }

    private void destoryProcess(Process p) throws IOException{
        p.getInputStream().close();
        p.getOutputStream().close();
        p.getErrorStream().close();
        p.destroy();
    }

    @Test
    public void collectTargetProcessWhichNameLikeCanFindCmdExeWithWildCard() throws Exception {
        List<ProcessCommandLineInfo> listOld = target.collectTargetProcessWhichNameLike("%md.exe");
        Process proc = runCmdExe();

        List<ProcessCommandLineInfo> list = target.collectTargetProcessWhichNameLike("%md.exe");
        assertThat("", list.size() - listOld.size(), is(1));

        destoryProcess(proc);
    }

    @Test
    public void collectTargetProcessWhichNameLikeCanFindCmdExeWithSingleWildCard() throws Exception {
        List<ProcessCommandLineInfo> listOld = target.collectTargetProcessWhichNameLike("_md.exe");
        Process proc = runCmdExe();

        List<ProcessCommandLineInfo> list = target.collectTargetProcessWhichNameLike("_md.exe");
        assertThat("", list.size() - listOld.size(), is(1));

        destoryProcess(proc);
    }


    @Test
    public void collectTargetProcessWhichNameContainsCanFindCmdExe() throws Exception {
        List<ProcessCommandLineInfo> listOld = target.collectTargetProcessWhichNameContains("cmd.exe");
        Process proc = runCmdExe();

        List<ProcessCommandLineInfo> list = target.collectTargetProcessWhichNameContains("cmd.exe");
        assertThat("", list.size() - listOld.size(), is(1));

        destoryProcess(proc);
    }


    @Test
    public void collectTargetProcessWhichNameIsCanFindCmdExe() throws Exception {
        List<ProcessCommandLineInfo> listOld = target.collectTargetProcessWhichNameIs("cmd.exe");
        Process proc = runCmdExe();

        List<ProcessCommandLineInfo> list = target.collectTargetProcessWhichNameIs("cmd.exe");
        assertThat("", list.size() - listOld.size(), is(1));

        destoryProcess(proc);
    }

    @Test
    public void collectTargetProcessWhichProcessIsCanFindOnlyOneCmdExe() throws Exception {
        Process proc = runCmdExe();
        List<ProcessCommandLineInfo> listOld = target.collectTargetProcessWhichNameIs("cmd.exe");
        List<ProcessCommandLineInfo> list = target.collectTargetProcessWhichProcessIdIs(listOld.get(0).getProcessId());
        assertThat("", list.size(), is(1));
        assertThat("", list.get(0).getName(), is("cmd.exe"));
        assertThat("", list.get(0).getCommandLine(), containsString("cmd.exe"));
        assertThat("", list.get(0).getCommandLine(), not("cmd.exe"));

        destoryProcess(proc);
    }

    @Test
    public void collectTargetProcessWhichNameIsAndProcessIsCanFindOnlyOneCmdExe() throws Exception {
        Process proc = runCmdExe();
        List<ProcessCommandLineInfo> listOld = target.collectTargetProcessWhichNameIs("cmd.exe");
        List<ProcessCommandLineInfo> list = target.collectTargetProcessWhichNameIsAndProcessIdIs("cmd.exe", listOld.get(0).getProcessId());
        assertThat("", list.size(), is(1));
        assertThat("", list.get(0).getName(), is("cmd.exe"));
        assertThat("", list.get(0).getCommandLine(), containsString("cmd.exe"));
        assertThat("", list.get(0).getCommandLine(), not("cmd.exe"));

        destoryProcess(proc);
    }

    @Test
    public void collectTargetProcessWhichNameLikeAndProcessIsCanFindOnlyOneSomethingIncludesName() throws Exception {
        Process proc = runCmdExe();
        List<ProcessCommandLineInfo> listOld = target.collectTargetProcessWhichNameIs("cmd.exe");
        List<ProcessCommandLineInfo> list = target.collectTargetProcessWhichNameLikeAndProcessIdIs("%md.exe", listOld.get(0).getProcessId());
        assertThat("", list.size(), is(not(0)));
        assertThat("", list.get(0).getName(), containsString("md.exe"));
        assertThat("", list.get(0).getCommandLine(), containsString("md.exe"));

        list = target.collectTargetProcessWhichNameLikeAndProcessIdIs("cmd.ex%", listOld.get(0).getProcessId());
        assertThat("", list.size(), is(not(0)));
        assertThat("", list.get(0).getName(), is("cmd.exe"));
        assertThat("", list.get(0).getCommandLine(), containsString("cmd.ex"));


        destoryProcess(proc);
    }
}