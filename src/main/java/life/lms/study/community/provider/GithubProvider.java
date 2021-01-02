package life.lms.study.community.provider;

import com.alibaba.fastjson.JSON;
import life.lms.study.community.dto.AccessTokenDTO;
import life.lms.study.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author shkstart
 * @create 2021-01-02 11:51
 */
@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));

        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String backstring = response.body().string();
            String token = backstring.split("&")[0].split("=")[1];

            System.out.println(backstring);
            return token;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser githubUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        String st = "https://api.github.com/user?access_token="+accessToken;
        System.out.println(st);
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        }
         catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
