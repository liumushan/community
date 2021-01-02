package life.lms.study.community.controller;

import life.lms.study.community.dto.AccessTokenDTO;
import life.lms.study.community.dto.GithubUser;
import life.lms.study.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author shkstart
 * @create 2021-01-02 11:45
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientid;
    @Value("${github.client.secret}")
    private String clientsecret;
    @Value("${github.redirect.uri}")
    private String redirecturi;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirecturi);
        accessTokenDTO.setClient_id(clientid);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret(clientsecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.githubUser(accessToken);
        if (user != null) {
            //登陆成功
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        } else {
            //登陆失败
            return "redirect:/";
        }
    }
}
