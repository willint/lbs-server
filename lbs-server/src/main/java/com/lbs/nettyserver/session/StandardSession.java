package com.lbs.nettyserver.session;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;



/**
 * Standard implementation of the <b>Session</b> interface.  This object is
 * serializable, so that it can be stored in persistent storage or transferred
 * to a different JVM for distributable session support.
 * @author Craig R. McClanahan
 * @author Sean Legassick
 */
public class StandardSession implements  Session, Serializable {

    private static final long serialVersionUID = 1L;

    protected static final boolean ACTIVITY_CHECK;


    static {
        ACTIVITY_CHECK = false;
    }

    // ----------------------------------------------------------- Constructors

    /**
     * Construct a new Session associated with the specified Manager.
     *
     * @param manager The manager with which this Session is associated
     */
    public StandardSession() {
        super();
    }
    
    
    
    // ----------------------------------------------------- Instance Variables
    /**
     * The time this session was created, in milliseconds since midnight,
     * January 1, 1970 GMT.
     */
    protected long creationTime = 0L;

    /**
     * We are currently processing a session expiration, so bypass
     * certain IllegalStateException tests.  NOTE:  This value is not
     * included in the serialized version of this object.
     */

    /**
     * The session identifier of this Session.
     */
    protected String id = null;

    /**
     * The last accessed time for this Session.
     */
    protected volatile long lastAccessedTime = creationTime;


    /**
     * The Manager with which this Session is associated.
     */
    protected transient Manager manager = null;


    /**
     * The maximum time interval, in seconds, between client requests before
     * the servlet container may invalidate this session.  A negative time
     * indicates that the session should never time out.
     * 默认30分钟
     */
    protected int maxInactiveInterval = 60*30;

    /**
     * Flag indicating whether this session is valid or not.
     */
    protected volatile boolean isValid = false;


    /**
     * The access count for this session.
     */
    protected transient AtomicInteger accessCount = null;


    // ----------------------------------------------------- Session Properties
    /**
     * Set the creation time for this session.  This method is called by the
     * Manager when an existing Session instance is reused.
     *
     * @param time The new creation time
     */
    @Override
    public void setCreationTime(long time) {
        this.creationTime = time;
        this.lastAccessedTime = time;
    }

    /**
     * Return the session identifier for this session.
     */
    @Override
    public String getId() {

        return (this.id);

    }
    /**
     * Set the session identifier for this session.
     *
     * @param id The new session identifier
     */
    @Override
    public void setId(String id) {
    	this.id = id;
    }


	/**
     * Return the last time the client sent a request associated with this
     * session, as the number of milliseconds since midnight, January 1, 1970
     * GMT.  Actions that your application takes, such as getting or setting
     * a value associated with the session, do not affect the access time.
     * This one gets updated whenever a request finishes.
     */
    @Override
    public long getLastAccessedTime() {
        return (this.lastAccessedTime);
    }

    /**
     * Return the Manager within which this Session is valid.
     */
    @Override
    public Manager getManager() {
        return this.manager;
    }


    /**
     * Set the Manager within which this Session is valid.
     *
     * @param manager The new Manager
     */
    @Override
    public void setManager(Manager manager) {
        this.manager = manager;
    }


    /**
     * Return the maximum time interval, in seconds, between client requests
     * before the servlet container will invalidate the session.  A negative
     * time indicates that the session should never time out.
     */
    @Override
    public int getMaxInactiveInterval() {
        return (this.maxInactiveInterval);
    }

    /**
     * Set the maximum time interval, in seconds, between client requests
     * before the servlet container will invalidate the session.  A zero or
     * negative time indicates that the session should never time out.
     *
     * @param interval The new maximum interval
     */
    @Override
    public void setMaxInactiveInterval(int interval) {
        this.maxInactiveInterval = interval;
    }

    /**
     * Return the <code>isValid</code> flag for this session.
     */
    @Override
    public boolean isValid() {

        if (!this.isValid) {
            return false;
        }

        if (ACTIVITY_CHECK && accessCount.get() > 0) {
            return true;
        }

        if (maxInactiveInterval > 0) {
            long timeNow = System.currentTimeMillis();
            int timeIdle  = (int) ((timeNow - lastAccessedTime) / 1000L);
            if (timeIdle >= maxInactiveInterval) {
                expire();
            }
        }
        return this.isValid;
    }

    /**
     * Set the <code>isValid</code> flag for this session.
     *
     * @param isValid The new value for the <code>isValid</code> flag
     */
    @Override
    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    /**
     * Update the accessed time information for this session.  This method
     * should be called by the context when a request comes in for a particular
     * session, even if the application does not reference it.
     */
    @Override
    public void access() {

        this.lastAccessedTime = System.currentTimeMillis();

        if (ACTIVITY_CHECK) {
            accessCount.incrementAndGet();
        }

    }

    /**
     * Perform the internal processing required to invalidate this session,
     * without triggering an exception if the session has already expired.
     */
    @Override
    public void expire() {

        this.isValid = false;
    }
    
    /**
     * Return the time when this session was created, in milliseconds since
     * midnight, January 1, 1970 GMT.
     *
     * @exception IllegalStateException if this method is called on an
     *  invalidated session
     */
    @Override
    public long getCreationTime() {
        return (this.creationTime);
    }

}

