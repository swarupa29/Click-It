package com.yae;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * Hello world!
 *
 */


public class App implements Runnable
{
    
    @Parameters(paramLabel = "<service-name>", description = "The name of the service to which you want the request to be sent")
    private String serviceName;
    
    @Parameters(paramLabel = "<action>", description = "Action to be performed")
    private String actionName;

    
    @Override
    public void run() { 
        // The business logic of the command goes here...
        // In this case, code for generation of ASCII art graphics
        // (omitted for the sake of brevity).
        System.out.println("Here!");
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args); 
        System.exit(exitCode); 
    }

}
