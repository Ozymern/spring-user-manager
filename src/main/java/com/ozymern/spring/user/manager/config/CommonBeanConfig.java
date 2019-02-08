package com.ozymern.spring.user.manager.config;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;


@Configuration
@ComponentScan(basePackages = "com.drawcoders.monolith.app")
public class CommonBeanConfig implements WebMvcConfigurer {

    static String DEFAULT_LOCALE = "es";

    @Bean
    public MessageSource messageSource() {
        // https://stackoverflow.com/questions/40165151/how-to-handle-multiple-files-and-messages-for-internationalization-in-spring

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // messageSource.setBasename("classpath:i18n/messages");
        messageSource.setBasenames("classpath:i18n/messages/messages", "classpath:i18n/layout/messages",
                "classpath:i18n/backend/sites/messages", "classpath:i18n/backend/auth/messages");

        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        // HttpServletRequest request = ((ServletRequestAttributes)
        // RequestContextHolder.currentRequestAttributes()).getRequest();
        CookieLocaleResolver resolver = new CookieLocaleResolver();

        /*
         * try { if(!request.getHeader("Accept-Language").isEmpty()) {
         * List<Locale.LanguageRange> list =
         * Locale.LanguageRange.parse(request.getHeader("Accept-Language")); Locale
         * locale = Locale.lookup(list, Arrays.asList(new Locale("en"), new
         * Locale("es"))); resolver.setDefaultLocale(locale); } else {
         * resolver.setDefaultLocale(new Locale(DEFAULT_LOCALE)); } }catch(Exception e)
         * { // Accept-Language header is corrupt resolver.setDefaultLocale(new
         * Locale(DEFAULT_LOCALE)); }
         */

//		resolver.setDefaultLocale(new Locale(DEFAULT_LOCALE));
//		resolver.setCookieName("lang_site");
        return resolver;
    }

    /**
     * Forms field validation message source
     *
     * @return
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(this.messageSource());
        return bean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    // metodo sobreescrito que redireciona cuando ocurre un problema de autorizacion
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error_403").setViewName("/error_403");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncode() {
        return new BCryptPasswordEncoder();
    }
}
