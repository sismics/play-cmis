package helpers.cmis;

import com.google.common.base.Strings;
import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import play.Play;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * CMIS utilities.
 *
 * @author jtremeaux
 */
public class CmisUtil {

    /**
     * Create a new CMIS session from Play! parameters.
     *
     * @return New CMIS session
     */
    public static Session createSessionFromPlay() {
        // Instantiate a custom CMIS session impl
        String sessionClass = Play.configuration.getProperty("cmis.session_impl");
        if (!Strings.isNullOrEmpty(sessionClass)) {
            try {
                return (Session) (Class.forName(sessionClass).newInstance());
            } catch (Exception e) {
                throw new RuntimeException("Cannot instantiate CMIS session", e);
            }
        }

        return createAtomPubSession(
                Play.configuration.getProperty("cmis.endpoint_url"),
                Play.configuration.getProperty("cmis.user"),
                Play.configuration.getProperty("cmis.password"));
    }

    /**
     * Create a new CMIS session to an AtomPub endpoint.
     *
     * @param endPoint Endpoint URL
     * @param user User
     * @param password Password
     * @return New CMIS session
     */
    public static Session createAtomPubSession(String endPoint, String user, String password) {
        // Set repository parameters
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
        parameters.put(SessionParameter.ATOMPUB_URL, endPoint);
        parameters.put(SessionParameter.USER, user);
        parameters.put(SessionParameter.PASSWORD, password);

        // Create the session
        SessionFactory factory = SessionFactoryImpl.newInstance();
        List<Repository> repositories = factory.getRepositories(parameters);
        Session session = repositories.get(0).createSession();
        session.setDefaultContext(defaultOperationContext());

        return session;
    }

    /**
     * Returns the default operation context.
     *
     * @return Default operation context
     */
    public static OperationContext defaultOperationContext() {
        OperationContextImpl context = new OperationContextImpl();
        context.setCacheEnabled(false);
        return context;
    }

    /**
     * Return the root folder.
     *
     * @return Root folder
     */
    public static Folder getRootFolder() {
        Session session = CmisContext.get().getSession();
        String rootFolder = Play.configuration.getProperty("cmis.root_folder");
        try {
            return (Folder) session.getObjectByPath(rootFolder);
        } catch (CmisObjectNotFoundException e) {
            throw new RuntimeException("Parent not found: " + rootFolder, e);
        }
    }

    /**
     * Returns the file location of the parent concatenated to its children.
     *
     * @param parent Parent location
     * @param file Children document to add
     * @return Full of the file
     */
    public static String append(String parent, String file) {
        if (!parent.endsWith("/")) {
            parent = parent + "/";
        }
        return parent + file;
    }

    /**
     * Returns an available name in the specified folder by appending a number to the file name (eg. "file (2).jpg")
     *
     * @param parent Parent folder
     * @param name File name
     * @return Available file name
     */
    public static String getAvailableName(Folder parent, String name) {
        // The plugin allows several documents with the same name, to make it simple we simply use an UUID
        return UUID.randomUUID().toString();
    }
}
