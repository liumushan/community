package life.lms.study.community.mapper;

import life.lms.study.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author shkstart
 * @create 2021-01-02 19:59
 */
@Mapper
public interface UserMapper {

    @Insert("insert into user (name,ACCOUNT_ID,TOKEN,GMT_CREATE,GMT_MODIFIED) values (#{name} ,#{accountId}  ,#{token} ,#{gmtCreate} ,#{gmtModified} )")
    void insert(User user);
}
