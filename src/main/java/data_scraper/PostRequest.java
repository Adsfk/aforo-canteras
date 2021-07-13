package data_scraper;

import kong.unirest.RequestBodyEntity;
import kong.unirest.Unirest;

public class PostRequest {
    private RequestBodyEntity resquest;

    public PostRequest(String url, String moduleBase, String permutation, String body) {
        this.resquest = Unirest.post(url)
                .header("X-GWT-Module-Base", moduleBase)
                .header("X-GWT-Permutation", permutation)
                .header("Content-Type", "text/x-gwt-rpc; charset=UTF-8")
                .body(body);
    }

    public String getResponse(){
        return resquest.asString().getBody();
    }
}
