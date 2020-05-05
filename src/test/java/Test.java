import org.jfrog.artifactory.client.model.File;
import org.jfrog.artifactory.client.model.Repository;
import org.jfrog.artifactory.client.model.User;
import org.jfrog.artifactory.client.model.builder.UserBuilder;
import org.jfrog.artifactory.client.model.repository.settings.impl.DebianRepositorySettingsImpl;
import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Test extends AbstractTest {

    public String repoName = generateRandomStr();
    public String newUserName = generateRandomStr();

    @org.testng.annotations.Test
    public void pingArtifactory() {
        Assert.assertTrue(artifactory.system().ping());
    }

    @org.testng.annotations.Test
    public void createRepo() {
        DebianRepositorySettingsImpl settings = new DebianRepositorySettingsImpl();
        settings.setDebianTrivialLayout(true);

        Repository repository = artifactory.repositories()
                .builders()
                .localRepositoryBuilder()
                .key(repoName)
                .description("new local repository omri")
                .repositorySettings(settings)
                .build();

        String result = artifactory.repositories().create(2, repository);
        Assert.assertEquals(result, "Successfully created repository '" + repoName + "' \n");
    }

    @org.testng.annotations.Test(dependsOnMethods = "createRepo")
    public void uploadFile() throws IOException {
        java.io.File file = new java.io.File("newFile.txt");
        File result = artifactory.repository(repoName).upload("folder12/newFile.txt", file).doUpload();
        Assert.assertEquals(result.getDownloadUri(), artifactoryUrl + "/" + repoName + "/folder12/newFile.txt");
    }

    @org.testng.annotations.Test
    public void addUser() {
        UserBuilder userBuilder = artifactory.security().builders().userBuilder();
        User user = userBuilder.name(newUserName)
                .email(newUserName + "@mail.com")
                .admin(true)
                .profileUpdatable(true)
                .password("password")
                .build();

        artifactory.security().createOrUpdate(user);

        Collection<String> collectionOfUserNames = artifactory.security().userNames();
        boolean found = false;
        for (String newUser : collectionOfUserNames) {
            if (newUser.equals("omritest1234")) {
                found = true;
                break;
            }
        }
        Assert.assertTrue(found);
    }

    @org.testng.annotations.Test(dependsOnMethods = {"addUser", "uploadFile"})
    public void downloadFile() throws InterruptedException {
        artifactory = ArtifactoryConnection.createArtifactory(newUserName, "password", artifactoryUrl);
        InputStream iStream = artifactory.repository(repoName)
                .download("/folder12/newFile.txt")
                .doDownload();
        // verify the file actually been loaded : use last downloaded by
    }
}
