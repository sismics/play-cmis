package helpers.cmis;

import org.apache.chemistry.opencmis.client.api.Session;

/**
 * CMIS thread-local context.
 *
 * @author jtremeaux
 */
public class CmisContext {
    private static ThreadLocal<CmisContext> local = new ThreadLocal<>();

    private Session session;

    private CmisContext() {
    }

    public static CmisContext get() {
        if (local.get() == null) {
            CmisContext context = new CmisContext();
            local.set(context);
        }
        return local.get();
    }

    public static void reset() {
        if (local.get() != null) {
            local.set(null);
        }
    }

    public Session getSession() {
        if (session == null) {
            throw new RuntimeException("Session not set in the CMIS context. Did you annotate your controller with @With(Cmis.class)?");
        }
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
