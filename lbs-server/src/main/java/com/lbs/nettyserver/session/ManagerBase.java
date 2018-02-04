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


package com.lbs.nettyserver.session;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lbs.nettyserver.factory.SessionFactoryImpl;
import com.lbs.nettyserver.utils.sysutils.SpringContextUtil;


/**
 * Minimal implementation of the <b>Manager</b> interface that supports
 * no session persistence or distributable capabilities.  This class may
 * be subclassed to create more sophisticated Manager implementations.
 *
 * @author Craig R. McClanahan
 */
public  class ManagerBase implements Manager {

    private final Log log =  LogFactory.getLog(ManagerBase.class); // must not be static
    

    // ----------------------------------------------------- Instance Variables
    private final Queue<SecureRandom> randoms = new ConcurrentLinkedQueue<SecureRandom>();
    /**
     * The longest time (in seconds) that an expired session had been alive.
     */
    protected volatile int sessionMaxAliveTime;
    
    protected volatile int maxActive;

    /**
     * The set of currently active Sessions for this Manager, keyed by
     * session identifier.
     */
    protected Map<String, Session> sessions = new ConcurrentHashMap<String, Session>();

    // Number of sessions created by this manager
    protected long sessionCounter=0;
    

    /**
     * The maximum number of active Sessions allowed, or -1 for no limit.
     */
    protected int maxActiveSessions = -1;


    // ------------------------------------------------------------ Constructors

    public ManagerBase() {
        
    }

    // --------------------------------------------------------- Public Methods
    @Override
    public void add(Session session) {
        int size = getActiveSessions();
        if( size > maxActive ) {
            if( size > maxActive ) {
                maxActive = size;
            }
        }
        this.sessions.put(session.getId(), session);
    }
    
    
    @Override
    public Session createSession(){
    	
    	//生成ID
    	String sessionId ;
        do {
            sessionId = generateSessionId();
        } while (sessions.containsKey(sessionId));
        
    	//新建session
        SessionFactoryImpl sessionFactory = (SessionFactoryImpl) SpringContextUtil.getBean("mysessionFactory");
		Session session = sessionFactory.createSession(StandardSession.class);
		session.setValid(true);
	    session.setCreationTime(System.currentTimeMillis());
	    session.setMaxInactiveInterval(30 * 60);
    	session.setId(sessionId);
    	this.sessionCounter++;
    	return (session);
    }
    
    @Override
    public Session findSession(String id) throws IOException {
        if (id == null) {
            return null;
        }
        return sessions.get(id);
    }
    
    

    @Override
    public void remove(Session session) {
    	sessions.remove(session);
    }
    
    // ------------------------------------------------------ Protected Methods

    /**
     * Generate and return a new session identifier.
     */
    protected String generateSessionId() {
    	
    	 byte random[] = new byte[16];
         int sessionIdLength = 16;

         // Render the result as a String of hexadecimal digits
         // Start with enough space for sessionIdLength and medium route size
         StringBuilder buffer = new StringBuilder(2 * sessionIdLength + 20);

         int resultLenBytes = 0;

         while (resultLenBytes < sessionIdLength) {
             getRandomBytes(random);
             for (int j = 0;
             j < random.length && resultLenBytes < sessionIdLength;
             j++) {
                 byte b1 = (byte) ((random[j] & 0xf0) >> 4);
                 byte b2 = (byte) (random[j] & 0x0f);
                 if (b1 < 10)
                     buffer.append((char) ('0' + b1));
                 else
                     buffer.append((char) ('A' + (b1 - 10)));
                 if (b2 < 10)
                     buffer.append((char) ('0' + b2));
                 else
                     buffer.append((char) ('A' + (b2 - 10)));
                 resultLenBytes++;
             }
         }
        return buffer.toString();
    }


    @Override
    public int getActiveSessions() {
        return sessions.size();
    }


    /**
     * @return The maximum number of active Sessions allowed, or -1 for no
     *         limit.
     */
    public int getMaxActiveSessions() {
        return (this.maxActiveSessions);
    }

    /**
     * Set the maximum number of active Sessions allowed, or -1 for
     * no limit.
     *
     * @param max The new maximum number of sessions
     */
    public void setMaxActiveSessions(int max) {

        int oldMaxActiveSessions = this.maxActiveSessions;
        this.maxActiveSessions = max;

    }

    /**
     * Returns information about the session with the given session id.
     * 
     * <p>The session information is organized as a HashMap, mapping 
     * session attribute names to the String representation of their values.
     *
     * @param sessionId Session id
     * 
     * @return HashMap mapping session attribute names to the String
     * representation of their values, or null if no session with the
     * specified id exists, or if the session does not have any attributes
     */
    public HashMap<String, String> getSession(String sessionId) {
        Session s = sessions.get(sessionId);
        if (s == null) {
            if (log.isInfoEnabled()) {
                log.info("Session not found " + sessionId);
            }
            return null;
        }

//        Enumeration<String> ee = s.getSession().getAttributeNames();
//        if (ee == null || !ee.hasMoreElements()) {
//            return null;
//        }

        HashMap<String, String> map = new HashMap<String, String>();
//        while (ee.hasMoreElements()) {
//            String attrName = ee.nextElement();
//            map.put(attrName, getSessionAttribute(sessionId, attrName));
//        }

        return map;
    }


    public void expireSession( String sessionId ) {
        Session s=sessions.get(sessionId);
        if( s==null ) {
            if(log.isInfoEnabled())
                log.info("Session not found " + sessionId);
            return;
        }
        s.expire();
    }

    public long getLastAccessedTimestamp( String sessionId ) {
        Session s=sessions.get(sessionId);
        if(s== null)
            return -1 ;
        return s.getLastAccessedTime();
    }

    public String getLastAccessedTime( String sessionId ) {
        Session s=sessions.get(sessionId);
        if( s==null ) {
            if(log.isInfoEnabled())
                log.info("Session not found " + sessionId);
            return "";
        }
        return new Date(s.getLastAccessedTime()).toString();
    }

    public String getCreationTime( String sessionId ) {
        Session s=sessions.get(sessionId);
        if( s==null ) {
            if(log.isInfoEnabled())
                log.info("Session not found " + sessionId);
            return "";
        }
        return new Date(s.getCreationTime()).toString();
    }

    public long getCreationTimestamp( String sessionId ) {
        Session s=sessions.get(sessionId);
        if(s== null)
            return -1 ;
        return s.getCreationTime();
    }

    // ----------------------------------------------------------- Inner classes
    
    protected static final class SessionTiming {
        private final long timestamp;
        private final int duration;
        
        public SessionTiming(long timestamp, int duration) {
            this.timestamp = timestamp;
            this.duration = duration;
        }
        
        /**
         * @return Time stamp associated with this piece of timing information
         *         in milliseconds.
         */
        public long getTimestamp() {
            return timestamp;
        }
        
        /**
         * @return Duration associated with this piece of timing information in
         *         seconds.
         */
        public int getDuration() {
            return duration;
        }
    }
    
    protected void getRandomBytes(byte bytes[]) {

        SecureRandom random = randoms.poll();
        if (random == null) {
            try {
				random = SecureRandom.getInstance("SHA1PRNG");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			};
        }
        random.nextBytes(bytes);
        randoms.add(random);
    }

	@Override
	public int getMaxActive() {
		return 0;
	}

	@Override
	public void setMaxActive(int maxActive) {
		
	}

	@Override
	public List<Session> getSessions() {
		return (List<Session>)this.sessions.values();
	}

}
