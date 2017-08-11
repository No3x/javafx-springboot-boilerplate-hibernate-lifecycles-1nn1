package hello.fake;

import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by No3x on 11.08.2017.
 */

@Component
public class Fakes {

    private static final RandomNameGenerator randomNameGenerator = new org.kohsuke.randname.RandomNameGenerator(0);

    @Bean
    public RandomNameGenerator databaseBootstrap() {
        return randomNameGenerator;
    }

}
