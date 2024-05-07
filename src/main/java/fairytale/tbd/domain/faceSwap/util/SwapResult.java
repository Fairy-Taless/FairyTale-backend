package fairytale.tbd.domain.faceSwap.util;

public class SwapResult {
	String id, url;

	public SwapResult(String id, String url) {
		this.id = id;
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}
}
