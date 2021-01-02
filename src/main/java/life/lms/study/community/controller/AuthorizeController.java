package life.lms.study.community.controller;

import life.lms.study.community.dto.AccessTokenDTO;
import life.lms.study.community.dto.GithubUser;
import life.lms.study.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shkstart
 * @create 2021-01-02 11:45
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setClient_id("c10321603fb60559aee8");
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret("e26653ea53ce683e0ad057341312da171a9a7f9a");
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.githubUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
