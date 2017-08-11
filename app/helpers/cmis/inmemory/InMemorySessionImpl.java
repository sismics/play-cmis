package helpers.cmis.inmemory;

import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.ObjectIdImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.data.Acl;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;
import org.apache.chemistry.opencmis.commons.enums.IncludeRelationships;
import org.apache.chemistry.opencmis.commons.enums.RelationshipDirection;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisContentAlreadyExistsException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.chemistry.opencmis.commons.spi.CmisBinding;
import play.Logger;
import play.test.Fixtures;

import java.math.BigInteger;
import java.util.*;

/**
 * CMIS session that persists documents to temporary files, and keeps all metadata in memory.
 * Warning: not thread safe!
 *
 * @author jtremeaux
 */
public class InMemorySessionImpl implements Session {
    private OperationContext defaultContext;

    static {
        if (play.test.Fixtures.idCache.get("objectByPathMap") == null) {
            Logger.info("Initializing CMIS in-memory repository");

            Fixtures.idCache.put("objectByPathMap", new HashMap<String, CmisObject>());
            Fixtures.idCache.put("objectByIdMap", new HashMap<String, CmisObject>());

            Folder rootFolder = new FolderImpl(null, "/");
            getObjectByPathMap().put("/", rootFolder);
            getObjectByIdMap().put(rootFolder.getId(), rootFolder);
        }
    }

    public InMemorySessionImpl() {
    }

    @Override
    public void clear() {
        // NOP
    }

    @Override
    public CmisBinding getBinding() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public OperationContext getDefaultContext() {
        return defaultContext;
    }

    @Override
    public void setDefaultContext(OperationContext context) {
        this.defaultContext = context;
    }

    @Override
    public OperationContext createOperationContext() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public OperationContext createOperationContext(Set<String> filter, boolean includeAcls, boolean includeAllowableActions, boolean includePolicies, IncludeRelationships includeRelationships, Set<String> renditionFilter, boolean includePathSegments, String orderBy, boolean cacheEnabled, int maxItemsPerPage) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ObjectId createObjectId(String id) {
        return new ObjectIdImpl(id);
    }

    @Override
    public Locale getLocale() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public RepositoryInfo getRepositoryInfo() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ObjectFactory getObjectFactory() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ObjectType getTypeDefinition(String typeId) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ItemIterable<ObjectType> getTypeChildren(String typeId, boolean includePropertyDefinitions) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public List<Tree<ObjectType>> getTypeDescendants(String typeId, int depth, boolean includePropertyDefinitions) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public Folder getRootFolder() {
        return getRootFolder(null);
    }

    @Override
    public Folder getRootFolder(OperationContext context) {
        return (Folder) getObjectByPathMap().get("/");
    }

    @Override
    public ItemIterable<Document> getCheckedOutDocs() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ItemIterable<Document> getCheckedOutDocs(OperationContext context) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public CmisObject getObject(ObjectId objectId) {
        return getObject(objectId.getId());
    }

    @Override
    public CmisObject getObject(ObjectId objectId, OperationContext context) {
        return getObject(objectId);
    }

    @Override
    public CmisObject getObject(String objectId) {
        CmisObject object = getObjectByIdMap().get(objectId);
        if (object == null) {
            throw new CmisObjectNotFoundException("Object not found: " + objectId);
        }
        return object;
    }

    @Override
    public CmisObject getObject(String objectId, OperationContext context) {
        return getObject(objectId);
    }

    @Override
    public CmisObject getObjectByPath(String path) {
        CmisObject object = getObjectByPathMap().get(path);
        if (object == null) {
            throw new CmisObjectNotFoundException("Object not found: " + path);
        }
        return object;
    }

    @Override
    public CmisObject getObjectByPath(String path, OperationContext context) {
        return getObjectByPath(path);
    }

    @Override
    public void removeObjectFromCache(ObjectId objectId) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public void removeObjectFromCache(String objectId) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ItemIterable<QueryResult> query(String statement, boolean searchAllVersions) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ItemIterable<QueryResult> query(String statement, boolean searchAllVersions, OperationContext context) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ItemIterable<CmisObject> queryObjects(String typeId, String where, boolean searchAllVersions, OperationContext context) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public QueryStatement createQueryStatement(String statement) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ChangeEvents getContentChanges(String changeLogToken, boolean includeProperties, long maxNumItems) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ChangeEvents getContentChanges(String changeLogToken, boolean includeProperties, long maxNumItems, OperationContext context) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ObjectId createDocument(Map<String, ?> properties, ObjectId folderId, ContentStream contentStream, VersioningState versioningState, List<Policy> policies, List<Ace> addAces, List<Ace> removeAces) {
        return createDocument(properties, folderId, contentStream, versioningState);
    }

