package com.yae;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

public class App{
    public static void main(String[] args) throws ParseException {
        Option submission_upload  = Option.builder("submission upload")
                                .argName("f")
                                .hasArg(true)
                                .desc("Upload submission files of an assignment ")
                                .build();
                                
        Options options = new Options();
        options.addOption(submission_upload);
        CommandLineParser parser = new DefaultParser();
        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("file"))
            {
                String url="http://127.0.0.1:5000/upload";
                String file = line.getOptionValue("buildfile");
                HttpEntity entity = MultipartEntityBuilder.create()
                .addPart("file", new FileBody(file))
                .build();

                HttpPost request = new HttpPost(url);
                request.setEntity(entity);

                HttpClient client = HttpClientBuilder.create().build();
                HttpResponse response = client.execute(request);


            }
        }
        catch (ParseException exp) {
            // oops, something went wrong
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }



    }
}
