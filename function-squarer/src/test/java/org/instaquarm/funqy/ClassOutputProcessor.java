package org.instaquarm.funqy;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *  aws logs get-log-events --log-group-name /aws/lambda/squarer-function --log-stream-name "2023/04/21/[\$LATEST]f87fc521e56847b497bbd7f35e1de300"  --output=text --region=eu-north-1 > classes.txt
 */
@Disabled
public class ClassOutputProcessor {

    @Test
    void process() throws IOException {
        File classes = new File("src/test/resources/log.txt");
        var lines = Files.readAllLines(classes.toPath());

        Set<String> output = new LinkedHashSet<>();
        for (String line : lines) {
            var l = line.trim();
            if (l.startsWith("EVENTS")  && l.contains("[class,load]")  && ! l.contains("opened:")) {
                var c = l.substring(l.lastIndexOf("]") +1, l.indexOf("source")).trim();
                if (! c.isBlank()  && ! c.contains("0x")) {
                    output.add(c);
                }
            }
        }

        Files.write(new File("target/quarkus-preload-classes.txt").toPath(), output);
    }
}
