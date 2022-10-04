package wuhobin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wuhobin.mapper.NoderMapper;
import wuhobin.pojo.Noder;
import java.util.List;

@SpringBootTest
class SpringbootMybatisApplicationTests {

    @Autowired
    private NoderMapper userMapper;
    @Test
    public void testFindAll() {
        userMapper.insertNewNode(2,1.1,1.1,1.1,1.1,1.1,1.1,1.1,1.1,
                1.1,1.1,1.1,1.1);
        List<Noder> list= userMapper.findAll();
        System.out.println(list);
    }

}
