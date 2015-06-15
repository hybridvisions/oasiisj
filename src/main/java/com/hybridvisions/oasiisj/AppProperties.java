package com.hybridvisions.oasiisj;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * This singleton class provides access to a set of application wide properties.
 * These properties can be set via the "oasiisj.properties" file.
 */
public class AppProperties {

    /** logging setup */
    private static Logger log = Logger.getLogger(AppProperties.class.toString());

		/** Should be set to true if the application must instantiate embedded message broker */
	private static final String MESSAGE_BROKER_EMBEDDED = "message.broker.embedded";
	
	/** Parameters for starting embedded message broker  */
    private static final String MESSAGE_BROKER_EMBEDDED_PARAMS = "message.broker.embedded.params";

	/** Parameters for starting embedded message broker  */
    public static final String MESSAGE_BROKER_CONNECTION = "message.broker.connection";
    
	/** Timeout for message broker */
    private static final String MESSAGE_BROKER_TIMEOUT = "message.broker.timeout";
    
	/** Property object for maintaining application properties */
    private static Properties properties = null;
    private static Properties standaloneIni = null;
    private static Properties actionProps;

    /** OASIISJ_CONFIG_FILE_KEY */
    private static final String OASIISJ_CONFIG_FILE_KEY = "oasiisj.config.file";

    /** CHAMPS Exporting Service: boolean to enable/disable this service */
    private static final String CHAMPS_EXPORTING_SERVICE_ACTIVATED_KEY = "champ.exporting.service.activated";

    /** Oasiis OASIISJ_LICENSING_FILE_KEY */
    private static final String OASIISJ_LICENSING_FILE_KEY = "licensingFile";

    /** Logging configuration file key */
    private static final String LOGGING_CONFIG_FILE_KEY = "loggingConfigFile";    
    private static final String AUDIT_LOGGING_KEY = "auditLogging";
	private static final String ACTION_PLAN_PROPERTIES = "actionPlanDocument.properties";
   
    /** MAIL_SMTP_HOST_KEY */
    private static final String MAIL_SMTP_HOST_KEY = "mailSmtpHost";

    /** SERVER_EMAIL_KEY */
    private static final String SERVER_EMAIL_KEY = "systemEmail";
    
    /** SERVER_EMAIL_OVERWRITE_FROM */
    private static final String SERVER_EMAIL_OVERWRITE_FROM_KEY = "systemEmailOverwriteFrom";
    
    /** HELPDESK_EMAIL_KEY */
    private static final String HELPDESK_EMAIL_KEY = "helpdeskEmail";
    
    /** SERVICE_LEVEL_KEY */
    private static final String SERVICE_LEVEL_KEY = "serviceLevel";
    
    /**SALES ACCOUNT KEY */
    private static final String SALES_EMAIL_KEY ="salesEmail";
    
    /** REGULAR EMAIL */
	private static final String EMAIL_SUBJECT_KEY = "email.subject";
	private static final String EMAIL_BODY_KEY = "email.body";

    /** REFERRAL_EMAIL */
    private static final String REFERRAL_EMAIL_SUBJECT_KEY = "referral.email.subject";
    private static final String REFERRAL_GROUP_SUBJECT_KEY = "referral.group.subject";
    private static final String REFERRAL_EMAIL_BODY_KEY = "referral.email.body";
    private static final String REFERRAL_GROUP_BODY_KEY = "referral.group.body";
    private static final String REFERRAL_EMAIL_BODY_KEY_WEBENGINE = "referral.email.body.webengine";
    
    /** Invite_EMAIL */
    private static final String INVITE_EMAIL_SUBJECT_KEY = "invite.email.subject";
    private static final String INVITE_GROUP_SUBJECT_KEY = "invite.group.subject";
    private static final String INVITE_EMAIL_BODY_KEY = "invite.email.body";
    private static final String INVITE_GROUP_BODY_KEY = "invite.group.body";
    
