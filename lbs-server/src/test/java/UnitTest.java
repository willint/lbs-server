import com.lbs.nettyserver.dao.common.BaseDao;
import com.lbs.nettyserver.dao.find.FindVomitRankDAO;
import com.lbs.nettyserver.model.request.find.FindVomitRankRequest;
import com.lbs.nettyserver.model.response.find.FindVomitRankResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/7/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"classpath*:/config/application-context.xml"})
public class UnitTest {
    @Autowired  //自动注入,默认按名称
    private BaseDao dao;

    @Test   //标明是测试方法
    @Transactional   //标明此方法需使用事务
    @Rollback  //标明使用完此方法后事务不回滚,true时为回滚
    public void insert( ) {
//        FindVomitRankRequest rankRequest = new FindVomitRankRequest();
//        rankRequest.setJd(new BigDecimal("38.6518"));
//        rankRequest.setWd(new BigDecimal("104.07642"));
//        rankRequest.setVomitRange(new BigDecimal("2000"));
//        dao.getFindVomitRankInfo(rankRequest);
        try {
//            List<FindVomitRankResponse> re = dao.selectNativeSqlList("select nickName as sex, password as lv from t_user", FindVomitRankResponse.class);
//            re.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