    @Override
    public ObjectId createDocument(Map<String, ?> properties, ObjectId folderId, ContentStream contentStream, VersioningState versioningState) {
        String name = (String) properties.get(PropertyIds.NAME);
        Folder folder = (Folder) getObject(folderId);
        DocumentImpl document = new DocumentImpl(name, folder.getId());

        for (String path : document.getPaths()) {
            if (getObjectByPathMap().containsKey(path)) {
                throw new CmisContentAlreadyExistsException("Folder " + folder.getPath() + " already contains a document with name " + name);
            }
        }

        if (contentStream != null) {
            document.setContentStream(contentStream, true);
        }

        getObjectByIdMap().put(document.getId(), document);
        for (String path : document.getPaths()) {
            getObjectByPathMap().put(path, document);
        }

        return createObjectId(document.getId());
    }

    @Override
    public ObjectId createDocumentFromSource(ObjectId source, Map<String, ?> properties, ObjectId folderId, VersioningState versioningState, List<Policy> policies, List<Ace> addAces, List<Ace> removeAces) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ObjectId createDocumentFromSource(ObjectId source, Map<String, ?> properties, ObjectId folderId, VersioningState versioningState) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ObjectId createFolder(Map<String, ?> properties, ObjectId folderId, List<Policy> policies, List<Ace> addAces, List<Ace> removeAces) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ObjectId createFolder(Map<String, ?> properties, ObjectId folderId) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ObjectId createPolicy(Map<String, ?> properties, ObjectId folderId, List<Policy> policies, List<Ace> addAces, List<Ace> removeAces) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ObjectId createPolicy(Map<String, ?> properties, ObjectId folderId) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ObjectId createRelationship(Map<String, ?> properties, List<Policy> policies, List<Ace> addAces, List<Ace> removeAces) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ObjectId createRelationship(Map<String, ?> properties) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public ItemIterable<Relationship> getRelationships(ObjectId objectId, boolean includeSubRelationshipTypes, RelationshipDirection relationshipDirection, ObjectType type, OperationContext context) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public void delete(ObjectId objectId) {
        delete(objectId, true);
    }

    @Override
    public void delete(ObjectId objectId, boolean allVersions) {
        CmisObject object = getObject(objectId);
        getObjectByIdMap().put(object.getId(), null);
        if (object instanceof FileableCmisObject) {
            for (String path : ((FileableCmisObject) object).getPaths()) {
                getObjectByPathMap().remove(path);
            }
        }
        if (object instanceof Document) {
            ((Document) object).deleteContentStream();
        }
    }

    @Override
    public ContentStream getContentStream(ObjectId docId) {
        return getContentStream(null, null, null, null);
    }

    @Override
    public ContentStream getContentStream(ObjectId docId, String streamId, BigInteger offset, BigInteger length) {
        CmisObject object = getObject(docId);
        if (!(object instanceof Document)) {
            throw new RuntimeException("Object must be a document");
        }
        return ((Document) object).getContentStream();
    }

    @Override
    public Acl getAcl(ObjectId objectId, boolean onlyBasicPermissions) {
        return null;
    }

    @Override
    public Acl applyAcl(ObjectId objectId, List<Ace> addAces, List<Ace> removeAces, AclPropagation aclPropagation) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public Acl setAcl(ObjectId objectId, List<Ace> aces) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public void applyPolicy(ObjectId objectId, ObjectId... policyIds) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public void removePolicy(ObjectId objectId, ObjectId... policyIds) {
        throw new RuntimeException("not implemented");
    }

    @SuppressWarnings("unchecked")
    private static HashMap<String, CmisObject> getObjectByPathMap() {
        return (HashMap<String, CmisObject>) Fixtures.idCache.get("objectByPathMap");
    }

    @SuppressWarnings("unchecked")
    private static HashMap<String, CmisObject> getObjectByIdMap() {
        return (HashMap<String, CmisObject>) Fixtures.idCache.get("objectByIdMap");
    }
}