    /** COMPANY HELPDESK EMAIL */
    private static final String COMPANY_HELPDESK_EMAIL_SUBJECT_KEY = "email.company.helpdesk.subject";
    private static final String COMPANY_HELPDESK_EMAIL_BODY_KEY = "email.company.helpdesk.body";
    private static final String COMPANY_HELPDESK_EMAIL_BODY_KEY_WEBENGINE = "email.company.helpdesk.body.webengine";
    
    /** Default values */
    public static final String COMPANY = "WM+A";
    public static final String HOST = "localhost";
    public static final String PORT = "1200";
    public static final String DEFAULT_HELPDESK_EMAIL = "servicedesk@wmanda.com";
    public static final String DEFAULT_SERVICE_LEVEL = "24";
    public static final String DEFAULT_SALES_EMAIL ="sales@hybridvisions.com";

    public static final String WEB_SERVER_URL = "server.url";

    /**  images path */
    public static final String IMAGE_DIR_SHORT = "images/html_images/";

    private static boolean isTimberjack;
    private static boolean isT56IETM;
    private static String reloadList = "";
    public static final String ADD_CASEBASE = "add";
    public static final String DELETE_CASEBASE = "delete";
    public static final String MentorConnectionInfo = "MentorConnectionInfo";
    public static final String MentorHtmlImagesPath = "MentorHtmlImagesPath";

    /** Color (branding) theme */
	private static final String DEFAULT_COLOR_THEME_KEY = "default.color.theme";
	private static final String OASIISJ_DEFAULT_COLOR_THEME_KEY = "oasiisj.default.color.theme";

    /** This class is a singleton */
    private static final AppProperties INSTANCE = new AppProperties();
	private static final String CLUSTER_MODE_KEY = "cluster.mode";
	private static final String TIMEOUT_SESSION_KEY = "timeout.session";
	private static final String TIMEOUT_SESSION_TABLET_KEY = "timeout.session.tablet";
	private static final String TIMEOUT_USER_KEY = "timeout.user";
	private static final String CLUSTER_QUEUE_SIZE_KEY = "cluster.queue.size";
	//private static final String LOCAL_FILES_DIRECT_ACCESS = "local.files.direct.access";

	private static final String ALLOW_AUTO_RELOAD = "auto.reload.config";
	private static volatile ScheduledExecutorService scheduledExecutor = null;
	
    /** Application property file name. */
    private final String PROPERTY_FILE = "oasiisj.properties";

    /** RM_OASIISJ_JAR_NAME */
    private final String JWS_JAR_NAME1 = "javaws.jar";
    private final String JWS_JAR_NAME2 = "javaws-l10n.jar";
    private final String STANDALONE_INI_FILE = "oasiisj.ini";

    /** Silhouette Agent*/
    private final String SILHOUETTE_DIR = "silhouette.dir";
    
    /** boolean application version: 
     * false - standalone; 
     * true - web ver; 
     */
    private boolean standaloneVersion = false;
     
    /**
     * Private constructor supresses the default public constructor
     * to enforce singleton usage.
     */
    private AppProperties() {
    }

    public static AppProperties getInstance() {
        return INSTANCE;
    }

    /**
     * Loads the application's property file.
     */
    private void loadPropertyFile() {
        String propertiesFolder = null;
        if (properties != null) {            
            return; // property file is already open
        }

        properties = new Properties();
        try {
            // trying to get the application property file name (absolute path)
            // 1. try to capture PROPERTY_FILE from class path; if found we are dealing with standalone version 
            // 2. Alternative: look for PROPERTY_FILE in Tomcat directories - for web version
            log.info("Retrieving oasiisj.properties");
            standaloneVersion = isWebStartOnPath();

            if (standaloneVersion) {
                log.info("Loading standalone properties from classpath "
                    + System.getProperty("java.class.path"));

                String absoluteFileName = getStandaloneINIProperty(
                        "oasiisj_properties");

                log.info("Property file name is " + absoluteFileName);
                File propFile = new File(absoluteFileName);
                FileInputStream fis = new FileInputStream(propFile);
                properties.load(fis);
                propertiesFolder = propFile.getParent();
            }
            else {
                log.info("Loading web " + PROPERTY_FILE + " via class loader");
                properties.setProperty("Properties", PROPERTY_FILE);
                // ResourceLoader.getProperties(PROPERTY_FILE, OasiisJDomain.getOasiisJHome());
            }
            //Assert.assertTrue(!properties.isEmpty());
            log.info(PROPERTY_FILE + " loaded");
            propertiesFolder = "*"; //(String) OasiisJDomain.getOasiisJHome();
        }
        catch (Exception ex) {
            log.severe(ex.toString());
            log.severe("Failed to open properties file " + PROPERTY_FILE);
        }

        if (isAutoReloadAllowed(properties)) {  //If we want auto-reloads 
            if (scheduledExecutor==null         //If we haven't scheduled them yet 
                    && !propertiesFolder.isEmpty()) {   //And if the folder where properties are located is valid
                addChangesWatcher(propertiesFolder);
            }
        } else if (scheduledExecutor!=null && !scheduledExecutor.isShutdown()) {
            //If watch service was created for the properties file before, but now we don't want it, remove
            scheduledExecutor.shutdown();
            scheduledExecutor = null;
        }
    }

