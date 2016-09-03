package helpers.cmis.inmemory;

import com.google.common.io.Closer;
import helpers.cmis.CmisContext;
import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.ObjectIdImpl;
import org.apache.chemistry.opencmis.commons.data.*;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.ExtensionLevel;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.apache.commons.fileupload.util.Streams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * @author jtremeaux
 */
public class DocumentImpl implements Document {
    private String id;

    private String name;

    private String parentId;

    private File file;

    private String mimeType;

    private Long length;

    public DocumentImpl(String name, String parentId) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.parentId = parentId;
    }

    @Override
    public TransientDocument getTransientDocument() {
        return null;
    }

    @Override
    public void deleteAllVersions() {
        delete(true);
    }

    @Override
    public ContentStream getContentStream() {
        return getContentStream(null, null, null);
    }

    @Override
    public ContentStream getContentStream(BigInteger offset, BigInteger length) {
        return getContentStream(null, null, null);
    }

    @Override
    public ContentStream getContentStream(String streamId) {
        return getContentStream(null, null, null);
    }

    @Override
    public ContentStream getContentStream(String streamId, BigInteger offset, BigInteger length) {
        if (file == null) {
            return null;
        }
        try {
            return new ContentStreamImpl(name, length, mimeType, new FileInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Document setContentStream(ContentStream contentStream, boolean overwrite) {
        File file = null;
        long length;
        Closer closer = Closer.create();
        try {
            file = File.createTempFile(name, null);
            file.deleteOnExit();
            FileOutputStream os = closer.register(new FileOutputStream(file));
            length = Streams.copy(contentStream.getStream(), os, true);
        } catch (IOException e) {
            throw new RuntimeException("Error creating binary file " + name, e);
        } finally {
            try {
                closer.close();
            } catch (IOException e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }

        this.file = file;
        this.length = length;
        this.mimeType = contentStream.getMimeType();

        return this;
    }

    @Override
    public ObjectId setContentStream(ContentStream contentStream, boolean overwrite, boolean refresh) {
        return setContentStream(contentStream, true);
    }

    @Override
    public Document deleteContentStream() {
        if (file == null) {
            return this;
        };
        file.delete();
        file = null;
        length = 0L;
        mimeType = "";

        return this;
    }

    @Override
    public ObjectId deleteContentStream(boolean refresh) {
        return deleteContentStream();
    }

    @Override
    public ObjectId checkOut() {
        return null;
    }

    @Override
    public void cancelCheckOut() {
      
    }

    @Override
    public ObjectId checkIn(boolean major, Map<String, ?> properties, ContentStream contentStream, String checkinComment, List<Policy> policies, List<Ace> addAces, List<Ace> removeAces) {
        return null;
    }

    @Override
    public ObjectId checkIn(boolean major, Map<String, ?> properties, ContentStream contentStream, String checkinComment) {
        return null;
    }

    @Override
    public Document getObjectOfLatestVersion(boolean major) {
        return null;
    }

    @Override
    public Document getObjectOfLatestVersion(boolean major, OperationContext context) {
        return null;
    }

    @Override
    public List<Document> getAllVersions() {
        return null;
    }

    @Override
    public List<Document> getAllVersions(OperationContext context) {
        return null;
    }

    @Override
    public Document copy(ObjectId targetFolderId) {
        return null;
    }

    @Override
    public Document copy(ObjectId targetFolderId, Map<String, ?> properties, VersioningState versioningState, List<Policy> policies, List<Ace> addACEs, List<Ace> removeACEs, OperationContext context) {
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
        Folder folder = (Folder) CmisContext.get().getSession().getObject(parentId);

        return Arrays.asList(folder);
    }

    @Override
    public List<Folder> getParents(OperationContext context) {
        return getParents();
    }

    @Override
    public List<String> getPaths() {
        List<String> paths = new ArrayList<String>();
        for (Folder folder : getParents()) {
            paths.add(folder.getPath() + name);
        }
        return paths;
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
        delete(true);
    }

    @Override
    public void delete(boolean allVersions) {
        CmisContext.get().getSession().delete(new ObjectIdImpl(id));
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
        return name;
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
    public Boolean isImmutable() {
        return false;
    }

    @Override
    public Boolean isLatestVersion() {
        return true;
    }

    @Override
    public Boolean isMajorVersion() {
        return true;
    }

    @Override
    public Boolean isLatestMajorVersion() {
        return true;
    }

    @Override
    public String getVersionLabel() {
        return null;
    }

    @Override
    public String getVersionSeriesId() {
        return null;
    }

    @Override
    public Boolean isVersionSeriesCheckedOut() {
        return null;
    }

    @Override
    public String getVersionSeriesCheckedOutBy() {
        return null;
    }

    @Override
    public String getVersionSeriesCheckedOutId() {
        return null;
    }

    @Override
    public String getCheckinComment() {
        return null;
    }

    @Override
    public long getContentStreamLength() {
        return length;
    }

    @Override
    public String getContentStreamMimeType() {
        return mimeType;
    }

    @Override
    public String getContentStreamFileName() {
        return name;
    }

    @Override
    public String getContentStreamId() {
        return file.getAbsolutePath();
    }

    @Override
    public String getId() {
        return id;
    }
}
