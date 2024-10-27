package com.marioborrego.gestordocumentalbackend.configuration;

import jcifs.DialectVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.smb.session.SmbSessionFactory;

@Configuration
public class SmbConfig {
//    @Bean
//    public SmbSessionFactory smbSessionFactory() {
//        SmbSessionFactory smbSession = new SmbSessionFactory();
//        smbSession.setHost("192.168.0.124");
//        smbSession.setPort(445);
//        smbSession.setUsername("spring");
//        smbSession.setPassword("spring");
//        smbSession.setShareAndDir("Clientes");
//        smbSession.setSmbMinVersion(DialectVersion.SMB210);
//        smbSession.setSmbMaxVersion(DialectVersion.SMB311);
//        return smbSession;
//    }
}
