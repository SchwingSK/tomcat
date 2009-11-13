/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tomcat.jdbc.pool;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * @author Filip Hanik
 *
 */
public class PoolProperties implements PoolConfiguration {
    protected static final Log log = LogFactory.getLog(PoolProperties.class);
    
    protected static AtomicInteger poolCounter = new AtomicInteger(0);
    protected Properties dbProperties = new Properties();
    protected String url = null;
    protected String driverClassName = null;
    protected Boolean defaultAutoCommit = null;
    protected Boolean defaultReadOnly = null;
    protected int defaultTransactionIsolation = DataSourceFactory.UNKNOWN_TRANSACTIONISOLATION;
    protected String defaultCatalog = null;
    protected String connectionProperties;
    protected int initialSize = 10;
    protected int maxActive = 100;
    protected int maxIdle = Integer.MAX_VALUE;
    protected int minIdle = initialSize;
    protected int maxWait = 30000;
    protected String validationQuery;
    protected boolean testOnBorrow = false;
    protected boolean testOnReturn = false;
    protected boolean testWhileIdle = false;
    protected int timeBetweenEvictionRunsMillis = 5000;
    protected int numTestsPerEvictionRun;
    protected int minEvictableIdleTimeMillis = 60000;
    protected final boolean accessToUnderlyingConnectionAllowed = true;
    protected boolean removeAbandoned = false;
    protected int removeAbandonedTimeout = 60;
    protected boolean logAbandoned = false;
    protected String name = "Tomcat Connection Pool["+(poolCounter.addAndGet(1))+"-"+System.identityHashCode(PoolProperties.class)+"]";
    protected String password;
    protected String username;
    protected long validationInterval = 30000;
    protected boolean jmxEnabled = true;
    protected String initSQL;
    protected boolean testOnConnect =false;
    protected String jdbcInterceptors=null;
    protected boolean fairQueue = true;
    protected boolean useEquals = true;
    protected int abandonWhenPercentageFull = 0;
    protected long maxAge = 0;
    protected boolean useLock = false;
    private InterceptorDefinition[] interceptors = null;
    protected int suspectTimeout = 0;
    
    
    /** 
     * {@inheritDoc}
     */
    @Override
    public void setAbandonWhenPercentageFull(int percentage) {
        if (percentage<0) abandonWhenPercentageFull = 0;
        else if (percentage>100) abandonWhenPercentageFull = 100;
        else abandonWhenPercentageFull = percentage;
    }
    
