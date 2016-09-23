import net.case_of_t.lib.win.ProcessCollector;
import net.case_of_t.lib.win.ProcessCommandLineInfo;

import java.lang.management.ManagementFactory;
import java.util.List;

import static java.lang.Integer.parseInt;

class SampleMain {

    public static void main(String[] args){
        // sample code.

        final String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];

        List<ProcessCommandLineInfo> pi = new ProcessCollector().collectTargetProcessWhichProcessIdIs(parseInt(pid));

        System.out.println(pi.size());
        System.out.println(pi.get(0).getName());
        System.out.println(pi.get(0).getCommandLine());
    }
}
