package hello;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by No3x on 08.08.2017.
 */

/**
 * TODO: Maybe use http://www.greggbolinger.com/let-spring-be-your-javafx-controller-factory/
 */
public abstract class AbstractJavaFxApplicationSupport extends Application {

    private static String[] savedArgs;

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        applicationContext = SpringApplication.run(getClass(), savedArgs);
        applicationContext.getBeanFactory().registerSingleton("hostServices", getHostServices());
        applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        applicationContext.close();
    }

    protected static void launchApp(Class<? extends AbstractJavaFxApplicationSupport> appClass, String[] args) {
        AbstractJavaFxApplicationSupport.savedArgs = args;
        Application.launch(appClass, args);
    }

    public Object resolve(Class<?> aClass) {
        return applicationContext.getBeanFactory().getBean(aClass);
    }
}