    private void addChangesWatcher(String propertiesFolder) {
        try {
            final Path path = FileSystems.getDefault().getPath(propertiesFolder);
            scheduledExecutor = Executors.newScheduledThreadPool(1);
            scheduledExecutor.submit(new WatchThread(path));
        } catch (Exception e) {
            log.log(Level.WARNING, "Unable to schedule "+PROPERTY_FILE+" change watcher", (Object)e);
        }
    }

    /**
     * Test the classpath for the presence of JavaWebStart.
     *
     * This information is useful to determine whether the application
     * is running standalone or in the web server since JavaWebStart
     * should only exist in the standalone application's classpath.
     *
     * @return true if JavaWebStart jars are found on the path.
     */
    private boolean isWebStartOnPath() {
        String path = System.getProperty("java.class.path");
        //Assert.assertNotNull(path);
        return (path.indexOf(JWS_JAR_NAME1) >= 0) || (path.indexOf(JWS_JAR_NAME2) >= 0);
    }


    public String getStandaloneINIProperty(String key) {
        if (standaloneIni == null) {
            loadStandaloneProperties();
        }

        return standaloneIni.getProperty(key);
    }


    /**
     * Retrieve Oasiisj properties
     */
    private void loadStandaloneProperties() {
        standaloneIni = new Properties();
        String path = System.getProperty("java.class.path");
        //Assert.assertNotNull(path);

        int index = path.indexOf(JWS_JAR_NAME1);
        if (index < 0) {
            index = path.indexOf(JWS_JAR_NAME2);
        }

        if (index < 0) {
            String dirSeparator = System.getProperty("file.separator");

            path = "c:" + dirSeparator + "oasiisj" + dirSeparator;
            log.warning("Could not locate standalone properties directory; "
                + "trying " + path + " as a backup");
        }
        else {
            // for standalone version: remote jar file found in class path
            // truncate the string up to the index value
            path = path.substring(0, index);
        }

        String pathSeparator = System.getProperty("path.separator");
        if (path.lastIndexOf(pathSeparator) > -1) {
            path = path.substring(path.lastIndexOf(pathSeparator) + 1);
        }

        String absoluteFileName = path.trim() + STANDALONE_INI_FILE;
        try {
            log.log(Level.INFO, "Loading OasiisJRemote properties from " + absoluteFileName);
            FileInputStream fis = new FileInputStream(new File(absoluteFileName));
            standaloneIni.load(fis);
        }
        catch (Exception e) {
            log.log(Level.SEVERE, e.toString(), e);
        }
    }

    public boolean isTimberjack() {
        return isTimberjack;
    }

    public void setTimberjack(boolean b) {
        isTimberjack = b;
    }

    public boolean isT56IETM() {
        return isT56IETM;
    }

    public void setT56IETM(boolean b) {
        isT56IETM = b;
    }
    
    /**
     * This method is called to retrieve an application property.
     *
     * @param  key The hash table key.
     * @return  String Requested property.  "null" is returned if request
     *                property is not available.
     */
    public String getProperty(String key) {
        loadPropertyFile();
        // Attempt to retrieve the requested property
        return properties.getProperty(key);
    }

