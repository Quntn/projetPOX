package poei;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.sharing.PathLinkMetadata;

public class DbxTest {

	@Test
	public void toto() throws DbxException, IOException {
		DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").withUserLocale("en_US").build();
		DbxClientV2 client = new DbxClientV2(config, "rXd4qFWPnvAAAAAAAAAAEoS_V813cFWw5PGW_j8ZZH6962yWhls6-jqRV249u44y");


		System.out.println("Linked account: " + client.users().getCurrentAccount().getName());

		File inputFile = new File("C:\\code\\servers\\wildfly-10.0.0.Final\\bin\\vault\\tortue.jpg");

		FileInputStream inputStream = new FileInputStream(inputFile);

		try {

			FileMetadata uploadedFile = client.files().uploadBuilder("/tortue.jpg").uploadAndFinish(inputStream);
			PathLinkMetadata sharedUrl = client.sharing().createSharedLink("/tortue.jpg");
			/// String sharedUrl = client.createShareableUrl("/tortue.jpg");
			System.out.println("Uploaded: " + uploadedFile.toString() + " URL " + sharedUrl.getUrl());

		} finally {
			inputStream.close();
		}

	}
}
