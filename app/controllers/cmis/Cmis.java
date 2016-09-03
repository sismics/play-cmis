package controllers.cmis;

import helpers.cmis.CmisContext;
import helpers.cmis.CmisUtil;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.exceptions.CmisConnectionException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisUnauthorizedException;
import play.Logger;
import play.cache.Cache;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Finally;

/**
 * This interceptor initializes a CMIS session and sets it into the CMISContext.
 *
 *  @author jtremeaux
 */
public class Cmis extends Controller {

    @Before()
    public static void checkSession() {
        Session session = (Session) Cache.get(Controller.session.getId());
        if (session == null) {
            try {
                session = CmisUtil.createSessionFromPlay();
                Cache.set(Controller.session.getId(), session);
            } catch (CmisConnectionException e) {
                Logger.error(e.getMessage(), e);
                error(e.getMessage());
            } catch (CmisUnauthorizedException e) {
                unauthorized("CMIS");
            }
        }
        CmisContext.get().setSession(session);
    }

    @Finally
    public static void withCmisFinally() {
        CmisContext.reset();
    }

}
