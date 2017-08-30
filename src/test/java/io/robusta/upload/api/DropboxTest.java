package io.robusta.upload.api;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.GetTemporaryLinkResult;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;

public class DropboxTest {
	private static String DROPBOX_ACCESS_TOKEN = "rXd4qFWPnvAAAAAAAAAAEoS_V813cFWw5PGW_j8ZZH6962yWhls6-jqRV249u44y";

	@Test
	public void testConnection() throws IOException, DbxApiException, DbxException {
		DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").withUserLocale("en_US").build();

		DbxClientV2 client = new DbxClientV2(config, DROPBOX_ACCESS_TOKEN);

		System.out.println("Linked account: " + client.users().getCurrentAccount().getName());

		String folderPath = "";
		ListFolderResult listFolderResult = client.files().listFolder(folderPath);
		List<Metadata> metadatas = listFolderResult.getEntries();
		for (Metadata metadata : metadatas) {
			String name = metadata.getName();

			GetTemporaryLinkResult tempLinkResult = client.files().getTemporaryLink(folderPath + "/" + name);
			String link = tempLinkResult.getLink();
			FileMetadata fileMetadata = tempLinkResult.getMetadata();
			String id = fileMetadata.getId();

			System.out.println("file: " + name + ", url: " + link + ", id:" + new Outils().extractId(id));
		}
	}
}