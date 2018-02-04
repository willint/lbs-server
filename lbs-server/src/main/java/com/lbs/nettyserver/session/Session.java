
package com.lbs.nettyserver.session;

/**
 * A <b>Session</b> is the Catalina-internal facade for an
 * <code>HttpSession</code> that is used to maintain state information
 * between requests for a particular user of a web application.
 *
 * @author Craig R. McClanahan
 */
public interface Session {

    // ----------------------------------------------------- Manifest Constants

    /**
     * The SessionEvent event type when a session is created.
     */
    public static final String SESSION_CREATED_EVENT = "createSession";


    /**
     * The SessionEvent event type when a session is destroyed.
     */
    public static final String SESSION_DESTROYED_EVENT = "destroySession";


    /**
     * The SessionEvent event type when a session is activated.
     */
    public static final String SESSION_ACTIVATED_EVENT = "activateSession";


    /**
     * The SessionEvent event type when a session is passivated.
     */
    public static final String SESSION_PASSIVATED_EVENT = "passivateSession";


    // ------------------------------------------------------------- Properties

    /**
     * Return the creation time for this session.
     */
    public long getCreationTime();

    /**
     * Set the creation time for this session.  This method is called by the
     * Manager when an existing Session instance is reused.
     *
     * @param time The new creation time
     */
    public void setCreationTime(long time);


    /**
     * Return the session identifier for this session.
     */
    public String getId();

    /**
     * Set the session identifier for this session and notifies any associated
     * listeners that a new session has been created.
     *
     * @param id The new session identifier
     */
    public void setId(String id);

    /**
     * Return the last time the client sent a request associated with this
     * session, as the number of milliseconds since midnight, January 1, 1970
     * GMT.  Actions that your application takes, such as getting or setting
     * a value associated with the session, do not affect the access time.
     * This one gets updated whenever a request finishes.
     */
    public long getLastAccessedTime();

    /**
     * Return the Manager within which this Session is valid.
     */
    public Manager getManager();


    /**
     * Set the Manager within which this Session is valid.
     *
     * @param manager The new Manager
     */
    public void setManager(Manager manager);


    /**
     * Return the maximum time interval, in seconds, between client requests
     * before the servlet container will invalidate the session.  A negative
     * time indicates that the session should never time out.
     */
    public int getMaxInactiveInterval();


    /**
     * Set the maximum time interval, in seconds, between client requests
     * before the servlet container will invalidate the session.  A negative
     * time indicates that the session should never time out.
     *
     * @param interval The new maximum interval
     */
    public void setMaxInactiveInterval(int interval);


    /**
     * Set the <code>isValid</code> flag for this session.
     *
     * @param isValid The new value for the <code>isValid</code> flag
     */
    public void setValid(boolean isValid);


    /**
     * Return the <code>isValid</code> flag for this session.
     */
    public boolean isValid();


    // --------------------------------------------------------- Public Methods


    /**
     * Update the accessed time information for this session.  This method
     * should be called by the context when a request comes in for a particular
     * session, even if the application does not reference it.
     */
    public void access();

    /**
     * Perform the internal processing required to invalidate this session,
     * without triggering an exception if the session has already expired.
     */
    public void expire();

}
