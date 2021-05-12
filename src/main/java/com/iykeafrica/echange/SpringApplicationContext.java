package com.iykeafrica.echange;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

//we create this class because we want to add public user id
// to the response header when a user sign in, to do this we
// need to use UserServiceImpl bean to access UserDto in
// AuthenticationFilter class. But we cannot inject it in
// AuthenticationFilter class because it's not a bean
// and we added it manually into WebSecurity
//don't forget to make this class as a bean in the Main Application Class
public class SpringApplicationContext implements ApplicationContextAware {

    private static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
    }

    public static Object getBean (String beanName) {
        return CONTEXT.getBean(beanName);
    }
}