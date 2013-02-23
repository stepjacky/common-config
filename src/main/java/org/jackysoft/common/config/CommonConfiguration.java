package org.jackysoft.common.config;

import java.beans.PropertyVetoException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@ImportResource({ ("classpath:/spring/configuration/Spring-applicationContext.xml") })
public class CommonConfiguration implements ResourceLoaderAware {
	protected final Log log = LogFactory.getLog(this.getClass());
	
	public CommonConfiguration() {

	}

	@PostConstruct
	public void initilize() {
	}

	@PreDestroy
	public void destory() {

	}	

	private String[] packagesToScan = { "org.jackysoft.entity","org.atomsoft.chatserver.entity"};
	

	@Bean
	public SessionFactory sessionFactory() {

		final ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()

		.buildServiceRegistry();
		SessionFactory sf = new LocalSessionFactoryBuilder(dataSource(),
				resourceLoader)
		        .scanPackages(packagesToScan)
				
		        .buildSessionFactory(serviceRegistry);
        
		return sf;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager tx = new HibernateTransactionManager();
		tx.setSessionFactory(sessionFactory());
		tx.setDataSource(dataSource());
		return tx;

	}

	@Value("${jdbc.driver}")
	private String driverClass;
	@Value("${jdbc.url}")
	private String jdbcUrl;
	@Value("${jdbc.username}")
	private String user;
	@Value("${jdbc.password}")
	private String password;
	private boolean autoCommitOnClose = true;
	@Value("${cpool.checkoutTimeout}")
	private int checkoutTimeout;
	@Value("${cpool.initialPoolSize}")
	private int initialPoolSize;
	@Value("${cpool.minPoolSize}")
	private int minPoolSize;
	@Value("${cpool.maxPoolSize}")
	private int maxPoolSize;
	@Value("${cpool.maxIdleTime}")
	private int maxIdleTime;
	@Value("${cpool.acquireIncrement}")
	private int acquireIncrement;
	@Value("${cpool.maxIdleTimeExcessConnections}")
	private int maxIdleTimeExcessConnections;

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		ComboPooledDataSource cds = new ComboPooledDataSource();

		try {
			cds.setDriverClass(driverClass);
			cds.setJdbcUrl(jdbcUrl);
			cds.setUser(user);
			cds.setPassword(password);
			cds.setAutoCommitOnClose(autoCommitOnClose);
			cds.setCheckoutTimeout(checkoutTimeout);
			cds.setInitialPoolSize(initialPoolSize);
			cds.setMinPoolSize(minPoolSize);
			cds.setMaxPoolSize(maxPoolSize);
			cds.setMaxIdleTime(maxIdleTime);
			cds.setAcquireIncrement(acquireIncrement);
			cds.setMaxIdleTimeExcessConnections(maxIdleTimeExcessConnections);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cds;
	}
	
	private ResourceLoader resourceLoader;

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;

	}
}
