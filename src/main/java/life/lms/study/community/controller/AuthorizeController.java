package life.lms.study.community.controller;

import life.lms.study.community.dto.AccessTokenDTO;
import life.lms.study.community.dto.GithubUser;
import life.lms.study.community.mapper.UserMapper;
import life.lms.study.community.model.User;
import life.lms.study.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

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

    @Autowired
    private UserMapper userMapper;

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
        GithubUser githubUser = githubProvider.githubUser(accessToken);
        if (githubUser != null) {
            //登陆成功
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            request.getSession().setAttribute("user", githubUser);
            return "redirect:/";
        } else {
            //登陆失败
            return "redirect:/";
        }
    }
}
