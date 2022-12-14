package wuhobin.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import wuhobin.pojo.Noder;

import java.util.List;

@Mapper
@Repository
public interface NoderMapper {
    @Select("select * from node_table")
    public List<Noder> findAll();

    @Select("select loc_x from node_table where ID=1")
    public double finda();

    @Insert("Insert into node_table (ID,loc_x,loc_y,loc_z,stress_x,stress_y,stress_z," +
            "strain_x,strain_y,strain_z,deform_x,deform_y,deform_z) values" +
            " (#{ID},#{loc_x},#{loc_y},#{loc_z},#{stress_x},#{stress_y},#{stress_z}," +
            " #{strain_x},#{strain_y},#{strain_z},#{deform_x},#{deform_y},#{deform_z})")
    public void insertNewNode1(@Param("ID") int ID,
                               @Param("loc_x") double loc_x,
                               @Param("loc_y") double loc_y,
                               @Param("loc_z") double loc_z,
                               @Param("stress_x") double stress_x,
                               @Param("stress_y") double stress_y,
                               @Param("stress_z") double stress_z,
                               @Param("strain_x") double strain_x,
                               @Param("strain_y") double strain_y,
                               @Param("strain_z") double strain_z,
                               @Param("deform_x") double deform_x,
                               @Param("deform_y") double deform_y,
                               @Param("deform_z") double deform_z
    );

    @Insert("Insert into node_table (ID, LOC_X, LOC_Y, LOC_Z, ${type}) values" +
            " (#{ID},#{loc_x},#{loc_y},#{loc_z},#{data})")
    public void insertNewNode(@Param("ID") int ID,
                              @Param("loc_x") double loc_x,
                              @Param("loc_y") double loc_y,
                              @Param("loc_z") double loc_z,
                              @Param("data") double data,
                              @Param("type") String type
    );

    @Update("update node_table set ${type} = #{data} where ID = #{ID}")
    public void updateNewNode(@Param("ID") int ID,
                              @Param("data") double data,
                              @Param("type") String type
    );

    @Select("select LOC_X locX from node_table where ID=#{ID}")
    public Noder getNoderByID(int ID);
}