    /**
     * This method is called to retrieve an application property.  A
     * default value can be specified in the event that the property
     * is not specified in the property file.
     *
     * @param  key The hash table key.
     * @param  defaultValue Default value if property not found.
     * @return  String Requested property.
     */
    public String getProperty(String key, String defaultValue) {
        // Attempt to retrieve the value
        String value = getProperty(key);

        if (value == null) {
            value = defaultValue;
        }
        return value;
    }


    /**
     * This method is called to check standalone application version.
     *
     * @return  boolean
     */
    public boolean isStandaloneVersion() {
        loadPropertyFile();
        return this.standaloneVersion;
    }


    /**
     * This method returns the configuration file name.
     *
     * @return  String
     */
    public String getOasiisJConfigFile() {
        String value = getProperty(OASIISJ_CONFIG_FILE_KEY);
        //Assert.assertNotNull(value);
        return substOasiisJHome(value);
    }

    /**
     * This method returns the licensing file name.
     *
     * @return  String
     */
    public String getLicensingStrategyFile() {
      String value = getProperty(OASIISJ_LICENSING_FILE_KEY);
      //Assert.assertNotNull(value);
      return substOasiisJHome(value);
    }

    /**
     * Test whether the CHAMPS exporting service is active.
     * 
     * @return True if CHAMPS export is enabled.
     */
    public boolean isChampsExportingServiceActivated() {
        String value = getProperty(CHAMPS_EXPORTING_SERVICE_ACTIVATED_KEY);
        return (value == null) ? false : value.equalsIgnoreCase("TRUE");
    }

    public boolean isAuditLoggingEnabled() {
        String value = getProperty(AUDIT_LOGGING_KEY);
        return (value == null) ? true : value.equalsIgnoreCase("true");
    }

    /**
     * This method returns mail smtp host server name.
     *
     * @return  String
     */
    public String getMailSmtpHost() {
        String value = getProperty( MAIL_SMTP_HOST_KEY );
        //Assert.assertNotNull( value );
        return value;
    }
  
    /**
     * This method returns SL server email address FROM where all notification
     * are sent 
     * 
     * @return  String
     */
    public String getServerEmail() {
        String value = getProperty(SERVER_EMAIL_KEY);
        if (value==null) return "";
        //Assert.assertNotNull(value);
        return value.trim();
    }
   

    /**
     * This method returns whether server email address should overwrite users email in the FROM filed
     * 
     * @return  boolean
     */
    public boolean isServerEmailOverwriteFrom() {
        String value = getProperty(SERVER_EMAIL_OVERWRITE_FROM_KEY);
        return (value == null) ? true : value.equalsIgnoreCase("TRUE");
    }
    
    public String getSalesEmail() {
        String value = getProperty(SALES_EMAIL_KEY);
        if(value == null || value.equals("")){
            value = DEFAULT_SALES_EMAIL;
        }
        return value;
    }
    
    /**
     * This method returns helpdeskEmail
     * 
     * @return  String
     */
    public String getHelpdeskEmail() {
        String value = getProperty(HELPDESK_EMAIL_KEY);
        //Assert.assertNotNull(value);
        if(value == null || value.equals("")){
            value = DEFAULT_HELPDESK_EMAIL;
        }
        return value;
    }

    /**
     * This method returns helpdeskEmail
     * 
     * @return  String
     */
    public String getServiceLevel() {
        String value = getProperty(SERVICE_LEVEL_KEY);
        //Assert.assertNotNull(value);
        if(value == null || value.equals("")){
            value = DEFAULT_SERVICE_LEVEL;
        }
        return value;
    }

    /**
     * This method returns ReferralGroupSubject.
     *
     * @return  String
     */
    public String getReferralGroupSubject() {
        String value = getProperty(REFERRAL_GROUP_SUBJECT_KEY);
        //Assert.assertTrue(value != null);
        return value;
    }


    /**
     * This method returns ReferralEmailSubject.
     *
     * @return  String
     */
    public String getReferralEmailSubject() {
        String value = getProperty(REFERRAL_EMAIL_SUBJECT_KEY);
        //Assert.assertTrue(value != null);
        return value;
    }
    