    /** 
     * {@inheritDoc}
     */
    @Override
    public int getAbandonWhenPercentageFull() {
        return abandonWhenPercentageFull;
    }
    
    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean isFairQueue() {
        return fairQueue;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setFairQueue(boolean fairQueue) {
        this.fairQueue = fairQueue;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean isAccessToUnderlyingConnectionAllowed() {
        return accessToUnderlyingConnectionAllowed;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public String getConnectionProperties() {
        return connectionProperties;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Properties getDbProperties() {
        return dbProperties;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Boolean isDefaultAutoCommit() {
        return defaultAutoCommit;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public String getDefaultCatalog() {
        return defaultCatalog;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Boolean isDefaultReadOnly() {
        return defaultReadOnly;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public int getDefaultTransactionIsolation() {
        return defaultTransactionIsolation;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public String getDriverClassName() {
        return driverClassName;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public int getInitialSize() {
        return initialSize;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean isLogAbandoned() {
        return logAbandoned;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public int getMaxActive() {
        return maxActive;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public int getMaxIdle() {
        return maxIdle;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public int getMaxWait() {
        return maxWait;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public int getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public int getMinIdle() {
        return minIdle;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public int getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public String getPassword() {
        return password;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public String getPoolName() {
        return getName();
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean isRemoveAbandoned() {
        return removeAbandoned;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public int getRemoveAbandonedTimeout() {
        return removeAbandonedTimeout;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public int getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public String getUrl() {
        return url;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public String getUsername() {
        return username;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public String getValidationQuery() {
        return validationQuery;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public long getValidationInterval() {
        return validationInterval;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public String getInitSQL() {
        return initSQL;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean isTestOnConnect() {
        return testOnConnect;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public String getJdbcInterceptors() {
        return jdbcInterceptors;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public InterceptorDefinition[] getJdbcInterceptorsAsArray() {
        if (interceptors == null) {
            if (jdbcInterceptors==null) {
                interceptors = new InterceptorDefinition[0];
            } else {
                String[] interceptorValues = jdbcInterceptors.split(";");
                InterceptorDefinition[] definitions = new InterceptorDefinition[interceptorValues.length];
                for (int i=0; i<interceptorValues.length; i++) {
                    int propIndex = interceptorValues[i].indexOf("(");
                    int endIndex = interceptorValues[i].indexOf(")");
                    if (propIndex<0 || endIndex<0 || endIndex <= propIndex) {
                        definitions[i] = new InterceptorDefinition(interceptorValues[i]);
                    } else {
                        String name = interceptorValues[i].substring(0,propIndex);
                        definitions[i] = new InterceptorDefinition(name);
                        String propsAsString = interceptorValues[i].substring(propIndex+1, interceptorValues[i].length()-1);
                        String[] props = propsAsString.split(",");
                        for (int j=0; j<props.length; j++) {
                            int pidx = props[j].indexOf("=");
                            String propName = props[j].substring(0,pidx);
                            String propValue = props[j].substring(pidx+1);
                            definitions[i].addProperty(new InterceptorProperty(propName,propValue));
                        }
                    }
                }
                interceptors = definitions;
            }
        }
        return interceptors;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setAccessToUnderlyingConnectionAllowed(boolean accessToUnderlyingConnectionAllowed) {
        // NOOP
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setConnectionProperties(String connectionProperties) {
        this.connectionProperties = connectionProperties;
        getProperties(connectionProperties, getDbProperties());
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setDbProperties(Properties dbProperties) {
        this.dbProperties = dbProperties;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setDefaultAutoCommit(Boolean defaultAutoCommit) {
        this.defaultAutoCommit = defaultAutoCommit;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setDefaultCatalog(String defaultCatalog) {
        this.defaultCatalog = defaultCatalog;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setDefaultReadOnly(Boolean defaultReadOnly) {
        this.defaultReadOnly = defaultReadOnly;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setDefaultTransactionIsolation(int defaultTransactionIsolation) {
        this.defaultTransactionIsolation = defaultTransactionIsolation;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setLogAbandoned(boolean logAbandoned) {
        this.logAbandoned = logAbandoned;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setRemoveAbandoned(boolean removeAbandoned) {
        this.removeAbandoned = removeAbandoned;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
        this.removeAbandonedTimeout = removeAbandonedTimeout;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setTimeBetweenEvictionRunsMillis(int
                                                 timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setValidationInterval(long validationInterval) {
        this.validationInterval = validationInterval;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setInitSQL(String initSQL) {
        this.initSQL = initSQL;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setTestOnConnect(boolean testOnConnect) {
        this.testOnConnect = testOnConnect;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setJdbcInterceptors(String jdbcInterceptors) {
        this.jdbcInterceptors = jdbcInterceptors;
        this.interceptors = null;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("ConnectionPool[");
        try {
            String[] fields = DataSourceFactory.ALL_PROPERTIES;
            for (int i=0; i<fields.length; i++) {
                final String[] prefix = new String[] {"get","is"};
                for (int j=0; j<prefix.length; j++) {

                    String name = prefix[j] + fields[i].substring(0, 1).toUpperCase() +
                                  fields[i].substring(1);
                    Method m = null;
                    try {
                        m = getClass().getMethod(name);
                    }catch (NoSuchMethodException nm) {
                        continue;
                    }
                    buf.append(fields[i]);
                    buf.append("=");
                    buf.append(m.invoke(this, new Object[0]));
                    buf.append("; ");
                    break;
                }
            }
        }catch (Exception x) {
            //shouldn;t happen
            x.printStackTrace();
        }
        return buf.toString();
    }

    public static int getPoolCounter() {
        return poolCounter.get();
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean isJmxEnabled() {
        return jmxEnabled;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setJmxEnabled(boolean jmxEnabled) {
        this.jmxEnabled = jmxEnabled;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Boolean getDefaultAutoCommit() {
        return defaultAutoCommit;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public Boolean getDefaultReadOnly() {
        return defaultReadOnly;
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getSuspectTimeout() {
        return this.suspectTimeout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSuspectTimeout(int seconds) {
        this.suspectTimeout = seconds;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean isPoolSweeperEnabled() {
        boolean timer = getTimeBetweenEvictionRunsMillis()>0;
        boolean result = timer && (isRemoveAbandoned() && getRemoveAbandonedTimeout()>0);
        result = result || (timer && getSuspectTimeout()>0); 
        result = result || (timer && isTestWhileIdle() && getValidationQuery()!=null);
        return result;
    }
    
    
    
    public static class InterceptorDefinition {
        protected String className;
        protected Map<String,InterceptorProperty> properties = new HashMap<String,InterceptorProperty>();
        protected volatile Class<?> clazz = null;
        public InterceptorDefinition(String className) {
            this.className = className;
        }

        public String getClassName() {
            return className;
        }
        public void addProperty(String name, String value) {
            InterceptorProperty p = new InterceptorProperty(name,value);
            addProperty(p);
        }
        
        public void addProperty(InterceptorProperty p) {
            properties.put(p.getName(), p);
        }
        
        public Map<String,InterceptorProperty> getProperties() {
            return properties;
        }
        
        public Class<? extends JdbcInterceptor> getInterceptorClass() throws ClassNotFoundException {
            if (clazz==null) {
                if (getClassName().indexOf(".")<0) {
                    if (log.isDebugEnabled()) {
                        log.debug("Loading interceptor class:"+PoolConfiguration.PKG_PREFIX+getClassName());
                    }
                    clazz = Class.forName(PoolConfiguration.PKG_PREFIX+getClassName(), true, this.getClass().getClassLoader());
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("Loading interceptor class:"+getClassName());
                    }
                    clazz = Class.forName(getClassName(), true, this.getClass().getClassLoader());
                }
            }
            return (Class<? extends JdbcInterceptor>)clazz;
        }
    } 
    
    public static class InterceptorProperty {
        String name;
        String value;
        public InterceptorProperty(String name, String value) {
            assert(name!=null);
            this.name = name;
            this.value = value;
        }
        public String getName() {
            return name;
        }
        public String getValue() {
            return value;
        }
        @Override
        public int hashCode() {
            return name.hashCode();
        }
        @Override
        public boolean equals(Object o) {
            if (o==this) return true;
            if (o instanceof InterceptorProperty) {
                InterceptorProperty other = (InterceptorProperty)o;
                return other.name.equals(this.name);
            }
            return false;
        }
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean isUseEquals() {
        return useEquals;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setUseEquals(boolean useEquals) {
        this.useEquals = useEquals;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public long getMaxAge() {
        return maxAge;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setMaxAge(long maxAge) {
        this.maxAge = maxAge;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public boolean getUseLock() {
        return useLock;
    }

    /** 
     * {@inheritDoc}
     */
    @Override
    public void setUseLock(boolean useLock) {
        this.useLock = useLock;
    }
    
        
    public static Properties getProperties(String propText, Properties props) {
        if (props==null) props = new Properties();
        if (propText != null) {
            try {
                props.load(new ByteArrayInputStream(propText.replace(';', '\n').getBytes()));
            }catch (IOException x) {
                throw new RuntimeException(x);
            }
        }
        return props;
    }
}
