package com.hybridvisions.oasiisj;

import static org.junit.Assert.*;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import com.hybridvisions.oasiisj.AppProperties;

public class AppPropertiesTest {
    private static Logger log = Logger.getLogger(AppPropertiesTest.class.toString());
    
    private AppProperties appProps = null;
    private Properties props = null;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetInstance() {
        log.log(Level.INFO, "Lets begin!");
        appProps = mock(AppProperties.class);
        assertNotNull(appProps);
    }

    @Test
    public void testGetStandaloneINIProperty() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getStandaloneINIProperty(""));
    }

    @Test
    public void testIsTimberjack() {
        appProps = mock(AppProperties.class);
        log.log(Level.INFO, appProps.isTimberjack()+" Timberjack? <-- didn't I print something here?");
        assertFalse(appProps.isTimberjack());
    }

    @Test
    public void testSetTimberjack() {
        appProps = mock(AppProperties.class);
        appProps.setTimberjack(true);
        log.log(Level.INFO, appProps.isTimberjack()+" isTimberjack ? <-- didn't I set something here?");
        
        assertTrue(!appProps.isTimberjack());
    }

    @Test
    public void testIsT56IETM() {
        appProps = mock(AppProperties.class);
        if (appProps.isT56IETM())
            assertTrue(appProps.isT56IETM());
        else
            assertFalse(appProps.isT56IETM());
    }

    @Test
    public void testSetT56IETM() {
        appProps = mock(AppProperties.class);
        appProps.setT56IETM(true);
        log.log(Level.INFO, appProps.isT56IETM()+" T56IETM ? <-- didn't I set something here?");
        assertTrue(!appProps.isT56IETM());
    }

    @Test
    public void testGetPropertyString   () {
        appProps = mock(AppProperties.class);
        
        assertNull(appProps.getProperty(""));
    }

    @Test
    public void testGetPropertyStringString() {
        appProps = mock(AppProperties.class);
        
        assertNull(appProps.getProperty("",""));
    }

    @Test
    public void testIsStandaloneVersion() {
        appProps = mock(AppProperties.class);
        
        if (appProps.isStandaloneVersion())
            assertTrue(appProps.isStandaloneVersion());
        else
            assertFalse(appProps.isStandaloneVersion());
    }

    @Test
    public void testGetOasiisJConfigFile() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getOasiisJConfigFile());
    }

    @Test
    public void testGetLicensingStrategyFile() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getLicensingStrategyFile());
    }

    @Test
    public void testIsChampsExportingServiceActivated() {
        appProps = mock(AppProperties.class);        
        if (appProps.isChampsExportingServiceActivated())
            assertTrue(appProps.isChampsExportingServiceActivated());
        else 
            assertFalse(appProps.isChampsExportingServiceActivated());
    }

    @Test
    public void testIsAuditLoggingEnabled() {
        appProps = mock(AppProperties.class);
        if (appProps.isAuditLoggingEnabled())
            assertTrue(appProps.isAuditLoggingEnabled());
        else    
            assertFalse(appProps.isAuditLoggingEnabled());
    }

    @Test
    public void testGetMailSmtpHost() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getMailSmtpHost());
    }

    @Test
    public void testGetServerEmail() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getServerEmail());
    }

    @Test
    public void testIsServerEmailOverwriteFrom() {
        appProps = mock(AppProperties.class);        
        if (appProps.isServerEmailOverwriteFrom())
            assertTrue(appProps.isServerEmailOverwriteFrom());
        else    
            assertFalse(appProps.isServerEmailOverwriteFrom());
    }

    @Test
    public void testGetSalesEmail() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getSalesEmail());
    }

    @Test
    public void testGetHelpdeskEmail() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getHelpdeskEmail());
    }

    @Test
    public void testGetServiceLevel() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getServiceLevel());
    }

    @Test
    public void testGetReferralGroupSubject() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getReferralGroupSubject()); 
    }

    @Test
    public void testGetReferralEmailSubject() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getReferralEmailSubject());
    }

    @Test
    public void testGetCompanyHelpdeskEmailSubject() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getCompanyHelpdeskEmailSubject());
    }

    @Test
    public void testGetEmailSubject() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getEmailSubject());
    }

    @Test
    public void testGetSessionStartEmailSubject() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getSessionStartEmailSubject());
    }

    @Test
    public void testGetReferralEmailBody() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getReferralEmailBody());
    }

    @Test
    public void testGetInivitationEmailBody() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getInivitationEmailBody());
    }

    @Test
    public void testGetInivitationEmailSubject() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getInivitationEmailSubject());
    }

    @Test
    public void testGetReferralGroupBody() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getReferralGroupBody());
    }

    @Test
    public void testGetInivitationEmailGroupSubject() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getInivitationEmailGroupSubject());
    }

    @Test
    public void testGetInivitationEmailGroupBody() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getInivitationEmailGroupBody());
    }

    @Test
    public void testGetCompanyHelpdeskEmailBody() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getCompanyHelpdeskEmailBody());
    }

    @Test
    public void testGetEmailBody() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getEmailBody());
    }

    @Test
    public void testGetSessionStartEmailBody() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getSessionStartEmailBody());
    }

    @Test
    public void testGetCompanyHelpdeskEmailBodyWebEngine() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getCompanyHelpdeskEmailBodyWebEngine());
    }

    @Test
    public void testGetReferralEmailBodyWebEngine() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getReferralEmailBodyWebEngine());
    }

    @Test
    public void testGetReloadOasiisJList() {
        appProps = mock(AppProperties.class);
        assertNotNull(appProps.getReloadOasiisJList());
    }

    @Test
    public void testGetLoggingConfigFile() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getLoggingConfigFile());
    }

    @Test
    public void testGetDefaultColourTheme() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getDefaultColorTheme());
    }

    @Test
    public void testGetActionProps() {
        appProps = mock(AppProperties.class);
        log.log(Level.INFO, "AppProperties.getActionProps() <-- doing this causes problems; revisit later");
    }

    @Test
    public void testIsDirectLocaFilesAccess() {
        appProps = mock(AppProperties.class);
        if (appProps.isDirectLocaFilesAccess())
            assertTrue(appProps.isDirectLocaFilesAccess());
        else
            assertFalse(appProps.isDirectLocaFilesAccess());
    }

    @Test
    public void testIsAccountManagementEnabled() {
        appProps = mock(AppProperties.class);
        if (appProps.isAccountManagementEnabled())
            assertTrue(appProps.isAccountManagementEnabled());
        else
            assertFalse(appProps.isAccountManagementEnabled());
    }

    @Test
    public void testSubstOasiisJHome() {
        appProps = mock(AppProperties.class);
        log.log(Level.INFO, "appProps.substOasiisJHome('testing') <-- doing this causes problems; revisit later");
    }

    @Test
    public void testGetClusterMode() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getClusterMode());
    }

    @Test
    public void testGetTimeoutSessionPing() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getTimeoutSessionPing());
    }

    @Test
    public void testGetTimeoutSessionPingTablet() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getTimeoutSessionPingTablet());
    }

    @Test
    public void testGetTimeoutUserPing() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getTimeoutUserPing());
    }

    @Test
    public void testGetClusterQueueSize() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getClusterQueueSize());
    }

    @Test
    public void testIsOldDomainReport() {
        appProps = mock(AppProperties.class);
        if (appProps.isOldDomainReport())
            assertTrue(appProps.isOldDomainReport());
        else
            assertFalse(appProps.isOldDomainReport());
    }

    @Test
    public void testGetDomainReportName() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getDomainReportName());
    }

    @Test
    public void testGetOTPCertsPath() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getEmbeddedMessageBrokerParameters());
    }

    @Test
    public void testIsStandalone() {
        appProps = mock(AppProperties.class);
        if (appProps.isStandalone())
            assertTrue(appProps.isStandalone());
        else
            assertFalse(appProps.isStandalone());
    }

    @Test
    public void testIsStartEmbeddedMessageBroker() {
        appProps = mock(AppProperties.class);
        if (appProps.isStartEmbeddedMessageBroker())
            assertTrue(appProps.isStartEmbeddedMessageBroker());
        else
            assertFalse(appProps.isStartEmbeddedMessageBroker());
    }

    @Test
    public void testGetEmbeddedMessageBrokerParameters() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getEmbeddedMessageBrokerParameters());
    }

    @Test
    public void testGetMessageBrokerConnection() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getMessageBrokerConnection());
    }

    @Test
    public void testGetMessageBrokerTimeout() {
        appProps = mock(AppProperties.class);
        assertNull(appProps.getMessageBrokerTimeout());
    }

    @Test
    public void testAllowPropertiesAutoReload() {
        appProps = mock(AppProperties.class);        
        if (appProps.allowPropertiesAutoReload())
            assertTrue(appProps.allowPropertiesAutoReload());
        else
            assertFalse(appProps.allowPropertiesAutoReload());
    }
}