    /**
     * This method returns CompanyHelpdeskEmailSubject.
     *
     * @return  String
     */
    public String getCompanyHelpdeskEmailSubject() {
        String value = getProperty(COMPANY_HELPDESK_EMAIL_SUBJECT_KEY);
        //Assert.assertTrue(value != null);
        return value;
    }
    
    /**
     * This method returns FTSSEmailSubject.
     *
     * @return  String
     */
    public String getEmailSubject() {
        String value = getProperty(EMAIL_SUBJECT_KEY);
        //Assert.assertTrue(value != null);
        return value;
    }
    
    /**
     * This method returns FTSSEmailSubject.
     *
     * @return  String
     */
    public String getSessionStartEmailSubject() {
        String value = getProperty(EMAIL_SUBJECT_KEY);
        //Assert.assertTrue(value != null);
        return value;
    }
    
    
    /**
     * This method returns ReferralEmailBody for standard logon.
     *
     * @return  String
     */
    public String getReferralEmailBody() {
        String value = getProperty(REFERRAL_EMAIL_BODY_KEY);
        //Assert.assertTrue(value != null);
        return value;
    }


    /**
     * This method returns IniviteEmailBody for standard logon.
     *
     * @return  String
     */
    public String getInivitationEmailBody() {
        return getProperty(INVITE_EMAIL_BODY_KEY);
    }

    /**
     * This method returns IniviteEmailSubject  for standard logon.
     *
     * @return  String
     */
    public String getInivitationEmailSubject() {
        return getProperty(INVITE_EMAIL_SUBJECT_KEY);
    }

    /**
     * This method returns ReferralGroupBody for standard logon.
     *
     * @return  String
     */
    public String getReferralGroupBody() {
        String value = getProperty(REFERRAL_GROUP_BODY_KEY);
        //Assert.assertTrue(value != null);
        return value;
    }
    
    /**
     * This method returns IniviteEmailSubject  for standard logon.
     *
     * @return  String
     */
    public String getInivitationEmailGroupSubject() {
        return getProperty(INVITE_GROUP_SUBJECT_KEY);
    }

    /**
     * This method returns ReferralGroupBody for standard log on.
     *
     * @return  String
     */
    public String getInivitationEmailGroupBody() {
        return getProperty(INVITE_GROUP_BODY_KEY);
    }
    
    /**
     * This method returns CompanyHelpdeskEmailBody for standard log on.
     *
     * @return  String
     */
    public String getCompanyHelpdeskEmailBody() {
        String value = getProperty(COMPANY_HELPDESK_EMAIL_BODY_KEY);
        //Assert.assertTrue(value != null);
        return value;
    }    
    
    /**
     * This method returns EmailBody for standard log on.
     *
     * @return  String
     */
    public String getEmailBody() {
        String value = getProperty(EMAIL_BODY_KEY);
        //Assert.assertTrue(value != null);
        return value;
    }
    
    /**
     * This method returns the email body at session start .
     *
     * @return  String
     */
    public String getSessionStartEmailBody() {
        String value = getProperty(EMAIL_BODY_KEY);

        //Assert.assertTrue(value != null);

        return value;
    }    

    /**
     * This method returns CompanyHelpdeskEmailBodyWebEngine.
     *
     * @return  String
     */
    public String getCompanyHelpdeskEmailBodyWebEngine() {
        String value = getProperty(COMPANY_HELPDESK_EMAIL_BODY_KEY_WEBENGINE);
        //Assert.assertTrue(value != null);
        return value;
    }

    /**
     * This method returns ReferralEmailBodyEngine.
     *
     * @return  String
     */
    public String getReferralEmailBodyWebEngine() {
        String value = getProperty(REFERRAL_EMAIL_BODY_KEY_WEBENGINE);
        //Assert.assertTrue(value != null);
        return value;
    }
    
    /**
     * 
     * @param casebase
     * @param action
     */
    public static synchronized void updateReloadOasiisJList(String listName,
        String action) {
        if (action.equalsIgnoreCase("add")) {
            if ((listName != null) && !listName.trim().isEmpty()) {
                if ((reloadList.indexOf(listName + ",")) < 0) {
                    reloadList = reloadList.concat(listName + ",");
                }
            }
        }
        else {
            reloadList = reloadList.replaceAll(listName + ",", "");
        }
    }


