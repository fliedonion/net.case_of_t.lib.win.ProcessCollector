package net.case_of_t.lib.win;

import java.lang.reflect.Field;

import static java.lang.Integer.parseInt;

public class ProcessCommandLineInfo {

    public String getCommandLine() {
        return CommandLine;
    }

    public void setCommandLine(String commandLine) {
        CommandLine = commandLine;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getProcessId() {
        return ProcessId;
    }

    public void setProcessId(int processId) {
        ProcessId = processId;
    }

    private String CommandLine;
    private String Name;
    private int ProcessId;

    public boolean applyField(String fieldName, String value){
        try{
            return applyField(fieldName, value, false);
        }catch(NoSuchFieldException ex){
            return false;
        }
    }

    public boolean applyField(String fieldName, String value, boolean throwIfNotFoundSuchField)
        throws NoSuchFieldException{
        fieldName = fieldName.toUpperCase();
        Field field;
        switch(fieldName){
            case "NAME":
                setName(value);
                break;
            case "COMMANDLINE":
                setCommandLine(value);
                break;
            case "PROCESSID":
                if(value.trim().equals(""))
                    setProcessId(-1);
                else
                    setProcessId(parseInt(value.trim()));
                break;
            default:
                if(throwIfNotFoundSuchField){
                    throw new NoSuchFieldException(fieldName);
                }
                return false;
        }
        return true;

    }
}
