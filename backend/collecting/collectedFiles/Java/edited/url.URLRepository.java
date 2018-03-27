

package org.elasticsearch.repositories.url;

import org.elasticsearch.cluster.metadata.RepositoryMetaData;
import org.elasticsearch.common.blobstore.BlobPath;
import org.elasticsearch.common.blobstore.BlobStore;
import org.elasticsearch.common.blobstore.url.URLBlobStore;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.settings.Setting.Property;
import org.elasticsearch.common.util.URIPattern;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.env.Environment;
import org.elasticsearch.repositories.RepositoryException;
import org.elasticsearch.repositories.blobstore.BlobStoreRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;


public class URLRepository extends BlobStoreRepository {

    public static final String TYPE = "url";

    public static final Setting<List<String>> SUPPORTED_PROTOCOLS_SETTING =
        Setting.listSetting("repositories.url.supported_protocols", Arrays.asList("http", "https", "ftp", "file", "jar"),
            Function.identity(), Property.NodeScope);

    public static final Setting<List<URIPattern>> ALLOWED_URLS_SETTING =
        Setting.listSetting("repositories.url.allowed_urls", Collections.emptyList(), URIPattern::new, Property.NodeScope);

    public static final Setting<URL> URL_SETTING = new Setting<>("url", "http:", URLRepository::parseURL, Property.NodeScope);
    public static final Setting<URL> REPOSITORIES_URL_SETTING =
        new Setting<>("repositories.url.url", (s) -> s.get("repositories.uri.url", "http:"), URLRepository::parseURL,
            Property.NodeScope);

    private final List<String> supportedProtocols;

    private final URIPattern[] urlWhiteList;

    private final Environment environment;

    private final URLBlobStore blobStore;

    private final BlobPath basePath;

    
    public URLRepository(RepositoryMetaData metadata, Environment environment,
                         NamedXContentRegistry namedXContentRegistry) throws IOException {
        super(metadata, environment.settings(), namedXContentRegistry);

        if (URL_SETTING.exists(metadata.settings()) == false && REPOSITORIES_URL_SETTING.exists(settings) ==  false) {
            throw new RepositoryException(metadata.name(), "missing url");
        }
        supportedProtocols = SUPPORTED_PROTOCOLS_SETTING.get(settings);
        urlWhiteList = ALLOWED_URLS_SETTING.get(settings).toArray(new URIPattern[]{});
        this.environment = environment;

        URL url = URL_SETTING.exists(metadata.settings()) ? URL_SETTING.get(metadata.settings()) : REPOSITORIES_URL_SETTING.get(settings);
        URL normalizedURL = checkURL(url);
        blobStore = new URLBlobStore(settings, normalizedURL);
        basePath = BlobPath.cleanPath();
    }

    @Override
    protected BlobStore blobStore() {
        return blobStore;
    }

    @Override
    protected BlobPath basePath() {
        return basePath;
    }

    
    private URL checkURL(URL url) {
        String protocol = url.getProtocol();
        if (protocol == null) {
            throw new RepositoryException(getMetadata().name(), "unknown url protocol from URL [" + url + "]");
        }
        for (String supportedProtocol : supportedProtocols) {
            if (supportedProtocol.equals(protocol)) {
                try {
                    if (URIPattern.match(urlWhiteList, url.toURI())) {
                                                return url;
                    }
                } catch (URISyntaxException ex) {
                    logger.warn("cannot parse the specified url [{}]", url);
                    throw new RepositoryException(getMetadata().name(), "cannot parse the specified url [" + url + "]");
                }
                                URL normalizedUrl = environment.resolveRepoURL(url);
                if (normalizedUrl == null) {
                    String logMessage = "The specified url [{}] doesn't start with any repository paths specified by the " +
                        "path.repo setting or by {} setting: [{}] ";
                    logger.warn(logMessage, url, ALLOWED_URLS_SETTING.getKey(), environment.repoFiles());
                    String exceptionMessage = "file url [" + url + "] doesn't match any of the locations specified by path.repo or "
                        + ALLOWED_URLS_SETTING.getKey();
                    throw new RepositoryException(getMetadata().name(), exceptionMessage);
                }
                return normalizedUrl;
            }
        }
        throw new RepositoryException(getMetadata().name(), "unsupported url protocol [" + protocol + "] from URL [" + url + "]");
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    private static URL parseURL(String s) {
        try {
            return new URL(s);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Unable to parse URL repository setting", e);
        }
    }
}
