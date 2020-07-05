package com.llh.server.tooltest;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.StringWriter;

/**
 * VelocityTest
 * <p>
 * CreatedAt: 2020-07-05 15:43
 *
 * @author llh
 */
@SpringBootTest
public class VelocityTest {

    @Test
    public void testInit() throws IOException {
        String f = new ClassPathResource("velocity.properties").getURI().getPath();
        System.out.println(f);
        Velocity.init(f);
        VelocityContext context = new VelocityContext();
        StringWriter sw = new StringWriter();

        Template tpl = Velocity.getTemplate(new ClassPathResource("vm/test.vm").getURI().getPath(), "UTF-8");
        tpl.merge(context, sw);
        System.out.println(sw.toString());
    }
}
