import org.apache.commons.lang.StringUtils;
import org.jfrog.artifactory.client.ArtifactoryClientBuilder;

public class ArtifactoryConnection {

    static org.jfrog.artifactory.client.Artifactory createArtifactory(String username, String password, String artifactoryUrl) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(artifactoryUrl)){
            throw new IllegalArgumentException("Arguments passed to createArtifactory are not valid");
        }

        return ArtifactoryClientBuilder.create()
                .setUrl(artifactoryUrl)
                .setUsername(username)
                .setPassword(password)
                .build();
    }
}
