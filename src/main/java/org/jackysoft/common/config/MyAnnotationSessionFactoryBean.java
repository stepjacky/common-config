package org.jackysoft.common.config;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;


public class MyAnnotationSessionFactoryBean extends  LocalSessionFactoryBean{
	private static final Log log = LogFactory.getLog(MyAnnotationSessionFactoryBean.class);
	
	private ResourcePatternResolver resourceResolver;
		

	@Resource(name="mappedXml")
	public void setMappedXmlResource(ResourceSet mappedXmlResource) {
		String[] mappingResources = mappedXmlResource.toArray(new String[0]);
		this.setMappingResources(mappingResources);
	}

	
	@Resource(name="scannedPackage")
	public void setPackage4Scan(ResourceSet package4Scan) {
		String[] packagesToScan = package4Scan.toArray(new String[0]);
		this.setPackagesToScan(packagesToScan);
	}

	@Override
	@Resource
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	public void setHibernateProperties(Properties hibernateProperties) {
		org.springframework.core.io.Resource res = resourceResolver.getResource("classpath*:hibernate.properties");
		Properties  hp = new Properties();
		try {
			hp.load(res.getInputStream());
			super.setHibernateProperties(hp);
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
			
	
	
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		super.setResourceLoader(resourceLoader);
		resourceResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
	}
	
	
}