    public static String getReloadOasiisJList() {
        return reloadList;
    }
    
    public String getLoggingConfigFile() {
        String file = System.getProperty("oasiisj.log");
        if (file==null) {
            return substOasiisJHome(getProperty(LOGGING_CONFIG_FILE_KEY));
        } else {
            return file;
        }
    }

    /**
     * Here SL Variant would be set at license initialization time.
     * @param clName class name. Can be<br>
     * <ul><code>null</code></ul>
     * <UL>com.hybridvisions.oasiisj.app.variant.StandardSLVariant</UL>
     * <UL>com.hybridvisions.oasiisj.app.variant.StandaloneVariant</UL>
     * <UL>com.hybridvisions.oasiisj.app.variant.SilhouetteVariant</UL>
     * <UL>com.hybridvisions.oasiisj.app.variant.SilhouetteIETMVariant</UL>
     * @see SLVariant
     * 
     */
    public final void setSlVariant(String clName) {
        Object slVariant ;
        if (clName == null || clName.trim().length() ==0) {
            //slVariant = null;
            return;
        }
        try {
            Class<?> cl = Class.forName(clName);
            Object objInst =  cl.newInstance();
            if (!(objInst instanceof Object)) { //SLVariant)) {
                log.log(Level.SEVERE, "Unknown Oasiisj variant:" + cl.getName());
                //throw new OasiisJRuntimeException("Unknown Oasiisj variant:" + cl.getName());
            }
            slVariant = (Object) objInst;
        }
        catch (Exception e) {
            log.log(Level.SEVERE, "License invalid: " + clName, e);
            //throw new OasiisJRuntimeException(clName);
        }
    }

    public String getDefaultColorTheme() {
        return getProperty(OASIISJ_DEFAULT_COLOR_THEME_KEY,OASIISJ_DEFAULT_COLOR_THEME_KEY);
    }
    
    
    public static Properties getActionProps() {
        if (actionProps == null) {
            actionProps.setProperty(ACTION_PLAN_PROPERTIES, ACTION_PLAN_PROPERTIES);//OasiisJDomain.getOasiisJHome());
            return  actionProps;
        }
        return actionProps;
    }

    /**
     * This method returns FTSSEmailBody for standard logon.
     *
     * @return  String
     */
    public boolean isDirectLocaFilesAccess() {
        return true; // getSlVariant().getLocalFileSystemAccessGranted();
    }
    
    /**
     * @return <code>true</code> if user can reset the password or request new account
     */
    public boolean isAccountManagementEnabled() {
        return true; //getSlVariant().getAccountManagementEnabled();
    }
    
    /**
     * substitute every occurrence of "${oasiisj.home}" with oasiisj.home definition
     * @param value
     * @return 
     */
    public static String substOasiisJHome(String value) {
        return value != null ? "".replaceAll("${oasiisj.home}", value) : "";
				//StringUtils.replace(value, "${oasiisj.home}", (String) OasiisJDomain.getOasiisJHome()) : null;
    }

    /**
     * @return value of "cluster.mode" property if defined. 
     * If undefined, returns "session"
     */
    public String getClusterMode() {
        return getProperty(CLUSTER_MODE_KEY,"session");
    }

    /**
     * @return value of "ping.timeout.session" property 
     */
    public String getTimeoutSessionPing() {
        return getProperty(TIMEOUT_SESSION_KEY);
    }
    
    /**
     * @return value of "ping.timeout.session.tablet" property
     */
    public String getTimeoutSessionPingTablet(){
        return getProperty(TIMEOUT_SESSION_TABLET_KEY);
    }
    
    /**
     * @return value of "ping.timeout.user" property
     */
    public String getTimeoutUserPing() {
        return getProperty(TIMEOUT_USER_KEY);
    }

    /**
     * @return value of "cluster.queue.size" property 
     */
    public String getClusterQueueSize() {
        return getProperty(CLUSTER_QUEUE_SIZE_KEY);
    }

