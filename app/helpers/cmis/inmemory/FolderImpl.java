package helpers.cmis.inmemory;

import helpers.cmis.CmisContext;
import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.ObjectIdImpl;
import org.apache.chemistry.opencmis.client.runtime.util.EmptyItemIterable;
import org.apache.chemistry.opencmis.commons.data.*;
import org.apache.chemistry.opencmis.commons.enums.*;

import java.util.*;

/**
 * @author jtremeaux
 */
public class FolderImpl implements Folder {
    private String id;

    private Folder parent;

    private String name;

    public FolderImpl(Folder parent, String name) {
        this.id = UUID.randomUUID().toString();
        this.parent = parent;
        this.name = name;
    }
    
    @Override
    public TransientFolder getTransientFolder() {
        return null;
    }

    @Override
    public Document createDocument(Map<String, ?> properties, ContentStream contentStream, VersioningState versioningState, List<Policy> policies, List<Ace> addAces, List<Ace> removeAces, OperationContext context) {
        return null;
    }

    @Override
    public Document createDocument(Map<String, ?> properties, ContentStream contentStream, VersioningState versioningState) {
        ObjectId objectId = CmisContext.get().getSession().createDocument(properties, new ObjectIdImpl(id), contentStream, versioningState);
        return (Document) CmisContext.get().getSession().getObject(objectId);
    }

    @Override
    public Document createDocumentFromSource(ObjectId source, Map<String, ?> properties, VersioningState versioningState, List<Policy> policies, List<Ace> addAces, List<Ace> removeAces, OperationContext context) {
        return null;
    }

    @Override
    public Document createDocumentFromSource(ObjectId source, Map<String, ?> properties, VersioningState versioningState) {
        return null;
    }

    @Override
    public Folder createFolder(Map<String, ?> properties, List<Policy> policies, List<Ace> addAces, List<Ace> removeAces, OperationContext context) {
        return null;
    }

    @Override
    public Folder createFolder(Map<String, ?> properties) {
        return null;
    }

    @Override
    public Policy createPolicy(Map<String, ?> properties, List<Policy> policies, List<Ace> addAces, List<Ace> removeAces, OperationContext context) {
        return null;
    }

    @Override
    public Policy createPolicy(Map<String, ?> properties) {
        return null;
    }

    @Override
    public List<String> deleteTree(boolean allversions, UnfileObject unfile, boolean continueOnFailure) {
        return null;
    }

    @Override
    public List<Tree<FileableCmisObject>> getFolderTree(int depth) {
        return null;
    }

    @Override
    public List<Tree<FileableCmisObject>> getFolderTree(int depth, OperationContext context) {
        return null;
    }

    @Override
    public List<Tree<FileableCmisObject>> getDescendants(int depth) {
        return null;
    }

    @Override
    public List<Tree<FileableCmisObject>> getDescendants(int depth, OperationContext context) {
        return null;
    }

    @Override
    public ItemIterable<CmisObject> getChildren() {
        return new EmptyItemIterable<CmisObject>();
    }

    @Override
    public ItemIterable<CmisObject> getChildren(OperationContext context) {
        return new EmptyItemIterable<CmisObject>();
    }

    @Override
    public boolean isRootFolder() {
        return parent == null;
    }

    @Override
    public Folder getFolderParent() {
        return parent;
    }

    @Override
    public String getPath() {
        if (isRootFolder()) {
            return name;
        } else {
            return getFolderParent().getPath() + name + "/";
        }
    }

    @Override
    public ItemIterable<Document> getCheckedOutDocs() {
        return null;
    }

    @Override
    public ItemIterable<Document> getCheckedOutDocs(OperationContext context) {
        return null;
    }

    @Override
    public FileableCmisObject move(ObjectId sourceFolderId, ObjectId targetFolderId) {
        return null;
    }

    @Override
    public FileableCmisObject move(ObjectId sourceFolderId, ObjectId targetFolderId, OperationContext context) {
        return null;
    }

    @Override
    public List<Folder> getParents() {
        return null;
    }

    @Override
    public List<Folder> getParents(OperationContext context) {
        return null;
    }

    @Override
    public List<String> getPaths() {
        return Arrays.asList(getPath());
    }

    @Override
    public void addToFolder(ObjectId folderId, boolean allVersions) {
      
    }

    @Override
    public void removeFromFolder(ObjectId folderId) {
      
    }

    @Override
    public AllowableActions getAllowableActions() {
        return null;
    }

    @Override
    public List<Relationship> getRelationships() {
        return null;
    }

    @Override
    public Acl getAcl() {
        return null;
    }

    @Override
    public void delete() {
      
    }

    @Override
    public void delete(boolean allVersions) {
      
    }

    @Override
    public CmisObject updateProperties(Map<String, ?> properties) {
        return null;
    }

    @Override
    public ObjectId updateProperties(Map<String, ?> properties, boolean refresh) {
        return null;
    }

    @Override
    public List<Rendition> getRenditions() {
        return null;
    }

    @Override
    public void applyPolicy(ObjectId... policyIds) {
      
    }

    @Override
    public void removePolicy(ObjectId... policyIds) {
      
    }

    @Override
    public List<Policy> getPolicies() {
        return null;
    }

    @Override
    public Acl applyAcl(List<Ace> addAces, List<Ace> removeAces, AclPropagation aclPropagation) {
        return null;
    }

    @Override
    public Acl addAcl(List<Ace> addAces, AclPropagation aclPropagation) {
        return null;
    }

    @Override
    public Acl removeAcl(List<Ace> removeAces, AclPropagation aclPropagation) {
        return null;
    }

    @Override
    public Acl setAcl(List<Ace> aces) {
        return null;
    }

    @Override
    public List<CmisExtensionElement> getExtensions(ExtensionLevel level) {
        return null;
    }

    @Override
    public <T> T getAdapter(Class<T> adapterInterface) {
        return null;
    }

    @Override
    public TransientCmisObject getTransientObject() {
        return null;
    }

    @Override
    public long getRefreshTimestamp() {
        return 0;
    }

    @Override
    public void refresh() {
      
    }

    @Override
    public void refreshIfOld(long durationInMillis) {
      
    }

    @Override
    public List<Property<?>> getProperties() {
        return null;
    }

    @Override
    public <T> Property<T> getProperty(String id) {
        return null;
    }

    @Override
    public <T> T getPropertyValue(String id) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getCreatedBy() {
        return null;
    }

    @Override
    public GregorianCalendar getCreationDate() {
        return null;
    }

    @Override
    public String getLastModifiedBy() {
        return null;
    }

    @Override
    public GregorianCalendar getLastModificationDate() {
        return null;
    }

    @Override
    public BaseTypeId getBaseTypeId() {
        return null;
    }

    @Override
    public ObjectType getBaseType() {
        return null;
    }

    @Override
    public ObjectType getType() {
        return null;
    }

    @Override
    public String getChangeToken() {
        return null;
    }

    @Override
    public String getParentId() {
        return parent.getId();
    }

    @Override
    public List<ObjectType> getAllowedChildObjectTypes() {
        return null;
    }

    @Override
    public String getId() {
        return id;
    }
}
