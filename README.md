# cors-filter

>介绍cors-filter-1.0.0.jar的使用:
 + 本次依赖的是:org.ebaysf.web.cors.CORSFilter
 + 因为本次使用servlet3.0,所以很多的配置不需要在web.xml中去配置,使用注解进行配置,详见:Filter0_CrossOriginResource.java
 + 在cors.allowed.origins中配置域名或配置 * ,比如配置了192.168.56.130，那么只有192.168.56.130 能拿到数据，否则全部报403异常
 + cors.allowed.methods :http方法列表,cors.allowed.headers:请求头列表,cors.exposed.headers:是否暴露的头列表,默认 None ,cors.support.credentials:表示资源是否支持用户凭据的标志,默认:true,
 + cors.logging.enabled :用于控制日志记录到容器日志的标志。默认值:false
 + 查看源码可以发现,CrosFilter 是一个final修饰的类,所以没有办法去继承,或者重写方法,只能在Filter0_CrossOriginResource.java中new出corsFilter进行使用

+ 当程序运行时会先初始化我们的配置,例如: @WebInitParam(name="cors.allowed.origins",value = "*"),等等..分析源码可以看到(在此只以allowedOrigins为例,allowedHttpMethods等同理,不做分析):

private void parseAndStore(final String allowedOrigins,

            final String allowedHttpMethods, final String allowedHttpHeaders,

            final String exposedHeaders, final String supportsCredentials,

            final String preflightMaxAge, final String loggingEnabled,

            final String decorateRequest)
            throws ServletException {

        if (allowedOrigins != null) {

            if (allowedOrigins.trim().equals("*")) {                             //判断配置是否等同于*

                this.anyOriginAllowed = true;
            } else {
                this.anyOriginAllowed = false;

                Set<String> setAllowedOrigins =parseStringToSet(allowedOrigins);  //调用parseStringToSet();

                this.allowedOrigins.clear();                                      //清空默认或之前的配置

                this.allowedOrigins.addAll(setAllowedOrigins);                    //将配置的域名添加到this.allowedOrigins
            }
        }
+它会进行判断你的配置是 * 还是域名或者多个域名,如果是域名就会parseStringToSet()方法,在parseStringToSet()方法中:

private Set<String> parseStringToSet(final String data) {
        String[] splits = null;

        if (data != null && data.length() > 0) {                                  //判断域名是否为空,长度是否大于0
            splits = data.split(",");                                             //将域名已","进行分割,因为配置的域名可能为多个
        } else {
            splits = new String[] {};
        }


        Set<String> set = new HashSet<String>();

        if (splits.length > 0) {

            for (String split : splits) {                                          //分割之后进行遍历

                set.add(split.trim());                                             //将域名添加到set集合中,并返回

            }

        }

        return set;
    }
+在parseStringToSet()方法中会将配置的域名进行分割,并保存到set集合中,返回给setAllowedOrigins中,