    /**
     * DEBUG
     * @return
     */
    public boolean isOldDomainReport() {
        boolean oldReport = false;
        String p = getProperty("old.domain.report");
        if (p!= null) {
            oldReport = Boolean.parseBoolean(p);
        }
        return oldReport;
    }

    /**
     * DEBUG
     * @return
     */
    public String getDomainReportName() {
        return getProperty("domain.report.name");
    }   

    /**
     * @return Online Tech Pubs SSL certificates path
     */
    public String getOTPCertsPath() {
        return getProperty("otp.certs");
    }
    
    /**
     * Checking oasiisj.properties for existence of silhouette.dir
     * 
     * @return true if silhouette.dir is defined in oasiisj.properties
     */
    public Boolean isStandalone() {
        return (getProperty(SILHOUETTE_DIR)!=null) ? !getProperty(SILHOUETTE_DIR).isEmpty() : false;
        //!StringUtils.containsWhitespace(getProperty(SILHOUETTE_DIR));
    }

    /**
     * @return boolean value of "message.broker.embedded" property. 
     * true if embedded JMS message broker must be started, false otherwise
     */
    public boolean isStartEmbeddedMessageBroker() {
        return "true".equalsIgnoreCase(getProperty(MESSAGE_BROKER_EMBEDDED));
    }

    /**
     * @return Value of "message.broker.embedded.param" property used to 
     * configure JMS message broker. If using ActiveMQ, parameters can be found at: 
     * <a href="http://activemq.apache.org/broker-uri.html">http://activemq.apache.org/broker-uri.html</a>
     */
    public String getEmbeddedMessageBrokerParameters() {
        return getProperty(MESSAGE_BROKER_EMBEDDED_PARAMS);
    }
    
    /**
     * @return Value of "message.broker.connection" property used to 
     * connect to JMS message broker.  
     */
    public String getMessageBrokerConnection() {
        return getProperty(MESSAGE_BROKER_CONNECTION);
    }

    /**
     * @return Value of "message.broker.timeout" property used to 
     * specify timeout values for the messages sent through JMS message broker.  
     */
    public String getMessageBrokerTimeout() {
        return getProperty(MESSAGE_BROKER_TIMEOUT);
    }
    
    /**
     * Used to allow auto-reload of oasiisj.properties. 
     * Setting to true allows you to reload properties without restarting the web application.
     * @return true if "config.autoreload" is set to true. 
     */
    public boolean allowPropertiesAutoReload() {
        return isAutoReloadAllowed(properties);
    }
    
    /**
     * Method is required as we can't use com.hybridvisions.oasiisj.app.AppProperties.getProperty(String) 
     * because the property is read at startup time.
     * @param checkedProperties
     * @return
     */
    private boolean isAutoReloadAllowed(Properties checkedProperties) {
        return "true".equalsIgnoreCase(checkedProperties.getProperty(ALLOW_AUTO_RELOAD));
    }

    class WatchThread implements Runnable {
        
        private final WatchService _watchService; 
        
        public WatchThread(Path path) throws IOException {
            log.info("Scheduling watcher on "+path);
            _watchService = FileSystems.getDefault().newWatchService();
            path.register(_watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        }

        public void run() {
            try {
                while (true) {
                    //Following call will lock until next event happens. That's why we need the separate thread.
                    final WatchKey wk = _watchService.take();
                    if (properties==null) { //If we are already in process of properties reset
                        wk.reset();
                        continue;
                    }
                    for (WatchEvent<?> event : wk.pollEvents()) {
                        if (event.context() instanceof Path) {
                            final Path changed = (Path) event.context();
                            if (changed.endsWith(PROPERTY_FILE)) {
                                log.info(PROPERTY_FILE+" file has changed - triggering reload on next access");
                                properties = null;  //Reset current properties. They will be re-loaded upon next access
                            }
                        }
                    }   
                    // reset the key
                    wk.reset();
                }
            } catch (Exception e) {
                log.log(Level.WARNING, "Exception running change watcher for "+PROPERTY_FILE, e);
            }
        }
            
    }
}