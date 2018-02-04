package com.lbs.nettyserver.session;

import java.io.IOException;
import java.util.List;


/**
 * A <b>Manager</b> manages the pool of Sessions that are associated with a
 * particular Container.  Different Manager implementations may support
 * value-added features such as the persistent storage of session data,
 * as well as migrating sessions for distributable web applications.
 * <p>
 * In order for a <code>Manager</code> implementation to successfully operate
 * with a <code>Context</code> implementation that implements reloading, it
 * must obey the following constraints:
 * <ul>
 * <li>Must implement <code>Lifecycle</code> so that the Context can indicate
 *     that a restart is required.
 * <li>Must allow a call to <code>stop()</code> to be followed by a call to
 *     <code>start()</code> on the same <code>Manager</code> instance.
 * </ul>
 *
 * @author Craig R. McClanahan
 */
public interface Manager {


    // ------------------------------------------------------------- Properties
	//设置每个manager的session容量
	public  int MANAGER_SIZE = 1000;
	
	
    /**
     * Gets the maximum number of sessions that have been active at the same
     * time.
     *
     * @return Maximum number of sessions that have been active at the same
     * time
     */
    public int getMaxActive();


    /**
     * (Re)sets the maximum number of sessions that have been active at the
     * same time.
     *
     * @param maxActive Maximum number of sessions that have been active at
     * the same time.
     */
    public void setMaxActive(int maxActive);


    /**
     * Gets the number of currently active sessions.
     *
     * @return Number of currently active sessions
     */
    public int getActiveSessions();

    // --------------------------------------------------------- Public Methods


    /**
     * Add this Session to the set of active Sessions for this Manager.
     *
     * @param session Session to be added
     */
    public void add(Session session);


    public Session createSession();


    /**
     * Return the active Session, associated with this Manager, with the
     * specified session id (if any); otherwise return <code>null</code>.
     *
     * @param id The session id for the session to be returned
     *
     * @exception IllegalStateException if a new session cannot be
     *  instantiated for any reason
     * @exception IOException if an input/output error occurs while
     *  processing this request
     */
    public Session findSession(String id) throws IOException;


    /**
     * Remove this Session from the active Sessions for this Manager.
     *
     * @param session Session to be removed
     */
    public void remove(Session session);
    
    
    public List<Session> getSessions();
    
}
